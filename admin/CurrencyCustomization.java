package admin;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;
import java.util.ResourceBundle;

public class CurrencyCustomization {
    String currencyFormat;
    String userMessage;
    public CurrencyCustomization(){
        userMessage = "Please note any currency conversion will require build, deployment and restart of server to reflect across all screens. Your change will not be reflected until then";
        ResourceBundle properties  = ResourceBundle.getBundle("Properties");
        currencyFormat = properties.getString("currencyFormat");
    }
    public String getCurrencyFormat(){
        return currencyFormat;
    }

    public String getMessage(){
        return userMessage;
    }
    public void customizeCurrency(String Key, String testToken) throws ConfigurationException {
        Configurations configs = new Configurations();
        File propertiesFile = new File("C:\\Dev\\FinancialStatements\\src\\main\\resources\\Properties.properties");
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.fileBased()
                                .setFile(propertiesFile));

        Configuration config = builder.getConfiguration();
        System.out.println("Should add logging: " + testToken);
        config.setProperty("currencyFormat",testToken);

        builder.save();
        userMessage = "Your currency is updated to the format " + testToken + ". Please build the code again, deploy the executables and restart the server.";
    }
}
