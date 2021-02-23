package com.Assignment.Restaurant.service;

import com.Assignment.Restaurant.dao.ReservationRepository;
import com.Assignment.Restaurant.dao.TableRepository;
import com.Assignment.Restaurant.dao.UserRepository;
import com.Assignment.Restaurant.entity.Reservation;
import com.Assignment.Restaurant.entity.Table;
import com.Assignment.Restaurant.utils.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImp implements AdminService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private ReservationRepository reservationRepository;


    @Override
    public List<Table> getAllTables() {
        return tableRepository.findAll();
    }

    @Override
    public Table addTable(Table table) {
        table.setId(0);  // to make sure its inserting
        table = tableRepository.save(table);
        return table;
    }

    @Override
    public void removeTable(int id) {
        tableRepository.deleteById(id);
    }

    @Override
    public List<Reservation> getReservationsOnDate(Date date) {
        return reservationRepository.getReservationsOnDate(Sort.by("startTime"), date.getDate());
    }


}
