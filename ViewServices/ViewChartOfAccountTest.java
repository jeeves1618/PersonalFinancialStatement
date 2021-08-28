package ViewServices;

import CommonModules.ChartOfAccounts;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class ViewChartOfAccountTest {
    public static void main(String[] args) throws IOException {
        ViewChartOfAccounts viewChartOfAccounts = new ViewChartOfAccounts();
        System.out.println("His Name : " + viewChartOfAccounts.getHisName());
        System.out.println("Her Name : " + viewChartOfAccounts.getHerName());
        ChartOfAccounts[] chartOfAccountsList = viewChartOfAccounts.getChartOfAccounts();
        viewChartOfAccounts.updateChartElement(9999998, "John Smith",0.0, "N");
        String accountHolder, accountType;

        ResourceBundle properties = ResourceBundle.getBundle("Properties");
        String pathName = properties.getString("chartOfAccounts");
        //System.out.println("Path Name is : " + pathName);
        accountHolder = "One";
        accountType = "Sal1";
        String fileWithPathname = properties.getString(accountHolder + accountType);
        //System.out.println("For the accountHolder " + accountHolder + "and the accountType " + accountType + ", Path Name is : " + fileWithPathname);

        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
        String datetime = ft.format(dNow);
        System.out.println(datetime);
        /*
        //yyMMddhhmmssMsSSSSS
        ViewNetworthHistory NLP = new ViewNetworthHistory();
        //NLP.updateTokenEntry(2010169,"ACH/ICICI","Family","Y");
        NetworthHistory[] NWList = NLP.getNetworthHistory();

        for (int chartOfAccountsIterator = 0; chartOfAccountsIterator < NetworthHistory.numofElements; chartOfAccountsIterator++) {
            System.out.println(NWList[chartOfAccountsIterator].rowNumber + " " + NWList[chartOfAccountsIterator].columnNumber + " " + NWList[chartOfAccountsIterator].serialNumber + " " + NWList[chartOfAccountsIterator].valueDate + " " + NWList[chartOfAccountsIterator].totalAmountFmtd);
        }
        NetworthHistory N = NLP.getHistoryEntry(2);
        NLP.updateHistoryEntry(131,6900000,7900000,"27/08/2021");
        //System.out.println("Token is " + N.tokenizedWord + " and Token ID is " + N.identificationNumber );
        //NLP.deleteHistoryEntry(132);
        System.out.println("Her Amount " + N.oneAmountFmtd + " and Total Amount is " + N.totalAmountFmtd);*/
        }
    }

