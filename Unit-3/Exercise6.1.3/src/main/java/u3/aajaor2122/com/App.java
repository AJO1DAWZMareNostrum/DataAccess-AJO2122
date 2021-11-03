package u3.aajaor2122.com;

import org.apache.commons.dbutils.DbUtils;

import java.sql.*;

public class App 
{
    public static void main( String[] args )
    {
        String url = "jdbc:postgresql://localhost:5432/VTInstitute";
        String user = "postgres";
        String password = "dandy123.,";
        String SQLforeignKey = "ALTER TABLE subjects " +
                    "ADD CONSTRAINT fk_subjects_courses FOREIGN KEY (course) REFERENCES courses (code)";

        Connection con = null;
        PreparedStatement prst = null;
        ResultSet rs = null;

        try {
            Class.forName("org.postgresql.Driver");

            con = DriverManager.getConnection(url, user, password);
            prst = con.prepareStatement(SQLforeignKey);
            prst.executeUpdate();

            System.out.println("The corresponding fields have been modified to be a foreign key.");

        } catch (ClassNotFoundException e) {
            System.out.println("The class hasn´t been found!");
        } catch (SQLException e) {
            System.out.println("Some kind of SQL error has occurred!");
            e.printStackTrace();
        }
        finally {
            DbUtils.closeQuietly(con);
            DbUtils.closeQuietly(prst);
            DbUtils.closeQuietly(rs);
        }
    }
}
