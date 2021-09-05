package IncomeStatement;

import CommonModules.AccountStatement;
import CommonModules.NaturalLanguageProcessor;
import CommonModules.RupeeFormatter;
import IngestionEngine.IngestNLPExcel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

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
    private double dLearningExpenses;
    private double healthCareExpenses;
    private double discretionaryExpenses;
    private double nonDiscretionaryExpenses;
    private double Unknown;
    private String datetime;
    long monthsBetween;
    private static LocalDate transactionDateHigh = LocalDate.parse("0001-01-01");
    private LocalDate transactionDateLow  = LocalDate.parse("9999-12-31");
    //private LocalDate transactionDate;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    ResourceBundle properties  = ResourceBundle.getBundle("Properties");
    String currencyFormat = properties.getString("currencyFormat");
    DecimalFormat ft = new DecimalFormat(currencyFormat);
    RupeeFormatter rf = new RupeeFormatter();
    ArrayList<AccountStatement> unknownList = new ArrayList<>();
    ArrayList<AccountStatement> requestedList = new ArrayList<>();
    ArrayList<AccountStatement> AccountStatementList;

    Date dateNow;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss z");

    public ExpenseCalculator(String accountHolder, String accountType) throws IOException {

        ResourceBundle properties  = ResourceBundle.getBundle("Properties");

        String fileWithPathname = properties.getString(accountHolder + accountType);
        System.out.println("File being accessed: " + fileWithPathname);
        IngestionEngine.IngestStatementExcel balanceSheet = new IngestionEngine.IngestStatementExcel(fileWithPathname);
        //IngestH2db balanceSheet = new IngestH2db();

        AccountStatementList = balanceSheet.transferData();

        //Read the NLP Tokens from the spreadsheet
        String tokenFileWithPathname = properties.getString("NLPtokenizer");

        IngestNLPExcel tokenizer = new IngestNLPExcel(tokenFileWithPathname);
        ArrayList<NaturalLanguageProcessor> tokenDescriptionMapper;
        tokenDescriptionMapper = tokenizer.transferData();

        for (int i=0; i < AccountStatement.numofElements; i++) {

            LocalDate transactionDate = LocalDate.parse(AccountStatementList.get(i).transactionDate, formatter);
            if (transactionDateHigh.isBefore(transactionDate))
                transactionDateHigh = transactionDate;
            if (transactionDateLow.isAfter(transactionDate))
                transactionDateLow = transactionDate;
        }

        transactionDateLow = transactionDateHigh.minusYears(1l).plusDays(1l);
        for (int i=0; i < AccountStatement.numofElements; i++){
            String transactionRemarks = AccountStatementList.get(i).transactionRemarks;

            totalWithdrawals = totalWithdrawals + AccountStatementList.get(i).withdrawalAmount;
            totalDeposits = totalDeposits + AccountStatementList.get(i).depositAmount;

            for (int j=0; j < NaturalLanguageProcessor.numofElements; j++){
                if(transactionRemarks.toUpperCase().contains(tokenDescriptionMapper.get(j).tokenizedWord)){
                    AccountStatementList.get(i).entryCategory = tokenDescriptionMapper.get(j).entryCategory;
                    AccountStatementList.get(i).discretionarySpendingIndicator = tokenDescriptionMapper.get(j).discretionarySpendingIndicator;
                    /*updateNLPLastUsedDate(tokenDescriptionMapper.get(j).rowNumber,tokenDescriptionMapper.get(j).columnNumber);*/
                    break;
                }
            }

            if (AccountStatementList.get(i).entryCategory == null){
                AccountStatementList.get(i).entryCategory = "Default";
                System.out.println("AccountStatementList.get(i).entryCategory: " + AccountStatementList.get(i).entryCategory + transactionRemarks);
            }
            LocalDate transactionDate = LocalDate.parse(AccountStatementList.get(i).transactionDate, formatter);
            if (transactionDate.isBefore(transactionDateHigh.minusYears(1l).plusDays(1l))){
                ;
            } else
            {
                switch (AccountStatementList.get(i).entryCategory) {
                    case "Savings":
                        totalSavings = totalSavings + AccountStatementList.get(i).withdrawalAmount - AccountStatementList.get(i).depositAmount;
                        break;
                    case "Rental Income":
                        rentalIncome = rentalIncome + AccountStatementList.get(i).depositAmount;
                        break;
                    case "Brokerage Maintenance":
                        brokerageMaintenance = brokerageMaintenance + AccountStatementList.get(i).withdrawalAmount;
                        splitEntries(i, AccountStatementList.get(i).withdrawalAmount);
                        break;
                    case "Family":
                        forFamily = forFamily + AccountStatementList.get(i).withdrawalAmount - AccountStatementList.get(i).depositAmount;
                        splitEntries(i, AccountStatementList.get(i).withdrawalAmount - AccountStatementList.get(i).depositAmount);
                        break;
                    case "Monthly EMI":
                        monthlyEMI = monthlyEMI + AccountStatementList.get(i).withdrawalAmount;
                        splitEntries(i, AccountStatementList.get(i).withdrawalAmount );
                        break;
                    case "Bookentries":
                        break;
                    case "Cash Withdrawals":
                        cashWithdrawals = cashWithdrawals + AccountStatementList.get(i).withdrawalAmount - AccountStatementList.get(i).depositAmount;
                        splitEntries(i, AccountStatementList.get(i).withdrawalAmount - AccountStatementList.get(i).depositAmount);
                        break;
                    case "Apartment Maintenance":
                        apartmentMaintenance = apartmentMaintenance + AccountStatementList.get(i).withdrawalAmount;
                        splitEntries(i, AccountStatementList.get(i).withdrawalAmount );
                        break;
                    case "Electricity Expenses":
                        electricityBill = electricityBill + AccountStatementList.get(i).withdrawalAmount;
                        splitEntries(i, AccountStatementList.get(i).withdrawalAmount );
                        break;
                    case "Groceries":
                        groceries = groceries + AccountStatementList.get(i).withdrawalAmount;
                        splitEntries(i, AccountStatementList.get(i).withdrawalAmount );
                        break;
                    case "Interest Income":
                        interestIncome = interestIncome + AccountStatementList.get(i).depositAmount - AccountStatementList.get(i).withdrawalAmount;
                        break;
                    case "Sale Proceeds":
                        saleProceeds = saleProceeds + AccountStatementList.get(i).depositAmount;
                        break;
                    case "Miscellaneous":
                        creditCardBill = creditCardBill + AccountStatementList.get(i).withdrawalAmount;
                        splitEntries(i, AccountStatementList.get(i).withdrawalAmount );
                        break;
                    case "House Keeping":
                        housekeepingExpenses = housekeepingExpenses + AccountStatementList.get(i).withdrawalAmount;
                        splitEntries(i, AccountStatementList.get(i).withdrawalAmount );
                        break;
                    case "Entertainment":
                        entertainmentExpenses = entertainmentExpenses + AccountStatementList.get(i).withdrawalAmount;
                        splitEntries(i, AccountStatementList.get(i).withdrawalAmount );
                        break;
                    case "Salary":
                        salaryIncome = salaryIncome + AccountStatementList.get(i).depositAmount;
                        break;
                    case "Investments":
                        totalInvestment = totalInvestment + AccountStatementList.get(i).withdrawalAmount;
                        splitEntries(i, AccountStatementList.get(i).withdrawalAmount );
                        break;
                    case "Travel Expenses":
                        travelExpenses = travelExpenses + AccountStatementList.get(i).withdrawalAmount - AccountStatementList.get(i).depositAmount;
                        splitEntries(i, AccountStatementList.get(i).withdrawalAmount - AccountStatementList.get(i).depositAmount);
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
                        splitEntries(i, AccountStatementList.get(i).withdrawalAmount - AccountStatementList.get(i).depositAmount);
                        break;
                    case "Insurance":
                        homeInsurance = homeInsurance + AccountStatementList.get(i).withdrawalAmount - AccountStatementList.get(i).depositAmount;
                        splitEntries(i, AccountStatementList.get(i).withdrawalAmount - AccountStatementList.get(i).depositAmount);
                        break;
                    case "Education":
                        educationExpenses = educationExpenses + AccountStatementList.get(i).withdrawalAmount - AccountStatementList.get(i).depositAmount;
                        splitEntries(i, AccountStatementList.get(i).withdrawalAmount - AccountStatementList.get(i).depositAmount);
                        break;
                    case "Discretionary Learning":
                        dLearningExpenses = dLearningExpenses + AccountStatementList.get(i).withdrawalAmount - AccountStatementList.get(i).depositAmount;
                        splitEntries(i, AccountStatementList.get(i).withdrawalAmount - AccountStatementList.get(i).depositAmount);
                        break;
                    case "Healthcare and Fitness":
                        healthCareExpenses = healthCareExpenses + AccountStatementList.get(i).withdrawalAmount - AccountStatementList.get(i).depositAmount;
                        splitEntries(i, AccountStatementList.get(i).withdrawalAmount - AccountStatementList.get(i).depositAmount);
                        break;
                    default:
                        Unknown = Unknown + AccountStatementList.get(i).withdrawalAmount + AccountStatementList.get(i).depositAmount;
                        AccountStatementList.get(i).entryCategory = "Unknown";
                        AccountStatement unknownEntry = new AccountStatement();
                        unknownEntry = AccountStatementList.get(i);
                        unknownList.add(unknownEntry);
                }
                if (AccountStatementList.get(i).transactionRemarks.contains("CITIN21227484176")){
                    System.out.println("Sherlock :" + AccountStatementList.get(i).entryCategory);
                }
            }
        }
        System.out.println("Based on the data from " + transactionDateLow + " to " + transactionDateHigh);
        monthsBetween = ChronoUnit.MONTHS.between(transactionDateLow,transactionDateHigh) + 1;
    }
    void splitEntries(int i, double amt){
        if (AccountStatementList.get(i).discretionarySpendingIndicator.equals("Y")){
            discretionaryExpenses = discretionaryExpenses + amt;
        } else {
            nonDiscretionaryExpenses = nonDiscretionaryExpenses + amt;
        }
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
    public double getdLearningExpenses(){
        return dLearningExpenses/monthsBetween;
    }
    public double getdHealthCareExpenses(){
        return healthCareExpenses/monthsBetween;
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
                + entertainmentExpenses + housekeepingExpenses + totalInvestment + monthlyEMI + educationExpenses + healthCareExpenses
                + dLearningExpenses)/monthsBetween;
    }
    public double getTotalNonDiscretionExpenses(){
        //EMI Should not be included because this function is invoked for Accounts Payable in Balance Sheet.
        //EMI was already added there
        return (apartmentMaintenance + electricityBill + creditCardBill
                + brokerageMaintenance + homeInsurance + cashWithdrawals + groceries + travelExpenses + forFamily + shoppingEatout
                + entertainmentExpenses + housekeepingExpenses + educationExpenses + healthCareExpenses);
    }
    public double getNonDiscretionaryExpenses(){
        return nonDiscretionaryExpenses/monthsBetween;
    }
    public double getDiscretionaryExpenses(){
        return discretionaryExpenses/monthsBetween;
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
    public ArrayList<AccountStatement> getPayables(String entryCategory){
        AccountStatement requestedEntry = new AccountStatement();

        for (int i=0; i < AccountStatement.numofElements; i++) {
            LocalDate transactionDate = LocalDate.parse(AccountStatementList.get(i).transactionDate, formatter);
            if (transactionDate.isBefore(transactionDateHigh.minusYears(1l).plusDays(1l))){
                System.out.println("Before");
            } else{
                requestedEntry = AccountStatementList.get(i);
                requestedList.add(requestedEntry);

            }
        }
        return requestedList;
    }
    public void updateNLPLastUsedDate(int row, int col) throws IOException {
        try {
            String tokenFileWithPathname = properties.getString("NLPtokenizer");
            FileInputStream file = new FileInputStream(tokenFileWithPathname);

            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Cell cell = null;

            //Update the value of cell
            dateNow = new Date();
            datetime = dateFormat.format(dateNow);
            System.out.println(datetime);
            cell = sheet.getRow(row).getCell(col);
            cell.setCellValue(datetime);

            file.close();

            FileOutputStream outFile =new FileOutputStream(new File(tokenFileWithPathname));
            workbook.write(outFile);
            outFile.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}