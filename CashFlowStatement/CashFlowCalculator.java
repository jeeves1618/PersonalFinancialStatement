package CashFlowStatement;

import CommonModules.AccountStatement;
import CommonModules.RupeeFormatter;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CashFlowCalculator {
    private double totalSavings;
    private double beginningCashBalance;
    private double cashReceipts;
    private double cashDisbursements;
    private double fixedAssetPurchases;
    private double netBorrowings;
    private double incomeTaxesPaid;
    private double saleOfStock;
    private double endingCashBalance;
    private double saleProceeds;
    private double totalDeposits;
    long monthsBetween;
    private LocalDate transactionDateHigh = LocalDate.parse("0001-01-01");
    private LocalDate transactionDateLow  = LocalDate.parse("9999-12-31");
    //private LocalDate transactionDate;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    DecimalFormat ft = new DecimalFormat("Rs ##,##,##0.00");
    RupeeFormatter rf = new RupeeFormatter();
    ArrayList<AccountStatement> unknownList = new ArrayList<>();

    public CashFlowCalculator(String accountHolder, String accountType) throws ParseException {

        ResourceBundle properties  = ResourceBundle.getBundle("Properties");
        String fileWithPathname = properties.getString(accountHolder + accountType);
        System.out.println("File being accessed: " + fileWithPathname);
        IngestionEngine.IngestStatementExcel balanceSheet = new IngestionEngine.IngestStatementExcel(fileWithPathname);
        //IngestH2db balanceSheet = new IngestH2db();

        ArrayList<AccountStatement> AccountStatementList;
        AccountStatementList = balanceSheet.transferData();
        for (int i=0; i < AccountStatement.numofElements; i++){
            String transactionRemarks = AccountStatementList.get(i).transactionRemarks;

            LocalDate transactionDate = LocalDate.parse(AccountStatementList.get(i).transactionDate, formatter);
            if (transactionDateHigh.isBefore(transactionDate))
                transactionDateHigh = transactionDate;
            if (transactionDateLow.isAfter(transactionDate))
                transactionDateLow = transactionDate;

            if(i==0) beginningCashBalance=AccountStatementList.get(i).balanceAmount;
            if(i==(AccountStatement.numofElements - 1)) endingCashBalance=AccountStatementList.get(i).balanceAmount;

            cashDisbursements = cashDisbursements + AccountStatementList.get(i).withdrawalAmount;
            cashReceipts = cashReceipts + AccountStatementList.get(i).depositAmount;

            if((transactionRemarks.contains("MMT/IMPS/") || (transactionRemarks.contains("BIL/NEFT/")))&&
                    (transactionRemarks.contains("NithyaForBosch") ||
                            transactionRemarks.contains("NithyaMPure") ||
                            transactionRemarks.contains("NithyaFridge") ||
                            transactionRemarks.contains("VijayHiranandan/ATLANTURE") ||
                            transactionRemarks.contains("VijayHiranandai/ATLANTURE") ||
                            transactionRemarks.contains("VijayAmafiGrill/Prasanna") ||
                            transactionRemarks.contains("NithyaDishWashe")))
            {
                fixedAssetPurchases = fixedAssetPurchases + AccountStatementList.get(i).withdrawalAmount - AccountStatementList.get(i).depositAmount;
                AccountStatementList.get(i).entryCategory = "Investments";
                cashDisbursements = cashDisbursements - AccountStatementList.get(i).withdrawalAmount;
            } else if
            (transactionRemarks.toUpperCase().contains("SBI LIFE INSURANCE COMPANY LTD")){
                saleProceeds = saleProceeds + AccountStatementList.get(i).depositAmount;
                totalDeposits = totalDeposits - AccountStatementList.get(i).withdrawalAmount + AccountStatementList.get(i).depositAmount;
                AccountStatementList.get(i).entryCategory = "Sale Proceeds";
                cashReceipts = cashReceipts - AccountStatementList.get(i).depositAmount;
            }
        }
        System.out.println("Based on the data from " + transactionDateLow + " to " + transactionDateHigh);
        monthsBetween = ChronoUnit.MONTHS.between(transactionDateLow,transactionDateHigh) + 1;
    }

    public String getTimePeriod(){
        return ("For the period " + transactionDateLow + " through " + transactionDateHigh);
    }
    public double getTotalSavings(){
        return totalSavings/monthsBetween;
    }
    public String getTotalSavingsFmtd(){
        return rf.formattedRupee(ft.format(totalSavings/monthsBetween));
    }
    public double getSaleProceeds(){
        return saleProceeds/monthsBetween;
    }
    public double getTotalDeposits(){
        return totalDeposits/monthsBetween;
    }
    public double getNetBorrowings(){
        return netBorrowings;
    }
    public double getIncomeTaxesPaid(){
        return incomeTaxesPaid;
    }
    public ArrayList<AccountStatement> getUnknownList(){
        return unknownList;
    }
    public double getBeginningCashBalance() {
        return beginningCashBalance;
    }
    public double getEndingCashBalance() {
        return endingCashBalance;
    }
    public double getCashReceipts() {
        return cashReceipts;
    }
    public double getCashDisbursements() {
        return cashDisbursements;
    }
    public double getFixedAssetPurchases() {
        return fixedAssetPurchases;
    }

}

