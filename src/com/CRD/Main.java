package com.CRD;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args){
        Receptionist receptionist = new Receptionist();
        receptionist.countCarsInitially();
        JFrame.setDefaultLookAndFeelDecorated(true);
        new SwingUserInterface(receptionist);
    }
}
