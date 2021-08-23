package BalanceSheet;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Scanner;

class buildBalanceSheetTest {

    public static void main(String[] args) throws ParseException, IOException {
        double monthlyTakeHomeOne;
        double monthlyTakeHomeTwo;
        Scanner scan = new Scanner(System.in);

        System.out.println("Please Enter Ben Monthly Income :");

        monthlyTakeHomeOne = 100;

        System.out.println("Please Enter Bun Monthly Income :");
        monthlyTakeHomeTwo = 200;
        buildBalanceSheet T = new buildBalanceSheet(monthlyTakeHomeOne, monthlyTakeHomeTwo);
        System.out.println("Your total Monthly Incomes is : " + T.chartOfAccountsList[15].cashValue);
        System.out.println("Your monthly expenses are     : " + T.chartOfAccountsList[16].cashValue);
        System.out.println("Your net savings is           : " + T.chartOfAccountsList[17].cashValue);
        System.out.println("Your rental income 1 is       : " + T.chartOfAccountsList[18].cashValue);
        System.out.println("Your rental income 2 is       : " + (T.chartOfAccountsList[15].cashValue + T.chartOfAccountsList[16].cashValue + T.chartOfAccountsList[17].cashValue + T.chartOfAccountsList[18].cashValue));
        System.out.println("Your rental income 3 is       : " + T.getMonthlyEMIFmtd());
        System.out.println("Our total assets is           : " + T.getTotalAssetsFmtd());
        System.out.println("Our total liquid asset        : " + T.getTotalLiquidAssets());
        System.out.println(LocalDate.now().plusMonths(10));

        scan.close();
    }
}