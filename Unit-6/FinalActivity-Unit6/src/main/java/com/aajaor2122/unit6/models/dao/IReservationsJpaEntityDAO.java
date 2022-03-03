package com.aajaor2122.unit6.models.dao;

import com.aajaor2122.unit6.models.entities.ReservationsJpaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReservationsJpaEntityDAO extends CrudRepository<ReservationsJpaEntity, Integer> {
}
