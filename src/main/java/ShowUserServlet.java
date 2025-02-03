import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/ShowUserServlet")
public class ShowUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String query = "SELECT userId, userName, email, mobile, dob, city, gender FROM employee";

	public ShowUserServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter pw = response.getWriter();
		response.setContentType("text/html");

		// Include Bootstrap CSS for styling
		pw.println(
				"<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css'>");

		// Page Title
		pw.println("<div class='container'>");
		pw.println("<h2 class='text-center text-primary my-4'>User Details</h2>");

		try {
			// Load the MySQL JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Create a connection to the database
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_web_data", "root", "");

			// Prepare and execute the SQL query
			PreparedStatement psmt = con.prepareStatement(query);
			ResultSet rs = psmt.executeQuery();

			// Generate the table
			pw.println("<div style='margin:auto;width:90%;'>");
			pw.println(
					"<table class='table table-bordered table-striped table-hover shadow' style='border-radius: 8px;'>");
			pw.println("<thead class='thead-light'>");
			pw.println("<tr style='text-align: center; font-weight: bold;'>");
			pw.println("<th>ID</th>");
			pw.println("<th>Name</th>");
			pw.println("<th>Email</th>");
			pw.println("<th>Mobile No</th>");
			pw.println("<th>DOB</th>");
			pw.println("<th>City</th>");
			pw.println("<th>Gender</th>");
			pw.println("<th>Edit</th>");
			pw.println("<th>Delete</th>");
			pw.println("</tr>");
			pw.println("</thead>");
			pw.println("<tbody>");

			while (rs.next()) {
				pw.println("<tr style='text-align: center;'>");
				pw.println("<td>" + rs.getInt(1) + "</td>");
				pw.println("<td>" + rs.getString(2) + "</td>");
				pw.println("<td>" + rs.getString(3) + "</td>");
				pw.println("<td>" + rs.getString(4) + "</td>");
				pw.println("<td>" + rs.getString(5) + "</td>");
				pw.println("<td>" + rs.getString(6) + "</td>");
				pw.println("<td>" + rs.getString(7) + "</td>");
				pw.println("<td><a href='EditScreenServlet?id=" + rs.getInt(1)
						+ "' class='btn btn-outline-warning btn-sm'>Edit</a></td>");
				pw.println("<td><a href='DeleteServlet?id=" + rs.getInt(1)
						+ "' class='btn btn-outline-danger btn-sm'>Delete</a></td>");
				pw.println("</tr>");
			}
			pw.println("</tbody>");
			pw.println("</table>");
			pw.println("</div>");

		} catch (ClassNotFoundException e) {
			pw.println("<p class='text-danger text-center'>Error: Unable to load JDBC driver.</p>");
			e.printStackTrace();
		} catch (SQLException e) {
			pw.println("<p class='text-danger text-center'>Error: Unable to connect to the database.</p>");
			e.printStackTrace();
		}

		// Add a Home button
		pw.println("<div class='text-center my-4'>");
		pw.println("<a href='Home.html' class='btn btn-outline-success'>Home</a>");
		pw.println("</div>");
		pw.println("</div>");

		// Close the PrintWriter
		pw.close();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
