package u3.aajaor2122.com;

import org.apache.commons.dbutils.DbUtils;
import java.sql.*;

public class App 
{
    static final String url = "jdbc:postgresql://localhost:5432/VTInstitute";
    static final String user = "postgres";
    static final String password = "dandy123.,";
    static final String SQLSentence = "SELECT * FROM subjects ORDER BY code";

    public static void main( String[] args ) throws ClassNotFoundException, SQLException
    {
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            Class.forName("org.postgresql.Driver");

            // We connect to the database and make the query
            con = DriverManager.getConnection(url, user, password);
            statement = con.createStatement();
            rs = statement.executeQuery(SQLSentence);

            // The table is printed with the query result
            System.out.println("Code" + "\t" + "Name" + "\t\t\t" + "Año curso");
            System.out.println("-----------------------------------------------------");
            while (rs.next()) {
                System.out.println(rs.getString(1) + "\t" + rs.getString(2)
                        + "\t" + rs.getString(3));
            }
        }
        catch (SQLException e) {
            System.out.println("Some kind of error has occurred!");
            e.printStackTrace();
        }
        finally {
            DbUtils.closeQuietly(con);
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(rs);
        }
    }
}
