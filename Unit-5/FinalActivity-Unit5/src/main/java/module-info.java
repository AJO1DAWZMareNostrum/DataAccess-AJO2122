module com.aajaor2122.unit5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.persistence;
    requires java.sql;


    exports com.aajaor2122.unit5;
    opens com.aajaor2122.unit5 to javafx.fxml;
}