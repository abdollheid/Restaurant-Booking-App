package com.Assignment.Restaurant;

import com.Assignment.Restaurant.dao.ReservationRepository;
import com.Assignment.Restaurant.dao.TableRepository;
import com.Assignment.Restaurant.dao.UserRepository;
import com.Assignment.Restaurant.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class RestaurantApplication {


	public static void main(String[] args) {
		SpringApplication.run(RestaurantApplication.class, args);

	}

}
