package com.CRD;

import com.CRD.Beans.CarType;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        //12-21-2018 04:12:12 PM
        Receptionist receptionist = new Receptionist();
        receptionist.countCarsInitially();


        JLabel label = new JLabel("java2s.com");

        JFrame frame = new JFrame("Testing");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(label);

        JPanel buttons = new JPanel(new GridLayout(0, 1));
        for (int index = 0; index < 10; index++) {
            buttons.add(new JButton(String.valueOf(index)));
        }

        JPanel right = new JPanel(new BorderLayout());
        right.add(buttons, BorderLayout.NORTH);
        frame.add(right, BorderLayout.EAST);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        while (true) {
            System.out.println("\n******** Welcome to Car Rental System ***********\n\n" +
                    "Kindly provide date and time in below format \n" +
                    "MM-dd-yyyy hh:mm:ss PM\n" +
                    "Or\n" +
                    "MM-dd-yyyy hh:mm:ss AM");
            Scanner scanner = new Scanner(System.in);
            Date pickUpDate = getPickUpDateFromUser(scanner);
            System.out.println("Enter number of days you require Car: ");
            int numberOfDays = Integer.parseInt(scanner.next());

            System.out.println("Enter Car type from options:");
            int i = 0;
            for (CarType car : Receptionist.getAvailableCars().keySet()) {
                System.out.println(++i + ". " + car);
            }
            String carTypeString = scanner.next();
            CarType carType = CarType.valueOf(carTypeString.toUpperCase());
            boolean isAvailable = receptionist.checkReservationAvailability(pickUpDate, numberOfDays, carType);
            if (isAvailable) {
                boolean isCreated = receptionist.createReservation(pickUpDate, numberOfDays, carType);
                if (isCreated) {
                    System.out.println("Reservation successfully created, Thank you.");
                }
                else {
                    System.out.println("Error in creating reservation, kindly try again.");
                }
            }
            else {
                System.out.println("Sorry, " + carType + " is not available in given days. Kindly choose other Car type or other days.");
            }
        }
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
