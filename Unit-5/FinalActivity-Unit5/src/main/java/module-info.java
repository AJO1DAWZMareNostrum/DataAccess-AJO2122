module com.aajaor2122.unit5.finalactivityunit5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.aajaor2122.unit5.finalactivityunit5 to javafx.fxml;
    exports com.aajaor2122.unit5.finalactivityunit5;
}