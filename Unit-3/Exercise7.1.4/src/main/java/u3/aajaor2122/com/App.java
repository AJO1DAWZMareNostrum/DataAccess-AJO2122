package u3.aajaor2122.com;

import java.sql.*;

public class App 
{
    static final String url = "jdbc:postgresql://localhost:5432/employees";
    static final String user = "postgres";
    static final String password = "dandy123.,";

    public static void main( String[] args )
            throws ClassNotFoundException, SQLException
    {
        try ( Connection con = DriverManager.getConnection(url, user, password))
        {
            CallableStatement statement = con.prepareCall( "{call employees_in_a_department(30)}");
            ResultSet rs = statement.executeQuery();

            System.out.println("Number" + "\t" + "Name" + "\t" + "Job" + "\t\t" + "Department");
            System.out.println("---------------------------------------------------------------------");
            while (rs.next()) {
                System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" +
                        rs.getString(3) + "\t\t" + rs.getString(4));
            }
        } catch (SQLException e) {
            System.out.println("Some kind of SQL error has occurred!");
            e.printStackTrace();
        }
    }
}
