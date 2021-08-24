package ViewServices;

import CommonModules.ChartOfAccounts;

import CommonModules.NaturalLanguageProcessor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.ResourceBundle;

public class ViewChartOfAccountTest {
    public static void main(String[] args) {
        ViewChartOfAccounts viewChartOfAccounts = new ViewChartOfAccounts();
        System.out.println("His Name : " + viewChartOfAccounts.getHisName());
        System.out.println("Her Name : " + viewChartOfAccounts.getHerName());
        ChartOfAccounts[] chartOfAccountsList = viewChartOfAccounts.getChartOfAccounts();

        String accountHolder, accountType;

        ResourceBundle properties  = ResourceBundle.getBundle("Properties");
        String pathName = properties.getString("chartOfAccounts");
        System.out.println("Path Name is : " + pathName);
        accountHolder = "One";
        accountType = "Sal1";
        String fileWithPathname = properties.getString(accountHolder + accountType);
        System.out.println("For the accountHolder " + accountHolder + "and the accountType " + accountType + ", Path Name is : " + fileWithPathname);

        accountHolder = "Two";
        accountType = "Sal1";
        fileWithPathname = properties.getString(accountHolder + accountType);
        System.out.println("For the accountHolder " + accountHolder + "and the accountType " + accountType + ", Path Name is : " + fileWithPathname);

        accountHolder = "One";
        accountType = "Sav1";
        fileWithPathname = properties.getString(accountHolder + accountType);
        System.out.println("For the accountHolder " + accountHolder + "and the accountType " + accountType + ", Path Name is : " + fileWithPathname);

        accountHolder = "Two";
        accountType = "Sav1";
        fileWithPathname = properties.getString(accountHolder + accountType);
        System.out.println("For the accountHolder " + accountHolder + "and the accountType " + accountType + ", Path Name is : " + fileWithPathname);


        ViewNaturalLanguageProcessor NLP = new ViewNaturalLanguageProcessor();
        ArrayList<NaturalLanguageProcessor> NLPlist = NLP.getNlpEntries();

        for (int i = 0; i < NaturalLanguageProcessor.numofElements; i++) {
            System.out.println(NLPlist.get(i).tokenizedWord + " / " + NLPlist.get(i).entryCategory + " / " + NLPlist.get(i).discretionarySpendingIndicator
                    + NLPlist.get(i).identificationNumber + " / " + NLPlist.get(i).lastUsedDate);
        }
        for (int i = ChartOfAccounts.numofElements; i < ChartOfAccounts.numofElements; i++){
            System.out.println(chartOfAccountsList[i].identificationNumber);
            System.out.println(chartOfAccountsList[i].identificationNumber);
            System.out.println(chartOfAccountsList[i].identificationNumber);
            System.out.println(chartOfAccountsList[i].identificationNumber);
        }


        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMsSSSSS");
        String datetime = ft.format(dNow);
        System.out.println(datetime);
    }

}
