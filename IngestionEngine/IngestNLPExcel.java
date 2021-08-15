package IngestionEngine;

import CommonModules.NaturalLanguageProcessor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class IngestNLPExcel {

    private static String fileWithPathname;
    NaturalLanguageProcessor naturalLanguageProcessor = new NaturalLanguageProcessor();
    ArrayList<NaturalLanguageProcessor> naturalLanguageProcessors = new ArrayList<NaturalLanguageProcessor>();

    public IngestNLPExcel(String fileWithPathname){
        this.fileWithPathname = fileWithPathname;
    }
    public ArrayList<NaturalLanguageProcessor> transferData() {
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
                //naturalLanguageProcessors[bsIterator] = new NaturalLanguageProcessor();

                while (cellIterator.hasNext())
                {
                    Cell cell = cellIterator.next();

                    //Check the cell type and format accordingly
                    switch (cell.getCellType())
                    {
                        case NUMERIC:
                            //System.out.print(cell.getNumericCellValue() + "t");
                            switch (cell.getColumnIndex()) {
                                case 0:
                                    naturalLanguageProcessor.identificationNumber = (int) cell.getNumericCellValue();
                                    //System.out.print(bsheetElementsList[bsIterator].typeAssetOrLiability + "t");
                                    break;
                                default:
                                    throw new IllegalStateException("Unexpected Cell Value in the Spreadsheet; expected NUMERIC");
                            }
                            break;
                        case STRING:
                            //System.out.print(cell.getStringCellValue() + "t");
                            switch (cell.getColumnIndex()) {
                                case 1:
                                    naturalLanguageProcessor.tokenizedWord = cell.getStringCellValue();
                                    //System.out.print(bsheetElementsList[bsIterator].typeAssetOrLiability + "t");
                                    break;
                                case 2:
                                    naturalLanguageProcessor.entryCategory = cell.getStringCellValue();
                                    //System.out.print(bsheetElementsList[bsIterator].subType + "t");
                                    break;
                                case 3:
                                    naturalLanguageProcessor.discretionarySpendingIndicator = cell.getStringCellValue();
                                    //System.out.print(bsheetElementsList[bsIterator].subType + "t");
                                    break;
                                default:
                                    throw new IllegalStateException("Unexpected Cell Value in the Spreadsheet; expected STRING");
                            }
                            break;
                        case BLANK:
                            //System.out.print(cell.getStringCellValue() + "t");
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + cell.getCellType());
                    }
                }
                naturalLanguageProcessors.add(naturalLanguageProcessor);
                naturalLanguageProcessor = new NaturalLanguageProcessor();
                bsIterator++;
            }
            file.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        NaturalLanguageProcessor.numofElements = bsIterator;
        return naturalLanguageProcessors;
    }
}
