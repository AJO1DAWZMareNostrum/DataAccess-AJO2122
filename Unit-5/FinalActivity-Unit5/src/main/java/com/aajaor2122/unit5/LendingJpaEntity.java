package com.aajaor2122.unit5;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "lending", schema = "public", catalog = "Libraries")
@Where(clause = "returningdate is null") // Filters lendings to return only the one´s NOT returned yet
public class LendingJpaEntity {
    private int id;
    private Date lendingdate;
    private Date returningdate;
    private UsersJpaEntity borrower;
    private BooksJpaEntity book;

    @Id
    @Column(name = "id", nullable = false)
    // He cambiado tipo de strategia de IDENTITY a AUTO
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "lendingdate", nullable = false)
    public Date getLendingdate() {
        return lendingdate;
    }

    public void setLendingdate(Date lendingdate) {
        this.lendingdate = lendingdate;
    }

    @Basic
    @Column(name = "returningdate", nullable = true)
    public Date getReturningdate() {
        return returningdate;
    }

    public void setReturningdate(Date returningdate) {
        this.returningdate = returningdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LendingJpaEntity that = (LendingJpaEntity) o;
        return id == that.id && Objects.equals(lendingdate, that.lendingdate) && Objects.equals(returningdate, that.returningdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lendingdate, returningdate);
    }

    @ManyToOne
    @JoinColumn(name = "borrower", referencedColumnName = "code", nullable = false)
    public UsersJpaEntity getBorrower() {
        return borrower;
    }

    public void setBorrower(UsersJpaEntity borrower) {
        this.borrower = borrower;
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
