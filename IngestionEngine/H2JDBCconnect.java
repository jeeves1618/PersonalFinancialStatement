package IngestionEngine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2JDBCconnect{
    //URL for database in embedded mode
    private static String jdbcEmbedURL = "jdbc:h2:~/test";
    //url for database in server mode
    private static String jdbcURL = "jdbc:h2:tcp://localhost/~/test";
    private static String jdbcUsername = "sa";
    private static String jdbcPassword = "yajiv";

    public static Connection getConnection(){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return connection;
    }
    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}