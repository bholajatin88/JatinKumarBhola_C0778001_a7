package com.jatin;

import javax.swing.*;

/**
 * Main class for entry point of program
 */
public class Main {
    /**
     * @param args main method args
     */
    public static void main(String[] args) {
        //Create User class object
        User user = new User();
        //Load all Users credentials from file for comparison
        user.getUsersFromFile();
        //Ask for User login
        boolean isLoggedIn = user.login();
        if (!isLoggedIn)
            return;

        //Create BankOperations class object
        BankOperations bankOprs = new BankOperations(user.getUserName());
        while(true) {
            //Message to show options to the user to perform selected operation.
            String selectMessage = "Please select one of the below options:\n" +
                    "Enter 1 to deposit an amount\n" +
                    "Enter 2 to withdraw an amount\n" +
                    "Enter 3 to view your account balance\n" +
                    "Enter 4 to exit";
            String input = JOptionPane.showInputDialog(selectMessage);
            String compare = "1234";
            try {
                Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input!");
                continue;
            }
            if (input == null || !(compare.contains(input))) {
                JOptionPane.showMessageDialog(null, "Invalid input!");
                continue;
            }
            if (input.equals("4"))
                break;
            else {
                if (input.equals("3")) {
                    bankOprs.showBalance();
                } else if (input.equals("2")) {
                    bankOprs.updateBalance(true);
                } else {
                    bankOprs.updateBalance(false);
                }
            }
        }
    }
}
