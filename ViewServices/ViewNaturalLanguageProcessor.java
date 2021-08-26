package ViewServices;

import CommonModules.NaturalLanguageProcessor;
import IngestionEngine.IngestNLPExcel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewNaturalLanguageProcessor {
    String fileWithPathname;
    boolean tokenExists = false;
    public ViewNaturalLanguageProcessor(){
        ResourceBundle properties  = ResourceBundle.getBundle("Properties");
        this.fileWithPathname = properties.getString("NLPtokenizer");
    }

    public ArrayList<NaturalLanguageProcessor> getNlpEntries(){
        IngestNLPExcel nlpEntries = new IngestNLPExcel(this.fileWithPathname);
        return nlpEntries.transferData();
    }

    public NaturalLanguageProcessor getTokenEntry(int tokenNumber){
        ArrayList<NaturalLanguageProcessor> NLPTokensList = this.getNlpEntries();
        int NLPIterator;
        for (NLPIterator = 0; NLPIterator < NaturalLanguageProcessor.numofElements; NLPIterator++) {
            if (NLPTokensList.get(NLPIterator).identificationNumber == tokenNumber) break;
        }
        if (NLPIterator >= NaturalLanguageProcessor.numofElements) {
            return NLPTokensList.get(NLPIterator - 1);
        } else {
            return NLPTokensList.get(NLPIterator);
        }
    }

    public void updateTokenEntry(int identificationNumber, String tokenWord, String segmentCategory, String isDiscretionaryInd) throws IOException {
        try {
            FileInputStream file = new FileInputStream(fileWithPathname);

            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Cell cell = null;

            //Identify the cell to be updated
            ArrayList<NaturalLanguageProcessor> NLPTokensList = this.getNlpEntries();
            int NaturalLanguageProcessorIterator;
            for (NaturalLanguageProcessorIterator = 0; NaturalLanguageProcessorIterator < NaturalLanguageProcessor.numofElements; NaturalLanguageProcessorIterator++) {
                if (NLPTokensList.get(NaturalLanguageProcessorIterator).identificationNumber == identificationNumber) {
                    tokenExists = true;
                    break;
                }
            }
            //get the last row of the sheet
            int lastRow = sheet.getLastRowNum();
            tokenWord = tokenWord.toUpperCase();
            //Add or Update the value of cell based on token existence check
            System.out.println("tokenExists " + tokenExists);
            if(!tokenExists) {
                Row row = sheet.createRow(lastRow + 1);
                row.createCell(0).setCellValue(identificationNumber);
                row.createCell(1).setCellValue(tokenWord);
                row.createCell(2).setCellValue(segmentCategory);
                row.createCell(3).setCellValue(isDiscretionaryInd);
                row.createCell(4).setCellValue("Not Used");
                System.out.println("Creating a row");

            } else {
                cell = sheet.getRow(NLPTokensList.get(NaturalLanguageProcessorIterator).rowNumber).getCell(1);
                cell.setCellValue(tokenWord);
                System.out.println("tokenWord " + NLPTokensList.get(NaturalLanguageProcessorIterator).tokenizedWord);

                cell = sheet.getRow(NLPTokensList.get(NaturalLanguageProcessorIterator).rowNumber).getCell(2);
                cell.setCellValue(segmentCategory);

                cell = sheet.getRow(NLPTokensList.get(NaturalLanguageProcessorIterator).rowNumber).getCell(3);
                cell.setCellValue(isDiscretionaryInd);
            }

            file.close();

            FileOutputStream outFile =new FileOutputStream(new File(fileWithPathname));
            workbook.write(outFile);
            outFile.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteTokenEntry(int identificationNumber) throws IOException {

        //Identify the cell to be updated
        ArrayList<NaturalLanguageProcessor> NLPTokensList = this.getNlpEntries();
        int NaturalLanguageProcessorIterator;
        for (NaturalLanguageProcessorIterator = 0; NaturalLanguageProcessorIterator < NaturalLanguageProcessor.numofElements; NaturalLanguageProcessorIterator++) {
            if (NLPTokensList.get(NaturalLanguageProcessorIterator).identificationNumber == identificationNumber) {
                tokenExists = true;
                break;
            }
        }
        int rowNumber = NaturalLanguageProcessorIterator;
        FileInputStream file = new FileInputStream(fileWithPathname);
        System.out.println("Deleting row " + rowNumber + " from " + fileWithPathname);
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Row rowToBeDeleted =sheet.getRow(rowNumber);
        System.out.println("Deleting row " + rowNumber + " from " + fileWithPathname + " the entry, " + rowToBeDeleted.getCell(1));
        sheet.removeRow(rowToBeDeleted);
        //* Do not shift up if the row deleted is the last row.
        if (NaturalLanguageProcessorIterator < sheet.getLastRowNum()) {
            Row rowToBeShifted =sheet.getRow(NaturalLanguageProcessorIterator + 1);
            System.out.println("Shifting up the row " + (NaturalLanguageProcessorIterator + 1) + "sheet.getLastRowNum() " + sheet.getLastRowNum());
            sheet.shiftRows(NaturalLanguageProcessorIterator + 1, sheet.getLastRowNum(),-1);
        }

        file.close();

        FileOutputStream outFile =new FileOutputStream(new File(fileWithPathname));
        workbook.write(outFile);
        outFile.close();
    }
}

