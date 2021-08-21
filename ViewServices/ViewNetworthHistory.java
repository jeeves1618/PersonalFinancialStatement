package ViewServices;

import CommonModules.NetworthHistory;
import IngestionEngine.ingestNetworth;

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
}
