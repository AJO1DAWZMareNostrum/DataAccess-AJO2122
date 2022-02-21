package com.aajaor2122.unit5;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;
import java.util.Set;

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
    // Lendings/borrowings panes
    @FXML
    private TextField userSearchTextField;
    @FXML
    private ImageView userSearchImage;
    @FXML
    private TextField userResultTextField;
    @FXML
    private TextField bookSearchTextField;
    @FXML
    private ImageView bookSearchImage;
    @FXML
    private TextField bookResultTextField;
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
        BOOKSEARCHSUCCESS,
        LENDING,
        RETURNING,
        SEARCHEDUSERLENDING,
        SEARCHEDBOOKLENDING,
        SEARCHEDUSERRETURNING,
        SEARCHEDBOOKRETURNING
    }
    State state = State.WAITING;
    String lendOrReturn = "";

    // Constant to know the maximum days to return the book; otherwise the user will be FINED
    final int daysToBeFined = 15;
    final int penalisationDays = 10;
    // Today´s date, prepared for making comparisons to manage user´s fined
    LocalDate todayDateRaw = LocalDate.now();
    Date today = Date.valueOf(todayDateRaw);

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onUserButtonClicked() {
        setUpUserUI(State.WAITING);
        clearUserFields();

        // The fields are activated to allow introducing the user´s data
        userCodeTextField.setDisable(false);
        fnameTextField.setDisable(false);
        surnameTextField.setDisable(false);
        birthdayDatePicker.setDisable(false);

    }

    @FXML
    protected void onBookButtonClicked() {
        setUpBookUI(State.WAITING);
        clearBookFields();
    }

    // TODO: he cambiado los estados, pasar a State.WAITING si esto da errores
    @FXML
    protected void onLendBookClicked() {
        setUpLendReturnUI(State.LENDING);
        lendOrReturn = "lend";
        clearLendOrReturnFields();
    }

    @FXML
    protected void onReturnBookClicked() {
        setUpLendReturnUI(State.RETURNING);
        lendOrReturn = "return";
        clearLendOrReturnFields();
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
        if (state != State.USERSEARCHSUCCESS && state != State.BOOKSEARCHSUCCESS) {
            resultMessage("You need to make a succesful search first, before you´re allowed to edit data");
            return;
        }

        if (state == State.USERSEARCHSUCCESS) {
            state = State.EDITINGUSER;

            fnameTextField.setDisable(false);
            surnameTextField.setDisable(false);
            birthdayDatePicker.setDisable(false);
        } else {
            state = State.EDITINGBOOK;

            titleTextField.setDisable(false);
            bookCopiesTextField.setDisable(false);
            outlineTextField.setDisable(false);
            publisherTextField.setDisable(false);
        }

        bottomMainPane.setVisible(false);
        bottomAcceptCancelPane.setVisible(true);

    }

    @FXML
    protected void onSearchUserClicked() {
        try {
            String userCode, fullName;
            UsersJpaEntity user;
            if (userSearchTextField.getLength() > 0) {
                userCode = userSearchTextField.getText();
                user = LibraryModel.getUserByCode(userCode);

                if (user != null) {
                    fullName = user.getName() + " " + user.getSurname();
                    userResultTextField.setText(fullName);

                    state = State.SEARCHEDUSERLENDING;
                    bottomMainPane.setVisible(false);
                    bottomAcceptCancelPane.setVisible(true);
                } else
                    resultMessage("User has NOT been found, or incorrect code introduced.");
            } else
                resultMessage("You must introduce an user code to be able to make a search.");
        } catch (Exception e) {
            reportError(e);
        }
    }

    @FXML
    protected void onSearchBookClicked() {
        try {
            //TODO: bloque de código CORRECTO, no borrar
            String isbn, title;
            BooksJpaEntity book;
            if (bookSearchTextField.getLength() > 0) {
                isbn = bookSearchTextField.getText();
                book = LibraryModel.getBookByIsbn(isbn);

                if (book != null) {
                    title = book.getTitle();
                    bookResultTextField.setText(title);

                    state = State.SEARCHEDBOOKLENDING;
                    bottomMainPane.setVisible(false);
                    bottomAcceptCancelPane.setVisible(true);
                } else
                resultMessage("Book has NOT been found, or incorrect ISBN introduced.");
            } else
                resultMessage("You must introduce a book´s ISBN to be able to make a search.");
        } catch (Exception e) {
            reportError(e);
        }
    }

    @FXML
    protected void onAcceptClicked() {
        // Important method, that makes the link with the LibraryModel class, calling its DB methods
        // TODO - Convert these if´s into a Switch expression

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
                        setUpUserUI(State.USERSEARCHSUCCESS);
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
                        setUpBookUI(State.BOOKSEARCHSUCCESS);
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
                    setUpUserUI(State.WAITING);
                    clearUserFields();

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
                    setUpBookUI(State.WAITING);
                    clearBookFields();

                } else {
                    resultMessage("All fields must be introduced before adding a new Book.");
                }
            } catch (Exception e) {
                reportError(e);
            }
        }

        if (state == State.EDITINGUSER) {
            try {
                if (userCodeTextField.getLength() > 0 && fnameTextField.getLength() > 0 &&
                        surnameTextField.getLength() > 0) {
                    String code, fname, surname;

                    code = userCodeTextField.getText();
                    fname = fnameTextField.getText();
                    surname = surnameTextField.getText();
                    LibraryModel.editUser(code, fname, surname);
                    setUpUserUI(State.WAITING);
                    clearUserFields();

                } else {
                    resultMessage("New name and surname must be introduced before editing a User, and a existing " +
                            "user code must be choosed.");
                }

            } catch (Exception e) {
                reportError(e);
            }
        }

        if (state == State.EDITINGBOOK) {
            try {
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

                    LibraryModel.editBook(isbn, title, bookCopies, outline, publisher);
                    setUpBookUI(State.WAITING);
                    clearBookFields();
                }
            } catch (Exception ex) {
                reportError(ex);
            }
        }

        // Insertion of new lending into DB if restrictions are passed
        if (lendOrReturn.equals("lend")) {
            if (state == State.SEARCHEDBOOKLENDING || state == State.SEARCHEDUSERLENDING) {
                try {
                    if (bookSearchTextField.getLength() > 0 && userSearchTextField.getLength() > 0) {

                        //Restrictions for the book field
                        BooksJpaEntity book;
                        String isbn;
                        int borrowedCopies;
                        isbn = bookSearchTextField.getText();

                        book = LibraryModel.getBookByIsbn(isbn);
                        borrowedCopies = book.getBorrowedBy().size();

                        // Restriction for the user field
                        UsersJpaEntity user;
                        String code;
                        int booksAlreadyLended;
                        code = userSearchTextField.getText();

                        user = LibraryModel.getUserByCode(code);
                        booksAlreadyLended = user.getLentBooks().size();

                        // Check if user has already the book in its possession
                        Set<LendingJpaEntity> userActualBooks = user.getLentBooks();
                        String ownedBook;
                        for (LendingJpaEntity lending : userActualBooks) {
                            ownedBook = lending.getBook().getIsbn();

                            if (ownedBook.equals(isbn)) {
                                resultMessage("User has already borrowed this book. Operation canceled!");
                                return;
                            }
                        }

                        // Checks that book is available, and in case is NOT, gives the option to make a reservation
                        if (borrowedCopies >= book.getCopies()) {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Reservation option");
                            alert.setContentText("None of the copies are available at this moment. " +
                                    "Do you wish to register a reservation for this book and user?");
                            // Capture the dialog result for Ok or Cancel
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                LibraryModel.insertReservation(user, book);
                                resultMessage("Reservation has been registered successfully.");
                            } else {
                                alert.close();
                                resultMessage("Reservation has been canceled.");
                            }

                            return;
                        }

                        // Checking that user hasn´t already 3 books, and canceling lending in that case
                        if (booksAlreadyLended >= 3) {
                            resultMessage("The User has already borrowed the maximum number of books (3).");
                            return;
                        }

                        // Checks that the user isn´t fined at this moment - in that case, cancels the lending
                        if (user.getFined() != null) {
                            Date finedDate = user.getFined();
                            // Compare today´s date to the fined day, to know if fine is over or not
                            int result = finedDate.compareTo(today);
                            // If the fined dated haven´t passed yet (User is STILL fined)
                            // TODO: check that restriction is working
                            if (result > 0) {
                                resultMessage("User is fined until date " + finedDate + ". Operation canceled.");
                                return;
                            } else {
                                //TODO: make a query in LibraryModel to update User´s fined date to NULL
                                LibraryModel.updateFinedToNull(code);
                                resultMessage("Fined user restriction is over. Welcome again.");
                            }
                        } else {
                            resultMessage("User´s fined date is NULL.");
                        }

                        // Proceed to insert the new lending into the DB (after checking and passing all the restrictions)
                        LibraryModel.insertLending(user, book);

                    } else {
                        resultMessage("You need a succesful search from a valid user as from a valid book, before you " +
                                "insert a new lending");
                    }

                } catch (Exception ex) {
                    reportError(ex);
                }
            }
        }

        // Devolution or return of a book into DB and checking of restrictions and Reservations (to show message to user)
        if (lendOrReturn.equals("return")) {
            if (state == State.SEARCHEDBOOKLENDING || state == State.SEARCHEDUSERLENDING) {
                try {
                    if (bookSearchTextField.getLength() > 0 && userSearchTextField.getLength() > 0) {

                        BooksJpaEntity book;
                        String isbn;
                        isbn = bookSearchTextField.getText();
                        book = LibraryModel.getBookByIsbn(isbn);

                        UsersJpaEntity user;
                        String code;
                        code = userSearchTextField.getText();
                        user = LibraryModel.getUserByCode(code);

                        // Check if user has already the book in its possession, to allow or not the returning
                        Set<LendingJpaEntity> userActualBooks = user.getLentBooks();
                        String ownedBook;
                        for (LendingJpaEntity lending : userActualBooks) {
                            ownedBook = lending.getBook().getIsbn();

                            if (!ownedBook.equals(isbn)) {
                                resultMessage("User doesn´t have this book at the moment. Operation canceled!");
                                return;
                            }
                        }

                        LendingJpaEntity lendingToUpdate = null;
                        // Lending instance is updated to register the devolution of the book
                        //TODO: QUITAR COMENTARIOS después de testear las condiciones
                        lendingToUpdate = LibraryModel.returnBook(user, book);

                        if (lendingToUpdate != null) {
                            // Checks that the user has returned the book inside of the allowed days; fine him if he exceeds the
                            // maximum returning date (15 days)
                            Date lendingDate = lendingToUpdate.getLendingdate();
                            java.sql.Date maximumDate = addDays(lendingDate, daysToBeFined);

                            LocalDate todayDateRaw = LocalDate.now();
                            Date today = Date.valueOf(todayDateRaw);

                            int result = maximumDate.compareTo(today);
                            // If the maximum date has been surpassed, user is fined
                            if (result < 0) {
                                //TODO: testeo, implementar metodo que actualice multa al usuario
                                resultMessage("User has been fined. He isn´t allowed to borrow more books until 10 days have passed from now.");
                                java.sql.Date newFinedDate = addDays(today, penalisationDays);

                                LibraryModel.applyFineIntoUser(code, newFinedDate);
                            }

                        }

                        //TODO: avisar de que libro está disponible a bibliotecario, si hay alguna reserva del mismo
                        // Avisar de la Reservation más antigua (la priera de la lista asignada a ese ISBN),
                        // y luego eliminarla de la tabla Reservations

                    } else {
                        resultMessage("You need a succesful search from a valid user as from a valid book, before you " +
                                "insert a new lending");
                    }
                } catch (Exception ex) {
                    reportError(ex);
                }
            }
        }

    }

    public static Date addDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return new Date(c.getTimeInMillis());
    }

    @FXML
    protected void onCancelClicked() {
        if (state == State.SEARCHINGUSER || state == State.ADDINGUSER || state == State.EDITINGUSER ||
            state == State.USERSEARCHSUCCESS) {
            setUpUserUI(State.WAITING);
            clearUserFields();
        }
        else if (state == State.SEARCHINGBOOK || state == State.ADDINGBOOK || state == State.EDITINGBOOK ||
                state == State.BOOKSEARCHSUCCESS) {
            setUpBookUI(State.WAITING);
            clearBookFields();
        }
        else if (state == State.LENDING || state == State.RETURNING || state == State.SEARCHEDBOOKLENDING ||
                state == State.SEARCHEDBOOKRETURNING || state == State.SEARCHEDUSERLENDING ||
                state == State.SEARCHEDUSERRETURNING) {
            setUpLendReturnUI(State.WAITING);
            clearLendOrReturnFields();
        }

    }

    public void setUpUserUI(State newState) {
        userMiddlePane.setVisible(true);
        bookMiddlePane.setVisible(false);
        lendOrReturnPane.setVisible(false);
        bottomMainPane.setVisible(true);
        bottomAcceptCancelPane.setVisible(false);
        state = newState;
    }

    public void setUpBookUI(State newState) {
        userMiddlePane.setVisible(false);
        bookMiddlePane.setVisible(true);
        lendOrReturnPane.setVisible(false);
        bottomMainPane.setVisible(true);
        bottomAcceptCancelPane.setVisible(false);
        state = newState;
    }

    public void setUpLendReturnUI (State newState) {
        userMiddlePane.setVisible(false);
        bookMiddlePane.setVisible(false);
        lendOrReturnPane.setVisible(true);
        bottomMainPane.setVisible(false);
        bottomAcceptCancelPane.setVisible(true);
        state = newState;
    }

    public void clearUserFields() {
        userCodeTextField.setText("");
        fnameTextField.setText("");
        surnameTextField.setText("");
        birthdayDatePicker.setAccessibleText("");
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

    public void clearLendOrReturnFields() {
        userSearchTextField.setText("");
        userResultTextField.setText("");
        bookSearchTextField.setText("");
        bookResultTextField.setText("");
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