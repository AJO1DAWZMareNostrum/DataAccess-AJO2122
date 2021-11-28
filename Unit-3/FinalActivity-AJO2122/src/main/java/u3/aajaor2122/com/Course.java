package u3.aajaor2122.com;

/**
 *  Class to store and manage the data of individual courses.
 */
public class Course {

    private int code;
    private String name;

    public Course(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public Course() { }

    public void setCode(int code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    /**
     *   Overloads the number object reference, to return the actual name of the student instead
     *   of the id reference in a number format
     *
     *  @return  the name of the course in a readable string format
      */
    @Override
    public String toString() {
        return name;
    }
}
