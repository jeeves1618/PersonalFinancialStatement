package CommonModules;

public class bsheetElements{
    public static int numofElements;
    public String typeAssetOrLiability;
    public String subType;
    public String itemDescription;
    public double cashValue;
    public String cashValueFmtd;

    public bsheetElements(){
        this.typeAssetOrLiability = "typeAssetOrLiability";
        this.subType = "subType";
        this.itemDescription = "itemDescription";
        this.cashValue = 0.0d;
        this.cashValueFmtd = "Rs 0.00";
    }
}