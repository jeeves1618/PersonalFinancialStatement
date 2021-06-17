package BalanceSheet;

import java.util.Scanner;

class buildBalanceSheetTest {

    public static void main(String[] args) {
        double monthlyTakeHomeOne;
        double monthlyTakeHomeTwo;
        Scanner scan = new Scanner(System.in);

        System.out.println("Please Enter Ben Monthly Income :");
        monthlyTakeHomeOne = scan.nextDouble();
        System.out.println("Please Enter Bun Monthly Income :");
        monthlyTakeHomeTwo = scan.nextDouble();
        buildBalanceSheet T = new buildBalanceSheet(monthlyTakeHomeOne, monthlyTakeHomeTwo);
        System.out.println("Your total Monthly Incomes is : " + T.getIncomeTotalFmtd());
        System.out.println("Your monthly expenses are     : " + T.getMonthlyExpensesFmtd());
        System.out.println("Your net savings is           : " + T.getNetSavingsFmtd());
        System.out.println("Your rental income 1 is       : " + T.totalCurrentLiabilities);
        System.out.println("Your rental income 2 is       : " + T.totalCurrentAssets);
        System.out.println("Your rental income 3 is       : " + T.getMonthlyEMIFmtd());
        //System.out.println("Your rental income 3 is       : " + T.getSurvivalDateFmtd());
        scan.close();
    }
}