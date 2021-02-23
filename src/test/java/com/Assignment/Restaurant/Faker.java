package com.Assignment.Restaurant;

import com.Assignment.Restaurant.dao.ReservationRepository;
import com.Assignment.Restaurant.dao.TableRepository;
import com.Assignment.Restaurant.dao.UserRepository;
import com.Assignment.Restaurant.entity.Reservation;
import com.Assignment.Restaurant.entity.Table;
import com.Assignment.Restaurant.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

public class Faker {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private ReservationRepository reservationRepository;





    public User getAdmin(){
        return userRepository.findById(1).get();
    }

    public static User getUser(boolean rand) {
        if (!rand)
            return new User("name", "name@emial.com", "12345", "01000000000");

        StringBuilder stringBuilder = new StringBuilder("011");

        int size = 8 ;
        Random random = new Random();
        for (int i = 0 ;  i < size; ++i){
            int num = random.nextInt(10) ;

            stringBuilder.append(num);
        }

        String randnums = stringBuilder.toString();
        return new User("name", "test"+ randnums   + "@emial.com", "12345", randnums);
    }

    public static Table getTable() {
        return new Table(2);
    }


    public static Reservation getReservation(boolean rand, int size) {
        User user = getUser(rand);
        Table table = getTable();
        return new Reservation(user, table, "2021-01-01 20:00:00", "2021-01-01 21:00:00", size);
    }


    public static Reservation getReservation(boolean rand, int size, String startTime, String endTime) {
        User user = getUser(rand);
        Table table = getTable();
        return new Reservation(user, table, startTime, endTime, size);
    }
}
