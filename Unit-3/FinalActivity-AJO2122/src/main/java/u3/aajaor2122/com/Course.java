package u3.aajaor2122.com;

public class Course {

    private int code;
    private String name;

    public Course(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
