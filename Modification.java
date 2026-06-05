import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Modification{
    private DatabaseConnection database;
    private Insertion insert;


    public Modification(){
        database = new DatabaseConnection();
        database.getConnection();
        insert = new Insertion();
    }

    /**
     * 
     * @param investorID
     * @return
     */
    public String[] findInvestorById(int investorID) {
        String query = "SELECT * FROM INVESTOR WHERE InvestorID = ?";

        try (Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, investorID);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return new String[] {
                    String.valueOf(rs.getInt("InvestorID")),
                    rs.getString("FirstName"),
                    rs.getString("LastName"),
                    rs.getString("Email"),
                    rs.getString("PhoneNumber")
                };
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 
     * @param investorID
     * @param firstName
     * @param lastName
     * @param email
     * @param phoneNumber
     * @return
     */
    public boolean updateInvestor(int investorID, String firstName, String lastName, String email, String phoneNumber) {
        String query = "UPDATE INVESTOR SET FirstName = ?, LastName = ?, Email = ?, PhoneNumber = ? WHERE InvestorID = ?";

        try (Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, phoneNumber);
            statement.setInt(5, investorID);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 
     * @param companyID
     * @param companyName
     * @param industry
     * @param headquarters
     * @param foundedYear
     * @return
     */
    public boolean updateCompany(int companyID, String companyName, String industry, String headquarters, Integer foundedYear) {
        String query = "UPDATE COMPANY SET CompanyName = ?, Industry = ?, Headquarters = ?, FoundedYear = ? WHERE CompanyID = ?";

        try (Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, companyName);
            statement.setString(2, industry);
            statement.setString(3, headquarters);
            statement.setObject(4, foundedYear);
            statement.setInt(5, companyID);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 
     * @param companyID
     * @return
     */
    public String[] findCompanyById(int companyID) {
        String query = "SELECT * FROM COMPANY WHERE CompanyID = ?";

        try (Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, companyID);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return new String[] {
                    String.valueOf(rs.getInt("CompanyID")),
                    rs.getString("CompanyName"),
                    rs.getString("Industry"),
                    rs.getString("Headquarters"),
                    rs.getString("FoundedYear")
                };
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 
     * @param stockID
     * @return
     */
    public String[] findStockById(int stockID) {
        String query =
            "SELECT s.StockID, c.CompanyName, s.TickerSymbol, " +
            "s.ExchangeName, s.CurrentPrice " +
            "FROM STOCK s " +
            "JOIN COMPANY c ON s.CompanyID = c.CompanyID " +
            "WHERE s.StockID = ?";

        try (Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, stockID);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return new String[] {
                    String.valueOf(rs.getInt("StockID")),
                    rs.getString("CompanyName"),
                    rs.getString("TickerSymbol"),
                    rs.getString("ExchangeName"),
                    String.valueOf(rs.getDouble("CurrentPrice"))
                };
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 
     * @param stockID
     * @param companyName
     * @param tickerSymbol
     * @param exchangeName
     * @param currentPrice
     * @return
     */
    public boolean updateStock(int stockID, String companyName, String tickerSymbol, String exchangeName, double currentPrice) {
        String query = "UPDATE STOCK SET CompanyID = ?, TickerSymbol = ?, ExchangeName = ?, CurrentPrice = ? WHERE StockID = ?";

        int companyID = insert.getCompanyIdByName(companyName);

        if (companyID == 0) {
            System.out.println("Company not found.");
            return false;
        }

        try (Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, companyID);
            statement.setString(2, tickerSymbol);
            statement.setString(3, exchangeName);
            statement.setDouble(4, currentPrice);
            statement.setInt(5, stockID);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}