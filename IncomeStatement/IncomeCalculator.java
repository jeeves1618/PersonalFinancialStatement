package IncomeStatement;

import CommonModules.RupeeFormatter;
import CommonModules.bsheetElements;
import IngestionEngine.ingestExcel;

import java.text.DecimalFormat;

public class IncomeCalculator {
    private double houseLoanInt;
    private double professionalTax;
    private double section80c;
    private double standardDeduction;
    private double employmentTax;
    private double annualSalary;
    private double monthlyTakeHome;
    private double totalTax;
    private double PF;
    private double taxSurchargeFactor;

    DecimalFormat ft = new DecimalFormat("Rs ##,##,##0.00");
    RupeeFormatter rf = new RupeeFormatter();

    public IncomeCalculator(double annualSalary, double houseLoanInt, double pf) {
        professionalTax = 2496.0D;
        section80c = 150000.0D;
        standardDeduction = 50000.0D;
        employmentTax=2500.0D;

        this.houseLoanInt = houseLoanInt;
        if (pf > 0.0D)
            this.PF = pf;
        else
            this.PF = (annualSalary * 0.4 * 0.12) / 12;
        this.annualSalary = annualSalary;
    }

    public IncomeCalculator(String itemDescription){
        String fileWithPathname = "C:\\dev\\Data\\BL.xlsx";
        ingestExcel balanceSheet = new ingestExcel(fileWithPathname);

        bsheetElements[] bsheetElementsList;
        bsheetElementsList = balanceSheet.transferData();
        for (int i = 0; i < bsheetElements.numofElements; i++){
            if(bsheetElementsList[i].subType.equals("Salary") && bsheetElementsList[i].itemDescription.equals(itemDescription)) {
                this.annualSalary = bsheetElementsList[i].cashValue;
            }
            professionalTax = 2496.0D;
            houseLoanInt = 200000.0D;
            section80c = 150000.0D;
            standardDeduction = 50000.0D;
            employmentTax=2500.0D;
            this.PF = (annualSalary * 0.4 * 0.12) / 12;
            taxSurchargeFactor=1.0D;
        }
    }
    public void calculateOldTakeHome() {

        double remainingSalary = 0.0D;

        totalTax = 0.0D;
        int[] salarySlab = new int[]{250000, 250000, 500000, 1000000};
        double[] taxRate = new double[]{0.0D, 5.2D, 20.8D, 31.2D};

        remainingSalary = annualSalary - section80c - standardDeduction - houseLoanInt - employmentTax;
        if (remainingSalary >= 5000000) taxSurchargeFactor = 1.1D;

        for (int i = 0; i < 4 && remainingSalary > 0.0D; ++i) {
            totalTax += (double) salarySlab[i] * taxRate[i] / 100.0D;
            remainingSalary -= (double) salarySlab[i];
            if (i == 2) {
                salarySlab[i + 1] = (int) remainingSalary;
            }
        }
        System.out.println(remainingSalary);
        totalTax = totalTax * taxSurchargeFactor;
        monthlyTakeHome = (annualSalary - totalTax - professionalTax - (PF*12)) / 12.0D;
    }

    public void calculateNewTakeHome() {

        double remainingSalary = 0.0D;
        totalTax = 0.0D;
        int[] salarySlab2020 = new int[]{250000, 250000, 250000, 250000, 250000, 250000, 1000000};
        double[] taxRate2020 = new double[]{0.0D, 5.2D, 10.4D, 15.6D, 20.8D, 26.0D, 31.2D};

        remainingSalary = annualSalary;
        for (int i = 0; i < 7 && remainingSalary > 0.0D; ++i) {
            totalTax += (double) salarySlab2020[i] * taxRate2020[i] / 100.0D;
            remainingSalary -= (double) salarySlab2020[i];
            if (i == 5) {
                salarySlab2020[i + 1] = (int) remainingSalary;
            }
        }
        if (annualSalary >= 5000000) totalTax = totalTax * 1.1;
        monthlyTakeHome = (annualSalary - totalTax - professionalTax - (PF * 12)) / 12.0D;
    }

    public String getAnnualSalaryFmtd(){
        return rf.formattedRupee(ft.format(annualSalary));
    }

    public String getMonthlyTakeHomeFmtd(){
        return rf.formattedRupee(ft.format(monthlyTakeHome));
    }

    public String getTotalTaxFmtd(){
        return rf.formattedRupee(ft.format(totalTax));
    }

    public String getPFFmtd(){
        return rf.formattedRupee(ft.format(PF));
    }

    public String getMonthlySalaryFmtd(){
        return rf.formattedRupee(ft.format(annualSalary / 12.0));
    }

    public String getMonthlyTaxFmtd(){
        return rf.formattedRupee(ft.format(totalTax / 12.0));
    }

    public double getmonthlyTakeHome(){
        return monthlyTakeHome;
    }
}

