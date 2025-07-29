package servletsJDBC;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;



@WebServlet("/ReadServlet")
public class ReadServlet extends GenericServlet {

	private Connection conn;
	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/servlets", "root", "root");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter(); 
		try {
			Statement st=conn.createStatement();
			ResultSet resultSet = st.executeQuery("select * from user;");
			out.println("<table border='1'>");  
	        out.println("<tr>");
	        out.println("<th>First Name</th>");
	        out.println("<th>Last Name</th>");
	        out.println("<th>Email</th>");
	        out.println("</tr>");
	        
	        while(resultSet.next()){
	            out.println("<tr>");
	            out.println("<td>" + resultSet.getString("firstname") + "</td>");
	            out.println("<td>" + resultSet.getString("lastname") + "</td>");
	            out.println("<td>" + resultSet.getString("email") + "</td>");
	            out.println("</tr>");
	        }
	        
	        out.println("</table>");
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public void destroy() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
