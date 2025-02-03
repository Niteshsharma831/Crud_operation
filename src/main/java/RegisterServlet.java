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
import java.sql.SQLException;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter pw = response.getWriter();
        response.setContentType("text/html");

        String name = request.getParameter("userName");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        String dob = request.getParameter("dob");
        String city = request.getParameter("city");
        String gender = request.getParameter("gender");

        Connection con = null;
        PreparedStatement ps = null;

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Creating connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_web_data", "root", "");

            // Create statement
            ps = con.prepareStatement(
                    "insert into employee (username, email, mobile, dob, city, gender) values (?, ?, ?, ?, ?, ?)");

            // Set parameters
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, mobile);
            ps.setString(4, dob);
            ps.setString(5, city);
            ps.setString(6, gender);

            int count = ps.executeUpdate();
            if (count == 1) {
                // Show a success alert box and redirect to another page or home page
                pw.println("<script type='text/javascript'>");
                pw.println("alert('Record registered successfully');");
                pw.println("window.location.href = 'Home.html';"); // Redirect to the home page
                pw.println("</script>");
            } else {
                pw.println("<h2>Record not registered</h2>");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            pw.println("<h2>" + e.getMessage() + "</h2>");
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
