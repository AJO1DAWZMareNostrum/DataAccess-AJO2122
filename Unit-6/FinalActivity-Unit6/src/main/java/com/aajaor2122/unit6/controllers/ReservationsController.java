package com.aajaor2122.unit6.controllers;

import com.aajaor2122.unit6.models.dao.IReservationsJpaEntityDAO;
import com.aajaor2122.unit6.models.entities.ReservationsJpaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-rest-aajaor2122/Reservations")
public class ReservationsController {

    @Autowired
    private IReservationsJpaEntityDAO reservationDAO;

    @GetMapping
    public List<ReservationsJpaEntity> findAllUsers() {
        return (List<ReservationsJpaEntity>) reservationDAO.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationsJpaEntity> findEmployeeById(@PathVariable(value = "id") int id) {
        Optional<ReservationsJpaEntity> reservation = reservationDAO.findById(id);

        if (reservation.isPresent()) {
            return ResponseEntity.ok().body(reservation.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ReservationsJpaEntity saveReservation(@Validated @RequestBody ReservationsJpaEntity reservation) {
        return reservationDAO.save(reservation);
    }

}
