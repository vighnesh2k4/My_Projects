package servletsJDBC;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;



@WebServlet("/DeleteServlet")
public class DeleteServlet extends GenericServlet {

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
		String email= request.getParameter("email");
		String password= request.getParameter("password");
		PrintWriter out = response.getWriter(); 
		try {
			Statement st=conn.createStatement();
			int result= st.executeUpdate("delete from user where email='"+email+"' and password='"+password+"';");
			if(result>0) {
				out.println("<h2>User Deleted</h2>");
			}
			else {
				out.println("<h2>Error while Deleting user from the database</h2>");
			}
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
