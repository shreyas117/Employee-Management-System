<%@page import="java.sql.*"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%

// Database credentials and URL
String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
String username = "dmeher"; // Replace with your username
String password = "ivertoot"; // Replace with your password

// Get the transfer details from the request
String fromSSN = request.getParameter("ssn1");
String toSSN = request.getParameter("ssn2");
int amount; // Variable to hold the parsed transfer amount

try {
    // Parse the amount to an integer
    amount = Integer.parseInt(request.getParameter("amount"));
} catch (NumberFormatException e) {
    out.print("Invalid transfer amount.");
    return; // Stop further execution if the amount is not valid
}

// SQL INSERT query using placeholders for parameters
String query = "INSERT INTO Transfer (from_ssn, to_ssn, transfer_date, amount) VALUES (?, ?, SYSDATE, ?)";

// Try-with-resources to ensure proper resource management
try (Connection con = DriverManager.getConnection(url, username, password);
     PreparedStatement pstmt = con.prepareStatement(query)) {

    // Set the parameters in the PreparedStatement
    pstmt.setString(1, fromSSN);
    pstmt.setString(2, toSSN);
    pstmt.setInt(3, amount); // Use setInt for the amount

    // Execute the INSERT operation
    int rowsAffected = pstmt.executeUpdate();

    // Check if the INSERT was successful
    if (rowsAffected > 0) {
        out.print("Transfer completed successfully.");
    } else {
        out.print("Transfer failed.");
    }

} catch (SQLException e) {
    // Handle SQL exceptions
    out.print("SQL Error: " + e.getMessage());
    // Log the exception
} catch (Exception e) {
    // Handle other exceptions
    out.print("Error: " + e.getMessage());
    // Log the exception
}
%>
