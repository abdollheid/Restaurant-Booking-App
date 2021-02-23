package com.Assignment.Restaurant.utils;

import com.Assignment.Restaurant.validation.ValidDate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true, value = {""})
public class Date {

    @ValidDate
    private String date;


    public Date() {
    }

    public Date(String date) {
        this.date = date;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Date{" +
                "date='" + date + '\'' +
                '}';
    }
}
