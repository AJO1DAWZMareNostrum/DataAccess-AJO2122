module com.aajaor2122.unit5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.naming;
    requires java.persistence;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires org.jboss.logging;


    exports com.aajaor2122.unit5;
    opens com.aajaor2122.unit5 to javafx.fxml;
}