package ViewServices;

import CommonModules.ChartOfAccounts;
import CommonModules.NaturalLanguageProcessor;

import java.io.IOException;
import java.util.ResourceBundle;

public class ViewChartOfAccountTest {
    public static void main(String[] args) throws IOException {
        ViewChartOfAccounts viewChartOfAccounts = new ViewChartOfAccounts();
        System.out.println("His Name : " + viewChartOfAccounts.getHisName());
        System.out.println("Her Name : " + viewChartOfAccounts.getHerName());
        ChartOfAccounts[] chartOfAccountsList = viewChartOfAccounts.getChartOfAccounts();

        String accountHolder, accountType;

        ResourceBundle properties = ResourceBundle.getBundle("Properties");
        String pathName = properties.getString("chartOfAccounts");
        System.out.println("Path Name is : " + pathName);
        accountHolder = "One";
        accountType = "Sal1";
        String fileWithPathname = properties.getString(accountHolder + accountType);
        System.out.println("For the accountHolder " + accountHolder + "and the accountType " + accountType + ", Path Name is : " + fileWithPathname);

        ViewNaturalLanguageProcessor NLP = new ViewNaturalLanguageProcessor();
        //NLP.updateTokenEntry(2010169,"ACH/ICICI","Family","Y");
        /*for (int chartOfAccountsIterator = 0; chartOfAccountsIterator < ChartOfAccounts.numofElements; chartOfAccountsIterator++) {
            System.out.println(chartOfAccountsList[chartOfAccountsIterator].identificationNumber);*/
        //NLP.deleteTokenEntry(2010174);
        NaturalLanguageProcessor N = NLP.getTokenEntry(9999999);
        System.out.println("Token is " + N.tokenizedWord + " and Token ID is " + N.identificationNumber );
        N = NLP.getTokenEntry(2010174);
        System.out.println("Token is " + N.tokenizedWord + " and Token ID is " + N.identificationNumber );
        }
    }

