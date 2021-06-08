package IncomeStatement;

import CommonModules.RupeeFormatter;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class ExpenseCalculator {
    private double totalSavings;
    private double apartmentMaintenance;
    private double electricityBill;
    private double creditCardBill;
    private double salaryIncome;
    private double interestIncome;
    private double rentalIncome;
    private double dividendIncome;
    private double saleProceeds;
    private double brokerageMaintenance;
    private double monthlyEMI;
    private double homeInsurance;
    private double transferOut;
    private double transferIn;
    private double cashWithdrawals;
    private double totalWithdrawals;
    private double totalInvestment;
    private double totalDeposits;
    private double travelExpenses;
    private double shoppingEatout;
    private double forFamily;
    private double groceries;
    private double entertainmentExpenses;
    private double housekeepingExpenses;
    private double totalSellTrades;
    private double totalBuyTrades;
    private double Unknown;
    long monthsBetween;
    private LocalDate transactionDateHigh = LocalDate.parse("0001-01-01");
    private LocalDate transactionDateLow  = LocalDate.parse("9999-12-31");
    //private LocalDate transactionDate;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    DecimalFormat ft = new DecimalFormat("Rs ##,##,##0.00");
    RupeeFormatter rf = new RupeeFormatter();
    ArrayList<AccountStatement> unknownList = new ArrayList<>();

    public ExpenseCalculator(String accountHolder, String accountType) throws ParseException {

        String fileWithPathname = "C:\\dev\\Data\\" + accountHolder + "Acct" + accountType + ".xlsx";
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

            if(transactionRemarks.contains("INF/INFT/") && transactionRemarks.contains("/Self") && accountType.contains("Sal")) {
                AccountStatementList.get(i).entryCategory = "Savings";
                totalSavings = totalSavings + AccountStatementList.get(i).withdrawalAmount - AccountStatementList.get(i).depositAmount;
                totalWithdrawals = totalWithdrawals + AccountStatementList.get(i).withdrawalAmount;
                totalDeposits = totalDeposits + AccountStatementList.get(i).depositAmount;
            } else
            if(transactionRemarks.contains("BIL/INFT/") && transactionRemarks.toUpperCase().contains("RENT")) {
                rentalIncome = rentalIncome + AccountStatementList.get(i).depositAmount;
                totalDeposits = totalDeposits + AccountStatementList.get(i).depositAmount;
                AccountStatementList.get(i).entryCategory = "Rental Income";
            } else
            if(transactionRemarks.toUpperCase().contains("EBA/NSDL CDSL CHG") || transactionRemarks.toUpperCase().contains("DP CHGS TILL")) {
                brokerageMaintenance = brokerageMaintenance + AccountStatementList.get(i).withdrawalAmount;
                totalWithdrawals = totalWithdrawals + AccountStatementList.get(i).withdrawalAmount;
                AccountStatementList.get(i).entryCategory = "Brokerage Maintenance";
            } else
            if((transactionRemarks.contains("BIL/INFT/") || transactionRemarks.contains("MMT/IMPS/")
                    || transactionRemarks.contains("NEFT-") || transactionRemarks.contains("BIL/NEFT"))&&
                    (transactionRemarks.toUpperCase().contains("KANNAN") ||
                    transactionRemarks.toUpperCase().contains("CHANDRA") ||
                    transactionRemarks.toUpperCase().contains("DHEEPIKA") || transactionRemarks.toUpperCase().contains("DHEEPS") ||
                    transactionRemarks.toUpperCase().contains("AMMA") ||
                    transactionRemarks.toUpperCase().contains("NANA"))) {
                forFamily = forFamily + AccountStatementList.get(i).withdrawalAmount - AccountStatementList.get(i).depositAmount;
                totalDeposits = totalDeposits + AccountStatementList.get(i).depositAmount;
                totalWithdrawals = totalWithdrawals + AccountStatementList.get(i).withdrawalAmount;
                AccountStatementList.get(i).entryCategory = "Family";
            }else
            if((transactionRemarks.contains("MMT/IMPS/") || (transactionRemarks.contains("BIL/NEFT/")))&&
                    (transactionRemarks.contains("NithyaForBosch") ||
                            transactionRemarks.contains("NithyaMPure") ||
                            transactionRemarks.contains("NithyaFridge") ||
                            transactionRemarks.contains("VijayHiranandan/ATLANTURE") ||
                            transactionRemarks.contains("VijayHiranandai/ATLANTURE") ||
                            transactionRemarks.contains("VijayAmafiGrill/Prasanna") ||
                            transactionRemarks.contains("NithyaDishWashe")))
                            {
                totalInvestment = totalInvestment + AccountStatementList.get(i).withdrawalAmount - AccountStatementList.get(i).depositAmount;
                totalDeposits = totalDeposits + AccountStatementList.get(i).depositAmount;
                totalWithdrawals = totalWithdrawals + AccountStatementList.get(i).withdrawalAmount;
                AccountStatementList.get(i).entryCategory = "Investments";
            } else
            if((transactionRemarks.contains("BIL/INFT/") && transactionRemarks.toUpperCase().contains("EMI"))
                    || transactionRemarks.toUpperCase().contains("ACH/HDFCLTD")
                    || transactionRemarks.toUpperCase().contains("CLG/HDFC LTD")
                    || transactionRemarks.toUpperCase().contains("ACH/LIC HOUSING FINANCE")
                    || transactionRemarks.contains("BIL/NEFT/") && transactionRemarks.toUpperCase().contains("EMI")){
                monthlyEMI = monthlyEMI + AccountStatementList.get(i).withdrawalAmount;
                totalWithdrawals = totalWithdrawals + AccountStatementList.get(i).withdrawalAmount;
                AccountStatementList.get(i).entryCategory = "Monthly EMI";
            } else
            if(transactionRemarks.contains("Rev Sweep From") || transactionRemarks.toUpperCase().contains("AUTOSWEEP")){
                AccountStatementList.get(i).entryCategory = "Bookentries";
            } else
            if(transactionRemarks.contains("ATM") || transactionRemarks.toUpperCase().contains("CASH WDL")){
                cashWithdrawals = cashWithdrawals + AccountStatementList.get(i).withdrawalAmount;
                totalWithdrawals = totalWithdrawals + AccountStatementList.get(i).withdrawalAmount + AccountStatementList.get(i).depositAmount;
                AccountStatementList.get(i).entryCategory = "Cash Withdrawals";
            } else
            if((transactionRemarks.contains("State Bank/SBCOLLECT")) || (transactionRemarks.contains("Join Associatio")) ||
            (transactionRemarks.toUpperCase().contains("BIL/ONL/") && transactionRemarks.toUpperCase().contains("3FIVE8 TEC"))
            ){
                apartmentMaintenance = apartmentMaintenance + AccountStatementList.get(i).withdrawalAmount;
                totalWithdrawals = totalWithdrawals + AccountStatementList.get(i).withdrawalAmount + AccountStatementList.get(i).depositAmount;
                AccountStatementList.get(i).entryCategory = "Apartment Maintenance";
            } else
            if(transactionRemarks.contains("BIL/ONL/") && transactionRemarks.toUpperCase().contains("EB")){
                electricityBill = electricityBill + AccountStatementList.get(i).withdrawalAmount;
                totalWithdrawals = totalWithdrawals + AccountStatementList.get(i).withdrawalAmount + AccountStatementList.get(i).depositAmount;
                AccountStatementList.get(i).entryCategory = "Electricity Expenses";
            }  else
            if ((transactionRemarks.contains("BIL/ONL/") && transactionRemarks.toUpperCase().contains("BB DAILY")) ||
            (transactionRemarks.contains("VPS") && transactionRemarks.toUpperCase().contains("NILGIRIS")) ||
                    (transactionRemarks.toUpperCase().contains("RADHA BUT")) ||
                    (transactionRemarks.toUpperCase().contains("HILSON HEA"))
            ) {
                groceries = groceries + AccountStatementList.get(i).withdrawalAmount;
                totalWithdrawals = totalWithdrawals + AccountStatementList.get(i).withdrawalAmount - AccountStatementList.get(i).depositAmount;
                AccountStatementList.get(i).entryCategory = "Groceries";
            } else
            if(transactionRemarks.contains("Closure Proceeds") || transactionRemarks.toUpperCase().contains("INT.COLL")
                || transactionRemarks.toUpperCase().contains("INT.PD:")){
                interestIncome = interestIncome + AccountStatementList.get(i).depositAmount + AccountStatementList.get(i).withdrawalAmount;
                totalDeposits = totalDeposits - AccountStatementList.get(i).withdrawalAmount + AccountStatementList.get(i).depositAmount;
                AccountStatementList.get(i).entryCategory = "Interest Income";
            } else
            if(transactionRemarks.toUpperCase().contains("SBI LIFE INSURANCE COMPANY LTD")){
                saleProceeds = saleProceeds + AccountStatementList.get(i).depositAmount;
                totalDeposits = totalDeposits - AccountStatementList.get(i).withdrawalAmount + AccountStatementList.get(i).depositAmount;
                AccountStatementList.get(i).entryCategory = "Sale Proceeds";
            }
            else
            if(transactionRemarks.toUpperCase().contains("ICICI BANK CREDIT")){
                creditCardBill = creditCardBill + AccountStatementList.get(i).withdrawalAmount;
                totalWithdrawals = totalWithdrawals + AccountStatementList.get(i).withdrawalAmount + AccountStatementList.get(i).depositAmount;
                AccountStatementList.get(i).entryCategory = "Credit Card Bills";
            } else
            if(transactionRemarks.toUpperCase().contains("UNNAMALAI")){
                housekeepingExpenses = housekeepingExpenses + AccountStatementList.get(i).withdrawalAmount;
                totalWithdrawals = totalWithdrawals + AccountStatementList.get(i).withdrawalAmount + AccountStatementList.get(i).depositAmount;
                AccountStatementList.get(i).entryCategory = "House Keeping";
            } else
            if(transactionRemarks.toUpperCase().contains("NETFLIX") || transactionRemarks.toUpperCase().contains("CAKE")
               || transactionRemarks.toUpperCase().contains("NITHYA2705")){
                entertainmentExpenses = entertainmentExpenses + AccountStatementList.get(i).withdrawalAmount;
                totalWithdrawals = totalWithdrawals + AccountStatementList.get(i).withdrawalAmount + AccountStatementList.get(i).depositAmount;
                AccountStatementList.get(i).entryCategory = "Entertainment";
            } else
            if ((transactionRemarks.contains("NEFT") && transactionRemarks.toUpperCase().contains("BNY MELLON"))
                || transactionRemarks.toUpperCase().contains("SALARY")){
                salaryIncome = salaryIncome + AccountStatementList.get(i).depositAmount;
                totalDeposits = totalDeposits + AccountStatementList.get(i).withdrawalAmount + AccountStatementList.get(i).depositAmount;
                AccountStatementList.get(i).entryCategory = "Salary";
            } else
            if(transactionRemarks.toUpperCase().contains("AMALFI2705/HIRANANDAN/UTIB00") || transactionRemarks.toUpperCase().contains("DTAX")
                    || transactionRemarks.toUpperCase().contains("/RDGOTN")
                    || transactionRemarks.toUpperCase().contains("AMALFI")
                    || transactionRemarks.toUpperCase().contains("/TNREGOTHDRC")
                    || transactionRemarks.toUpperCase().contains("/REAL VALUE")){
                totalInvestment = totalInvestment + AccountStatementList.get(i).withdrawalAmount;
                totalWithdrawals = totalWithdrawals + AccountStatementList.get(i).withdrawalAmount + AccountStatementList.get(i).depositAmount;
                AccountStatementList.get(i).entryCategory = "Investments";
            } else
            if(transactionRemarks.toUpperCase().contains("IRCTC") ){
                travelExpenses = travelExpenses + AccountStatementList.get(i).withdrawalAmount - AccountStatementList.get(i).depositAmount;
                totalWithdrawals = totalWithdrawals + AccountStatementList.get(i).withdrawalAmount + AccountStatementList.get(i).depositAmount;
                AccountStatementList.get(i).entryCategory = "Travel Expenses";
            } else
            if(transactionRemarks.contains("CMS/ SBIDIV") || transactionRemarks.toUpperCase().contains("DIVIDEND")
                    || transactionRemarks.toUpperCase().contains("ACH/")){
                dividendIncome = dividendIncome + AccountStatementList.get(i).depositAmount;
                totalDeposits = totalDeposits + AccountStatementList.get(i).withdrawalAmount + AccountStatementList.get(i).depositAmount;
                AccountStatementList.get(i).entryCategory = "Dividend Income";
            } else
            if (((transactionRemarks.contains("EBA/NSE")) && AccountStatementList.get(i).withdrawalAmount > 0) ||
                    (transactionRemarks.contains("BIL/ONL/") && transactionRemarks.toUpperCase().contains("/ZERODHA") && AccountStatementList.get(i).withdrawalAmount > 0))    {
                totalBuyTrades = totalBuyTrades + AccountStatementList.get(i).withdrawalAmount;
                totalWithdrawals = totalWithdrawals + AccountStatementList.get(i).withdrawalAmount + AccountStatementList.get(i).depositAmount;
                AccountStatementList.get(i).entryCategory = "Buy Trades";
            } else
            if (((transactionRemarks.contains("EBA/NSE")) && AccountStatementList.get(i).depositAmount > 0) ||
                    (transactionRemarks.contains("NEFT-") && transactionRemarks.toUpperCase().contains("FUND REDN")) ||
                    (transactionRemarks.contains("NEFT-") && transactionRemarks.toUpperCase().contains("ZERODHA BROKING") && AccountStatementList.get(i).depositAmount > 0)) {
                totalSellTrades = totalSellTrades + AccountStatementList.get(i).depositAmount;
                totalDeposits = totalDeposits + AccountStatementList.get(i).withdrawalAmount + AccountStatementList.get(i).depositAmount;
                AccountStatementList.get(i).entryCategory = "Sell Trades";
            } else
            if ((transactionRemarks.contains("BIL/INFT/")) && AccountStatementList.get(i).withdrawalAmount > 0) {
                transferOut = transferOut + AccountStatementList.get(i).withdrawalAmount;
                totalWithdrawals = totalWithdrawals + AccountStatementList.get(i).withdrawalAmount + AccountStatementList.get(i).depositAmount;
                AccountStatementList.get(i).entryCategory = "Transfer Out";
            } else
            if ((transactionRemarks.contains("BIL/INFT/")) && AccountStatementList.get(i).depositAmount > 0) {
                transferIn = transferIn + AccountStatementList.get(i).depositAmount;
                totalDeposits = totalDeposits + AccountStatementList.get(i).withdrawalAmount + AccountStatementList.get(i).depositAmount;
                AccountStatementList.get(i).entryCategory = "Transfer In";
            } else
            if(transactionRemarks.toUpperCase().contains("VPS/") || transactionRemarks.toUpperCase().contains("IPS/")
                    || (transactionRemarks.toUpperCase().contains("BIL/ONL/") && !transactionRemarks.toUpperCase().contains("3FIVE8 TEC"))
                    || transactionRemarks.toUpperCase().contains("BIL/REV PMT ID")) {
                shoppingEatout = shoppingEatout + AccountStatementList.get(i).withdrawalAmount - AccountStatementList.get(i).depositAmount;
                totalWithdrawals = totalWithdrawals + AccountStatementList.get(i).withdrawalAmount + AccountStatementList.get(i).depositAmount;
                totalDeposits = totalDeposits + AccountStatementList.get(i).withdrawalAmount + AccountStatementList.get(i).depositAmount;
                AccountStatementList.get(i).entryCategory = "Shopping and Eatout";
            }  else {
                Unknown = Unknown + AccountStatementList.get(i).withdrawalAmount + AccountStatementList.get(i).depositAmount;
                AccountStatementList.get(i).entryCategory = "Unknown";
                AccountStatement unknownEntry = new AccountStatement();
                unknownEntry = AccountStatementList.get(i);
                unknownList.add(unknownEntry);
            }
        }
        System.out.println("Based on the data from " + transactionDateLow + " to " + transactionDateHigh);
        monthsBetween = ChronoUnit.MONTHS.between(transactionDateLow,transactionDateHigh) + 1;

        if(accountType.equals("Sal1") && accountHolder.equals("Bun")){
            // Subtracting for Cots, ChimneyHob, TV, Mattress
            creditCardBill = creditCardBill - (93673 + 27700 + 62000 + 15000);
            totalInvestment = totalInvestment + 93673 + 27700 + 62000 + 15000;
        }
    }

    public String getTimePeriod(){
        return ("From " + transactionDateLow + " to " + transactionDateHigh);
    }
    public double getTotalSavings(){
        return totalSavings/monthsBetween;
    }
    public String getTotalSavingsFmtd(){
        return rf.formattedRupee(ft.format(totalSavings/monthsBetween));
    }
    public double getApartmentMaintenance(){
        return apartmentMaintenance/monthsBetween;
    }
    public double getElectricityBill(){
        return electricityBill/monthsBetween;
    }
    public double getCreditCardBill(){
        return creditCardBill/monthsBetween;
    }
    public double getSalaryIncome(){
        return salaryIncome/monthsBetween;
    }
    public double getInterestIncome(){
        return interestIncome/monthsBetween;
    }
    public double getSaleProceeds(){
        return saleProceeds/monthsBetween;
    }
    public double getCapitalGains(){
        return 0/monthsBetween;
    }
    public double getMonetaryGifts(){
        return 0/monthsBetween;
    }
    public double getMonthlyEMI(){
        return monthlyEMI/monthsBetween;
    }
    public double getRentalIncome(){
        return rentalIncome/monthsBetween;
    }
    public double getDividendIncome(){
        return dividendIncome/monthsBetween;
    }
    public double getBrokerageMaintenance(){
        return brokerageMaintenance/monthsBetween;
    }
    public double getHomeInsurance(){
        return homeInsurance/monthsBetween;
    }
    public double getTransferOut(){
        return transferOut/monthsBetween;
    }
    public double getTransferIn(){
        return transferIn/monthsBetween;
    }

    public double getEntertainmentExpenses(){
        return entertainmentExpenses/monthsBetween;
    }
    public double getCashWithdrawals(){
        return cashWithdrawals/monthsBetween;
    }
    public double getUnknown(){
        return Unknown/monthsBetween;
    }
    public double getTotalWithdrawals(){
        return totalWithdrawals/monthsBetween;
    }
    public double getTotalDeposits(){
        return totalDeposits/monthsBetween;
    }
    public double getTotalInvestments(){
        return totalInvestment/monthsBetween;
    }
    public double getHousekeepingExpenses(){
        return housekeepingExpenses/monthsBetween;
    }
    public double getTotalIncome(){
        return (salaryIncome + dividendIncome + interestIncome + rentalIncome + saleProceeds)/monthsBetween;
    }
    public String getTotalIncomeFmtd(){
        return rf.formattedRupee(ft.format(this.getTotalIncome()));
    }
    public double getGroceryExpenses(){
        return groceries/monthsBetween;
    }
    public double getTravelExpense(){
        return travelExpenses/monthsBetween;
    }
    public double getShoppingExpense(){
        return shoppingEatout/monthsBetween;
    }
    public double getFamilyExpenses(){
        return forFamily/monthsBetween;
    }
    public double getTotalBuyTrades(){
        return totalBuyTrades/monthsBetween;
    }
    public double getTotalSellTrades(){
        return totalSellTrades/monthsBetween;
    }
    public double getTotalBrokerage(){
        return brokerageMaintenance/monthsBetween;
    }
    public double getTotalExpenses(){
        return (apartmentMaintenance + electricityBill + creditCardBill
                + brokerageMaintenance + homeInsurance + cashWithdrawals + groceries + travelExpenses + forFamily + shoppingEatout
                + entertainmentExpenses + housekeepingExpenses + totalInvestment + monthlyEMI)/monthsBetween;
    }
    public String getTotalExpensesFmtd(){
        return rf.formattedRupee(ft.format(this.getTotalExpenses()));
    }
    public double getUnknownAmt(){
        return Unknown;
    }
    public ArrayList<AccountStatement> getUnknownList(){
        return unknownList;
    }
}
