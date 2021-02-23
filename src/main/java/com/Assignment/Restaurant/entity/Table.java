package com.Assignment.Restaurant.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.util.List;

@Entity
@javax.persistence.Table(name = "tables")
@JsonIgnoreProperties(ignoreUnknown = true, value = {"reservationList"})
public class Table {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "table_id")
    private int id;

    @Positive
    @Column(name = "table_size")
    private int size;

    @OneToMany(mappedBy = "table", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservationList;

    public Table() {
    }

    public Table(int size) {

        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof User))
            return false;

        Table anotherTable = (Table) obj;

        return this.id == anotherTable.id && this.size == anotherTable.size;
    }

    @Override
    public String toString() {
        return "Table{" +
                "id=" + id +
                ", size=" + size +
                '}';
    }
}
