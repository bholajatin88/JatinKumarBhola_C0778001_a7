package com.jatin;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;


/**
 * Class for storing information related to User
 */
public class User {

    private String userName;
    private String password;
    private String fileName = "Docs/credentials.txt";
    //Usage of Arrays
    private ArrayList<User> users = new ArrayList<User>();

    /**
     * default Constructor
     */
    public User() {
        this.userName = "";
        this.password = "";
    }


    /**
     * Parameterized Constructor
     * @param userName username of user
     * @param password password of user
     */
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    /**
     * @return userName
     * Method to get UserName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Method to get all the users from file
     */
    public void getUsersFromFile() {
        try {
            File file = new File(fileName);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String[] creds = reader.nextLine().split(":");
                User newUser = new User(creds[0], creds[1]);
                users.add(newUser);
            }
        }
        catch (FileNotFoundException ex) {
            return;
        }
    }

    /**
     * @return Is user logged in or not
     * Method to perform login */
    public boolean login() {
        try {
            int i = 0;
            //Validate UserName and Password three times.
            String inputUserName = "";
            String inputPassword = "";
            while (i < 3) {
                inputUserName = JOptionPane.showInputDialog("Enter Username:");
                String pass = "";
                if (inputUserName != null) {
                    for (User user : users) {
                        if (user.userName.toLowerCase().equals(inputUserName.toLowerCase())) {
                            pass = user.password;
                            break;
                        }
                    }
                }
                if (!pass.equals("")) {
                    inputPassword = JOptionPane.showInputDialog("Enter password:");
                    if (inputPassword != null && inputPassword.equals(pass))
                        break;
                }
                i++;
                if (i < 3) {
                    JOptionPane.showMessageDialog(null, "Incorrect Credentials. Please try again.");
                }
            }
            if (i == 3) {
                //Tries exceeded message shown using JOptionPane
                JOptionPane.showMessageDialog(null, "You have exceeded your try’s, goodbye.");
                return false;
            }
            this.userName = inputUserName;
            this.password = inputPassword;
            return true;
        }
        catch(Exception ex) {
            return false;
        }
    }
}
