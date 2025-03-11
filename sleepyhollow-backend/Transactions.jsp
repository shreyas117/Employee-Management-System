<%@page import="java.sql.*"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%

// It's not typically necessary to register the driver manually for modern JDBC drivers.
// DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
String username = "dmeher";
String password = "ivertoot";
String ssn = request.getParameter("ssn");

// The query now uses a placeholder for the SSN value.
String query = "SELECT trans_id, t_date, amount FROM Transactions WHERE ssn = ?";

try (Connection con = DriverManager.getConnection(url, username, password);
     PreparedStatement stmt = con.prepareStatement(query)) {
    
    stmt.setString(1, ssn);
    try (ResultSet rs = stmt.executeQuery()) {
        StringBuilder result = new StringBuilder();
        while (rs.next()) {
            result.append(rs.getObject("trans_id"))
                  .append(",")
                  .append(rs.getObject("t_date"))
                  .append(",")
                  .append(rs.getObject("amount"))
                  .append("#");
        }

        if (result.length() == 0) {
            out.print("Empty Result Set");
        } else {
            // Remove the trailing '#' character.
            out.print(result.substring(0, result.length() - 1));
        }
    }
} catch (SQLException e) {
    out.print("SQL Error: " + e.getMessage());
    // Ideally, log this error to a file or database for review, rather than printing to the output.
}
%>
