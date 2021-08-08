package ViewServices;

import CommonModules.NetworthHistory;

public class ViewNetworthHistoryTest {
    public static void main(String[] args) {
        ViewNetworthHistory viewNetworthHistory = new ViewNetworthHistory();
        NetworthHistory[] NetworthHistoryList = viewNetworthHistory.getNetworthHistory();
        for (int i = 0; i < NetworthHistory.numofElements; i++){
            System.out.println(NetworthHistoryList[i].valueDate);
            System.out.println(NetworthHistoryList[i].oneAmountFmtd);
            System.out.println(NetworthHistoryList[i].twoAmountFmtd);
            System.out.println(NetworthHistoryList[i].totalAmountFmtd);
        }
    }

}
