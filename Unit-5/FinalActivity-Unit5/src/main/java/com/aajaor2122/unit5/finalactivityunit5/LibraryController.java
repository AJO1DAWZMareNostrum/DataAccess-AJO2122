package com.aajaor2122.unit5.finalactivityunit5;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 *  CONTROLLER class - related to every aspect of the graphical interface: controls and set up
 *  every element of the program and relates it to the Database methods in the program
 *
 * @author Aarón Jamet Orgiles - 2DAMU
 */
public class LibraryController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField userCodeTextField;
    @FXML
    private TextField fnameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private DatePicker birthdayDatePicker;
    @FXML
    private Pane userMiddlePane;
    @FXML
    private TextField isbnTextField;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField bookCopiesTextField;
    @FXML
    private TextField outlineTextField;
    @FXML
    private TextField publisherTextField;
    @FXML
    private Pane bookMiddlePane;
    @FXML
    private Pane lendOrReturnPane;
    @FXML
    private GridPane bottomMainPane;
    @FXML
    private GridPane bottomAcceptCancelPane;
    @FXML
    private ImageView searchImage;
    @FXML
    private ImageView addImage;
    @FXML
    private ImageView editImage;
    @FXML
    private ImageView acceptImage;
    @FXML
    private ImageView cancelImage;

    // Variable to save the state of the program at different times
    // TODO: CAMBIAR estados al momento de CANCELAR las operaciones y volver a pantalla anterior
    private String state = "waiting";

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onUserButtonClicked() {
        userMiddlePane.setVisible(true);
        bookMiddlePane.setVisible(false);
        lendOrReturnPane.setVisible(false);
        bottomMainPane.setVisible(true);
        bottomAcceptCancelPane.setVisible(false);

        // The fields are activated to allow introducing the user´s data
        userCodeTextField.setDisable(false);
        fnameTextField.setDisable(false);
        surnameTextField.setDisable(false);
        birthdayDatePicker.setDisable(false);


    }

    @FXML
    protected void onBookButtonClicked() {
        userMiddlePane.setVisible(false);
        bookMiddlePane.setVisible(true);
        lendOrReturnPane.setVisible(false);
        bottomMainPane.setVisible(true);
        bottomAcceptCancelPane.setVisible(false);


    }

    @FXML
    protected void onSearchButtonClicked() {

        // Correcto: testeado y funciona
        if (userMiddlePane.isVisible()) {
            // We update the state of the application
            state = "searchingUser";

            // We left only the user code field to make non-exact User´s searches
            userCodeTextField.setDisable(false);
            fnameTextField.setDisable(true);
            surnameTextField.setDisable(true);
            birthdayDatePicker.setDisable(true);
            // Bottom pane activated for accept/cancel actions
            bottomMainPane.setVisible(false);
            bottomAcceptCancelPane.setVisible(true);

        }

        if (bookMiddlePane.isVisible()) {
            state = "searchingBook";

            isbnTextField.setDisable(false);
            titleTextField.setDisable(true);
            bookCopiesTextField.setDisable(true);
            outlineTextField.setDisable(true);
            publisherTextField.setDisable(true);
            bottomMainPane.setVisible(false);
            bottomAcceptCancelPane.setVisible(true);
        }


    }

    @FXML
    protected void onAcceptClicked() {
        // Important method, that makes the link with the LibraryModel class, calling its DB methods

        // Option selected for searching user in the database
        if (state == "searchingUser")
            //TODO: Call method in controller for inserting User into DB
            userCodeTextField.setText("Accept working!");


    }

    @FXML
    protected void onCancelClicked() {
        //TODO: Implementar algún tipo de comportamiento más genérico, ya que al cancelar las operaciones
        // obtendremos resultado visuales más parecidos (y sin lógica como en Aceptar)
        if (state == "searchingUser")
            setUpUserUI();
        if (state == "searchingBook")
            setUpBookUI();
    }

    public void setUpUserUI() {
        userMiddlePane.setVisible(true);
        bookMiddlePane.setVisible(false);
        lendOrReturnPane.setVisible(false);
        bottomMainPane.setVisible(true);
        bottomAcceptCancelPane.setVisible(false);
    }

    public void setUpBookUI() {
        userMiddlePane.setVisible(false);
        bookMiddlePane.setVisible(true);
        lendOrReturnPane.setVisible(false);
        bottomMainPane.setVisible(true);
        bottomAcceptCancelPane.setVisible(false);
    }

    public void setUpLendReserveUI () {
        userMiddlePane.setVisible(false);
        bookMiddlePane.setVisible(false);
        lendOrReturnPane.setVisible(true);
        bottomMainPane.setVisible(false);
        bottomAcceptCancelPane.setVisible(true);
    }

    /**
     *  Method that receives an exception from some part of the program, and allows to
     *  show it into a MessageDialog to the program´s user
     *
     * @param ex  the exception received by the program and showed in a MessageDialog
     */
    public void reportError(Exception ex) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(ex.toString());

        a.show();
    }

    /**
     *  Method that receives an success message from some part of the program, and allows to
     *  show it into a MessageDialog to the program´s user
     *
     * @param message  the message received by the program and showed in a MessageDialog
     */
    public void resultMessage(String message) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText(message);

        a.show();
    }
}