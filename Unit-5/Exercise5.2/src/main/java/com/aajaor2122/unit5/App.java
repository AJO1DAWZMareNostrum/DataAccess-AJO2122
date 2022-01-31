package com.aajaor2122.unit5;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;

/**
 * Hello world!
 *
 */
public class App 
{
    static Scanner sc = new Scanner(System.in);

    public static void main( String[] args ) throws ParseException {
        int option = -1;

        while (option != 0) {
            System.out.println("1. Consult entries.");
            System.out.println("2. Create/insert entry.");
            System.out.println("3. Update entry.");
            System.out.println("4. Delete entry");
            System.out.print("Choose one option, or 0 to leave program: ");
            option = Integer.parseInt(sc.nextLine());

            switch (option) {
                // Option to consult the entries in the database
                case 1:
                    int consultOption = 0;
                    System.out.println("1. Consult Books data.");
                    System.out.println("2. Consult Lending data.");
                    System.out.println("3. Consult Users data.");
                    System.out.print("Select one option: ");
                    consultOption = Integer.parseInt(sc.nextLine());

                    if (consultOption == 1)
                        consultBooksData();
                    else if (consultOption == 2)
                        consultLendingsData();
                    else if (consultOption == 3)
                        consultUsersData();
                    else
                        System.out.println("Option NOT valid. Incorrect option number.");

                    break;

                // Option to create/insert new entries in the database
                case 2:
                    int insertOption = 0;
                    System.out.println("1. Insert a new book.");
                    System.out.println("2. Insert a new lending.");
                    System.out.println("3. Insert a new user");
                    System.out.print("Select one option: ");
                    insertOption = Integer.parseInt(sc.nextLine());

                    if (insertOption == 1)
                        insertBook();
                    else if (insertOption == 2)
                        insertLending();
                    else if (insertOption == 3)
                        insertUser();
                    else
                        System.out.println("Option NOT valid. Incorrect option number.");

                    break;

            }
        }

    }

    // Creating and opening the session
    public static Session openSession() throws HibernateException {
        // Code to avoid the warnings
        @SuppressWarnings("unused")
        org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger("org.hibernate");
        java.util.logging.Logger.getLogger("org.hibernate") .setLevel(Level.SEVERE);

        SessionFactory sessionFactory = new
                Configuration().configure().buildSessionFactory();
        Session session = null;
        try {
            session = sessionFactory.openSession();
        } catch (Throwable t) {
            System.err.println("Exception while opening session...");
            t.printStackTrace();
        }
        if (session == null) {
            System.err.println("Session was found to be null.");
        }

        return session;
    }

    // Method to print all the data from Books entities
    public static void consultBooksData() {
        try ( Session session = openSession() ) {
            Query<BooksJpaEntity> myQuery =
                    session.createQuery("from com.aajaor2122.unit5.BooksJpaEntity");
            List<BooksJpaEntity> books = myQuery.list();

            for (Object bookObject : books) {
                BooksJpaEntity book = (BooksJpaEntity) bookObject;
                System.out.printf("ISBN: %s   Title: %s   Copies: %d%n" +
                                "Publishers: %s%n" +
                                "Lending: %s%n%n",
                        book.getIsbn(), book.getTitle(), book.getCopies(),
                        book.getPublisher(), printLendingList(book.getBorrowedBy()));
            }
        }
        catch (HibernateException hiex) {
            System.err.println( hiex.getMessage() );
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to print all the data from Lending entities
    public static void consultLendingsData() {
        try ( Session session = openSession() ) {
            Query<LendingJpaEntity> myQuery =
                    session.createQuery("from com.aajaor2122.unit5.LendingJpaEntity");
            List<LendingJpaEntity> lendings = myQuery.list();

            for (Object lendingObject : lendings) {
                LendingJpaEntity lending = (LendingJpaEntity) lendingObject;
                BooksJpaEntity book = lending.getBook();
                UsersJpaEntity user = lending.getBorrower();
                System.out.printf("Id: %s   Lending date: %tD   Returning date: %tD%n" +
                                "Borrower: %s   Book: %s%n%n",
                        lending.getId(), lending.getLendingdate(), lending.getReturningdate(),
                        (user.getName() + " " + user.getSurname()), book.getTitle());
            }
        }
        catch (HibernateException hiex) {
            System.err.println( hiex.getMessage() );
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to print the book borrowers list in a String format
    public static String printLendingList(Set lendingList) {

        String lendingData = "";
        for(Object lended : lendingList) {
            lendingData += ((LendingJpaEntity) lended).getId() + ", ";
            lendingData += ((LendingJpaEntity) lended).getLendingdate() + ", ";
            lendingData += ((LendingJpaEntity) lended).getReturningdate() + ", ";
            lendingData += ((LendingJpaEntity) lended).getBorrower().getName() + " " +
                    ((LendingJpaEntity) lended).getBorrower().getSurname() + " | /n";
        }

        return lendingData;
    }

    // Method to print all the data from Lending entities
    public static void consultUsersData() {
        try ( Session session = openSession() ) {
            Query<UsersJpaEntity> myQuery =
                    session.createQuery("from com.aajaor2122.unit5.UsersJpaEntity");
            List<UsersJpaEntity> users = myQuery.list();

            for (Object userObject : users) {
                UsersJpaEntity user = (UsersJpaEntity) userObject;
                System.out.printf("Code: %s   Name: %s   Surname: %s   Birthday: %tD%n" +
                                "Lent books: %s%n%n",
                        user.getCode(), user.getName(), user.getSurname(), user.getBirthdate(),
                        printLentBooks(user.getLentBooks()));
            }
        }
        catch (HibernateException hiex) {
            System.err.println( hiex.getMessage() );
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String printLentBooks(Set lentBooks) {

        String lent = "";
        for(Object lended : lentBooks) {
            lent += ((LendingJpaEntity) lended).getBook().getTitle() + " | ";
        }

        return lent;
    }

    public static void insertBook() {
        System.out.print("ISBN of the new book? ");
        String isbn = sc.nextLine();
        System.out.print("Title of the book?: ");
        String btitle = sc.nextLine();
        System.out.print("Number of copies of the book?: ");
        int bcopies = Integer.parseInt(sc.nextLine());
        System.out.print("Book´s outline?: ");
        String boutline = sc.nextLine();
        System.out.print("Publisher of the book?: ");
        String bpublisher = sc.nextLine();
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            BooksJpaEntity book = new BooksJpaEntity();
            book.setIsbn(isbn);
            book.setTitle(btitle);
            book.setCopies(bcopies);
            book.setOutline(boutline);
            book.setPublisher(bpublisher);
            session.save(book);
            transaction.commit();

            System.out.println("The book has been inserted into the database.\n");
        }
        catch (HibernateException hiex) {
            System.err.println( hiex.getMessage() );
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertLending() throws ParseException {
        System.out.print("ID of the new lending: ");
        int idlending = Integer.parseInt(sc.nextLine());
        System.out.print("Lending date of the book (yyyy/MM/dd): ");
        String lDate = sc.nextLine();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        java.util.Date langDate = sdf.parse(lDate);
        java.sql.Date sqlDate = new java.sql.Date(langDate.getTime());
        System.out.print("Returning date of the book (yyyy/MM/dd): ");
        String rDate = sc.nextLine();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        java.util.Date langDate2 = formatter.parse(rDate);
        java.sql.Date sqlDate2 = new java.sql.Date(langDate2.getTime());

        System.out.print("Enter an existing user´s code: ");
        String userCode = sc.nextLine();
        System.out.println("Enter the lent book´s ISBN: ");
        String bookISBN = sc.nextLine();

        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            LendingJpaEntity lending = new LendingJpaEntity();
            lending.setId(idlending);
            lending.setLendingdate(sqlDate);
            lending.setReturningdate(sqlDate2);
            lending.setBorrower(getUserByCode(userCode));
            lending.setBook(getBookByIsbn(bookISBN));
            session.save(lending);
            transaction.commit();

            System.out.println("The lending has been inserted into the database.\n");
        }  catch (HibernateException hiex) {
            System.err.println( hiex.getMessage() );
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static UsersJpaEntity getUserByCode(String code) {
        UsersJpaEntity user = null;
        try (Session session = openSession()) {
            Query<UsersJpaEntity> userQuery =
                    session.createQuery("from com.aajaor2122.unit5.UsersJpaEntity where code='" +
                            String.valueOf(code) + "' ");
            List<UsersJpaEntity> users = userQuery.list();
            user = (UsersJpaEntity) users.get(0);

            if (user == null)
                System.out.println("The code of the user is NOT correct.");

        } catch (HibernateException hiex) {
            System.err.println( hiex.getMessage() );
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public static BooksJpaEntity getBookByIsbn (String isbn) {
        BooksJpaEntity book = null;
        try (Session session = openSession()) {
            Query<BooksJpaEntity> booksQuery =
                    session.createQuery("from com.aajaor2122.unit5.BooksJpaEntity where isbn='" +
                            String.valueOf(isbn) + "' ");
            List<BooksJpaEntity> books = booksQuery.list();
            book = (BooksJpaEntity) books.get(0);

            if (book == null)
                System.out.println("The ISBN of the book is NOT correct.");

        } catch (HibernateException hiex) {
            System.err.println( hiex.getMessage() );
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return book;
    }

    public static void insertUser() throws ParseException {
        System.out.print("Code of the new user? ");
        String ucode = sc.nextLine();
        System.out.print("Name of the new user?: ");
        String uname = sc.nextLine();
        System.out.print("Surname of the new user?:  ");
        String usurname = sc.nextLine();
        System.out.print("Birth´s date of the user (yyyy/MM/dd): ");
        String birthday = sc.nextLine();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        java.util.Date langDate = sdf.parse(birthday);
        java.sql.Date sqlDate = new java.sql.Date(langDate.getTime());
    }
}
