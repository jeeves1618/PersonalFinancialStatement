package IngestionEngine;

import CommonModules.AccountStatement;
import CommonModules.RupeeFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class IngestStatementExcel{

    private static String fileWithPathname;
    DecimalFormat ft = new DecimalFormat("Rs ##,##,##0.00");
    RupeeFormatter rf = new RupeeFormatter();
    AccountStatement AccountStatementEntry = new AccountStatement();
    ArrayList<AccountStatement> AccountStatementList = new ArrayList<AccountStatement>();

    public IngestStatementExcel(String fileWithPathname){
        this.fileWithPathname = fileWithPathname;
    }
    public ArrayList<AccountStatement> transferData() {
        int bsIterator = 0;
        try {
            FileInputStream file=new FileInputStream(new File(fileWithPathname));
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();

            //Read through the Header Row
            Row headerRow = rowIterator.next();

            while (rowIterator.hasNext())
            {
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
                //Instantiate an Object for each individual member of Array
                //AccountStatementEntry = new AccountStatement();

                while (cellIterator.hasNext())
                {
                    Cell cell = cellIterator.next();

                    //Check the cell type and format accordingly
                    switch (cell.getCellType())
                    {
                        case NUMERIC:
                            //System.out.print(cell.getNumericCellValue() + "t");
                            switch (cell.getColumnIndex()) {
                                case 5:
                                    AccountStatementEntry.withdrawalAmount = cell.getNumericCellValue();
                                    AccountStatementEntry.withdrawalAmountFmtd = rf.formattedRupee(ft.format(AccountStatementEntry.withdrawalAmount));
                                    //System.out.print(AccountStatementEntry.cashValue + "t");
                                    break;
                                case 6:
                                    AccountStatementEntry.depositAmount = cell.getNumericCellValue();
                                    AccountStatementEntry.depositAmountFmtd = rf.formattedRupee(ft.format(AccountStatementEntry.depositAmount));
                                    //System.out.print(AccountStatementEntry.cashValue + "t");
                                    break;
                                case 7:
                                    AccountStatementEntry.balanceAmount = cell.getNumericCellValue();
                                    AccountStatementEntry.balanceAmountFmtd = rf.formattedRupee(ft.format(AccountStatementEntry.balanceAmount));
                                    //System.out.print(AccountStatementEntry.cashValue + "t");
                                    break;
                                default:
                                    throw new IllegalStateException("Unexpected NUMERIC Cell Value in the Spreadsheet :" + bsIterator + " and " + cell.getColumnIndex());
                            }
                            break;
                        case STRING:
                            //System.out.print(cell.getStringCellValue() + "t");
                            switch (cell.getColumnIndex()) {
                                case 0:
                                    AccountStatementEntry.serialNumber = cell.getStringCellValue();
                                    //System.out.print(AccountStatementEntry.typeAssetOrLiability + "t");
                                    break;
                                case 1:
                                    AccountStatementEntry.valueDate = cell.getStringCellValue();
                                    //System.out.print(AccountStatementEntry.subType + "t");
                                    break;
                                case 2:
                                    AccountStatementEntry.transactionDate = cell.getStringCellValue();
                                    //System.out.print(AccountStatementEntry.itemDescription + "t");
                                    break;
                                case 3:
                                    AccountStatementEntry.checkNumber = cell.getStringCellValue();
                                    //System.out.print(AccountStatementEntry.itemDescription + "t");
                                    break;
                                case 4:
                                    AccountStatementEntry.transactionRemarks = cell.getStringCellValue();
                                    //System.out.print(AccountStatementEntry.itemDescription + "t");
                                    break;
                                default:
                                    throw new IllegalStateException("Unexpected STRING Cell Value in the Spreadsheet :" + bsIterator + " and " + cell.getColumnIndex());
                            }
                            break;
                        case BLANK:
                            //System.out.print(cell.getStringCellValue() + "t");
                            break;
                        default:
                            throw new IllegalStateException("Unexpected Cell Value in the Spreadsheet :" + bsIterator + " and " + cell.getColumnIndex());
                    }
                }
                AccountStatementList.add(AccountStatementEntry);
                AccountStatementEntry = new AccountStatement();
                bsIterator++;
            }
            file.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        AccountStatement.numofElements = bsIterator;

        return AccountStatementList;
    }
}