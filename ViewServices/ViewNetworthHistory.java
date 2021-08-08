package ViewServices;

import CommonModules.NetworthHistory;
import IngestionEngine.ingestNetworth;

public class ViewNetworthHistory {
    String fileWithPathname;
    public ViewNetworthHistory(){
        this.fileWithPathname = "C:\\dev\\Data\\ChartOfAccounts.xlsx";
    }

    public NetworthHistory[] getNetworthHistory(){
        ingestNetworth balanceSheet = new ingestNetworth(this.fileWithPathname);
        return balanceSheet.transferData();
    }
}
