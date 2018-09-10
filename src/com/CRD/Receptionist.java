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

    /**
     *
     * Background: In future, if system stores millions of Reservation,
     *             then I don't want to go and check if new input reservation is overlapping with those millions of reservations.
     *
     * Solution/Scaling: Separated reservations for each day as a List and stored it in HashTable with that Day as a key for future retrieval.
     *                   So, if I want to check for reservation overlapping, I go to that day directly in O(1) complexity using Day as my key.
     *
     * Used below Data Structure for storing newly created Reservations:
     *              Map<Specific_Day, List<Reservation>> reservations = new Hashtable<>();
     *
     * Reason for choosing HashTable: It is thread safe. So we can get Concurrency even when multiple users add new reservations at the same time.
     *
     * Future Scope for more Scaling: Use hour as a key instead of day.
     *                                It will make system 24 time more faster when system is dealing with many/millions of reservations.
     */
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
            //bucket hashing done on single day for future scalability, this will fasten checkReservationAvailability
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
            pickUpDateWithZeroTime = getIncrementedDate(pickUpDateWithZeroTime, 1);
        }
        return true;
    }

    /**
     *
     * Time Complexity for checking Reservation Availability: O(Constant + number of reservation in that specific day) = ~ O(1)
     */
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
                pickUpDateWithZeroTime = getIncrementedDate(pickUpDateWithZeroTime, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static Date getIncrementedDate(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    public void countCarsInitially() {
        availableCars = new Hashtable<>();
        availableCars.put(CarType.SEDAN, SEDAN_COUNT);
        availableCars.put(CarType.SUV, SUV_COUNT);
        availableCars.put(CarType.VAN, VAN_COUNT);

        reservations = new Hashtable<>();
    }

    public static Map<CarType, Integer> getAvailableCars() {
        return availableCars;
    }

    private static Map<CarType, Integer> availableCars;
    private static Map<String, List<Reservation>> reservations;
    private static final int SEDAN_COUNT = 2;
    private static final int VAN_COUNT = 3;
    private static final int SUV_COUNT = 1;
}
