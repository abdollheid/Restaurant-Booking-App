package com.Assignment.Restaurant.rest;

import com.Assignment.Restaurant.RestaurantApplication;
import com.Assignment.Restaurant.entity.Reservation;
import com.Assignment.Restaurant.entity.Table;
import com.Assignment.Restaurant.service.AdminService;

import com.Assignment.Restaurant.utils.Date;

import com.Assignment.Restaurant.validation.ValidDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    private static final Logger logger = LogManager.getLogger(RestaurantApplication.class);

    @Autowired
    private AdminService adminService;

    @GetMapping("/table")
    public ResponseEntity<List<Table>> getTables(HttpServletRequest request) {
        logger.trace("@App-CustomExceptionHandler-getTables: Got request on " + request.getRequestURI());

        List<Table> allTables = adminService.getAllTables();

        if (allTables == null || allTables.size() < 1)
            new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        logger.debug("@App-CustomExceptionHandler-getTables: allTables: " + allTables);

        return new ResponseEntity<>(allTables, HttpStatus.OK);

    }

    @PostMapping("/table")
    public ResponseEntity<Table> addTable(HttpServletRequest request, @RequestBody Table table) {
        logger.trace("@App-CustomExceptionHandler-addTable: request on " + request.getRequestURI());


        logger.debug("@App-CustomExceptionHandler-addTable: table to add: " + table);


        table = adminService.addTable(table);

        if (table == null) {
            logger.debug("@App-CustomExceptionHandler-addTable: couldnt create table");
            throw new RuntimeException("Couldnt save the table");
        }


        logger.debug("@App-CustomExceptionHandler-addTable: created Table: " + table);

        return new ResponseEntity<>(table, HttpStatus.CREATED);
    }

    @GetMapping("/reservation")  // need to change to params urls
    public ResponseEntity<List<Reservation>> getReservationOnDate(HttpServletRequest request, @Valid @ModelAttribute Date date) {
        logger.debug("@App-CustomExceptionHandler-getReservationOnDate: Got request on " + request.getRequestURI());


        logger.debug("@App-CustomExceptionHandler-getReservationOnDate: requested date: " + date);


        List<Reservation> reservationsOnDate = adminService.getReservationsOnDate(date);

        if (reservationsOnDate == null || reservationsOnDate.size() < 1)
            new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        logger.debug("@App-CustomExceptionHandler-getReservationOnDate: reservations: " + reservationsOnDate);

        return new ResponseEntity<>(reservationsOnDate, HttpStatus.OK);
    }


}
