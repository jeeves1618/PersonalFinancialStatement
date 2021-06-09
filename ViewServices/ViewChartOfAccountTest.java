package ViewServices;

import CommonModules.ChartOfAccounts;

public class ViewChartOfAccountTest {
    public static void main(String[] args) {
        ViewChartOfAccounts viewChartOfAccounts = new ViewChartOfAccounts();
        ChartOfAccounts[] chartOfAccountsList = viewChartOfAccounts.getChartOfAccounts();
        for (int i = 0; i < ChartOfAccounts.numofElements; i++){
            System.out.println(chartOfAccountsList[i].identificationNumber);
            System.out.println(chartOfAccountsList[i].identificationNumber);
            System.out.println(chartOfAccountsList[i].identificationNumber);
            System.out.println(chartOfAccountsList[i].identificationNumber);
        }
    }

}
