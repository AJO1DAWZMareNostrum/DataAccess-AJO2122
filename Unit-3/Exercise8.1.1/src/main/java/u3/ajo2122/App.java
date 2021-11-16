package u3.ajo2122;

import java.sql.*;
import java.util.Scanner;

public class App
{
    static final String url = "jdbc:postgresql://localhost:5432/employees";
    static final String user = "postgres";
    static final String password = "dandy123.,";

    public static void main( String[] args )
            throws ClassNotFoundException, SQLException
    {
        try (Connection conn = DriverManager.getConnection(url, user, password))
        {
            conn.setAutoCommit(false);

            try {
                String departName, employeeName, deptNumber;
                Scanner sc = new Scanner(System.in);

                System.out.println("Introduce the Name of the new Employee: ");
                employeeName = sc.nextLine().toUpperCase();
                System.out.println("Introduce the Department in which the new employee works: ");
                departName = sc.nextLine().toUpperCase();
                System.out.println("Introduce the departmen ID number: ");
                deptNumber = sc.nextLine();


                PreparedStatement prst = conn.prepareStatement("SELECT COUNT(dname) FROM dept WHERE dname = ?");
                prst.setString(1, "'" + departName + "'");
                ResultSet rs = prst.executeQuery();

                if (!rs.next()) {
                    PreparedStatement prst2 = conn.prepareStatement("INSERT INTO dept (deptno, dname, loc) VALUES (?, ?, ?)");
                    prst2.setString(1, "50");
                    prst2.setString(2, departName);
                    prst2.setString(3, "CALIFORNIA");
                    prst2.executeUpdate();
                }

                PreparedStatement prst3 = conn.prepareStatement("INSERT INTO employee (empno, ename, job, deptno) VALUES (?, ?, ?, ?)");
                prst3.setString(1, "7938");
                prst3.setString(2, employeeName);
                prst3.setString(3, departName);
                prst3.setString(4, deptNumber);
                prst3.executeUpdate();

                System.out.println("The transaction has been completed sucessfully!");
                conn.commit();

            } catch (SQLException e) {
                System.err.println(e.getMessage());
                conn.rollback();
            }

        }
    }
}
