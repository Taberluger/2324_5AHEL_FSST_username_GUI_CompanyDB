package com.example.guicompanydb.models;

import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Login {

    private static Login instance = new Login();
    private final String path = "H:\\FSST\\5AHEL\\Geheim\\PW.txt";
    private final String host = "jdbc:postgresql://xserv:5432/jluger";
    private String user;
    private String password;

    private Login() {
    }

    public static Login getInstance() {
        return instance;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getHost() {
        return host;
    }
    public void setLoginData(){
        //Login einlesen
        try {
            Scanner scanner = new Scanner(new File(path));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.contains("user")) {
                    line = line.split(":")[1];
                    user = line.trim();
                } else if (line.contains("password")) {
                    line = line.split(":")[1];
                    password = line.trim();
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehler beim Login einlesen!");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

}
