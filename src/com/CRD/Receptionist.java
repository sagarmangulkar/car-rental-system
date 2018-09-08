package com.CRD;

import com.CRD.Beans.CarType;
import com.CRD.Beans.Reservation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Receptionist {
    public boolean checkReservationAvailability(Date pickUpDate, int numberOfDays, CarType carType) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date pickUpDateWithZeroTime = null;
        try {
            pickUpDateWithZeroTime = formatter.parse(formatter.format(pickUpDate));
            for (int i = 0; i < numberOfDays; i++) {
                String key = pickUpDateWithZeroTime.toString();
                if(reservations.containsKey(key)) {
                    List<Reservation> reservationList = reservations.get(key);
                    int carCount = availableCars.get(carType);
                    for (Reservation reservation : reservationList) {
                        if (reservation.getCarType().equals(carType)) {
                            carCount--;
                        }
                        if (carCount == 0) {
                            return false;
                        }
                    }
                }
                pickUpDateWithZeroTime = getIncrementedDate(pickUpDateWithZeroTime);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean createReservation(Date pickUpDate, int numberOfDays, CarType carType) {
        Reservation reservation = new Reservation(pickUpDate,numberOfDays, carType);
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date pickUpDateWithZeroTime = null;
        try {
            pickUpDateWithZeroTime = formatter.parse(formatter.format(pickUpDate));
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        for (int i = 0; i < numberOfDays; i++) {
            String key = pickUpDateWithZeroTime.toString();
            if (reservations.containsKey(key)) {
                List<Reservation> reservationList = reservations.get(key);
                reservationList.add(reservation);
                reservations.put(key, reservationList);
            } else {
                List<Reservation> reservationList = new LinkedList<>();
                reservationList.add(reservation);
                reservations.put(key, reservationList);
            }
            pickUpDateWithZeroTime = getIncrementedDate(pickUpDateWithZeroTime);
        }
        return true;
    }

    public static Date getIncrementedDate(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, ONE);
        return cal.getTime();
    }

    public void countCarsInitially() {
        availableCars = new Hashtable<>();
        availableCars.put(CarType.SEDAN, 6);
        availableCars.put(CarType.SUV, 3);
        availableCars.put(CarType.VAN, 2);

        reservations = new Hashtable<>();
    }

    public static Map<CarType, Integer> getAvailableCars() {
        return availableCars;
    }

    private static Map<CarType, Integer> availableCars;
    private static Map<String, List<Reservation>> reservations;
    private static final int ONE = 1;
}
