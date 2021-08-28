package ViewServices;

import CommonModules.NetworthHistory;
import IngestionEngine.ingestNetworth;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ResourceBundle;

public class ViewNetworthHistory {
    String fileWithPathname;
    public ViewNetworthHistory(){
        ResourceBundle properties  = ResourceBundle.getBundle("Properties");
        this.fileWithPathname = properties.getString("chartOfAccounts");
    }

    public NetworthHistory[] getNetworthHistory(){
        ingestNetworth balanceSheet = new ingestNetworth(this.fileWithPathname);
        return balanceSheet.transferData();
    }

    public NetworthHistory getHistoryEntry(int serialNumber){
        NetworthHistory[] NetWorthList = this.getNetworthHistory();
        int NWIterator;
        for (NWIterator = 0; NWIterator < NetworthHistory.numofElements; NWIterator++) {
            if (NetWorthList[NWIterator].serialNumber == serialNumber) break;
        }
        if (NWIterator >= NetworthHistory.numofElements) {
            return NetWorthList[NWIterator - 1];
        } else {
            return NetWorthList[NWIterator];
        }
    }

    public void updateHistoryEntry(int serialNumber, double twoAmount, double oneAmount, String dateTime) throws IOException {
        try {
            FileInputStream file = new FileInputStream(fileWithPathname);

            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(1);
            Cell cell = null;

            //Identify the cell to be updated
            NetworthHistory[] NetWorthList = this.getNetworthHistory();
            int NWIterator;
            boolean historyExists = false;
            System.out.println("NetworthHistory.numofElements : " + NetworthHistory.numofElements);
            for (NWIterator = 0; NWIterator < NetworthHistory.numofElements; NWIterator++) {
                if (NetWorthList[NWIterator].serialNumber == serialNumber) {
                    System.out.println(NetWorthList[NWIterator].serialNumber);
                    historyExists = true;
                    break;
                }
            }
            //get the last row of the sheet
            int lastRow = sheet.getLastRowNum();

            //Add or Update the value of cell based on token existence check
            System.out.println("historyExists " + historyExists);
            if(!historyExists) {
                Row row = sheet.createRow(lastRow + 1);
                row.createCell(0).setCellValue(dateTime);
                row.createCell(1).setCellValue(twoAmount);
                row.createCell(2).setCellValue(oneAmount);
                row.createCell(3).setCellValue(twoAmount+oneAmount);
                row.createCell(4).setCellValue(serialNumber);
                System.out.println("Creating a row");

            } else {
                System.out.println(NWIterator + " & " + NetWorthList[NWIterator].rowNumber);

                cell = sheet.getRow(NetWorthList[NWIterator].rowNumber).getCell(1);
                cell.setCellValue(twoAmount);

                cell = sheet.getRow(NetWorthList[NWIterator].rowNumber).getCell(2);
                cell.setCellValue(oneAmount);

                cell = sheet.getRow(NetWorthList[NWIterator].rowNumber).getCell(3);
                cell.setCellValue(twoAmount+oneAmount);
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

    public void deleteHistoryEntry(int serialNumber) throws IOException {

        //Identify the cell to be Deleted
        NetworthHistory[] NetWorthList = this.getNetworthHistory();
        int NWIterator;
        boolean historyExists = false;
        for (NWIterator = 0; NWIterator < NetworthHistory.numofElements; NWIterator++) {
            if (NetWorthList[NWIterator].serialNumber == serialNumber) {
                historyExists = true;
                break;
            }
        }
        int rowNumber = NWIterator;
        FileInputStream file = new FileInputStream(fileWithPathname);
        System.out.println("Deleting row " + rowNumber + " from " + fileWithPathname);
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(1);
        Row rowToBeDeleted =sheet.getRow(rowNumber);
        System.out.println("Deleting row " + rowNumber + " from " + fileWithPathname + " the entry, " + rowToBeDeleted.getCell(1));
        sheet.removeRow(rowToBeDeleted);
        //* Do not shift up if the row deleted is the last row.
        if (NWIterator < sheet.getLastRowNum()) {
            Row rowToBeShifted =sheet.getRow(NWIterator + 1);
            System.out.println("Shifting up the row " + (NWIterator + 1) + "sheet.getLastRowNum() " + sheet.getLastRowNum());
            sheet.shiftRows(NWIterator + 1, sheet.getLastRowNum(),-1);
        }

        file.close();

        FileOutputStream outFile =new FileOutputStream(new File(fileWithPathname));
        workbook.write(outFile);
        outFile.close();
    }
}
