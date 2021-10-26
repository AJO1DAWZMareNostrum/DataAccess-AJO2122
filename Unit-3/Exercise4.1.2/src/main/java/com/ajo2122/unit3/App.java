package com.ajo2122.unit3;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            String url = "jdbc:postgresql://localhost:5432/VTInstitute";
            String user = "postgres";
            String password = "dandy123.,";

            // Connect to the database, and send a INSERT statement
            Connection con = DriverManager.getConnection(url, user, password);
            Statement statement = con.createStatement();
            String SQLsentence = "INSERT INTO subjects VALUES (DEFAULT, 'MARKUP LANGUAGES', '1')";
            int subjectsModified = statement.executeUpdate(SQLsentence);

            // Prints the number of values modified correctly
            System.out.println("Number of values modified: " + subjectsModified);
        } catch (SQLException ex) {
            System.out.println("An error has ocurred (see in the log results)");
            ex.printStackTrace();
        }
    }
}
