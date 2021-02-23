package com.Assignment.Restaurant;

import com.Assignment.Restaurant.dao.ReservationRepository;
import com.Assignment.Restaurant.dao.TableRepository;
import com.Assignment.Restaurant.dao.UserRepository;
import com.Assignment.Restaurant.entity.Reservation;
import com.Assignment.Restaurant.entity.Table;
import com.Assignment.Restaurant.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.Optional;

import static com.Assignment.Restaurant.Faker.*;
import static org.assertj.core.api.Assertions.*;


@SpringBootTest()
public class EntitiesTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private ReservationRepository reservationRepository;



    @Test
    @Transactional
    public void testUser(){
        User userLocal = getUser(false);
        User userPersisted = userRepository.save(userLocal);

        assertThat(userPersisted).isNotEqualTo(null) ;
        assertThat(userLocal).isEqualTo(userPersisted);

    }

    @Test
    @Transactional
    public void testTable(){
        Table tableLocal = getTable();
        Table tablePersisted = tableRepository.save(tableLocal);

        assertThat(tablePersisted).isNotEqualTo(null) ;
        assertThat(tableLocal).isEqualTo(tablePersisted);

    }

    @Test
    @Transactional
    public void testReservation(){
        Reservation reservationLocal = Faker.getReservation(false , 2);

        userRepository.save(reservationLocal.getUser());
        tableRepository.save(reservationLocal.getTable());
        Reservation reservationPersisted = reservationRepository.save(reservationLocal);

        assertThat(reservationPersisted).isNotEqualTo(null) ;
        assertThat(reservationLocal).isEqualTo(reservationPersisted);

    }

    @Test
    @Transactional
    public void testAdmin() {
        User adminUser = userRepository.findById(1).get();
        assertThat(adminUser.isAdmin()).isTrue();
    }





}

