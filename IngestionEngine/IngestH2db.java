package IngestionEngine;

import CommonModules.RupeeFormatter;
import CommonModules.ChartOfAccounts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class IngestH2db{
    private static final String QUERY = "select BALTYPE ,BALSUBTYPE ,BALDESCCODE ,BALAMOUNT from BALSHEET where BALACTIVEIND = ?";
    protected ChartOfAccounts[] transferData() {
        int bsIterator = 0;
        DecimalFormat ft = new DecimalFormat("Rs ##,##,##0.00");
        RupeeFormatter rf = new RupeeFormatter();
        ChartOfAccounts[] chartOfAccountsList = new ChartOfAccounts[100];

        // Step 1: Establishing a Connection
        try (Connection connection = H2JDBCconnect.getConnection();
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY);) {
            char myChar = 'A';
            preparedStatement.setNString(1,String.valueOf(myChar));
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                //Instantiate an Object for each individual member of Array
                chartOfAccountsList[bsIterator] = new ChartOfAccounts();

                chartOfAccountsList[bsIterator].typeAssetOrLiability = rs.getString("BALTYPE");
                chartOfAccountsList[bsIterator].subType = rs.getString("BALSUBTYPE");
                chartOfAccountsList[bsIterator].itemDescription = rs.getString("BALDESCCODE");
                chartOfAccountsList[bsIterator].cashValue = rs.getDouble("BALAMOUNT");
                chartOfAccountsList[bsIterator].cashValueFmtd = rf.formattedRupee(ft.format(chartOfAccountsList[bsIterator].cashValue));
                bsIterator++;
            }
        } catch (SQLException e) {
            H2JDBCconnect.printSQLException(e);
        }
        // Return the array of data ingested from DB to the processing layer
        ChartOfAccounts.numofElements = bsIterator;
        return chartOfAccountsList;
    }

}