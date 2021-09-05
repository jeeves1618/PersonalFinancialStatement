package ViewServices;

import CommonModules.NetworthHistory;

import java.util.ResourceBundle;

public class ViewNetworthHistoryTest {
    public static void main(String[] args) {
        ViewNetworthHistory viewNetworthHistory = new ViewNetworthHistory();
        ResourceBundle properties  = ResourceBundle.getBundle("Properties");
        String currencyFormat = properties.getString("currencyFormat");
        String currencyPrefix = currencyFormat.substring(0, currencyFormat.indexOf(" ")).concat(" ");
        System.out.println("currencyFormat : " + currencyFormat + "currencyPrefix : " + currencyPrefix);
        NetworthHistory[] NetworthHistoryList = viewNetworthHistory.getNetworthHistory();
        for (int i = 0; i < NetworthHistory.numofElements; i++){
            System.out.println(NetworthHistoryList[i].valueDate);
            System.out.println(NetworthHistoryList[i].oneAmountFmtd);
            System.out.println(NetworthHistoryList[i].twoAmountFmtd);
            System.out.println(NetworthHistoryList[i].totalAmountFmtd);
        }
    }

}
