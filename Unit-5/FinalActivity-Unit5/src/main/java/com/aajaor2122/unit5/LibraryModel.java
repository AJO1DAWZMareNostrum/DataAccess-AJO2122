package com.aajaor2122.unit5;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

public class LibraryModel {

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
        } catch (Exception ex) {
            System.err.println("Exception while opening session...");
            LibraryController.reportError(ex);
        }
        if (session == null) {
            LibraryController.resultMessage("Session was found to be null.");
        }

        return session;
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
                LibraryController.resultMessage("The code of the user is NOT correct.");
        } catch (Exception e) {
            LibraryController.reportError(e);
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
                LibraryController.resultMessage("The ISBN of the book is NOT correct.");

        } catch (Exception e) {
            LibraryController.reportError(e);
        }

        return book;
    }

    public static void insertUser(String code, String name, String surname, Date birthday) {
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            UsersJpaEntity newUser = new UsersJpaEntity();
            newUser.setCode(code);
            newUser.setName(name);
            newUser.setSurname(surname);
            newUser.setBirthdate(birthday);
            session.save(newUser);
            transaction.commit();

            LibraryController.resultMessage("The User has been inserted into the database.");
        } catch (Exception e) {
            LibraryController.reportError(e);
        }
    }

    public static void insertBook(String isbn, String title, int copies, String outline, String publisher) {
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            BooksJpaEntity newBook = new BooksJpaEntity();
            newBook.setIsbn(isbn);
            newBook.setTitle(title);
            newBook.setCopies(copies);
            newBook.setOutline(outline);
            newBook.setPublisher(publisher);
            session.save(newBook);
            transaction.commit();

            LibraryController.resultMessage("The Book has been inserted into the database.");
        } catch (Exception e) {
            LibraryController.reportError(e);
        }
    }

    public static void editUser(String code, String name, String surname) {
        try (Session session = openSession()) {
            Query<UsersJpaEntity> usersQuery =
                    session.createQuery("from com.aajaor2122.unit5.UsersJpaEntity where code='" +
                            String.valueOf(code) + "' ");
            List<UsersJpaEntity> users = usersQuery.list();
            Transaction transaction = session.beginTransaction();
            UsersJpaEntity user = (UsersJpaEntity) users.get(0);
            user.setName(name);
            user.setSurname(surname);
            session.update(user);
            transaction.commit();

            LibraryController.resultMessage("The User has been updated successfully.");
        } catch (Exception e) {
        LibraryController.reportError(e);
        }
    }

    public static void editBook(String isbn, String title, int copies, String outline, String publisher) {
        try (Session session = openSession()) {
            Query<BooksJpaEntity> booksQuery =
                    session.createQuery("from com.aajaor2122.unit5.BooksJpaEntity where isbn='" +
                            String.valueOf(isbn) + "' ");
            List<BooksJpaEntity> books = booksQuery.list();
            Transaction transaction = session.beginTransaction();
            BooksJpaEntity book = (BooksJpaEntity) books.get(0);
            book.setTitle(title);
            book.setCopies(copies);
            book.setOutline(outline);
            book.setPublisher(publisher);
            session.update(book);
            transaction.commit();

            LibraryController.resultMessage("The Book has been updated successfully.");
        } catch (Exception e) {
            LibraryController.reportError(e);
        }
    }

    public static void insertLending(UsersJpaEntity user, BooksJpaEntity book) {
        // We obtain and convert the date from today, to insert in the register
        LocalDate todayDateRaw = LocalDate.now();
        Date today = Date.valueOf(todayDateRaw);

        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            LendingJpaEntity lending = new LendingJpaEntity();
            lending.setLendingdate(today);
            lending.setBorrower(user);
            lending.setBook(book);
            session.save(lending);
            transaction.commit();

            LibraryController.resultMessage("The lending has been inserted into the database.");
        }
        catch (Exception e) {
            LibraryController.reportError(e);
        }
    }

    public static void insertReservation(UsersJpaEntity user, BooksJpaEntity book) {
        LocalDate todayDateRaw = LocalDate.now();
        Date today = Date.valueOf(todayDateRaw);

        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            ReservationsJpaEntity reservation = new ReservationsJpaEntity();
            reservation.setDate(today);
            reservation.setBorrower(user);
            reservation.setBook(book);
            session.save(reservation);
            transaction.commit();

            LibraryController.resultMessage("The reservation of this book has been inserted into the database.");
        }
        catch (Exception e) {
            LibraryController.reportError(e);
        }
    }

    // Method that allows to update Lending table, after returning a book to the Library
    //TODO: TESTEAR SI FUNCIONA O NO
    public static LendingJpaEntity returnBook(UsersJpaEntity user, BooksJpaEntity book) {
        LendingJpaEntity lendingToUpdate = null;
        try (Session session = openSession()) {
            Query<LendingJpaEntity> lendingQuery =
                    session.createQuery("from com.aajaor2122.unit5.LendingJpaEntity");
            List<LendingJpaEntity> lendings = lendingQuery.list();
            Transaction transaction = session.beginTransaction();

            for (Object lendObject: lendings) {
                LendingJpaEntity lending = (LendingJpaEntity) lendObject;
                UsersJpaEntity lendingUser = lending.getBorrower();
                BooksJpaEntity lendingBook = lending.getBook();

                if (lendingUser.equals(user) && lendingBook.equals(book)) {
                    lendingToUpdate = lending;
                }
            }

            if (lendingToUpdate == null) {
                LibraryController.resultMessage("This lending data is incorrect or doesn´t match. Try again.");
                return null;
            }

            LocalDate todayDateRaw = LocalDate.now();
            Date today = Date.valueOf(todayDateRaw);

            // We update the lending, with the actual date assigned to "returning date" field
            lendingToUpdate.setReturningdate(today);
            session.update(lendingToUpdate);
            transaction.commit();
            LibraryController.resultMessage("Book returned. Lending updated successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lendingToUpdate;
    }


    public static void updateFinedToNull(String userCode) {
        try (Session session = openSession()) {
            Query<UsersJpaEntity> userQuery =
                    session.createQuery("from com.aajaor2122.unit5.UsersJpaEntity where code='" +
                            String.valueOf(userCode) + "' ");
            List<UsersJpaEntity> users = userQuery.list();
            Transaction transaction = session.beginTransaction();
            UsersJpaEntity user = (UsersJpaEntity) users.get(0);
            user.setFined(null);
            session.update(user);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // We update the 'fined' field to the user, passing the calculated date by parameter
    public static void applyFineIntoUser(String userCode, Date finedDate) {
        try (Session session = openSession()) {
            Query<UsersJpaEntity> userQuery =
                    session.createQuery("from com.aajaor2122.unit5.UsersJpaEntity where code='" +
                            String.valueOf(userCode) + "' ");
            List<UsersJpaEntity> users = userQuery.list();
            Transaction transaction = session.beginTransaction();
            UsersJpaEntity user = (UsersJpaEntity) users.get(0);
            user.setFined(finedDate);
            session.update(user);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
