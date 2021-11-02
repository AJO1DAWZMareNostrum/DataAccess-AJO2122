package u3.aajaor.com;

import org.apache.commons.dbutils.DbUtils;

import java.sql.*;

public class App 
{
    public static void main( String[] args )
    {
        String url = "jdbc:postgresql://localhost:5432/VTInstitute";
        String user = "postgres";
        String password = "dandy123.,";
        String SQLcreate = "CREATE TABLE courses (code SERIAL, name VARCHAR(90) )";
        String SQLinsert = "INSERT INTO courses VALUES (DEFAULT, ?)";
        int totalInserted = 0;

        Connection con = null;
        PreparedStatement prst = null;

        try {
            Class.forName("org.postgresql.Driver");

            // Connects to the database and creates the 'courses' table
            con = DriverManager.getConnection(url, user, password);
            prst = con.prepareStatement(SQLcreate);
            prst.executeUpdate();
            System.out.println("The table has been created.");

            prst = con.prepareStatement(SQLinsert);
            prst.setString(1, "Multiplatform app development");
            int i1 = prst.executeUpdate();
            System.out.println(i1 + " course has been added to the table");
            if (i1 > 0)
                totalInserted++;

            prst = con.prepareStatement(SQLinsert);
            prst.setString(1, "Web development");
            int i2 = prst.executeUpdate();
            System.out.println(i2 + " course has been added to the table");
            if (i2 > 0)
                totalInserted++;

            System.out.println("\nThe 'course' table has been created, with " + totalInserted + " new rows.");
        } catch (ClassNotFoundException e) {
            System.out.println("The class hasn´t been found!");
        } catch (SQLException e) {
            System.out.println("Some kind of SQL error has occurred!");
            e.printStackTrace();
        }
        finally {
            DbUtils.closeQuietly(con);
            DbUtils.closeQuietly(prst);
        }
    }
}
