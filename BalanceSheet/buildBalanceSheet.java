package BalanceSheet;

import CommonModules.ChartOfAccounts;
import CommonModules.RupeeFormatter;
import IncomeStatement.ExpenseCalculator;
import IngestionEngine.ingestChartOfAcctsExcel;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;

public class buildBalanceSheet {
    private double rentalIncomeOne;
    private double rentalIncomeTwo;
    private double rentalIncomeThree;
    private double monthlyTakeHomeOne;
    private double monthlyTakeHomeTwo;
    private double monthlyExpenses;
    private double monthlyEMI;
    private double incomeTotal;
    private double netSavings;
    public double totalCurrentAssets;
    private double totalNonCurrentAssets;
    public double totalCurrentLiabilities;
    private double totalNonCurrentLiabilities;
    private double totalLiquidAssets;

    public ChartOfAccounts[] chartOfAccountsList;
    private LocalDate survivalDate;

    DecimalFormat ft = new DecimalFormat("Rs ##,##,##0.00");
    RupeeFormatter rf = new RupeeFormatter();
    ExpenseCalculator e1 = new ExpenseCalculator("Two", "Sal1");
    ExpenseCalculator e2 = new ExpenseCalculator("One", "Sal1");

    public buildBalanceSheet(double monthlyTakeHomeOne, double monthlyTakeHomeTwo) throws ParseException {

        this.monthlyTakeHomeOne = monthlyTakeHomeOne;
        this.monthlyTakeHomeTwo = monthlyTakeHomeTwo;
        monthlyEMI = 0;
        totalCurrentAssets = 0;
        totalCurrentLiabilities = 0;
        totalNonCurrentAssets = 0;
        totalNonCurrentLiabilities = 0;
        String fileWithPathname = "C:\\dev\\Data\\ChartOfAccounts.xlsx";
        ingestChartOfAcctsExcel balanceSheet = new ingestChartOfAcctsExcel(fileWithPathname);
        //IngestH2db balanceSheet = new IngestH2db();

        chartOfAccountsList = balanceSheet.transferData();
        for (int i = 0; i < ChartOfAccounts.numofElements; i++){

            if (chartOfAccountsList[i].isAssetLiquidInd.equals("Y")) {
                totalLiquidAssets = totalLiquidAssets + chartOfAccountsList[i].cashValue;
            }

            if(chartOfAccountsList[i].subType.equals("Rent") && chartOfAccountsList[i].itemDescription.equals("RentalIncome1")) {
                this.rentalIncomeOne = chartOfAccountsList[i].cashValue;
            } else
            if(chartOfAccountsList[i].itemDescription.equals("Residential Loan EMI")) {
                monthlyEMI = (chartOfAccountsList[i].cashValue/12) + monthlyEMI;
                totalCurrentLiabilities = totalCurrentLiabilities + chartOfAccountsList[i].cashValue;
            }else
            if(chartOfAccountsList[i].subType.equals("Rent") && chartOfAccountsList[i].itemDescription.equals("RentalIncome2")) {
                this.rentalIncomeTwo = chartOfAccountsList[i].cashValue;
            } else
            if(chartOfAccountsList[i].subType.equals("Rent") && chartOfAccountsList[i].itemDescription.equals("RentalIncome3")) {
                this.rentalIncomeThree = chartOfAccountsList[i].cashValue;
            } else
            if(chartOfAccountsList[i].subType.equals("Account Payables") && chartOfAccountsList[i].itemDescription.equals("HouseHoldExpenses")) {
                chartOfAccountsList[i].cashValue = e1.getTotalNonDiscretionExpenses()+ e2.getTotalNonDiscretionExpenses();
                chartOfAccountsList[i].cashValueFmtd = rf.formattedRupee(ft.format((e1.getTotalNonDiscretionExpenses()+ e2.getTotalNonDiscretionExpenses())/e1.getMonthsBetween()*12));
                this.monthlyExpenses = chartOfAccountsList[i].cashValue/ e1.getMonthsBetween();
                totalCurrentLiabilities = totalCurrentLiabilities + chartOfAccountsList[i].cashValue;
            } else
            if(chartOfAccountsList[i].subType.equals("Total Current Assets") && chartOfAccountsList[i].itemDescription.equals("Total Current Assets")) {
                chartOfAccountsList[i].cashValue = totalCurrentAssets;
                chartOfAccountsList[i].cashValueFmtd = rf.formattedRupee(ft.format(chartOfAccountsList[i].cashValue));
            }else
            if(chartOfAccountsList[i].subType.equals("Current Assets") || chartOfAccountsList[i].subType.equals("Account Receivables")) {
                totalCurrentAssets = totalCurrentAssets + chartOfAccountsList[i].cashValue;
            }  else
            if(chartOfAccountsList[i].subType.equals("Total Current Liabilities") && chartOfAccountsList[i].itemDescription.equals("Current Liabilities")) {
                chartOfAccountsList[i].cashValue = totalCurrentLiabilities;
                chartOfAccountsList[i].cashValueFmtd = rf.formattedRupee(ft.format(chartOfAccountsList[i].cashValue));
            }else
            if(chartOfAccountsList[i].subType.equals("Accrued Expenses") || chartOfAccountsList[i].subType.equals("EMI") ||
                    chartOfAccountsList[i].subType.equals("Account Payables")) {
                totalCurrentLiabilities = totalCurrentLiabilities + chartOfAccountsList[i].cashValue;
            }else
            if(chartOfAccountsList[i].subType.equals("Total Non Current Liabilities") && chartOfAccountsList[i].itemDescription.equals("Non Current Liabilities")) {
                chartOfAccountsList[i].cashValue = totalNonCurrentLiabilities;
                chartOfAccountsList[i].cashValueFmtd = rf.formattedRupee(ft.format(chartOfAccountsList[i].cashValue));
            }else
            if(chartOfAccountsList[i].subType.equals("Long Term Debts")) {
                totalNonCurrentLiabilities = totalNonCurrentLiabilities + chartOfAccountsList[i].cashValue;
            }else
            if(chartOfAccountsList[i].subType.equals("Total Non Current Assets") && chartOfAccountsList[i].itemDescription.equals("Total Non Current Assets")) {
                chartOfAccountsList[i].cashValue = totalNonCurrentAssets;
                chartOfAccountsList[i].cashValueFmtd = rf.formattedRupee(ft.format(chartOfAccountsList[i].cashValue));
            }else
            if(chartOfAccountsList[i].subType.equals("Fixed Assets") || chartOfAccountsList[i].subType.equals("Other Assets")) {
                totalNonCurrentAssets = totalNonCurrentAssets + chartOfAccountsList[i].cashValue;
            }
        }
        incomeTotal = monthlyTakeHomeOne + monthlyTakeHomeTwo + rentalIncomeOne + rentalIncomeTwo + rentalIncomeThree;
        netSavings = incomeTotal - monthlyExpenses - monthlyEMI;
    }
    public String getRentalIncomeOneFmtd(){
        return rf.formattedRupee(ft.format(rentalIncomeOne));
    }
    public String getRentalIncomeTwoFmtd(){
        return rf.formattedRupee(ft.format(rentalIncomeTwo));
    }
    public String getRentalIncomeThreeFmtd(){
        return rf.formattedRupee(ft.format(rentalIncomeThree));
    }
    public String getMonthlyExpensesFmtd(){
        return rf.formattedRupee(ft.format(monthlyExpenses));
    }
    public String getAnnualExpensesFmtd(){
        return rf.formattedRupee(ft.format(monthlyExpenses*12));
    }
    public String getIncomeTotalFmtd(){
        return rf.formattedRupee(ft.format(incomeTotal));
    }
    public String getMonthlyEMIFmtd(){
        return rf.formattedRupee(ft.format(monthlyEMI));
    }
    public String getNetSavingsFmtd(){
        return rf.formattedRupee(ft.format(netSavings));
    }
    public String getTotalLiabilitiesFmtd(){
        return rf.formattedRupee(ft.format(totalCurrentLiabilities + totalNonCurrentLiabilities));
    }
    public String getTotalAssetsFmtd(){
        System.out.println(totalCurrentAssets);
        System.out.println(totalNonCurrentAssets);
        return rf.formattedRupee(ft.format(totalCurrentAssets + totalNonCurrentAssets));
    }
    public String getNetWorthFmtd(){
        return rf.formattedRupee(ft.format(totalCurrentAssets + totalNonCurrentAssets - totalCurrentLiabilities - totalNonCurrentLiabilities));
    }
    public String getCurrentNetWorthFmtd(){
        return rf.formattedRupee(ft.format(totalCurrentAssets - totalCurrentLiabilities));
    }
    public String getSurvivalDateFmtd(){
        survivalDate =  LocalDate.now().plusDays(Math.round((totalCurrentAssets)*365/totalCurrentLiabilities));
        return survivalDate.toString();
    }
    public double getTotalLiquidAssets(){
        return totalLiquidAssets;
    }
    public double getNetSavings(){
        return netSavings;
    }
}

