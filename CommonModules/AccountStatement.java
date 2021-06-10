package CommonModules;

public class AccountStatement{
    public static int numofElements;
    public String serialNumber;
    public String valueDate;
    public String transactionDate;
    public String checkNumber;
    public String transactionRemarks;
    public double withdrawalAmount;
    public double depositAmount;
    public double balanceAmount;
    public String withdrawalAmountFmtd;
    public String depositAmountFmtd;
    public String balanceAmountFmtd;
    public String entryCategory;

    public AccountStatement(){
        this.valueDate = "23/09/1980";
        this.transactionDate = "23/09/1980";
        this.checkNumber = "00000";
        this.transactionRemarks = "Default Comments";
        this.withdrawalAmount = 0;
        this.depositAmount = 0;
        this.balanceAmount = 0;
    }
}