<%@page import="java.sql.*"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%

// Set the content type for the response
response.setContentType("text/html;charset=UTF-8");

// Database credentials and URL
String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
String username = "dmeher"; // Update with your username
String password = "ivertoot"; // Update with your password

// Get the award id and employee ssn from the request
String awardId = request.getParameter("awardid");
String ssn = request.getParameter("ssn");

// SQL query using placeholders for parameters to prevent SQL injection
String query = "SELECT g.award_date, ac.center_name " +
               "FROM GRANTED g " +
               "JOIN Award_Centers ac ON g.center_id = ac.center_id " +
               "WHERE g.award_id = ? AND g.ssn = ?";

// Initialize an empty StringBuilder for the result
StringBuilder result = new StringBuilder();

// Try-with-resources to ensure proper resource management
try (Connection con = DriverManager.getConnection(url, username, password);
     PreparedStatement pstmt = con.prepareStatement(query)) {
    
    // Set the parameters in the PreparedStatement
    pstmt.setString(1, awardId);
    pstmt.setString(2, ssn);
    
    // Execute the query
    try (ResultSet rs = pstmt.executeQuery()) {
        
        // Loop through the ResultSet
        if (rs.next()) {
            // Append the award_date and center_name to the result
            result.append(rs.getDate("award_date"))
                  .append(",")
                  .append(rs.getString("center_name"))
                  .append("#");
        } else {
            // If no result found, set appropriate message
            result.append("No details found for the given award ID and SSN.");
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
