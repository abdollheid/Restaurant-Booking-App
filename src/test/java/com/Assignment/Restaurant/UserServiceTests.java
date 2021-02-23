package com.Assignment.Restaurant;

import com.Assignment.Restaurant.dao.ReservationRepository;
import com.Assignment.Restaurant.dao.TableRepository;
import com.Assignment.Restaurant.dao.UserRepository;
import com.Assignment.Restaurant.entity.Reservation;
import com.Assignment.Restaurant.entity.Table;
import com.Assignment.Restaurant.entity.User;
import com.Assignment.Restaurant.service.AdminService;
import com.Assignment.Restaurant.service.UserService;
import com.Assignment.Restaurant.utils.ReservationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.Assignment.Restaurant.Faker.getReservation;
import static com.Assignment.Restaurant.Faker.getUser;
import static org.assertj.core.api.Assertions.*;


@SpringBootTest()
public class UserServiceTests {

    @Autowired
    private UserService userService;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private ReservationRepository reservationRepository;


    @Test
    @Transactional
    public void testMakeRerservation() {
        Reservation reservation = getReservation(false, 2);

        userRepository.save(reservation.getUser());
        tableRepository.save(reservation.getTable());


        Reservation reservation1 = userService.makeReservation(reservation);
        Reservation reservation2 = userService.makeReservation(reservation);

        assertThat(reservation1).isNotEqualTo(null);
        assertThat(reservation2).isNotEqualTo(null);
    }

    @Test
    @Transactional
    public void testGetReservations() {

        Reservation reservation = getReservation(false,2);
        User user = userRepository.save(reservation.getUser());
        tableRepository.save(reservation.getTable());


        System.out.println(reservation);

        userService.makeReservation(reservation);

        Reservation reservation2 = getReservation(false,2);
        tableRepository.save(reservation2.getTable());
        reservation2.setUser(user);


        userService.makeReservation(reservation2);

        List<Reservation> reservationList = userService.getReservations(user);

        assertThat(reservationList).isNotEqualTo(null);
        assertThat(reservationList.size()).isGreaterThan(0);
        assertThat(reservationList.get(0)).isEqualTo(reservation);
        assertThat(reservationList.get(1)).isEqualTo(reservation2);

    }


    @Test
    @Transactional
    public void testAddUser() {
        User user = getUser(false);
        user = userService.addUser(user);
        assertThat(user).isNotEqualTo(null);
    }

    @Test
    @Transactional
    public void testGetAvailableTableForReservation() {

        Reservation reservation = getReservation(true,7, "2021-01-01 20:00:00", "2021-01-01 21:00:00");

        Table availableTable = userService.getAvailableTableForReservation(reservation);

        assertThat(availableTable).isNotEqualTo(null);
        assertThat(availableTable.getSize()).isEqualTo(10); // first suitable table for size of 4

        userRepository.save(reservation.getUser());
        reservation.setTable(availableTable);
        userService.makeReservation(reservation);



        Reservation reservation2 = getReservation(true,7, "2021-01-01 20:00:00", "2021-01-01 21:00:00");

        Table availableTable2 = userService.getAvailableTableForReservation(reservation2);

        assertThat(availableTable2).isNotEqualTo(null);
        assertThat(availableTable2.getSize()).isEqualTo(10); // first suitable table for size of 4
        assertThat(availableTable.getId()).isNotEqualTo(availableTable2.getId());


        userRepository.save(reservation2.getUser());
        reservation2.setTable(availableTable2);
        userService.makeReservation(reservation2);





        Reservation busyReservation = getReservation(true,7, "2021-01-01 20:00:00", "2021-01-01 21:00:00");

        Table noTable = userService.getAvailableTableForReservation(busyReservation);
        assertThat(noTable).isEqualTo(null);


        busyReservation = getReservation(true,7, "2021-01-01 20:00:00", "2021-01-01 22:00:00");

        noTable = userService.getAvailableTableForReservation(busyReservation);
        assertThat(noTable).isEqualTo(null);

        busyReservation = getReservation(true,7, "2021-01-01 19:00:00", "2021-01-01 22:00:00");

        noTable = userService.getAvailableTableForReservation(busyReservation);
        assertThat(noTable).isEqualTo(null);

        busyReservation = getReservation(true,7, "2021-01-01 19:00:00", "2021-01-01 20:00:01");

        noTable = userService.getAvailableTableForReservation(busyReservation);
        assertThat(noTable).isEqualTo(null);

        busyReservation = getReservation(true,7, "2021-01-01 20:30:00", "2021-01-01 20:35:01");

        noTable = userService.getAvailableTableForReservation(busyReservation);
        assertThat(noTable).isEqualTo(null);

        busyReservation = getReservation(true,7, "2021-01-01 20:30:00", "2021-01-01 22:35:01");

        noTable = userService.getAvailableTableForReservation(busyReservation);
        assertThat(noTable).isEqualTo(null);


        Reservation anotherTime = getReservation(true,7, "2021-01-01 19:00:00", "2021-01-01 20:00:00");
        availableTable = userService.getAvailableTableForReservation(anotherTime);
        assertThat(availableTable).isNotEqualTo(null);


        anotherTime = getReservation(true,7, "2021-01-01 21:00:00", "2021-01-01 22:00:00");
        availableTable = userService.getAvailableTableForReservation(anotherTime);
        assertThat(availableTable).isNotEqualTo(null);

    }


    @Test
    @Transactional
    public void testGetAvailableTableForReservationRequest() {
        List<Table> tableList = tableRepository.findAll();

        List<Table> availableTables = userService.getAvailableTableForReservationRequest(new ReservationRequest("2021-01-01 20:00:00", "2021-01-01 21:00:00"));

        assertThat(tableList.size()).isEqualTo(availableTables.size()) ;


    }


}
