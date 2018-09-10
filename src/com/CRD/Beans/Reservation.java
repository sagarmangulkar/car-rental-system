package com.CRD.Beans;

import java.util.Date;

public class Reservation {
    private Date pickUpDate;
    private int numberOfDays;
    private CarType carType;
    //future scope: reservationId, userDrivingLicenceId

    public Reservation(Date pickUpDate, int numberOfDays, CarType carType) {
        this.pickUpDate = pickUpDate;
        this.numberOfDays = numberOfDays;
        this.carType = carType;
    }

    public Date getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(Date pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "pickUpDate=" + pickUpDate +
                ", numberOfDays=" + numberOfDays +
                ", carType=" + carType +
                '}';
    }
}
