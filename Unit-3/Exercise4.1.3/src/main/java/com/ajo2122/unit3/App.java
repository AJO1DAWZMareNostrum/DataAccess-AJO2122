package com.ajo2122.unit3;

import java.sql.*;
import java.util.Scanner;

public class App 
{
    public static void main( String[] args )
    {
        try {
            String url = "jdbc:postgresql://localhost:5432/VTInstitute";
            String user = "postgres";
            String password = "dandy123.,";

            // Connects to the database, and executes the ALTER statement
            Connection con = DriverManager.getConnection(url, user, password);
            Statement statement = con.createStatement();
            String SQLsentence = "ALTER TABLE subjects ADD hours varchar(3)";
            statement.execute(SQLsentence);

            String SQLsentenceAll = "SELECT * FROM subjects";
            ResultSet rs = statement.executeQuery(SQLsentenceAll);

            // We ask the user, through a loop, to introduce the number of hours of each course
            Scanner sc = new Scanner(System.in);
            while (rs.next()) {
                System.out.print("Introduce the numbers of hours for the course " + rs.getString(2) + ": ");
                String hoursAdded = sc.nextLine();

                String SQLAddHours = "INSERT INTO subjects (hours) VALUES (" + hoursAdded + ")";
                statement.execute(SQLAddHours);
            }

            con.close();
        } catch (SQLException ex) {
            System.out.println("An error has ocurred (see in the log results)");
            ex.printStackTrace();
        }
    }
}
