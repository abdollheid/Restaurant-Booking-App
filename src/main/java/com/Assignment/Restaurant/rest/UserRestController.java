package com.Assignment.Restaurant.rest;

import com.Assignment.Restaurant.RestaurantApplication;
import com.Assignment.Restaurant.config.CustomUserDetails;
import com.Assignment.Restaurant.entity.Reservation;
import com.Assignment.Restaurant.entity.Table;
import com.Assignment.Restaurant.entity.User;
import com.Assignment.Restaurant.service.UserService;
import com.Assignment.Restaurant.utils.ReservationRequest;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class UserRestController {
    private static final Logger logger = LogManager.getLogger(RestaurantApplication.class);

    @Autowired
    private SmartValidator validator;

    @Autowired
    @Qualifier("passwordEncoder")
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(HttpServletRequest request, @Valid @RequestBody User user) throws ServletException {
        logger.trace("@App-UserRestController-register: Got request on " + request.getRequestURI());

        logger.debug("@App-UserRestController-register: User to add: " + user);

        initNewUser(request);

        String rawPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        logger.debug("@App-UserRestController-register: Saving user: " + user);

        user = userService.addUser(user);


        if (user == null) {
            throw new RuntimeException("Couldnt save user try again");
        }
        logger.debug("@App-UserRestController-register: Registered user: " + user);

        // login new user
        request.login(user.getEmail(), rawPassword);

        logger.debug("@App-UserRestController-register: Logged in user: " + user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);

    }


    @GetMapping("/reservation")
    public ResponseEntity<List<Reservation>> getReservations(HttpServletRequest request) {
        logger.trace("@App-UserRestController-getReservations: Got request on " + request.getRequestURI());

        List<Reservation> reservations = userService.getReservations(getUser(request));


        if (reservations == null || reservations.size() < 1)
            new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        logger.debug("@App-UserRestController-getReservations: Reservations: " + reservations);


        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/table")
    public ResponseEntity<List<Table>> getAvailableTables(HttpServletRequest request, @Valid @ModelAttribute ReservationRequest reservationRequest) {
        logger.trace("@App-UserRestController-getAvailableTables: Got request on " + request.getRequestURI());


        logger.debug("@App-UserRestController-getAvailableTables: Submitted reservationRequest: " + reservationRequest);


        List<Table> tables = userService.getAvailableTableForReservationRequest(reservationRequest);

        if (tables == null || tables.size() < 1)
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);


        logger.debug("@App-UserRestController-getAvailableTables: Tables: " + tables);


        return new ResponseEntity<>(tables, HttpStatus.OK);
    }


    @PostMapping("/reservation")
    public ResponseEntity<Reservation> makeReservation(HttpServletRequest request, @Valid @RequestBody ReservationRequest reservationRequest) {
        logger.trace("@App-UserRestController-makeReservation: Got request on " + request.getRequestURI());

        logger.debug("@App-UserRestController-makeReservation: Submitted reservationRequest: " + reservationRequest);


        Reservation reservation = validateReservation(new Reservation(reservationRequest));


        Table table = userService.getAvailableTableForReservation(reservation);

        if (table == null)
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        logger.debug("@App-UserRestController-makeReservation:  Found suitable table: " + table);


        reservation.setTable(table);

        reservation.setUser(getUser(request));

        reservation = userService.makeReservation(reservation);


        if (reservation == null) {
            throw new RuntimeException("Couldnt make the reservation, please try again");
        }

        logger.debug("@App-UserRestController-makeReservation: Reservation made: " + reservation);


        return new ResponseEntity<Reservation>(reservation, HttpStatus.CREATED);
    }

    private Reservation validateReservation(@Valid Reservation reservation) {
        logger.trace("@App-UserRestController-validateReservation");


        logger.debug("@App-UserRestController-validateReservation:  Reservation Valid: " + reservation);

        return reservation;
    }


    private void initNewUser(HttpServletRequest httpServletRequest) {
        logger.trace("@App-UserRestController-initNewUser");

        HttpSession session = httpServletRequest.getSession(false);
        SecurityContextHolder.clearContext();
        if (session != null) {
            logger.debug("@App-UserRestController-initNewUser: Session found");

            session.invalidate();
            logger.debug("@App-UserRestController-initNewUser: Session cleared");

        }

        httpServletRequest.getSession(); // create session

        logger.debug("@App-UserRestController-initNewUser: New session created");


    }

    private User getUser(HttpServletRequest httpServletRequest) {
        logger.trace("@App-UserRestController-getUser");


        User user = (User) (httpServletRequest.getSession().getAttribute("user"));

        if (user == null) {
            logger.debug("@App-UserRestController-getUser: No user in env");

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            user = ((CustomUserDetails) auth.getPrincipal()).getUser();
            httpServletRequest.getSession().setAttribute("user", user);
            logger.debug("@App-UserRestController-getUser: User logged in and stored in env");

        }

        logger.trace("@App-UserRestController-getUser: Done");


        return user;
    }


}
