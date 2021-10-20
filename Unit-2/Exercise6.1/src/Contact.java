import java.io.Serializable;

public class Contact  {
    // Properties
    protected String name;
    protected String surname;
    protected String email;
    protected String phoneNumber;
    protected String description;

    public Contact(String _name, String _surname, String _email, String _phoneNumber,
                   String _description) {
        name = _name;
        surname = _surname;
        email = _email;
        phoneNumber = _phoneNumber;
        description = _description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
