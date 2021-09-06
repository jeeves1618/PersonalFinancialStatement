package admin;

import CommonModules.AccountStatement;
import CommonModules.NaturalLanguageProcessor;
import IngestionEngine.IngestNLPExcel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class NLPTokenManager {
    private static LocalDate transactionDateHigh = LocalDate.parse("0001-01-01");
    private LocalDate transactionDateLow = LocalDate.parse("9999-12-31");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    ResourceBundle properties = ResourceBundle.getBundle("Properties");
    private String datetime;
    Date dateNow;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss z");

    public String NLPTokenRefresh(String accountHolder, String accountType) throws IOException {
        ArrayList<AccountStatement> AccountStatementList;

        String fileWithPathname = properties.getString(accountHolder + accountType);
        System.out.println("File being accessed: " + fileWithPathname);

        IngestionEngine.IngestStatementExcel balanceSheet = new IngestionEngine.IngestStatementExcel(fileWithPathname);
        //IngestH2db balanceSheet = new IngestH2db();

        AccountStatementList = balanceSheet.transferData();

        //Read the NLP Tokens from the spreadsheet
        String tokenFileWithPathname = properties.getString("NLPtokenizer");

        IngestNLPExcel tokenizer = new IngestNLPExcel(tokenFileWithPathname);
        ArrayList<NaturalLanguageProcessor> tokenDescriptionMapper;
        tokenDescriptionMapper = tokenizer.transferData();
        for (int i = 0; i < AccountStatement.numofElements; i++) {

            LocalDate transactionDate = LocalDate.parse(AccountStatementList.get(i).transactionDate, formatter);
            if (transactionDateHigh.isBefore(transactionDate))
                transactionDateHigh = transactionDate;
            if (transactionDateLow.isAfter(transactionDate))
                transactionDateLow = transactionDate;
        }

        transactionDateLow = transactionDateHigh.minusYears(1l).plusDays(1l);
        for (int i = 0; i < AccountStatement.numofElements; i++) {
            String transactionRemarks = AccountStatementList.get(i).transactionRemarks;

            LocalDate transactionDate = LocalDate.parse(AccountStatementList.get(i).transactionDate, formatter);

            for (int j = 0; j < NaturalLanguageProcessor.numofElements; j++) {
                if (transactionRemarks.toUpperCase().contains(tokenDescriptionMapper.get(j).tokenizedWord)) {
                    if (transactionDate.isBefore(transactionDateHigh.minusYears(1l).plusDays(1l))) {
                        ;
                    } else {
                        updateNLPLastUsedDate(tokenDescriptionMapper.get(j).rowNumber, tokenDescriptionMapper.get(j).columnNumber);
                    }
                    break;
                }
            }
        }
        return "The NLP tokens are refreshed with the last updated time and date. Please delete the unused tokens to improve performance.";
    }
    public void updateNLPLastUsedDate(int row, int col) throws IOException {
        try {
            String tokenFileWithPathname = properties.getString("NLPtokenizer");
            FileInputStream file = new FileInputStream(tokenFileWithPathname);

            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Cell cell = null;

            //Update the value of cell
            dateNow = new Date();
            datetime = dateFormat.format(dateNow);
            System.out.println(datetime);
            cell = sheet.getRow(row).getCell(col);
            cell.setCellValue(datetime);

            file.close();

            FileOutputStream outFile =new FileOutputStream(new File(tokenFileWithPathname));
            workbook.write(outFile);
            outFile.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
