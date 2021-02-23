package com.Assignment.Restaurant.service;

import com.Assignment.Restaurant.dao.ReservationRepository;
import com.Assignment.Restaurant.dao.TableRepository;
import com.Assignment.Restaurant.dao.UserRepository;
import com.Assignment.Restaurant.entity.Reservation;
import com.Assignment.Restaurant.entity.Table;
import com.Assignment.Restaurant.entity.User;
import com.Assignment.Restaurant.utils.ReservationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TableRepository tableRepository;


    @Override
    public List<Reservation> getReservations(User user) {
        return reservationRepository.getReservationsForUser(Sort.by("startTime"), user);
    }

    @Override
    public User addUser(User user) {
        user.setId(0); // to make sure its inserting
        return userRepository.save(user);
    }

    @Override
    public Table getAvailableTableForReservation(Reservation reservation) {
        return tableRepository.getAvailableTable(reservation.getStartTime(), reservation.getEndTime(), reservation.getSize());
    }

    @Override
    public List<Table> getAvailableTableForReservationRequest(ReservationRequest reservationRequest) {
        return tableRepository.getAvailableTables(reservationRequest.getStartTime(), reservationRequest.getEndTime());
    }


    public Reservation makeReservation(Reservation reservation) {
        reservation.setId(0);
        return reservationRepository.save(reservation);
    }
}
























