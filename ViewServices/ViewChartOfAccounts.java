package ViewServices;

import CommonModules.ChartOfAccounts;
import IngestionEngine.ingestChartOfAcctsExcel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ResourceBundle;

public class ViewChartOfAccounts {
    String fileWithPathname;
    ResourceBundle properties  = ResourceBundle.getBundle("Properties");
    public ViewChartOfAccounts(){
        this.fileWithPathname = properties.getString("chartOfAccounts");
    }

    public ChartOfAccounts[] getChartOfAccounts(){
        ingestChartOfAcctsExcel balanceSheet = new ingestChartOfAcctsExcel(this.fileWithPathname);
        return balanceSheet.transferData();
    }

    public String getHisName(){
        ChartOfAccounts[] chartOfAccountsList = this.getChartOfAccounts();
        int chartOfAccountsIterator;
        for (chartOfAccountsIterator = 0; chartOfAccountsIterator < ChartOfAccounts.numofElements; chartOfAccountsIterator++) {
            if (chartOfAccountsList[chartOfAccountsIterator].subType.equals("HisName")) break;
        }
        return chartOfAccountsList[chartOfAccountsIterator].itemDescription;
    }

    public String getHerName(){
        ChartOfAccounts[] chartOfAccountsList = this.getChartOfAccounts();
        int chartOfAccountsIterator;
        for (chartOfAccountsIterator = 0; chartOfAccountsIterator < ChartOfAccounts.numofElements; chartOfAccountsIterator++) {
            if (chartOfAccountsList[chartOfAccountsIterator].subType.equals("HerName")) break;
        }
        return chartOfAccountsList[chartOfAccountsIterator].itemDescription;
    }
    public ChartOfAccounts getChartElement(int identificationNumber){
        ChartOfAccounts[] chartOfAccountsList = this.getChartOfAccounts();
        int chartOfAccountsIterator;
        for (chartOfAccountsIterator = 0; chartOfAccountsIterator < ChartOfAccounts.numofElements; chartOfAccountsIterator++) {
            if (chartOfAccountsList[chartOfAccountsIterator].identificationNumber == identificationNumber) break;
        }
        return chartOfAccountsList[chartOfAccountsIterator];
    }
    public void updateChartElement(int identificationNumber, String itemDescription, Double cashValue, String isAssetLiquidInd) throws IOException {
        try {
            FileInputStream file = new FileInputStream(fileWithPathname);

            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Cell cell = null;

            //Identify the cell to be updated
            ChartOfAccounts[] chartOfAccountsList = this.getChartOfAccounts();
            int chartOfAccountsIterator;
            for (chartOfAccountsIterator = 0; chartOfAccountsIterator < ChartOfAccounts.numofElements; chartOfAccountsIterator++) {
                if (chartOfAccountsList[chartOfAccountsIterator].identificationNumber == identificationNumber) {

                    break;
                }
            }
            //Update the value of cell
            cell = sheet.getRow(chartOfAccountsList[chartOfAccountsIterator].rowNumber).getCell(2);
            cell.setCellValue(itemDescription);

            cell = sheet.getRow(chartOfAccountsList[chartOfAccountsIterator].rowNumber).getCell(4);
            cell.setCellValue(cashValue);

            cell = sheet.getRow(chartOfAccountsList[chartOfAccountsIterator].rowNumber).getCell(6);
            cell.setCellValue(isAssetLiquidInd);

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
}
