package IngestionEngine;

import CommonModules.RupeeFormatter;
import CommonModules.bsheetElements;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.Iterator;

public class ingestExcel{

    private static String fileWithPathname;
    DecimalFormat ft = new DecimalFormat("Rs ##,##,##0.00");
    RupeeFormatter rf = new RupeeFormatter();
    bsheetElements[] bsheetElementsList = new bsheetElements[100];

    public ingestExcel(String fileWithPathname){
        this.fileWithPathname = fileWithPathname;
    }
    public bsheetElements[] transferData() {
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
                bsheetElementsList[bsIterator] = new bsheetElements();

                while (cellIterator.hasNext())
                {
                    Cell cell = cellIterator.next();

                    //Check the cell type and format accordingly
                    switch (cell.getCellType())
                    {
                        case NUMERIC:
                            //System.out.print(cell.getNumericCellValue() + "t");
                            switch (cell.getColumnIndex()) {
                                case 3:
                                    bsheetElementsList[bsIterator].cashValue = cell.getNumericCellValue();
                                    bsheetElementsList[bsIterator].cashValueFmtd = rf.formattedRupee(ft.format(bsheetElementsList[bsIterator].cashValue));
                                    //System.out.print(bsheetElementsList[bsIterator].cashValue + "t");
                                    break;
                                default:
                                    throw new IllegalStateException("Unexpected Cell Value in the Spreadsheet");
                            }
                            break;
                        case STRING:
                            //System.out.print(cell.getStringCellValue() + "t");
                            switch (cell.getColumnIndex()) {
                                case 0:
                                    bsheetElementsList[bsIterator].typeAssetOrLiability = cell.getStringCellValue();
                                    //System.out.print(bsheetElementsList[bsIterator].typeAssetOrLiability + "t");
                                    break;
                                case 1:
                                    bsheetElementsList[bsIterator].subType = cell.getStringCellValue();
                                    //System.out.print(bsheetElementsList[bsIterator].subType + "t");
                                    break;
                                case 2:
                                    bsheetElementsList[bsIterator].itemDescription = cell.getStringCellValue();
                                    //System.out.print(bsheetElementsList[bsIterator].itemDescription + "t");
                                    break;
                                default:
                                    throw new IllegalStateException("Unexpected Cell Value in the Spreadsheet");
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
        bsheetElements.numofElements = bsIterator;
        return bsheetElementsList;
    }
}
