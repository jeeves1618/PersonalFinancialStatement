package IncomeStatement;

import java.util.Scanner;
class IncomeCalculatorTest {
    public static void main(String[] args) {
        boolean oldTaxRegulation;
        Scanner scan = new Scanner(System.in);
        System.out.println("Who are you?");
        String whoAmI = scan.nextLine();

        //takeHome takeHomeInstance = new takeHome(annualSalary, houseLoanInt ,PF);
        IncomeCalculator takeHomeInstance = new IncomeCalculator(whoAmI);
        takeHomeInstance.calculateOldTakeHome();
        oldTaxRegulation = true;
        printSummary(oldTaxRegulation, takeHomeInstance);

        takeHomeInstance.calculateNewTakeHome();
        oldTaxRegulation = false;
        printSummary(oldTaxRegulation, takeHomeInstance);

        scan.close();
    }
    public static void printSummary(boolean oldRegieme, IncomeCalculator takeHomeInstance) {
        System.out.println(" ");
        if (oldRegieme){
            System.out.println("================= SALARY COMPUTATION WITH DEDUCTIONS (OLD) ===================");
        }
        else{
            System.out.println("================= SALARY COMPUTATION WITHOUT DEDUCTIONS (NEW) ===================");
        }
        System.out.println("Your annual salary is         : " + takeHomeInstance.getAnnualSalaryFmtd());
        System.out.println("Your annual tax liability is  : " + takeHomeInstance.getTotalTaxFmtd());
        System.out.println("Your monthly salary is        : " + takeHomeInstance.getMonthlySalaryFmtd());
        System.out.println("Your monthly tax liability is : " + takeHomeInstance.getMonthlyTaxFmtd());
        System.out.println("Your monthly PF Contribution  : " + takeHomeInstance.getPFFmtd());
        System.out.println("Your net monthly take home is : " + takeHomeInstance.getMonthlyTakeHomeFmtd());
    }
}
