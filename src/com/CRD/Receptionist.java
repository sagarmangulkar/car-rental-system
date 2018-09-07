package com.CRD;

import com.CRD.Beans.CarType;
import com.CRD.Beans.Reservation;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Receptionist {
    public String createReservation(Date pickUpDate, int numberOfDays, CarType carType) {
        Reservation reservation = new Reservation(pickUpDate, numberOfDays, carType);
        return "temp";
    }

    public void countCarsInitially() {
        AVAILABLE_CARS = new Hashtable<>();
        AVAILABLE_CARS.put(CarType.SEDAN, 6);
        AVAILABLE_CARS.put(CarType.SUV, 3);
        AVAILABLE_CARS.put(CarType.VAN, 2);
    }

    public static Map<CarType, Integer> AVAILABLE_CARS;
    public static Map<String, List<Reservation>> RESEREVATIONS;
}
