package com.Assignment.Restaurant.dao;

import com.Assignment.Restaurant.entity.Reservation;
import com.Assignment.Restaurant.entity.Table;

import com.Assignment.Restaurant.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    @Query("select u from Reservation u where u.startTime like CONCAT(:date,'%')")
    public List<Reservation> getReservationsOnDate(Sort sort, @Param("date") String date);

    @Query("select u from Reservation u where u.user = :user")
    public List<Reservation> getReservationsForUser(Sort sort, @Param("user") User user);

}