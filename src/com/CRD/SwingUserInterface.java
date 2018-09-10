package com.CRD;

import com.CRD.Beans.CarType;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import static javax.swing.JOptionPane.showMessageDialog;

public class SwingUserInterface extends JFrame {
    private JFrame frame;

    public SwingUserInterface(Receptionist receptionist) {
        frame = configureFrame();

        JPanel panelInitialCars = new JPanel();
        JLabel carCountMessage = new JLabel("Initial Available Cars: ");
        JLabel carCountLabel1 = new JLabel("1. SEDAN - " + Receptionist.getAvailableCars().get(CarType.SEDAN).toString());
        JLabel carCountLabel2 = new JLabel("2. VAN - " + Receptionist.getAvailableCars().get(CarType.VAN).toString());
        JLabel carCountLabel3 = new JLabel("3. SUV - " + Receptionist.getAvailableCars().get(CarType.SUV).toString());
        panelInitialCars.setLayout(new BoxLayout(panelInitialCars, BoxLayout.Y_AXIS));
        panelInitialCars.add(carCountMessage, CENTER_ALIGNMENT);
        panelInitialCars.add(carCountLabel1, CENTER_ALIGNMENT);
        panelInitialCars.add(carCountLabel2, CENTER_ALIGNMENT);
        panelInitialCars.add(carCountLabel3, CENTER_ALIGNMENT);

        JPanel panelDate = new JPanel();
        JComboBox<String> monthBox = addMonthInPanel(panelDate);
        JComboBox<Integer> dayBox = addDayInPanel(panelDate);
        JComboBox<Integer> yearBox = addYearInPanel(panelDate);

        JPanel panelTime = new JPanel();
        JComboBox<Integer> hourBox = addHourInPanel(panelTime);
        JComboBox<Integer> minuteBox = addMinuteInPanel(panelTime);
        JComboBox<String> amOrPmBox = addAmOrPmInPanel(panelTime);

        JPanel panelDaysAndCars = new JPanel();
        JTextField numberOfDaysText = addNumberOFDaysInPanel(panelDaysAndCars);
        JComboBox<CarType> carTypeBox = addCarTypeInPanel(panelDaysAndCars);

        JButton buttonOk = new JButton("OK");
        buttonOk.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date pickUpDate = getPickUpDateFromUser(String.valueOf(monthBox.getSelectedIndex() + 1),
                        String.valueOf(dayBox.getSelectedItem()),
                        String.valueOf(yearBox.getSelectedItem()),
                        String.valueOf(hourBox.getSelectedItem()),
                        String.valueOf(minuteBox.getSelectedItem()),
                        (String) amOrPmBox.getSelectedItem());
                if (pickUpDate.compareTo(Calendar.getInstance().getTime()) < 0)  {
                    showMessageDialog(null, "Kindly provide Future date.");
                    return;
                }

                String numberOfDaysString = numberOfDaysText.getText();
                if (numberOfDaysString == null || Objects.equals(numberOfDaysString, "") || !numberOfDaysString.matches("^[0-9]*[1-9][0-9]*$")) {
                    showMessageDialog(null, "Kindly provide Number of Days.");
                    return;
                }
                int numberOfDays = Integer.parseInt(numberOfDaysString);

                CarType carType = (CarType) carTypeBox.getSelectedItem();

                boolean isAvailable = receptionist.checkReservationAvailability(pickUpDate, numberOfDays, carType);
                if (isAvailable) {
                    boolean isCreated = receptionist.createReservation(pickUpDate, numberOfDays, carType);
                    if (isCreated) {
                        showMessageDialog(null, "Reservation successfully created from\n"
                                + pickUpDate
                                + "\nto\n"
                                + Receptionist.getIncrementedDate(pickUpDate, numberOfDays)
                                + "\nfor Car\n"
                                + carType
                                + "\nThank you.");
                    } else {
                        showMessageDialog(null, "Error in creating reservation, kindly try again.");
                    }
                } else {
                    showMessageDialog(null, "Sorry, " + carType + " is not available in given days. Kindly choose other Car type or other days.");
                }
            }
        });

        JPanel panelButton = new JPanel();
        panelButton.add(buttonOk);

        JPanel outer = new JPanel();
        outer.add(panelInitialCars);
        outer.add(panelDate);
        outer.add(panelTime);
        outer.add(panelDaysAndCars);
        outer.add(panelButton);
        outer.setLayout(new BoxLayout(outer, BoxLayout.Y_AXIS));

        frame.add(outer);
        frame.setVisible(true);
    }

    private JComboBox<CarType> addCarTypeInPanel(JPanel panel) {
        JLabel carTypeLabel = new JLabel("Car Type:");
        carTypeLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panel.add(carTypeLabel);
        CarType[] carTypes = Receptionist.getAvailableCars().keySet().toArray(new CarType[0]);
        final JComboBox<CarType> carTypeBox = new JComboBox<>(carTypes);
        carTypeBox.setMaximumSize(carTypeBox.getPreferredSize());
        carTypeBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(carTypeBox);
        return carTypeBox;
    }

    private JTextField addNumberOFDaysInPanel(JPanel panel) {
        JLabel numberOfDaysLabel = new JLabel("Number of Days:");
        numberOfDaysLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panel.add(numberOfDaysLabel);
        JTextField numberOFDaysText = new JTextField(3);
        panel.add(numberOFDaysText);
        return numberOFDaysText;
    }

    private static Date getPickUpDateFromUser(String month,
                                              String day,
                                              String year,
                                              String hour,
                                              String minute,
                                              String amOrPm) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a");
        Date pickUpDate = null;
        try {
            pickUpDate = simpleDateFormat.parse(month + "-" + day + "-" + year + " "
                    + hour + ":" + minute + ":00" + " " + amOrPm);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return pickUpDate;
    }

    private JComboBox<String> addAmOrPmInPanel(JPanel panel) {
        JLabel amOrPmLabel = new JLabel("AM/PM:");
        amOrPmLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panel.add(amOrPmLabel);
        String[] amOrPmOptions = new String[2];
        amOrPmOptions[0] = "AM";
        amOrPmOptions[1] = "PM";
        final JComboBox<String> amOrPmBox = new JComboBox<>(amOrPmOptions);
        amOrPmBox.setMaximumSize(amOrPmBox.getPreferredSize());
        amOrPmBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(amOrPmBox);
        return amOrPmBox;
    }

    private JComboBox<Integer> addMinuteInPanel(JPanel panel) {
        JLabel minuteLabel = new JLabel("Minute:");
        minuteLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panel.add(minuteLabel);
        Integer[] minuteOptions = new Integer[60];
        for (int i = 0; i < minuteOptions.length; i++) {
            minuteOptions[i] = i;
        }
        final JComboBox<Integer> minuteBox = new JComboBox<>(minuteOptions);
        minuteBox.setMaximumSize(minuteBox.getPreferredSize());
        minuteBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(minuteBox);
        return minuteBox;
    }

    private JComboBox<Integer> addHourInPanel(JPanel panel) {
        JLabel hourLabel = new JLabel("Hour:");
        hourLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panel.add(hourLabel);
        Integer[] hourOptions = new Integer[12];
        for (int i = 0; i < hourOptions.length; i++) {
            hourOptions[i] = i+1;
        }
        final JComboBox<Integer> hourBox = new JComboBox<>(hourOptions);
        hourBox.setMaximumSize(hourBox.getPreferredSize());
        hourBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(hourBox);
        return hourBox;
    }

    private JComboBox<Integer> addYearInPanel(JPanel panel) {
        JLabel yearLabel = new JLabel("Year:");
        yearLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panel.add(yearLabel);
        Integer[] yearOptions = new Integer[5];
        int year = Calendar.getInstance().getWeekYear();
        for (int i = 0; i < yearOptions.length; i++) {
            yearOptions[i] = year++;
        }
        final JComboBox<Integer> yearBox = new JComboBox<>(yearOptions);
        yearBox.setMaximumSize(yearBox.getPreferredSize());
        yearBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(yearBox);
        return yearBox;
    }

    private JComboBox<Integer> addDayInPanel(JPanel panel) {
        JLabel dayLabel = new JLabel("Day:");
        dayLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panel.add(dayLabel);
        Integer[] dayOption = new Integer[31];
        for (int i = 0; i < dayOption.length; i++) {
            dayOption[i] = i+1;
        }
        final JComboBox<Integer> dayBox = new JComboBox<>(dayOption);
        dayBox.setMaximumSize(dayBox.getPreferredSize());
        dayBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(dayBox);
        return dayBox;
    }

    private JComboBox<String> addMonthInPanel(JPanel panel) {
        JLabel monthLabel = new JLabel("Month:");
        monthLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(monthLabel);
        String[] choices = { "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        final JComboBox<String> monthBox = new JComboBox<>(choices);
        monthBox.setMaximumSize(monthBox.getPreferredSize());
        monthBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(monthBox);
        return  monthBox;
    }

    private JFrame configureFrame() {
        frame = new JFrame("Car Rental System");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocation(430, 100);
        return frame;
    }
}
