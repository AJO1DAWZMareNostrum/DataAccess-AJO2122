package com.aajaor2122.unit6.controllers;

import com.aajaor2122.unit6.models.dao.IBooksJpaEntityDAO;
import com.aajaor2122.unit6.models.dao.IReservationsJpaEntityDAO;
import com.aajaor2122.unit6.models.entities.BooksEntity;
import com.aajaor2122.unit6.models.entities.ReservationsJpaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-rest-aajaor2122/Books")
public class BooksController {

    @Autowired
    private IBooksJpaEntityDAO bookDAO;

    @DeleteMapping("/{isbn}")
    public ResponseEntity<?> deleteReservation(@PathVariable(value = "isbn") String isbn) {
        Optional<BooksEntity> book = bookDAO.findById(isbn);
        if (book.isPresent()) {
            bookDAO.deleteById(isbn);
            return ResponseEntity.ok().body("Deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
