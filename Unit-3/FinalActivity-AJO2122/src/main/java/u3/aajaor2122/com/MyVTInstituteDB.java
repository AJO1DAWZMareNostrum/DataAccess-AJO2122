package u3.aajaor2122.com;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyVTInstituteDB {

    static final String url = "jdbc:postgresql://localhost:5432/VTInstitute";
    static final String user = "postgres";
    static final String pass = "dandy123.,"; // en EXAMEN preguntar password al profesor

    // QUERY FOR THE TAB 3
    static final String getStudentDataToPrint = "SELECT course.name, subjects.name, scores.score\n" +
            "FROM enrollment \n" +
            "INNER JOIN scores ON enrollment.code = scores.enrollmentid \n" +
            "INNER JOIN subjects ON subjects.code = scores.subjectid \n" +
            "INNER JOIN course ON course.code = subjects.course\n" +
            "WHERE student = 2424";

    public static List<Integer> getAllStudentIds() throws Exception {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            List<Integer> studentIDS = new ArrayList<>();

            ResultSet rsGetAllIds = conn.createStatement().executeQuery(SQLquerys.getAllStudentIds);
            while (rsGetAllIds.next()){
                    studentIDS.add(rsGetAllIds.getInt("idcard"));
            }

            return studentIDS;
        }
    }

    public static List<Course> getAllCourses() throws Exception {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            List<Course> courses = new ArrayList<>();

            ResultSet rsGetAllCourses = conn.createStatement().executeQuery(SQLquerys.getAllCoursesData);
            while (rsGetAllCourses.next()){
                Course course = new Course(rsGetAllCourses.getInt("code"),
                                            rsGetAllCourses.getString("name"));

                courses.add(course);
            }

            return courses;
        }
    }

    public static void insertStudent(String name, String surname, int id, String email, String phone)
        throws Exception {
            try (Connection conn = DriverManager.getConnection(url, user, pass)) {

                // MAYBE REUSABLE in Tab 3
                PreparedStatement psCheckStudent = conn.prepareStatement(SQLquerys.checkStudentID);
                psCheckStudent.setInt(1, id);
                ResultSet rsCheckStudent = psCheckStudent.executeQuery();
                rsCheckStudent.next();
                int resultCheckId = rsCheckStudent.getInt(1);
                if (resultCheckId != 0) {
                    throw new Exception("Student already exists in the DB.");
                }

                PreparedStatement psInsert = conn.prepareStatement(SQLquerys.insertStudent);
                psInsert.setString(1, name);
                psInsert.setString(2, surname);
                psInsert.setInt(3, id);
                psInsert.setString(4, email);
                psInsert.setString(5, phone);

                psInsert.executeUpdate();
            }
    }

    public static void insertEnrollment(int studentId, int courseId) throws Exception {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            // First check - that it´s NOT now enrolled or hasn´t been already enrolled in this course
            PreparedStatement psEnrollCourses = conn.prepareStatement(SQLquerys.getAllEnrolledCourses);
            psEnrollCourses.setInt(1, studentId);
            ResultSet rsEnrollCourses = psEnrollCourses.executeQuery();
            while (rsEnrollCourses.next()){
                int retrievedCourseId = rsEnrollCourses.getInt(1);

                if (courseId == retrievedCourseId) {
                    throw new Exception("The student has previously been enrolled in this course");
                }
            }

            // Second check - Student it isn´t enrolled in ANY course at this moment
            PreparedStatement psCheckScore = conn.prepareStatement(SQLquerys.checkStudentScore);
            psCheckScore.setInt(1, studentId);
            ResultSet rsCheckScore = psCheckScore.executeQuery();
            rsCheckScore.next();

            int scoreChecked = rsCheckScore.getInt(1);
            if (scoreChecked != 0) {
                throw new Exception("Student is already enrolled in one course");
            }

            String generatedColumns[] = { "code" };
            PreparedStatement psInsertEnrollment = conn.prepareStatement(SQLquerys.insertEnrollmentSQL, generatedColumns);
            psInsertEnrollment.setInt(1, studentId);
            psInsertEnrollment.setInt(2, courseId);
            psInsertEnrollment.executeUpdate();
            ResultSet rsIdEnroll = psInsertEnrollment.getGeneratedKeys();
            rsIdEnroll.next();
            int idEnroll = rsIdEnroll.getInt(1);

            // SETS UP every subject´s course score to 0
            PreparedStatement psSubjectsInCourse = conn.prepareStatement(SQLquerys.getAllSubjectsInCourse);
            psSubjectsInCourse.setInt(1, courseId);
            ResultSet rsSubjectsInCourse = psSubjectsInCourse.executeQuery();
            while (rsSubjectsInCourse.next()){
                int retrievedSubjectId = rsSubjectsInCourse.getInt(1);

                PreparedStatement psInsertScore = conn.prepareStatement(SQLquerys.insertIntoScoresSQL);
                psInsertScore.setInt(1, idEnroll);
                psInsertScore.setInt(2, retrievedSubjectId);

                psInsertScore.executeUpdate();
            }

        }
    }

}
