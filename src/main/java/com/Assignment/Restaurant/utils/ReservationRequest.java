package com.Assignment.Restaurant.utils;


import com.Assignment.Restaurant.validation.ValidReservationRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.AssertTrue;


@JsonIgnoreProperties(ignoreUnknown = true, value = {""})
public class ReservationRequest {
    @ValidReservationRequest
    private String startTime;

    @ValidReservationRequest
    private String endTime;

    private int size;

    @AssertTrue(message = "endTime must be greater than startTime")
    private boolean isPeriod() {
        if (startTime != null && endTime != null)
            return this.endTime.compareTo(this.startTime) > 0;

        return false;
    }


    public ReservationRequest() {
    }

    public ReservationRequest(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ReservationRequest(String startTime, String endTime, int size) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.size = size;
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
    public String toString() {
        return "ReservationTimestamp{" +
                "startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", size=" + size +
                '}';
    }
}
