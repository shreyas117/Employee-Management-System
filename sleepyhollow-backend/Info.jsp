<%@page import="java.sql.*"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%

String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
String username = "dmeher";
String password = "ivertoot";
String ssn = request.getParameter("ssn");

try (Connection conn = DriverManager.getConnection(url, username, password);
     PreparedStatement pstmt = conn.prepareStatement("SELECT e.name, es.total_sales FROM Employees e JOIN Emp_Sales es ON e.ssn = es.ssn WHERE e.ssn = ?")) {
    
    pstmt.setString(1, ssn);
    try (ResultSet rs = pstmt.executeQuery()) {
        StringBuilder result = new StringBuilder();
        while(rs.next()){
            result.append(rs.getString("name")).append(",").append(rs.getString("total_sales"));
        }

        if(result.length() == 0){
            out.print("Empty Result Set");
        } else {
            out.print(result.toString());
        }
    }
} catch (SQLException e) {
    out.print("SQL Error: " + e.getMessage());
    // Ideally, you would log this error rather than printing it to the output.
}
%>
