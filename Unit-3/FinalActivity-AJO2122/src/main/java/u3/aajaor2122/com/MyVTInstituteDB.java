package u3.aajaor2122.com;

import java.sql.*;

public class MyVTInstituteDB {

    static final String url = "jdbc:postgresql://localhost:5432/VTInstitute";
    static final String user = "postgres";
    static final String pass = "dandy123.,"; // en EXAMEN preguntar password al profesor

    public void insertStudent(String name, String surname, int id, String email, String phone)
        throws SQLException {
            try (Connection coon = DriverManager.getConnection(url, user, pass)) {
                PreparedStatement ps = coon.prepareStatement("INSERT into student VALUES(?, ?, ?, ?, ?)");
                ps.setString(1, name);
                ps.setString(2, surname);
                ps.setInt(3, id);
                ps.setString(4, email);
                ps.setString(5, phone);

                ps.executeUpdate();
            }
    }

}
