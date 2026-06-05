import java.sql.*;
import java.util.*;

/**
 * DatabaseConnection contains all MySQL database functions for the stock-system software
 * 
 */
public class DatabaseConnection{
    private static final String URL = "jdbc:mysql://localhost:3306/stock_system";
    private static final String username = "root";
    private static final String password = "RootUser420";


    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(URL, username, password);
            System.out.println("successful connection!");
            return connection;
            
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
            return null;
        } catch(SQLException e){
            System.out.println("Database connection failed.");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * main method to test connection
     * @param args
     */
    public static void main(String[] args) {
        getConnection();
    }
}