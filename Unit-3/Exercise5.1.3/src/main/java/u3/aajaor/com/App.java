package u3.aajaor.com;

import org.apache.commons.dbutils.DbUtils;

import java.sql.*;

public class App 
{
    static final String url = "jdbc:postgresql://localhost:5432/VTInstitute";
    static final String user = "postgres";
    static final String password = "dandy123.,";
    static final String SQLSentence = "ALTER TABLE subjects ADD hours varchar(3)";

    public static void main( String[] args )
    {
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            Class.forName("org.postgresql.Driver");

            // We connect to the database and make the query
            con = DriverManager.getConnection(url, user, password);
            statement = con.createStatement();

            statement.execute(SQLSentence);

            String SQLsentenceAll = "SELECT * FROM subjects";
            rs = statement.executeQuery(SQLsentenceAll);
        } catch (ClassNotFoundException e) {
            System.out.println("The class hasn´t been found!");
        } catch (SQLException e) {
            System.out.println("Some kind of SQL error has occurred!");
            e.printStackTrace();
        }
        finally {
            DbUtils.closeQuietly(con);
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(rs);
        }
    }
}
