package com.aajaor2122.unit5;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "reservations", schema = "public", catalog = "Libraries")
public class ReservationsJpaEntity {
    private int idreserv;
    private Date reservedate;
    private UsersJpaEntity reserver;
    private BooksJpaEntity book;

    @Id
    @Column(name = "idreserv", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getIdreserv() {
        return idreserv;
    }

    public void setIdreserv(int idreserv) {
        this.idreserv = idreserv;
    }

    @Basic
    @Column(name = "reservedate", nullable = false)
    public Date getReservedate() {
        return reservedate;
    }

    public void setReservedate(Date reservedate) {
        this.reservedate = reservedate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationsJpaEntity that = (ReservationsJpaEntity) o;
        return idreserv == that.idreserv && Objects.equals(reservedate, that.reservedate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idreserv, reservedate);
    }

    @ManyToOne
    @JoinColumn(name = "reserver", referencedColumnName = "code", nullable = false)
    public UsersJpaEntity getReserver() {
        return reserver;
    }

    public void setReserver(UsersJpaEntity reserver) {
        this.reserver = reserver;
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
