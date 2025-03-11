<%@page import="java.sql.*"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%

String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
String username = "dmeher";
String password = "ivertoot";
String transId = request.getParameter("txnid");

// SQL query uses placeholders for parameters to prevent SQL injection.
String query = "SELECT t.t_date AS transaction_date, " +
               "t.amount AS transaction_amount, " +
               "p.prod_name AS product_name, " +
               "p.p_price AS product_price, " +
               "tp.quantity AS quantity " +
               "FROM Transactions t " +
               "JOIN Txns_Prods tp ON t.trans_id = tp.trans_id " +
               "JOIN Products p ON tp.prod_id = p.prod_id " +
               "WHERE t.trans_id = ?";

try (Connection con = DriverManager.getConnection(url, username, password);
     PreparedStatement stmt = con.prepareStatement(query)) {
    
    stmt.setString(1, transId); // Set the transaction ID in the query.
    
    try (ResultSet rs = stmt.executeQuery()) {
        StringBuilder result = new StringBuilder();
        while (rs.next()) {
            result.append(rs.getDate("transaction_date"))  // Get the transaction date
                  .append(", ")
                  .append(rs.getBigDecimal("transaction_amount"))  // Get the transaction amount
                  .append(", ")
                  .append(rs.getString("product_name"))  // Get the product name
                  .append(", ")
                  .append(rs.getBigDecimal("product_price"))  // Get the product price
                  .append(", ")
                  .append(rs.getInt("quantity"))  // Get the quantity
                  .append("#");  // Add a separator for the records
        }

        if (result.length() == 0) {
            out.print("No transaction details found for the given ID.");
        } else {
            out.print(result.toString());
        }
    }
} catch (SQLException e) {
    out.print("SQL Error: " + e.getMessage());
    // Log the error to server logs or database.
}
%>
