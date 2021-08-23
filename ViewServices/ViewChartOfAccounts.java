package ViewServices;

import CommonModules.ChartOfAccounts;
import IngestionEngine.ingestChartOfAcctsExcel;

import java.util.ResourceBundle;

public class ViewChartOfAccounts {
    String fileWithPathname;
    public ViewChartOfAccounts(){
        ResourceBundle properties  = ResourceBundle.getBundle("Properties");
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
}
