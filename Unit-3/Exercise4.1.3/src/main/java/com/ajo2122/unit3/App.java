package com.ajo2122.unit3;

import java.sql.*;

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
            String SQLsentence = "ALTER TABLE subjects ADD hours integer";
            boolean hoursFieldAdded = statement.execute(SQLsentence);

            // We show through a boolean if the field has been added to the table subjects
            System.out.println("Field has been added correctly to the table: " + hoursFieldAdded);
            // It has returned a false result, after creating the field correctly: I don´t understand why

        } catch (SQLException ex) {
            System.out.println("An error has ocurred (see in the log results)");
            ex.printStackTrace();
        }
    }
}
