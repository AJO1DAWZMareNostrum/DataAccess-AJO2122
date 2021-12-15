package u3.aajaor2122.com;

/**
 *  Class contaning all the SQL querys that are used in the program, to achieve a cleaner
 *  and more organized code, and avoid overloading the other classes with content
 */
public class SQLquerys {

    static final String insertStudent = "INSERT into student VALUES(?, ?, ?, ?, ?)";
    static final String checkStudentID = "SELECT COUNT(*) FROM student WHERE idcard = ?";
    static final String checkStudentScore = "SELECT COUNT (scores.score) FROM enrollment " +
            "INNER JOIN scores ON enrollment.code = scores.enrollmentid \n" +
            "INNER JOIN subjects ON subjects.code = scores.subjectid \n" +
            "WHERE student = ? AND scores.score < 5";

    static final String getAllStudentIds = "SELECT idcard FROM student";
    static final String getAllCoursesData = "SELECT * FROM course";

    static final String insertEnrollmentSQL = "INSERT INTO enrollment (student, course) VALUES (?, ?)";
    static final String getAllEnrolledCourses =
            "SELECT course.code\n" +
                    "FROM course \n" +
                    "INNER JOIN enrollment ON enrollment.course = course.code \n" +
                    "WHERE student = ?";
    static final String getAllSubjectsInCourse = "SELECT code FROM subjects WHERE course = ?";
    static final String insertIntoScoresSQL = "INSERT INTO scores (enrollmentid, subjectid, score) VALUES (?, ?, 0)";

    // QUERY FOR THE TAB 3
    static final String getStudentDataToPrint = "SELECT course.name, subjects.name, scores.score\n" +
            "FROM enrollment \n" +
            "INNER JOIN scores ON enrollment.code = scores.enrollmentid \n" +
            "INNER JOIN subjects ON subjects.code = scores.subjectid \n" +
            "INNER JOIN course ON course.code = subjects.course\n" +
            "WHERE student = ?";

    static final String insertCourse = "INSERT INTO course (code, name) VALUES (?, ?)";
    static final String insertSubject = "INSERT INTO subjects (code, name, year, hours, course) VALUES (?, ?, ?, ?, ?)";

    static final String getStudentFullName = "SELECT firstname, lastname FROM student WHERE idcard = ?";
}
