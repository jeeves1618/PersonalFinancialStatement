package IncomeStatement;

import CommonModules.AccountStatement;
import CommonModules.RupeeFormatter;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ListIterator;

public class ExpenseCalculatorTest {
    public static void main(String[] Args) throws ParseException {
        DecimalFormat ft = new DecimalFormat("Rs ##,##,##0.00");
        RupeeFormatter rf = new RupeeFormatter();

        ExpenseCalculator e1 = new ExpenseCalculator("Two", "Sal1");
        ExpenseCalculator e2 = new ExpenseCalculator("One", "Sal1");
        int numOfMonths = 1;


        System.out.println("Ben Income      : " + rf.formattedRupee(ft.format(e1.getTotalIncome())));
        System.out.println("Bun Income      : " + rf.formattedRupee(ft.format(e2.getTotalIncome())));
        System.out.println("Bun Expenses    : " + e1.getTotalExpensesFmtd());
        System.out.println("Ben Expenses    : " + e2.getTotalExpensesFmtd());
        System.out.println("Ben Savings     : " + rf.formattedRupee(ft.format(e1.getTotalSavings())));
        System.out.println("Bun Savings     : " + rf.formattedRupee(ft.format(e2.getTotalSavings())));

        System.out.println(" ");


        System.out.println("Total Monthly Income     : " + rf.formattedRupee(ft.format(((e1.getTotalIncome() + e2.getTotalIncome()) / numOfMonths))));
        System.out.println("Total Monthly Expenses   : " + rf.formattedRupee(ft.format(((e1.getTotalExpenses() + e2.getTotalExpenses()) / numOfMonths))));
        System.out.println("Total Monthly Savings    : " + rf.formattedRupee(ft.format(((e1.getTotalSavings() + e2.getTotalSavings()) / numOfMonths))));
        System.out.println("Total Unknown Entries    : " + rf.formattedRupee(ft.format(((e1.getUnknown() + e2.getUnknown())))));
        System.out.println(" ");
        System.out.println("-------- Monthly Split -----------");

        System.out.println("apartmentMaintenance : " + rf.formattedRupee(ft.format(((e1.getApartmentMaintenance() + e2.getApartmentMaintenance()) / numOfMonths))));
        System.out.println("Brokerage            : " + rf.formattedRupee(ft.format(((e1.getBrokerageMaintenance() + e2.getBrokerageMaintenance()) / numOfMonths))));
        System.out.println("Cash Withdrawals     : " + rf.formattedRupee(ft.format(((e1.getCashWithdrawals() + e2.getCashWithdrawals()) / numOfMonths))));
        System.out.println("Credit Card Bill     : " + rf.formattedRupee(ft.format(((e1.getCreditCardBill() + e2.getCreditCardBill()) / numOfMonths))));
        System.out.println("Electricity Bill     : " + rf.formattedRupee(ft.format(((e1.getElectricityBill() + e2.getElectricityBill()) / numOfMonths))));
        System.out.println("Home Insurance       : " + rf.formattedRupee(ft.format(((e1.getHomeInsurance() + e2.getHomeInsurance()) / numOfMonths))));
        System.out.println("Grocery Expenses     : " + rf.formattedRupee(ft.format(((e1.getGroceryExpenses() + e2.getGroceryExpenses()) / numOfMonths))));
        System.out.println("Travel Expenses      : " + rf.formattedRupee(ft.format(((e1.getTravelExpense() + e2.getTravelExpense()) / numOfMonths))));
        System.out.println("Shopping and Eat out : " + rf.formattedRupee(ft.format(((e1.getShoppingExpense() + e2.getShoppingExpense()) / numOfMonths))));
        System.out.println("Family               : " + rf.formattedRupee(ft.format(((e1.getFamilyExpenses() + e2.getFamilyExpenses()) / numOfMonths))));
        System.out.println("Brokerage            : " + rf.formattedRupee(ft.format(((e1.getTotalBrokerage() + e2.getTotalBrokerage()) / numOfMonths))));
        System.out.println("Entertainment        : " + rf.formattedRupee(ft.format(((e1.getEntertainmentExpenses() + e2.getEntertainmentExpenses()) / numOfMonths))));
        System.out.println("Domestic help        : " + rf.formattedRupee(ft.format(((e1.getHousekeepingExpenses() + e2.getHousekeepingExpenses()) / numOfMonths))));
        System.out.println("Home EMI             : " + rf.formattedRupee(ft.format(((e1.getMonthlyEMI() + e2.getMonthlyEMI()) / 1))));
        System.out.println("Education Expenses   : " + rf.formattedRupee(ft.format(((e1.getEducationExpenses() + e2.getEducationExpenses()) / numOfMonths))));
        System.out.println("Healthcare  Expenses : " + rf.formattedRupee(ft.format(((e1.getdHealthCareExpenses() + e2.getdHealthCareExpenses()) / numOfMonths))));
        System.out.println("Total Expenses       : " + rf.formattedRupee(ft.format(((e1.getTotalExpenses() + e2.getTotalExpenses()) / numOfMonths))));
        System.out.println(" ");
        System.out.println("Bun Expenses         : " + e1.getTotalExpensesFmtd());
        System.out.println("Ben Expenses         : " + e2.getTotalExpensesFmtd());
        System.out.println("Dividend Income      : " + rf.formattedRupee(ft.format(((e1.getDividendIncome() + e2.getDividendIncome()) / numOfMonths))));
        System.out.println("Interest Income      : " + rf.formattedRupee(ft.format(((e1.getInterestIncome() + e2.getInterestIncome()) / numOfMonths))));
        System.out.println("Rental Income        : " + rf.formattedRupee(ft.format(((e1.getRentalIncome() + e2.getRentalIncome()) / numOfMonths))));
        System.out.println("Salary Income        : " + rf.formattedRupee(ft.format(((e1.getSalaryIncome() + e2.getSalaryIncome()) / numOfMonths))));
        System.out.println("Sale Proceeds        : " + rf.formattedRupee(ft.format(((e1.getSaleProceeds() + e2.getSaleProceeds()) / numOfMonths))));
        System.out.println("Total Monthly Income : " + rf.formattedRupee(ft.format(((e1.getTotalIncome() + e2.getTotalIncome()) / numOfMonths))));
        System.out.println(" ");
        System.out.println("Monthly EMI          : " + rf.formattedRupee(ft.format(((e1.getMonthlyEMI() + e2.getMonthlyEMI()) / numOfMonths))));
        System.out.println(" ");
        System.out.println("Monthly Savings      : " + rf.formattedRupee(ft.format(((e1.getTotalSavings() + e2.getTotalSavings()) / numOfMonths))));
        System.out.println(" ");
        System.out.println("Total Investments    : " + rf.formattedRupee(ft.format(((e1.getTotalInvestments() + e2.getTotalInvestments())))));
        System.out.println("Total Buy Trades     : " + rf.formattedRupee(ft.format(((e1.getTotalBuyTrades() + e2.getTotalBuyTrades())))));
        System.out.println("Total Sell Trades    : " + rf.formattedRupee(ft.format(((e1.getTotalSellTrades() + e2.getTotalSellTrades())))));
        System.out.println("Total Deposits       : " + rf.formattedRupee(ft.format(((e1.getTotalDeposits() + e2.getTotalDeposits())))));
        System.out.println("Total Withdrawals    : " + rf.formattedRupee(ft.format(((e1.getTotalWithdrawals() + e2.getTotalWithdrawals())))));
        System.out.println("UnknownFmtd          : " + rf.formattedRupee(ft.format(((e1.getUnknown() + e2.getUnknown())))));

        System.out.println(" ");
        System.out.println("Total non discret    : " + rf.formattedRupee(ft.format(((e1.getTotalNonDiscretionExpenses() + e2.getTotalNonDiscretionExpenses())/1))));
        System.out.println("Non Discretionary    : " + rf.formattedRupee(ft.format(((e1.getNonDiscretionaryExpenses() + e2.getNonDiscretionaryExpenses())))));
        System.out.println("Discretionary        : " + rf.formattedRupee(ft.format(((e1.getDiscretionaryExpenses() + e2.getDiscretionaryExpenses())))));


        ListIterator<AccountStatement> unknownIterator = e1.getUnknownList().listIterator();
        AccountStatement temp;
        while (unknownIterator.hasNext()) {
            temp = unknownIterator.next();
            System.out.println("Remarks : " + temp.transactionRemarks);
            System.out.println("Withdrawal Amount : " + temp.withdrawalAmountFmtd);
            System.out.println("Deposit Amount : " + temp.depositAmountFmtd);
        }

        unknownIterator = e2.getUnknownList().listIterator();
        while (unknownIterator.hasNext()) {
            temp = unknownIterator.next();
            System.out.println("Remarks : " + temp.transactionRemarks);
            System.out.println("Withdrawal Amount : " + temp.withdrawalAmountFmtd);
            System.out.println("Deposit Amount : " + temp.depositAmountFmtd);
        }
    }
}