package com.aajaor2122.unit5;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users", schema = "public", catalog = "Libraries")
public class UsersJpaEntity {
    private String code;
    private String name;
    private String surname;
    private Date birthdate;
    private Set<LendingJpaEntity> lentBooks;
    private Set<ReservationsJpaEntity> reservedBooks;

    @Id
    @Column(name = "code", nullable = false, length = 8)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 25)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "surname", nullable = false, length = 25)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "birthdate", nullable = true)
    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersJpaEntity that = (UsersJpaEntity) o;
        return Objects.equals(code, that.code) && Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && Objects.equals(birthdate, that.birthdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, surname, birthdate);
    }

    @OneToMany(mappedBy = "borrower")
    public Set<LendingJpaEntity> getLentBooks() {
        return lentBooks;
    }

    public void setLentBooks(Set<LendingJpaEntity> lentBooks) {
        this.lentBooks = lentBooks;
    }

    @OneToMany(mappedBy = "reserver")
    public Set<ReservationsJpaEntity> getReservedBooks() {
        return reservedBooks;
    }

    public void setReservedBooks(Set<ReservationsJpaEntity> reservedBooks) {
        this.reservedBooks = reservedBooks;
    }
}
