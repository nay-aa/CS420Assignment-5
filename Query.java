import java.sql.*;
import java.util.ArrayList;

public class Query {
    private DatabaseConnection database;

    public Query() {
        database = new DatabaseConnection();
        database.getConnection();
    }

    /**
     * 
     * @return
     */
    public ArrayList<String> getAllInvestors() {
        String query = "SELECT InvestorID, FirstName, LastName FROM INVESTOR";
        ArrayList<String> investors = new ArrayList<>();
        investors.add("");

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                investors.add(
                    rs.getInt("InvestorID") + " - " +
                    rs.getString("FirstName") + " " +
                    rs.getString("LastName")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return investors;
    }

    /**
     * 
     * @return
     */
    public ArrayList<String> getAllIndustries() {
        String query = "SELECT DISTINCT Industry FROM COMPANY ORDER BY Industry";
        ArrayList<String> industries = new ArrayList<>();
        industries.add("All");

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                industries.add(rs.getString("Industry"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return industries;
    }

    /**
     * 
     * @param investorChoice
     * @param tradeType
     * @param sortOption
     * @return
     */
    public Object[][] getTransactionsByInvestor(String investorChoice, String tradeType, String sortOption) {
        ArrayList<Object[]> rows = new ArrayList<>();

        int investorID = Integer.parseInt(investorChoice.split(" - ")[0]);

        String sql =
            "SELECT t.TradeDate, t.TradeType, s.TickerSymbol, c.CompanyName, " +
            "c.Industry, t.Quantity, t.PricePerShare " +
            "FROM INVESTOR i " +
            "JOIN BROKERAGEACCOUNT b ON i.InvestorID = b.InvestorID " +
            "JOIN TRADETRANSACTION t ON b.AccountID = t.AccountID " +
            "JOIN STOCK s ON t.StockID = s.StockID " +
            "JOIN COMPANY c ON s.CompanyID = c.CompanyID " +
            "WHERE i.InvestorID = ? ";

        if (!tradeType.equals("All")) {
            sql += "AND t.TradeType = ? ";
        }

        if (sortOption.equals("Date newest first")) {
            sql += "ORDER BY t.TradeDate DESC";
        } else if (sortOption.equals("Date oldest first")) {
            sql += "ORDER BY t.TradeDate ASC";
        } else {
            sql += "ORDER BY t.Quantity DESC";
        }

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, investorID);

            if (!tradeType.equals("All")) {
                statement.setString(2, tradeType);
            }

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                rows.add(new Object[] {
                    rs.getDate("TradeDate"),
                    rs.getString("TradeType"),
                    rs.getString("TickerSymbol"),
                    rs.getString("CompanyName"),
                    rs.getString("Industry"),
                    rs.getInt("Quantity"),
                    rs.getDouble("PricePerShare")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows.toArray(new Object[0][]);
    }

    /**
     * 
     * @param industry
     * @param topN
     * @return
     */
    public Object[][] getTopStocksByVolume(String industry, String topN) {
        ArrayList<Object[]> rows = new ArrayList<>();

        String sql =
            "SELECT s.TickerSymbol, c.CompanyName, c.Industry, " +
            "SUM(t.Quantity) AS TotalSharesTraded, " +
            "COUNT(t.TransactionID) AS NumberOfTransactions " +
            "FROM STOCK s " +
            "JOIN TRADETRANSACTION t ON s.StockID = t.StockID " +
            "JOIN COMPANY c ON s.CompanyID = c.CompanyID ";

        if (!industry.equals("All")) {
            sql += "WHERE c.Industry = ? ";
        }

        sql +=
            "GROUP BY s.StockID, s.TickerSymbol, c.CompanyName, c.Industry " +
            "ORDER BY TotalSharesTraded DESC ";

        if (!topN.equals("All")) {
            sql += "LIMIT ?";
        }

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            int paramIndex = 1;

            if (!industry.equals("All")) {
                statement.setString(paramIndex, industry);
                paramIndex++;
            }

            if (!topN.equals("All")) {
                statement.setInt(paramIndex, Integer.parseInt(topN));
            }

            ResultSet rs = statement.executeQuery();

            int rank = 1;

            while (rs.next()) {
                rows.add(new Object[] {
                    rank,
                    rs.getString("TickerSymbol"),
                    rs.getString("CompanyName"),
                    rs.getString("Industry"),
                    rs.getInt("TotalSharesTraded"),
                    rs.getInt("NumberOfTransactions")
                });

                rank++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows.toArray(new Object[0][]);
    }
}
