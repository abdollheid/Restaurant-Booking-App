package com.Assignment.Restaurant.entity;

import com.Assignment.Restaurant.utils.ReservationRequest;

import com.Assignment.Restaurant.validation.ValidTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Entity
@javax.persistence.Table(name = "reservations")
@JsonIgnoreProperties(ignoreUnknown = true, value = {"id", "user"})
public class Reservation implements Comparable<Reservation> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private int id;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "table_id")
    private Table table;

    @ValidTimestamp
    @Column(name = "reservation_start_time")
    private String startTime;

    @ValidTimestamp
    @Column(name = "reservation_end_time")
    private String endTime;

    @Positive
    @Column(name = "reservation_size")
    private int size;

    public Reservation() {
    }

    public Reservation(User user, Table table, String startTime, String endTime, int size) {
        this.user = user;
        this.table = table;
        this.startTime = startTime;
        this.endTime = endTime;
        this.size = size;
    }

    public Reservation(ReservationRequest reservationRequest) {
        this.startTime = reservationRequest.getStartTime();
        this.endTime = reservationRequest.getEndTime();
        this.size = reservationRequest.getSize();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public int compareTo(Reservation o) {
        return startTime.compareTo(o.startTime);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof User))
            return false;

        Reservation anotherReservation = (Reservation) obj;

        return this.id == anotherReservation.id && this.user.equals(anotherReservation.user) && this.table.equals(anotherReservation.table) && this.startTime.equals(anotherReservation.startTime);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", user=" + user +
                ", table=" + table +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", size=" + size +
                '}';
    }
}


