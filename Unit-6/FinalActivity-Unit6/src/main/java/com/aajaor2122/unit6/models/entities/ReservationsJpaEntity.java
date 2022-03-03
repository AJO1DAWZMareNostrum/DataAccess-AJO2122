package com.aajaor2122.unit6.models.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Date;

@Entity
@Table(name = "reservations", schema = "public", catalog = "Libraries")
public class ReservationsJpaEntity {
    private int id;
    private Date date;
    private String book;
    private String borrower;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @NotEmpty(message = "The book isbn cannot be empty.")
    @Column(name = "book", nullable = false)
    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    @Basic
    @NotEmpty(message = "The borrower´s code needs to be provided.")
    @Column(name = "borrower", nullable = false)
    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReservationsJpaEntity that = (ReservationsJpaEntity) o;

        if (id != that.id) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (book != null ? !book.equals(that.book) : that.book != null) return false;
        if (borrower != null ? !borrower.equals(that.borrower) : that.borrower != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (book != null ? book.hashCode() : 0);
        result = 31 * result + (borrower != null ? borrower.hashCode() : 0);
        return result;
    }
}
