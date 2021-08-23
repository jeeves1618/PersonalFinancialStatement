package CommonModules;

import org.apache.poi.ss.usermodel.Cell;

public class NaturalLanguageProcessor {
    public static int numofElements;
    public int identificationNumber;
    public String tokenizedWord;
    public String entryCategory;
    public String discretionarySpendingIndicator;
    public String lastUsedDate;
    public int columnNumber;
    public int rowNumber;

    public NaturalLanguageProcessor() {
        this.identificationNumber = 1010000;
        this.tokenizedWord = "tokenizedWord";
        this.entryCategory = "entryCategory";
        this.discretionarySpendingIndicator = "N";
        this.lastUsedDate = "Not Used";
        this.columnNumber = 0;
        this.rowNumber = 0;
    }
}
