package com.Assignment.Restaurant.service;

import com.Assignment.Restaurant.entity.Reservation;
import com.Assignment.Restaurant.entity.Table;
import com.Assignment.Restaurant.entity.User;
import com.Assignment.Restaurant.utils.ReservationRequest;

import java.util.List;


public interface UserService {
    public List<Reservation> getReservations(User user);

    public User addUser(User user);

    public Table getAvailableTableForReservation(Reservation reservation);

    public List<Table> getAvailableTableForReservationRequest(ReservationRequest reservation);

    public Reservation makeReservation(Reservation reservation);
}