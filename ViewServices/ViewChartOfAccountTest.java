package ViewServices;

import CommonModules.ChartOfAccounts;

public class ViewChartOfAccountTest {
    public static void main(String[] args) {
        ViewChartOfAccounts viewChartOfAccounts = new ViewChartOfAccounts();
        System.out.println("His Name : " + viewChartOfAccounts.getHisName());
        System.out.println("Her Name : " + viewChartOfAccounts.getHerName());
        ChartOfAccounts[] chartOfAccountsList = viewChartOfAccounts.getChartOfAccounts();

        for (int i = ChartOfAccounts.numofElements; i < ChartOfAccounts.numofElements; i++){
            System.out.println(chartOfAccountsList[i].identificationNumber);
            System.out.println(chartOfAccountsList[i].identificationNumber);
            System.out.println(chartOfAccountsList[i].identificationNumber);
            System.out.println(chartOfAccountsList[i].identificationNumber);
        }
    }

}
