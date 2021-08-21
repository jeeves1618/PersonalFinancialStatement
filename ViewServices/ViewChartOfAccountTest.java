package ViewServices;

import CommonModules.ChartOfAccounts;
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

        for (int i = ChartOfAccounts.numofElements; i < ChartOfAccounts.numofElements; i++){
            System.out.println(chartOfAccountsList[i].identificationNumber);
            System.out.println(chartOfAccountsList[i].identificationNumber);
            System.out.println(chartOfAccountsList[i].identificationNumber);
            System.out.println(chartOfAccountsList[i].identificationNumber);
        }
    }

}
