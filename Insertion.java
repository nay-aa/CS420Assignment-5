import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Insertion {
    private DatabaseConnection database;


    public Insertion(){
        database = new DatabaseConnection();
        database.getConnection();
    }

    /**
     * 
     * @param firstName
     * @param lastname
     * @param email
     * @param phoneNumber
     * @return
     */
    public boolean insertIntoInvestor(String firstName, String lastname, String email, String phoneNumber){
        String query = "INSERT INTO INVESTOR (FirstName, LastName, Email, PhoneNumber) VALUES (?, ?, ?, ?);";

        try (Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, firstName);
            statement.setString(2, lastname);
            statement.setString(3, email);
            statement.setString(4, phoneNumber);

            statement.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 
     * @param companyName
     * @param industry
     * @param headquarters
     * @param foundedYear
     * @return
     */
    public boolean insertIntoCompany(String companyName, String industry, String headquarters, Integer foundedYear){
        String query = "INSERT INTO COMPANY (CompanyName, Industry, Headquarters, FoundedYear) VALUES (?, ?, ?, ?);";

        try (Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, companyName);
            statement.setString(2, industry);
            statement.setString(3, headquarters);
            statement.setObject(4, foundedYear);

            statement.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 
     * @param companyName
     * @param tickerSymbol
     * @param exchangeName
     * @param currentPrice
     * @return
     */
    public boolean insertIntoStock(String companyName, String tickerSymbol, String exchangeName, double currentPrice){
        String query = "INSERT INTO STOCK (CompanyID, TickerSymbol, ExchangeName, CurrentPrice) VALUES (?, ?, ?, ?);";
        int companyID = getCompanyIdByName(companyName);
        try (Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, companyID);
            statement.setString(2, tickerSymbol);
            statement.setString(3, exchangeName);
            statement.setDouble(4, currentPrice);

            statement.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 
     * @param companyname
     * @return
     */
    public int getCompanyIdByName(String companyname){
        String query = "SELECT CompanyID FROM COMPANY WHERE CompanyName = ?";

        try (Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, companyname);

            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return rs.getInt("CompanyID");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 
     * @return
     */
    public ArrayList<String> getAllCompanies(){
        String query = "SELECT CompanyName FROM COMPANY";
        ArrayList<String> companies =  new ArrayList<>();
        companies.add("");
        try (Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                companies.add((rs.getString("CompanyName")));
            }
            return companies;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
