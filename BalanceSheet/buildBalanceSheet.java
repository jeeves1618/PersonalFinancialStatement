package BalanceSheet;

import CommonModules.RupeeFormatter;
import CommonModules.bsheetElements;
import IngestionEngine.ingestExcel;

import java.text.DecimalFormat;
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

    public bsheetElements[] bsheetElementsList;
    private LocalDate survivalDate;

    DecimalFormat ft = new DecimalFormat("Rs ##,##,##0.00");
    RupeeFormatter rf = new RupeeFormatter();

    public buildBalanceSheet(double monthlyTakeHomeOne, double monthlyTakeHomeTwo){

        this.monthlyTakeHomeOne = monthlyTakeHomeOne;
        this.monthlyTakeHomeTwo = monthlyTakeHomeTwo;
        monthlyEMI = 0;
        totalCurrentAssets = 0;
        totalCurrentLiabilities = 0;
        totalNonCurrentAssets = 0;
        totalNonCurrentLiabilities = 0;
        String fileWithPathname = "C:\\dev\\Data\\BL.xlsx";
        ingestExcel balanceSheet = new ingestExcel(fileWithPathname);
        //IngestH2db balanceSheet = new IngestH2db();

        bsheetElementsList = balanceSheet.transferData();
        for (int i=0; i < bsheetElements.numofElements; i++){
            if(bsheetElementsList[i].subType.equals("Rent") && bsheetElementsList[i].itemDescription.equals("RentalIncome1")) {
                this.rentalIncomeOne = bsheetElementsList[i].cashValue;
            } else
            if(bsheetElementsList[i].subType.equals("EMI")) {
                monthlyEMI = (bsheetElementsList[i].cashValue/12) + monthlyEMI;
                totalCurrentLiabilities = totalCurrentLiabilities + bsheetElementsList[i].cashValue;
            }else
            if(bsheetElementsList[i].subType.equals("Rent") && bsheetElementsList[i].itemDescription.equals("RentalIncome2")) {
                this.rentalIncomeTwo = bsheetElementsList[i].cashValue;
            } else
            if(bsheetElementsList[i].subType.equals("Rent") && bsheetElementsList[i].itemDescription.equals("RentalIncome3")) {
                this.rentalIncomeThree = bsheetElementsList[i].cashValue;
            } else
            if(bsheetElementsList[i].subType.equals("Account Payables") && bsheetElementsList[i].itemDescription.equals("HouseHoldExpenses")) {
                this.monthlyExpenses = bsheetElementsList[i].cashValue/12;
                totalCurrentLiabilities = totalCurrentLiabilities + bsheetElementsList[i].cashValue;
            } else
            if(bsheetElementsList[i].subType.equals("Total Current Assets") && bsheetElementsList[i].itemDescription.equals("Total Current Assets")) {
                bsheetElementsList[i].cashValue = totalCurrentAssets;
                bsheetElementsList[i].cashValueFmtd = rf.formattedRupee(ft.format(bsheetElementsList[i].cashValue));
            }else
            if(bsheetElementsList[i].subType.equals("Current Assets") || bsheetElementsList[i].subType.equals("Account Receivables")) {
                totalCurrentAssets = totalCurrentAssets + bsheetElementsList[i].cashValue;
            }  else
            if(bsheetElementsList[i].subType.equals("Current Liabilities") && bsheetElementsList[i].itemDescription.equals("Current Liabilities")) {
                bsheetElementsList[i].cashValue = totalCurrentLiabilities;
                bsheetElementsList[i].cashValueFmtd = rf.formattedRupee(ft.format(bsheetElementsList[i].cashValue));
            }else
            if(bsheetElementsList[i].subType.equals("Accrued Expenses") || bsheetElementsList[i].subType.equals("EMI") ||
                    bsheetElementsList[i].subType.equals("Account Payables")) {
                totalCurrentLiabilities = totalCurrentLiabilities + bsheetElementsList[i].cashValue;
            }else
            if(bsheetElementsList[i].subType.equals("Non Current Liabilities") && bsheetElementsList[i].itemDescription.equals("Non Current Liabilities")) {
                bsheetElementsList[i].cashValue = totalNonCurrentLiabilities;
                bsheetElementsList[i].cashValueFmtd = rf.formattedRupee(ft.format(bsheetElementsList[i].cashValue));
            }else
            if(bsheetElementsList[i].subType.equals("Long Term Debts")) {
                totalNonCurrentLiabilities = totalNonCurrentLiabilities + bsheetElementsList[i].cashValue;
            }else
            if(bsheetElementsList[i].subType.equals("Total Non Current Assets") && bsheetElementsList[i].itemDescription.equals("Total Non Current Assets")) {
                bsheetElementsList[i].cashValue = totalNonCurrentAssets;
                bsheetElementsList[i].cashValueFmtd = rf.formattedRupee(ft.format(bsheetElementsList[i].cashValue));
            }else
            if(bsheetElementsList[i].subType.equals("Fixed Assets") || bsheetElementsList[i].subType.equals("Other Assets")) {
                totalNonCurrentAssets = totalNonCurrentAssets + bsheetElementsList[i].cashValue;
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
        return rf.formattedRupee(ft.format(totalCurrentAssets + totalNonCurrentAssets));
    }
    public String getNetWorthFmtd(){
        return rf.formattedRupee(ft.format(totalCurrentAssets + totalNonCurrentAssets - totalCurrentLiabilities - totalNonCurrentLiabilities));
    }
    public String getSurvivalDateFmtd(){
        survivalDate =  LocalDate.now().plusDays(Math.round((totalCurrentAssets)*365/totalCurrentLiabilities));
        return survivalDate.toString();
    }
}

