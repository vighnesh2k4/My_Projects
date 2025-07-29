package servletsJDBC;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@WebServlet(
	    urlPatterns = "/EmployeeServlet",
	    initParams = {
	        @WebInitParam(name = "dbUrl", value = "jdbc:mysql://localhost:3306/servlets"),
	        @WebInitParam(name = "dbUser", value = "root"),
	        @WebInitParam(name = "dbPass", value = "root")
	    }
	)
public class EmployeeServlet extends GenericServlet {

    private Connection conn;

    public void init(ServletConfig config) throws ServletException {
        try {
            String dbUrl = config.getInitParameter("dbUrl");
            String dbUser = config.getInitParameter("dbUser");
            String dbPass = config.getInitParameter("dbPass");

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        } catch (Exception e) {
            throw new ServletException("DB Connection error", e);
        }
    }

    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String designation = request.getParameter("designation");

        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO employee (id, name, designation) VALUES (?, ?, ?)")) {
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, designation);
            int result = ps.executeUpdate();

            if (result > 0) {
                out.println("<h2>Employee Added Successfully</h2>");
            } else {
                out.println("<h2>Failed to Add Employee</h2>");
            }
        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }

    public void destroy() {
        try {
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
