// Añadirlo a la clase principal de db4o
// import com.db4o.*;

class Author {

    private String code;
    private String name;
    private String nationality;

    public Author() {

    }

    public Author(String code, String name, String nationality) {
        this.code = code;
        this.name = name;
        this.nationality = nationality;
    }

    public String getCode() { return code; }
    public void setCode(String value) {

        // Checks that code has a format beginning in 3 letters and 4 numbers (initials and year of birth)
        if (value.matches("...\\d\\d\\d\\d"))
            code = value;
    }

    public String getName() { return name; }
    public void setName(String value) { name = value; }

    public String getNationality() { return nationality; }
    public void setNationality(String value) { nationality = value; }

    public String toString() {
        return
              "Author: " + "code = " + code + ", " + "name = " + name + ", " +
                      "nationality = " + nationality;
    }

    public boolean equals(Object other) {
        if (other == this)
            return true;

        if ( !(other instanceof Author))
            return false;

        Author otherAuthor = (Author)other;
        return (this.code.equals(otherAuthor.code) &&
                this.name.equals(otherAuthor.name) &&
                this.nationality.equals(otherAuthor.nationality));
    }
}
