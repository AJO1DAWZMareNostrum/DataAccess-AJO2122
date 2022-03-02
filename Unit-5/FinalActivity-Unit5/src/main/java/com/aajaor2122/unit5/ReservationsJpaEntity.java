package com.aajaor2122.unit5;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "reservations", schema = "public", catalog = "Libraries")
public class ReservationsJpaEntity {
    private int id;
    private Date date;
    private UsersJpaEntity borrower;
    private BooksJpaEntity book;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int idreserv) {
        this.id = idreserv;
    }

    @Basic
    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date reservedate) {
        this.date = reservedate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationsJpaEntity that = (ReservationsJpaEntity) o;
        return id == that.id && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date);
    }

    @ManyToOne
    @JoinColumn(name = "borrower", referencedColumnName = "code", nullable = false)
    public UsersJpaEntity getBorrower() {
        return borrower;
    }

    public void setBorrower(UsersJpaEntity reserver) {
        this.borrower = reserver;
    }

    @ManyToOne
    @JoinColumn(name = "book", referencedColumnName = "isbn", nullable = false)
    public BooksJpaEntity getBook() {
        return book;
    }

    public void setBook(BooksJpaEntity book) {
        this.book = book;
    }
}
