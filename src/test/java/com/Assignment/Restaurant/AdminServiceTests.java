package com.Assignment.Restaurant;

import com.Assignment.Restaurant.dao.ReservationRepository;
import com.Assignment.Restaurant.dao.TableRepository;
import com.Assignment.Restaurant.dao.UserRepository;
import com.Assignment.Restaurant.entity.Reservation;
import com.Assignment.Restaurant.entity.Table;
import com.Assignment.Restaurant.entity.User;
import com.Assignment.Restaurant.service.AdminService;
import com.Assignment.Restaurant.service.UserService;
import com.Assignment.Restaurant.utils.Date;
import com.Assignment.Restaurant.utils.ReservationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest()
public class AdminServiceTests {

    @Autowired
    private AdminService adminService;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private ReservationRepository reservationRepository;




    @Test
    @Transactional
    public void testGetAllTables() {

        List<Table> tableList = tableRepository.findAll();

        List<Table> availableTables = adminService.getAllTables();

        assertThat(tableList.size()).isEqualTo(availableTables.size()) ;


    }

    @Test
    @Transactional
    public void testAddTable() {
        Table table = Faker.getTable();

        table = adminService.addTable(table);

        assertThat(table).isNotEqualTo(null) ;
        assertThat(table.getId()).isNotEqualTo(0) ;


    }


    @Test
    @Transactional
    public void testGetReservationsOnDate() {
        Reservation reservation = Faker.getReservation(false, 2, "2021-01-01 21:00:00", "2021-01-01 22:00:00");

        userRepository.save(reservation.getUser());
        tableRepository.save(reservation.getTable());
        reservationRepository.save(reservation);


        List<Reservation> reservationsOnDate = adminService.getReservationsOnDate(new Date("2021-01-01"));

        assertThat(reservationsOnDate).isNotEqualTo(null) ;
        assertThat(reservationsOnDate.get(0)).isEqualTo(reservation);
    }
}
