package ViewServices;

import CommonModules.AccountStatement;

import java.io.IOException;
import java.text.ParseException;
import java.util.ListIterator;

public class ViewPayablesDrilldownTest {
    public static void main(String[] args) throws ParseException, IOException {
        double totalWithdrawals = 0, totalDeposits = 0;
        ViewPayablesDrilldown viewPayablesDrilldown = new ViewPayablesDrilldown("Entertainment  ");
        ListIterator<AccountStatement> requestIterator = viewPayablesDrilldown.getPayables().listIterator();
        AccountStatement temp;
        System.out.println("Num of entries = " + viewPayablesDrilldown.getNumberOfEntries());
        while (requestIterator.hasNext()) {
            temp = requestIterator.next();
            System.out.println(temp.transactionDate + "  |  ");
            System.out.print(temp.transactionRemarks + "  |  ");
            System.out.print(temp.depositAmount + "  |  ");
            System.out.print(temp.withdrawalAmount + "  |  ");
            totalWithdrawals = totalWithdrawals + temp.withdrawalAmount;
            totalDeposits = totalDeposits + temp.depositAmount;
        }
        System.out.println(" ");
        System.out.println("Deposits  :" + totalDeposits);
        System.out.println("Withdrawals  :" + totalWithdrawals);
    }
}
