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
}
