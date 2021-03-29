package com.jatin;

import javax.swing.*;
import java.io.*;
import java.util.Scanner;

/**
 * Class for performing all the Bank operations
 */
public class BankOperations {
    String userName;
    float balance;
    String fileName;

    /**
     * default constructor
     */
    public BankOperations() {
        this.userName = "";
        this.balance = 0;
        this.fileName = "";
    }


    /**
     * Parameterized Constructor
     * @param userName setup username for performing bank operations
     */
    public BankOperations(String userName) {
        this.userName = userName;
        this.fileName = "Docs/" + userName.toLowerCase() + ".txt";
        this.balance = getBalanceForUserFromFile();
    }


    /**
     * Method to get user balance from user related txt file
     * @return balance
     */
    private float getBalanceForUserFromFile() {
        try {
            File file = new File(this.fileName);
            Scanner reader = new Scanner(file);
            boolean found = false;
            String balance = "";
            while (reader.hasNextLine()) {
                String[] details = reader.nextLine().split(":");
                balance = details[1];
                found = true;
                break;
            }
            if(!found) {
                return 0;
            }
            return Float.parseFloat(balance);
        }
        catch (FileNotFoundException ex) {
            return 0;
        }
    }

    /**
     * Method to show balance to User */
    //Method Overloading
    public void showBalance() {
        JOptionPane.showMessageDialog(null, "Hello " + this.userName + "!!"
                + "\n" + "Your balance is " + String.format("%.3f", this.balance));
    }

    /**
     * Method to show updated balance to the User
     * @param updated value for setting updated balance show flag
     */
    //Method Overloading
    public void showBalance(boolean updated) {
        JOptionPane.showMessageDialog(null, "Hello " + this.userName + "!!"
                + "\n" + "Your updated balance is $" + String.format("%.3f", this.balance));
    }

    /**
     * @param deduct boolean to check amount deducted or deposited
     * @param amount amount that has been entered and changed
     * Method to save the balance in the User related txt file
     */
    private void saveBalance(boolean deduct, float amount) {
        try {
            File file = new File(this.fileName);
            boolean created = file.createNewFile();
            Scanner reader = new Scanner(file);
            boolean found = false;
            while (reader.hasNextLine()) {
                reader.nextLine();
                found = true;
                break;
            }
            String lines = "";
            if(found) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                lines = bufferedReader.readLine();
                String oldContent = lines.split(":")[1];
                lines = lines.replace(oldContent, Float.toString(this.balance));
                String line = bufferedReader.readLine();
                while(line != null) {
                    lines += "\n" + line;
                    line = bufferedReader.readLine();
                }
                bufferedReader.close();
                if(deduct) {
                    lines += "\nDeduction";
                }
                else {
                    lines += "\nDeposit";
                }
                lines += " of $" + amount + " has been made. Updated Balance: " + this.balance;
            }
            else {
                lines = "balance:" + this.balance;
                lines += "\nEntry of $" + amount + " has been made. Updated Balance: " + this.balance;
            }
            PrintWriter pw = new PrintWriter(new FileOutputStream(file));
            pw.print(lines);
            pw.close();
            showBalance(true);
        }
        catch (FileNotFoundException ex) {
            return;
        }
        catch (IOException ex) {
            return;
        }
    }


    /**
     * Method to update balance of user
     * @param deduct boolean value to check amount deducted or deposited
     */
    public void updateBalance(boolean deduct) {
        String message = "Please enter an amount to ";
        if(deduct) {
            message += "deduct:";
        }
        else {
            message += "deposit:";
        }
        String input = JOptionPane.showInputDialog(message);
        if(input == null) {
            JOptionPane.showMessageDialog(null, "Invalid input amount!");
            return;
        }
        float amount = 0;
        try {
            amount = Float.parseFloat(input);
        }
        catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Invalid input amount!");
            return;
        }
        if(amount <= 0) {
            JOptionPane.showMessageDialog(null, "Amount must be non-negative.");
            return;
        }
        if(deduct && amount > this.balance) {
            if(this.balance == 0.0) {
                JOptionPane.showMessageDialog(null, "Balance must be non-zero to withdraw.");
                return;
            }
            JOptionPane.showMessageDialog(null, "Amount must be less than balance.");
            return;
        }
        if(!deduct) {
            this.balance = balance + amount;
        }
        else {
            this.balance = balance - amount;
        }
        saveBalance(deduct, amount);
    }
}
