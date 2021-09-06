package admin;

import java.io.IOException;

public class CurrencyCustomizationTest {
    public static void main(String[] args) {
        try {
            NLPTokenManager ExpenseInstanceTwo = new NLPTokenManager();
            String refreshMessageText;
            refreshMessageText = ExpenseInstanceTwo.NLPTokenRefresh("One", "Sal1");
            refreshMessageText = ExpenseInstanceTwo.NLPTokenRefresh("Two", "Sal1");
            refreshMessageText = ExpenseInstanceTwo.NLPTokenRefresh("One", "Sav1");
            refreshMessageText = ExpenseInstanceTwo.NLPTokenRefresh("Two", "Sav1");
            System.out.println(refreshMessageText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
