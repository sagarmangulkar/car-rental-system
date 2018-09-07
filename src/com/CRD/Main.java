package com.CRD;

import com.CRD.Beans.CarType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        //12-21-2018 04:12:12 PM
        Receptionist receptionist = new Receptionist();
        receptionist.countCarsInitially();

        System.out.println("Welcome to Car Rental System...!\n Kindly provide date and time in below format \nMM-dd-yyyy hh:mm:ss PM\nOr\nMM-dd-yyyy hh:mm:ss AM");
        Scanner scanner = new Scanner(System.in);
        Date pickUpDate = getPickUpDateFromUser(scanner);
        System.out.println(pickUpDate);
        System.out.println("Enter number of days you require Car: ");
        int numberOfDays = Integer.parseInt(scanner.next());
        System.out.println(numberOfDays);

        String carTypeString = scanner.next();
        CarType carType = CarType.valueOf(carTypeString.toUpperCase());
        String message = receptionist.createReservation(pickUpDate, numberOfDays, carType);
        System.out.println(message);
    }

    private static Date getPickUpDateFromUser(Scanner scanner) {
        String dateString = scanner.next();
        String timeString = scanner.next();
        String amOrPM = scanner.next();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a");
        Date pickUpDate = null;
        try {
            pickUpDate = simpleDateFormat.parse(dateString + " " + timeString + " " + amOrPM);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return pickUpDate;
    }
}
