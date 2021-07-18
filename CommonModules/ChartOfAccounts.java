package CommonModules;

/*
Companies use a chart of accounts (COA) to organize their finances and give interested parties,
such as investors and shareholders, a clearer insight into their financial health.
Separating expenditures, revenue, assets, and liabilities help to achieve this and
ensure that financial statements are in compliance with reporting standards.
 */

public class ChartOfAccounts {
    public static int numofElements;
    public int identificationNumber;
    public String typeAssetOrLiability;
    public String subType;
    public String itemDescription;
    public double cashValue;
    public String cashValueFmtd;
    public String financialStatement;
    public String isAssetLiquidInd;

    public ChartOfAccounts(){
        this.typeAssetOrLiability = "typeAssetOrLiability";
        this.identificationNumber = 1010000;
        this.subType = "subType";
        this.itemDescription = "itemDescription";
        this.cashValue = 0.0d;
        this.cashValueFmtd = "Rs 0.00";
        this.financialStatement = "BalanceSheetOrIncomeStatementOrCashFlow";
    }
}