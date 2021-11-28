package u3.aajaor2122.com;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *  Class that hosts all the methods to achieve the DB functionality in the program: insert data, select specific
 *  results of data through querys.
 *
 *  Gives all the necessary functionality to the program and connection with the database.
 */
public class MyVTInstituteDB {

    static final String url = "jdbc:postgresql://localhost:5432/VTInstitute";
    static final String user = "postgres";
    static final String pass = "dandy123.,"; // en EXAMEN preguntar password al profesor

    /**
     * Retrieves all the student ids registered into the database
     *
     * @return   all the student´s ids registered into our database
     * @throws Exception  an error to retrieve all the Student ids
     */
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

    /**
     * Retrieves all of the courses id´s and name´s registered into the database
     *
     * @return id´s and name´s of all courses in the database
     * @throws Exception  an error to retrieve all the Courses data
     */
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

    /**
     * Method that allows to insert a new Student into the database
     *
     * @param name  student´s name
     * @param surname   student´s surname
     * @param id    student´s id
     * @param email student´s email
     * @param phone student´s phone number
     * @throws Exception  an error at the time of inserting the new student
     */
    public static void insertStudent(String name, String surname, int id, String email, String phone)
        throws Exception {
            try (Connection conn = DriverManager.getConnection(url, user, pass)) {

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

    /**
     * Method that allows to enroll a registered Student into a given Course of our database
     *
     * @param studentId student´s id to enroll
     * @param courseId  course in which the student will be enrolled
     * @throws Exception    an error at the time of enrolling the student into the selected course
     */
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

    /**
     * Method that retrieves specific data about a student from our database, that later will
     * be showed to the user when he selects the student´s id
     *
     * @param idstudent  student´s id selected by the user
     * @return  a string with the prepared data: course name, subjects name and scores
     * @throws Exception  an error to retrieve the selected data from the student
     */
    public static String printStudentString(int idstudent) throws Exception {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {

            String studentResult = "";

            PreparedStatement psStudentData = conn.prepareStatement(SQLquerys.getStudentDataToPrint);
            psStudentData.setInt(1, idstudent);
            ResultSet rsStudentData = psStudentData.executeQuery();
            while (rsStudentData.next()) {
                studentResult += rsStudentData.getString(1) + " - " +
                        rsStudentData.getString(2) + ": " +
                        rsStudentData.getInt(3) + "\n";
            }

            return studentResult;
        }
    }

    /**
     * Method to insert all the parsed data from the XML file into the database. It works as a transaction:
     * it only will commit the insert if all the operations are completed without errors.
     *
     * @param studentList   list of students parsed from the XML file
     * @param courseList    list of courses parsed from the XML file
     * @param subjectList   list of subjects parsed from the XML file
     * @throws SQLException  some SQL error at the time of inserting the parsed data
     */
    public static void importXMLfile (List<Student> studentList, List<Course> courseList,
                                      List<Subject> subjectList) throws SQLException {

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            try {
                conn.setAutoCommit(false);
                for (Student student : studentList) {
                    PreparedStatement psInsert = conn.prepareStatement(SQLquerys.insertStudent);
                    psInsert.setString(1, student.getFirstName());
                    psInsert.setString(2, student.getLastName());
                    psInsert.setInt(3, student.getIdCard());
                    psInsert.setString(4, student.getEmail());
                    psInsert.setString(5, student.getPhone());

                    psInsert.executeUpdate();
                }
                for (Course course : courseList) {
                    PreparedStatement psInsert = conn.prepareStatement(SQLquerys.insertCourse);
                    psInsert.setInt(1, course.getCode());
                    psInsert.setString(2, course.getName());
                    psInsert.executeUpdate();
                }
                for (Subject subject : subjectList) {
                    PreparedStatement psInsert = conn.prepareStatement(SQLquerys.insertSubject);
                    psInsert.setInt(1, subject.getId());
                    psInsert.setString(2, subject.getName());
                    psInsert.setInt(3, subject.getYear());
                    psInsert.setInt(4, subject.getHours());
                    psInsert.setInt(5, subject.getCourseId());
                    psInsert.executeUpdate();
                }
                conn.commit();
                conn.close();
            } catch (Exception ex) {
                conn.rollback();
                throw ex;
            }
        }
    }

}
