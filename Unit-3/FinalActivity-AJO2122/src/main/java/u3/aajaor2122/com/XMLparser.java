package u3.aajaor2122.com;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


import java.util.ArrayList;
import java.util.List;

/** Class that provides a handler to the SAXParser object, indicating the approach in which
 * it must parse the XML document, and the elements/values we need to extract from it, that
 * later will be inserted into the database
 */
public class XMLparser extends DefaultHandler {

    private List<Student> students = new ArrayList<>();
    private List<Subject> subjects = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    private boolean isSubject;
    private Student student;
    private Subject subject;
    private Course course;
    private String tagContent;

    /**
     * List to store the students parsed from the XML file
     *
     * @return the students in the XML that will be inserted into the database
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * List to store the subjects parsed from the XML file
     *
     * @return the subjects in the XML that will be inserted into the database
     */
    public List<Subject> getSubjects() {
        return subjects;
    }

    /**
     * List to store the courses parsed from the XML file
     *
     * @return the courses in the XML that will be inserted into the database
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Method that detects all the selected opening tags in the XML file
     *
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     * @throws SAXException  an error at the time of parsing the XML file
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("student")) {
            student = new Student();
            student.setIdCard(Integer.parseInt(attributes.getValue("id")));
        }
        if (qName.equals("course")) {
            course = new Course();
            course.setCode(Integer.parseInt(attributes.getValue("id")));
        }
        if (qName.equals("subject")) {
            subject = new Subject();
            isSubject = true;
            subject.setId(Integer.parseInt(attributes.getValue("id")));
            subject.setCourseId(Integer.parseInt(attributes.getValue("course")));
        }
    }

    /**
     * Method that detects and reads the values placed between opening and closing tags, in the XML file
     *
     * @param ch
     * @param start
     * @param length
     * @throws SAXException  an error at the time of parsing the XML file
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        tagContent = new String(ch, start, length);
    }

    /**
     * Method that detects all the selected closing tags in the XML file
     *
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case "firstname": student.setFirstName(tagContent);break;
            case "lastname": student.setLastName(tagContent);break;
            case "email": student.setEmail(tagContent); break;
            case "phone": student.setPhone(tagContent); break;
            case "student": students.add(student); break;
            case "hours": subject.setHours(Integer.parseInt(tagContent)); break;
            case "year": subject.setYear(Integer.parseInt(tagContent)); break;
            case "subject": subjects.add(subject); break;
            case "course": courses.add(course); break;
        }
        if (qName.equals("name")) {
            if (isSubject)
                subject.setName(tagContent);
            else
                course.setName(tagContent);
        }
    }
}
