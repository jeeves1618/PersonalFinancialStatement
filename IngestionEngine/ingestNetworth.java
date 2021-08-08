package IngestionEngine;

import CommonModules.NetworthHistory;
import CommonModules.RupeeFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.Iterator;

public class ingestNetworth {

    private static String fileWithPathname;
    DecimalFormat ft = new DecimalFormat("Rs ##,##,##0.00");
    RupeeFormatter rf = new RupeeFormatter();
    NetworthHistory[] NetworthHistoryList = new NetworthHistory[200];

    public ingestNetworth(String fileWithPathname){
        this.fileWithPathname = fileWithPathname;
    }
    public NetworthHistory[] transferData() {
        int bsIterator = 0;
        try {
            FileInputStream file=new FileInputStream(new File(fileWithPathname));
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(1);

            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext())
            {
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
                //Instantiate an Object for each indivial member of Array
                NetworthHistoryList[bsIterator] = new NetworthHistory();

                while (cellIterator.hasNext())
                {
                    Cell cell = cellIterator.next();

                    //Check the cell type and format accordingly
                    switch (cell.getCellType())
                    {
                        case NUMERIC:
                            //System.out.print(cell.getNumericCellValue() + "t");
                            switch (cell.getColumnIndex()) {
                                case 1:
                                    NetworthHistoryList[bsIterator].twoAmount = cell.getNumericCellValue();
                                    NetworthHistoryList[bsIterator].twoAmountFmtd = rf.formattedRupee(ft.format(NetworthHistoryList[bsIterator].twoAmount));
                                    break;
                                case 2:
                                    NetworthHistoryList[bsIterator].oneAmount = cell.getNumericCellValue();
                                    NetworthHistoryList[bsIterator].oneAmountFmtd = rf.formattedRupee(ft.format(NetworthHistoryList[bsIterator].oneAmount));
                                    break;
                                case 3:
                                    NetworthHistoryList[bsIterator].totalAmount = cell.getNumericCellValue();
                                    NetworthHistoryList[bsIterator].totalAmountFmtd = rf.formattedRupee(ft.format(NetworthHistoryList[bsIterator].totalAmount));
                                    break;
                                default:
                                    throw new IllegalStateException("Unexpected Cell Value in the Spreadsheet " + bsIterator + " and " + cell.getColumnIndex());
                            }
                            break;
                        case STRING:
                            //System.out.print(cell.getStringCellValue() + "t");
                            switch (cell.getColumnIndex()) {
                                case 0:
                                    NetworthHistoryList[bsIterator].valueDate = cell.getStringCellValue();
                                    //System.out.print(bsheetElementsList[bsIterator].typeAssetOrLiability + "t");
                                    break;
                                default:
                                    throw new IllegalStateException("Unexpected Cell Value in the Spreadsheet " + bsIterator);
                            }
                            break;
                        case BLANK:
                            //System.out.print(cell.getStringCellValue() + "t");
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + cell.getCellType());
                    }
                }
                bsIterator++;
            }
            file.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        NetworthHistory.numofElements = bsIterator;
        return NetworthHistoryList;
    }
}
