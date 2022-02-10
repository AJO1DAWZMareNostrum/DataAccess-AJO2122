package com.aajaor2122.unit5;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.sql.Date;
import java.time.LocalDate;

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
    //private String state = "waiting";
    // ENUM to allocate all the possible states of the application at different times
    enum State {
        WAITING,
        SEARCHINGUSER,
        SEARCHINGBOOK,
        ADDINGUSER,
        ADDINGBOOK,
        EDITINGUSER,
        EDITINGBOOK,
        USERSEARCHSUCCESS,
        BOOKSEARCHSUCCESS
    }
    State state = State.WAITING;

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
        setUpBookUI();
    }

    @FXML
    protected void onLendBookClicked() {
        setUpLendReturnUI();
    }

    @FXML
    protected void onReturnBookClicked() {
        setUpLendReturnUI();
    }

    @FXML
    protected void onExitAppClicked() {
        System.exit(0);
    }

    @FXML
    protected void onSearchButtonClicked() {

        // Correcto: testeado y funciona
        if (userMiddlePane.isVisible()) {
            // We update the state of the application
            state = State.SEARCHINGUSER;

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
            state = State.SEARCHINGBOOK;

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
    protected void onAddButtonClicked() {

        if (userMiddlePane.isVisible()) {
            state = State.ADDINGUSER;

            userCodeTextField.setDisable(false);
            fnameTextField.setDisable(false);
            surnameTextField.setDisable(false);
            birthdayDatePicker.setDisable(false);
            bottomMainPane.setVisible(false);
            bottomAcceptCancelPane.setVisible(true);
        }

        if (bookMiddlePane.isVisible()) {
            state = State.ADDINGBOOK;

            isbnTextField.setDisable(false);
            titleTextField.setDisable(false);
            bookCopiesTextField.setDisable(false);
            outlineTextField.setDisable(false);
            publisherTextField.setDisable(false);
            bottomMainPane.setVisible(false);
            bottomAcceptCancelPane.setVisible(true);
        }
    }

    @FXML
    protected void onEditButtonClicked() {
        if (state != State.USERSEARCHSUCCESS || state != State.BOOKSEARCHSUCCESS) {
            resultMessage("You need to make a succesful search first, before you´re allowed to edit data");
        } else if (state == State.USERSEARCHSUCCESS) {
            fnameTextField.setDisable(false);
            surnameTextField.setDisable(false);
        } else if (state == State.BOOKSEARCHSUCCESS) {
            titleTextField.setDisable(false);
            bookCopiesTextField.setDisable(false);
            outlineTextField.setDisable(false);
            publisherTextField.setDisable(false);
        }


        // TODO: Check that this comprobation (length is bigger than 0) works in this specific case
        // Alternativa: comprobar estado SearchingUser antes de permitir
        if (userMiddlePane.isVisible() && userCodeTextField.getLength() > 0) {

        }
    }

    @FXML
    protected void onAcceptClicked() {
        // Important method, that makes the link with the LibraryModel class, calling its DB methods

        // Option selected for searching user in the database
        if (state == State.SEARCHINGUSER) {
            try {
                String userCode;
                UsersJpaEntity user;
                if (userCodeTextField.getLength() > 0) {
                    userCode = userCodeTextField.getText();
                    user = LibraryModel.getUserByCode(userCode);

                    if (user != null) {
                        fnameTextField.setText(user.getName());
                        surnameTextField.setText(user.getSurname());
                        state = State.USERSEARCHSUCCESS;
                    } else {
                        //TODO: este mensaje NO llega a mostrarse - arreglar la gestión de excepciones
                        resultMessage("User has NOT been found, or incorrect code introduced.");
                    }
                } else
                    resultMessage("You need to provide a user code to make a search");
            } catch (Exception e) {
                reportError(e);
            }
        }

        // Option selected for searching book in the database
        if (state == State.SEARCHINGBOOK) {
            try {
                String bookISBN;
                BooksJpaEntity book;
                if (isbnTextField.getLength() > 0) {
                    bookISBN = isbnTextField.getText();
                    book = LibraryModel.getBookByIsbn(bookISBN);

                    if (book != null) {
                        titleTextField.setText(book.getTitle());
                        bookCopiesTextField.setText(book.getCopies().toString());
                        outlineTextField.setText(book.getOutline());
                        publisherTextField.setText(book.getPublisher());
                        state = State.BOOKSEARCHSUCCESS;
                    } else {
                        //TODO: este mensaje NO llega a mostrarse - arreglar la gestión de excepciones
                        resultMessage("Book has NOT been found, or incorrect isbn introduced.");
                    }
                } else
                    resultMessage("You need to provide a user code to make a search");
            } catch (Exception e) {
                reportError(e);
            }
        }

        // Option selected for adding a user into the database
        if (state == State.ADDINGUSER) {
            try {
                //TODO: check that the DatePicker restriction is working
                if (userCodeTextField.getLength() > 0 && fnameTextField.getLength() > 0 &&
                    surnameTextField.getLength() > 0 && birthdayDatePicker.getValue() != null) {

                    String code, fname, surname;
                    LocalDate birthdayRaw;
                    Date birthday;

                    code = userCodeTextField.getText();
                    fname = fnameTextField.getText();
                    surname = surnameTextField.getText();
                    birthdayRaw = birthdayDatePicker.getValue();
                    birthday = Date.valueOf(birthdayRaw);

                    LibraryModel.insertUser(code, fname, surname, birthday);

                } else {
                    resultMessage("All field must be introduced before adding a new User.");
                }
            } catch (Exception e) {
                reportError(e);
            }
        }

        // Option selected for adding a book into the database
        if (state == State.ADDINGBOOK) {
            try {
                //TODO: implement logic for inserting user into DB
                if (isbnTextField.getLength() > 0 && titleTextField.getLength() > 0 &&
                        bookCopiesTextField.getLength() > 0 && outlineTextField.getLength() > 0 &&
                        publisherTextField.getLength() > 0) {

                    String isbn, title, outline, publisher;
                    int bookCopies;

                    isbn = isbnTextField.getText();
                    title = titleTextField.getText();
                    bookCopies = Integer.parseInt(bookCopiesTextField.getText());
                    outline = outlineTextField.getText();
                    publisher = publisherTextField.getText();

                    LibraryModel.insertBook(isbn, title, bookCopies, outline, publisher);

                } else {
                    resultMessage("All field must be introduced before adding a new Book.");
                }
            } catch (Exception e) {
                reportError(e);
            }
        }

    }

    @FXML
    protected void onCancelClicked() {
        if (state == State.SEARCHINGUSER || state == State.ADDINGUSER || state == State.EDITINGUSER) {
            setUpUserUI();
            //TODO: testear y quitar "clear..." si no interesa en tiempo de ejecución - solución temporal
            clearUserFields();
        }

        if (state == State.SEARCHINGBOOK || state == State.ADDINGBOOK || state == State.EDITINGBOOK) {
            setUpBookUI();
            clearBookFields();
        }

    }

    public void setUpUserUI() {
        userMiddlePane.setVisible(true);
        bookMiddlePane.setVisible(false);
        lendOrReturnPane.setVisible(false);
        bottomMainPane.setVisible(true);
        bottomAcceptCancelPane.setVisible(false);
        state = State.WAITING;
    }

    public void setUpBookUI() {
        userMiddlePane.setVisible(false);
        bookMiddlePane.setVisible(true);
        lendOrReturnPane.setVisible(false);
        bottomMainPane.setVisible(true);
        bottomAcceptCancelPane.setVisible(false);
        state = State.WAITING;
    }

    public void setUpLendReturnUI () {
        userMiddlePane.setVisible(false);
        bookMiddlePane.setVisible(false);
        lendOrReturnPane.setVisible(true);
        bottomMainPane.setVisible(false);
        bottomAcceptCancelPane.setVisible(true);
        state = State.WAITING;
    }

    public void clearUserFields() {
        userCodeTextField.setText("");
        fnameTextField.setText("");
        surnameTextField.setText("");
        state = State.WAITING;
    }

    public void clearBookFields() {
        isbnTextField.setText("");
        titleTextField.setText("");
        bookCopiesTextField.setText("");
        outlineTextField.setText("");
        publisherTextField.setText("");
        state = State.WAITING;
    }

    /**
     *  Method that receives an exception from some part of the program, and allows to
     *  show it into a MessageDialog to the program´s user
     *
     * @param ex  the exception received by the program and showed in a MessageDialog
     */
    public static void reportError(Exception ex) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(ex.toString());

        a.show();
    }

    //TODO: activar Hibernate
    //public void reportHibernateError(HibernateException hex) {
    //    Alert a = new Alert(Alert.AlertType.ERROR);
    //        a.setContentText(hex.toString());
    //
    //        a.show();
    //}

    /**
     *  Method that receives an success message from some part of the program, and allows to
     *  show it into a MessageDialog to the program´s user
     *
     * @param message  the message received by the program and showed in a MessageDialog
     */
    public static void resultMessage(String message) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText(message);

        a.show();
    }
}