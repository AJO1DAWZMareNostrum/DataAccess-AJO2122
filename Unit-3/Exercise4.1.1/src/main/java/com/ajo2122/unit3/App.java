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

            // We connect to the database and make the query
            Connection con = DriverManager.getConnection(url, user, password);
            Statement statement = con.createStatement();
            String SQLsentence = "SELECT * FROM subjects ORDER BY code";
            ResultSet rs = statement.executeQuery(SQLsentence);

            // The table is printed with the query result
            System.out.println("Code" + "\t" + "Name" + "\t\t\t" + "Año curso");
            System.out.println("-----------------------------------------------------");
            while (rs.next()) {
                System.out.println(rs.getString(1) + "\t" + rs.getString(2)
                                    + "\t" + rs.getString(3));
            }

            rs.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Some kind of error has ocurred.");
            e.printStackTrace();
        }
    }
}
