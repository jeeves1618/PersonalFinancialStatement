package admin;

import org.apache.commons.configuration2.ex.ConfigurationException;

public class CurrencyCustomizationTest {
    public static void main(String[] args) {
        String currencyFormat = new CurrencyCustomization().getCurrencyFormat();
        System.out.println(currencyFormat);
        CurrencyCustomization c = new CurrencyCustomization();

        try {
            c.customizeCurrency("testToken", "Order Parotta");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }
}
