package com.aajaor2122.unit5.finalactivityunit5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LibraryMainApplication extends Application {
    @Override
    // Stage es un parámetro para decir que vista debe abrirse
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LibraryMainApplication.class.getResource("library-view.fxml"));
        // Scene es lo que contienes el Stage principal. y que puede ir cambiando (investigar su relación con Estados/Events)
        Scene scene = new Scene(fxmlLoader.load(), 415, 390);
        stage.setTitle("Final Activity - LibraryFX");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}