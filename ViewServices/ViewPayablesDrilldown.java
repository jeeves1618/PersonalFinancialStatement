package ViewServices;

import CommonModules.AccountStatement;
import CommonModules.RupeeFormatter;
import IncomeStatement.ExpenseCalculator;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * This class should take the category as input and list down all transactions under that category
 */
public class ViewPayablesDrilldown {
    String entryCategory;
    private int numberOfEntries;
    ExpenseCalculator e1;
    ExpenseCalculator e2;
    ExpenseCalculator e3;
    ExpenseCalculator e4;
    ArrayList<AccountStatement> requestedList = new ArrayList<>();

    public ViewPayablesDrilldown(String entryCategory) {
        this.entryCategory = entryCategory;
    }

    public ArrayList<AccountStatement> getPayables() throws ParseException, IOException {
        DecimalFormat ft = new DecimalFormat("Rs ##,##,##0.00");
        RupeeFormatter rf = new RupeeFormatter();
        e1 = new ExpenseCalculator("Two", "Sal1");
        ListIterator<AccountStatement> requestIterator = e1.getPayables(entryCategory).listIterator();
        AccountStatement temp;
        while (requestIterator.hasNext()) {
            temp = requestIterator.next();
            if (temp.entryCategory.equals(entryCategory)){
                requestedList.add(temp);
                numberOfEntries++;
            }
        }
        e2 = new ExpenseCalculator("One", "Sal1");
        requestIterator = e2.getPayables(entryCategory).listIterator();
        while (requestIterator.hasNext()) {
            temp = requestIterator.next();
            if (temp.entryCategory.equals(entryCategory)){
                requestedList.add(temp);
                numberOfEntries++;
            }
        }
        if (this.entryCategory.equals("Dividend Income") || this.entryCategory.equals("Interest Income"))
        {
            e3 = new ExpenseCalculator("One", "Sav1");
            requestIterator = e3.getPayables(entryCategory).listIterator();
            while (requestIterator.hasNext()) {
                temp = requestIterator.next();
                if (temp.entryCategory.equals(entryCategory)){
                    requestedList.add(temp);
                    numberOfEntries++;
                }
            }
            e4 = new ExpenseCalculator("Two", "Sav1");
            requestIterator = e4.getPayables(entryCategory).listIterator();
            while (requestIterator.hasNext()) {
                temp = requestIterator.next();
                if (temp.entryCategory.equals(entryCategory)){
                    requestedList.add(temp);
                    numberOfEntries++;
                }
            }
        }
        return requestedList;
    }
    public int getNumberOfEntries() {
        return numberOfEntries;
    }
}
