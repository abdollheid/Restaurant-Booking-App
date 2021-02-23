package com.Assignment.Restaurant.service;

import com.Assignment.Restaurant.entity.Reservation;
import com.Assignment.Restaurant.entity.Table;
import com.Assignment.Restaurant.utils.Date;

import java.util.List;

public interface AdminService {

    public List<Table> getAllTables();

    public Table addTable(Table table);

    public void removeTable(int id);

    public List<Reservation> getReservationsOnDate(Date date);
}
