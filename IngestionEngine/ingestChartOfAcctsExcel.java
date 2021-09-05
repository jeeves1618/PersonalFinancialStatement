package IngestionEngine;

import CommonModules.ChartOfAccounts;
import CommonModules.RupeeFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.ResourceBundle;

public class ingestChartOfAcctsExcel {

    private static String fileWithPathname;
    ResourceBundle properties  = ResourceBundle.getBundle("Properties");
    String currencyFormat = properties.getString("currencyFormat");
    DecimalFormat ft = new DecimalFormat(currencyFormat);
    RupeeFormatter rf = new RupeeFormatter();
    ChartOfAccounts[] chartOfAccountsList = new ChartOfAccounts[100];

    public ingestChartOfAcctsExcel(String fileWithPathname){
        this.fileWithPathname = fileWithPathname;
    }
    public ChartOfAccounts[] transferData() {
        int bsIterator = 0;
        try {
            FileInputStream file=new FileInputStream(new File(fileWithPathname));
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext())
            {
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
                //Instantiate an Object for each indivial member of Array
                chartOfAccountsList[bsIterator] = new ChartOfAccounts();

                while (cellIterator.hasNext())
                {
                    Cell cell = cellIterator.next();

                    //Check the cell type and format accordingly
                    chartOfAccountsList[bsIterator].rowNumber = bsIterator;
                    switch (cell.getCellType())
                    {
                        case NUMERIC:
                            //System.out.print(cell.getNumericCellValue() + "t");
                            switch (cell.getColumnIndex()) {
                                case 4:
                                    chartOfAccountsList[bsIterator].cashValue = cell.getNumericCellValue();
                                    chartOfAccountsList[bsIterator].cashValueFmtd = rf.formattedRupee(ft.format(chartOfAccountsList[bsIterator].cashValue));
                                    //System.out.print(bsheetElementsList[bsIterator].cashValue + "t");
                                    break;
                                case 0:
                                    chartOfAccountsList[bsIterator].identificationNumber = (int) cell.getNumericCellValue();
                                    //System.out.print(bsheetElementsList[bsIterator].typeAssetOrLiability + "t");
                                    break;
                                default:
                                    throw new IllegalStateException("Unexpected Cell Value in the Spreadsheet " + bsIterator);
                            }
                            break;
                        case STRING:
                            //System.out.print(cell.getStringCellValue() + "t");
                            switch (cell.getColumnIndex()) {
                                case 1:
                                    chartOfAccountsList[bsIterator].typeAssetOrLiability = cell.getStringCellValue();
                                    //System.out.print(bsheetElementsList[bsIterator].typeAssetOrLiability + "t");
                                    break;
                                case 2:
                                    chartOfAccountsList[bsIterator].subType = cell.getStringCellValue();
                                    //System.out.print(bsheetElementsList[bsIterator].subType + "t");
                                    break;
                                case 3:
                                    chartOfAccountsList[bsIterator].itemDescription = cell.getStringCellValue();
                                    //System.out.print(bsheetElementsList[bsIterator].itemDescription + "t");
                                    break;
                                case 5:
                                    chartOfAccountsList[bsIterator].financialStatement = cell.getStringCellValue();
                                    //System.out.print(bsheetElementsList[bsIterator].itemDescription + "t");
                                    break;
                                case 6:
                                    chartOfAccountsList[bsIterator].isAssetLiquidInd = cell.getStringCellValue();
                                    //System.out.print(bsheetElementsList[bsIterator].itemDescription + "t");
                                    break;
                                default:
                                    throw new IllegalStateException("Unexpected Cell Value in the Spreadsheet " + cell.getColumnIndex() + " " + bsIterator);
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
        ChartOfAccounts.numofElements = bsIterator;
        return chartOfAccountsList;
    }
}
