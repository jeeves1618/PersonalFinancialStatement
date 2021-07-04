package IncomeStatement;

import CommonModules.AccountStatement;
import CommonModules.NaturalLanguageProcessor;
import CommonModules.RupeeFormatter;
import IngestionEngine.IngestNLPExcel;

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
    private double educationExpenses;
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

        //Read the NLP Tokens from the spreadsheet
        String tokenFileWithPathname = "C:\\dev\\Data\\NLP.xlsx";
        IngestNLPExcel tokenizer = new IngestNLPExcel(tokenFileWithPathname);
        ArrayList<NaturalLanguageProcessor> tokenDescriptionMapper;
        tokenDescriptionMapper = tokenizer.transferData();

        for (int i=0; i < AccountStatement.numofElements; i++){
            String transactionRemarks = AccountStatementList.get(i).transactionRemarks;

            LocalDate transactionDate = LocalDate.parse(AccountStatementList.get(i).transactionDate, formatter);
            if (transactionDateHigh.isBefore(transactionDate))
                transactionDateHigh = transactionDate;
            if (transactionDateLow.isAfter(transactionDate))
                transactionDateLow = transactionDate;

            totalWithdrawals = totalWithdrawals + AccountStatementList.get(i).withdrawalAmount;
            totalDeposits = totalDeposits + AccountStatementList.get(i).depositAmount;

            for (int j=0; j < NaturalLanguageProcessor.numofElements; j++){
                if(transactionRemarks.toUpperCase().contains(tokenDescriptionMapper.get(j).tokenizedWord)){
                    AccountStatementList.get(i).entryCategory = tokenDescriptionMapper.get(j).entryCategory;
                    break;
                }
            }

            if (AccountStatementList.get(i).entryCategory == null){
                AccountStatementList.get(i).entryCategory = "Default";
                System.out.println("AccountStatementList.get(i).entryCategory: " + AccountStatementList.get(i).entryCategory + transactionRemarks);
            }
            switch (AccountStatementList.get(i).entryCategory) {
                case "Savings":
                    totalSavings = totalSavings + AccountStatementList.get(i).withdrawalAmount - AccountStatementList.get(i).depositAmount;
                    break;
                case "Rental Income":
                    rentalIncome = rentalIncome + AccountStatementList.get(i).depositAmount;
                    break;
                case "Brokerage Maintenance":
                    brokerageMaintenance = brokerageMaintenance + AccountStatementList.get(i).withdrawalAmount;
                    break;
                case "Family":
                    forFamily = forFamily + AccountStatementList.get(i).withdrawalAmount - AccountStatementList.get(i).depositAmount;
                    break;
                case "Monthly EMI":
                    monthlyEMI = monthlyEMI + AccountStatementList.get(i).withdrawalAmount;
                    break;
                case "Bookentries":
                    break;
                case "Cash Withdrawals":
                    cashWithdrawals = cashWithdrawals + AccountStatementList.get(i).withdrawalAmount;
                    break;
                case "Apartment Maintenance":
                    apartmentMaintenance = apartmentMaintenance + AccountStatementList.get(i).withdrawalAmount;
                    break;
                case "Electricity Expenses":
                    electricityBill = electricityBill + AccountStatementList.get(i).withdrawalAmount;
                    break;
                case "Groceries":
                    groceries = groceries + AccountStatementList.get(i).withdrawalAmount;
                    break;
                case "Interest Income":
                    interestIncome = interestIncome + AccountStatementList.get(i).depositAmount - AccountStatementList.get(i).withdrawalAmount;
                    break;
                case "Sale Proceeds":
                    saleProceeds = saleProceeds + AccountStatementList.get(i).depositAmount;
                    break;
                case "Miscellaneous":
                    creditCardBill = creditCardBill + AccountStatementList.get(i).withdrawalAmount;
                    break;
                case "House Keeping":
                    housekeepingExpenses = housekeepingExpenses + AccountStatementList.get(i).withdrawalAmount;
                    break;
                case "Entertainment":
                    entertainmentExpenses = entertainmentExpenses + AccountStatementList.get(i).withdrawalAmount;
                    break;
                case "Salary":
                    salaryIncome = salaryIncome + AccountStatementList.get(i).depositAmount;
                    break;
                case "Investments":
                    totalInvestment = totalInvestment + AccountStatementList.get(i).withdrawalAmount;
                    break;
                case "Travel Expenses":
                    travelExpenses = travelExpenses + AccountStatementList.get(i).withdrawalAmount - AccountStatementList.get(i).depositAmount;
                    break;
                case "Dividend Income":
                    dividendIncome = dividendIncome + AccountStatementList.get(i).depositAmount;
                    break;
                case "Capital Market Transactions":
                    totalBuyTrades = totalBuyTrades + AccountStatementList.get(i).withdrawalAmount;
                    totalSellTrades = totalSellTrades + AccountStatementList.get(i).depositAmount;
                    break;
                case "Shopping and Eatout":
                    shoppingEatout = shoppingEatout + AccountStatementList.get(i).withdrawalAmount - AccountStatementList.get(i).depositAmount;
                    break;
                case "Insurance":
                    homeInsurance = homeInsurance + AccountStatementList.get(i).withdrawalAmount - AccountStatementList.get(i).depositAmount;
                    break;
                case "Education":
                    educationExpenses = educationExpenses + AccountStatementList.get(i).withdrawalAmount - AccountStatementList.get(i).depositAmount;
                    break;
                default:
                    Unknown = Unknown + AccountStatementList.get(i).withdrawalAmount + AccountStatementList.get(i).depositAmount;
                    AccountStatementList.get(i).entryCategory = "Unknown";
                    AccountStatement unknownEntry = new AccountStatement();
                    unknownEntry = AccountStatementList.get(i);
                    unknownList.add(unknownEntry);
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
    public double getEducationExpenses(){
        return educationExpenses/monthsBetween;
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
                + entertainmentExpenses + housekeepingExpenses + totalInvestment + monthlyEMI + educationExpenses)/monthsBetween;
    }
    public double getTotalNonDiscretionExpenses(){
        return (apartmentMaintenance + electricityBill + creditCardBill
                + brokerageMaintenance + homeInsurance + cashWithdrawals + groceries + travelExpenses + forFamily + shoppingEatout
                + entertainmentExpenses + housekeepingExpenses + educationExpenses);
    }
    public String getTotalExpensesFmtd(){
        return rf.formattedRupee(ft.format(this.getTotalExpenses()));
    }
    public double getUnknownAmt(){
        return Unknown;
    }
    public long getMonthsBetween(){
        return monthsBetween;
    }
    public ArrayList<AccountStatement> getUnknownList(){
        return unknownList;
    }
}