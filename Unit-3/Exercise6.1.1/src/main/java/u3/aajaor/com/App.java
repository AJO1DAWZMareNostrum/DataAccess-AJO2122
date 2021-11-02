package u3.aajaor.com;

import org.apache.commons.dbutils.DbUtils;

import java.sql.*;
import java.util.Scanner;

public class App 
{
    static final String url = "jdbc:postgresql://localhost:5432/VTInstitute";
    static final String user = "postgres";
    static final String password = "dandy123.,";
    static final String SQLSentence = "INSERT INTO subjects VALUES (DEFAULT, ?, ?)";

    public static void main( String[] args )
    {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            Class.forName("org.postgresql.Driver");

            // We connect to the database and make a while loop to insert Subjects into the database
            con = DriverManager.getConnection(url, user, password);
            ps = con.prepareStatement(SQLSentence);
            Scanner sc = new Scanner(System.in);
            int totalUpdates = 0;
            String yesOrNo;

            do {
                System.out.println("Enter the name of the subject or asignature: ");
                String name = sc.nextLine();
                System.out.println("Enter the number of the course on which is imparted (1 or 2): ");
                int curso = Integer.parseInt(sc.nextLine());

                ps.setString(1, name);
                ps.setInt(2, curso);
                int result = ps.executeUpdate();
                totalUpdates++;
                System.out.println(result + " of the rows has been updated.");

                System.out.println("Do you want to insert another subject in the table (y/n)");
                yesOrNo = sc.nextLine();
            } while (!yesOrNo.equalsIgnoreCase("n"));

            System.out.println("Total number of rows inserted: " + totalUpdates);

        } catch (ClassNotFoundException e) {
            System.out.println("The class hasn´t been found!");
        } catch (SQLException e) {
            System.out.println("Some kind of SQL error has occurred!");
            e.printStackTrace();
        }
        finally {
            DbUtils.closeQuietly(con);
            DbUtils.closeQuietly(ps);
        }
    }
}
