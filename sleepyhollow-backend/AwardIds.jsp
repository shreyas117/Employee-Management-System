<%@page import="java.sql.*"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%

// Set the content type for the response
response.setContentType("text/html;charset=UTF-8");

// Database credentials and URL
String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
String username = "dmeher"; // Update with your username
String password = "ivertoot"; // Update with your password

// Get the SSN from the request
String ssn = request.getParameter("ssn");

// SQL query using a placeholder for the SSN
String query = "SELECT DISTINCT g.award_id FROM GRANTED g WHERE g.ssn = ?";

// Initialize an empty StringBuilder for the result
StringBuilder result = new StringBuilder();

// Try-with-resources to ensure proper resource management
try (Connection con = DriverManager.getConnection(url, username, password);
     PreparedStatement pstmt = con.prepareStatement(query)) {
    
    // Set the SSN parameter in the PreparedStatement
    pstmt.setString(1, ssn);
    
    // Execute the query
    try (ResultSet rs = pstmt.executeQuery()) {
        
        // Loop through the ResultSet
        while (rs.next()) {
            // Append each award_id to the result with a comma separator
            result.append(rs.getString("award_id")).append("#");
        }

        // Check if result is not empty
        if (result.length() == 0) {
            // If result is empty, set appropriate message
            result.append("No awards found for the given SSN.");
        }
        
        // Print the result
        out.print(result.toString());
        
    } catch (SQLException e) {
        // Handle SQL exceptions during query execution
        out.print("SQL Error: " + e.getMessage());
        // Log the exception
    }
} catch (Exception e) {
    // Handle other exceptions such as unable to get connection
    out.print("Error: " + e.getMessage());
    // Log the exception
}
%>
