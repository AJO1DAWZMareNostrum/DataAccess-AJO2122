package u3.aajaor2122.com;

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
}
