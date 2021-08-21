package ViewServices;

import CommonModules.ChartOfAccounts;
import IngestionEngine.ingestChartOfAcctsExcel;

public class ViewChartOfAccounts {
    String fileWithPathname;
    public ViewChartOfAccounts(){
        this.fileWithPathname = "C:\\dev\\Data\\ChartOfAccounts.xlsx";
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
}
