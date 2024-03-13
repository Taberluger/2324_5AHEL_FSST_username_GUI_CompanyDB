package com.example.guicompanydb;

import com.example.guicompanydb.models.HumanResources;
import com.example.guicompanydb.models.Login;
import com.example.guicompanydb.models.Person;
import com.example.guicompanydb.models.Project;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.*;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class HelloController {

    private final Login login = Login.getInstance();
    private Connection dbConn = null;
    private Statement stm = null;
    private ResultSet rs = null;

    @FXML
    private ListView<String> lvwData;
    private ObservableList<String> items = FXCollections.observableArrayList();


    public void initialize() {
        lvwData.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        login.setLoginData();
    }

    @FXML
    protected void onBtnGetData() {
        String message = "";
        try {

            dbConn = DriverManager.getConnection(login.getHost(), login.getUser(), login.getPassword());
            stm = dbConn.createStatement();

            String sql = "SELECT first_name, last_name, date_of_birth, houseNumber, street, tc.city " +
                    "FROM company.t_human_resources " +
                    "INNER JOIN company.t_city tc ON tc.postal_code = t_human_resources.postal_code";
            rs = stm.executeQuery(sql);

            if (rs.next()) {
                while (rs.next()) {
                    StringBuilder row = new StringBuilder(); // Stringbuilder für die aktuelle Zeile erstellen
                    for (int j = 1; j <= rs.getMetaData().getColumnCount(); j++) { // Durch die Spalten iterieren
                        row.append(rs.getString(j)); // Wert der aktuellen Spalte zur Zeile hinzufügen
                        row.append(", "); // Trennzeichen für die Spalten hinzufügen
                    }
                    message = row.toString().trim();
                    items.add(message);
                    System.out.println(message);
                }
            } else {
                message = "kein Eintrag für diese ID";
                items.add(message);
                System.out.println(message);
            }
            rs.close();
            stm.close();
            dbConn.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehler beim Daten abrufen");
            alert.showAndWait();
            throw new RuntimeException(e);
        }
        lvwData.getItems().clear();
        lvwData.setItems(items); // Hinzufügen der Einträge zur ListView
    }

    @FXML
    protected void onBtnDeleteData() {

        // Zugriff auf das SelectionModel der ListView
        MultipleSelectionModel<String> selectionModel = lvwData.getSelectionModel();

        // Zugriff auf die ausgewählten Elemente
        ObservableList<String> selectedItems = selectionModel.getSelectedItems();
        int rowsAffected = 0;

        // Iterieren durch die ausgewählten Elemente
        for (String selectedItem : selectedItems) {

            // ID aus dem selectedItem extrahieren
            String[] parts = selectedItem.split(",");
            String firstname = parts[0].trim(); // Der Vorname befindet sich am Anfang
            String lastname = parts[1].trim(); // Der Nachname befindet sich nach dem ersten Komma
            String geburtsdatum = parts[2].trim(); // Das Geburtsdatum befindet sich nach dem zweiten Komma



            try {
                dbConn = DriverManager.getConnection(login.getHost(), login.getUser(), login.getPassword());
                stm = dbConn.createStatement();
                // SQL DELETE-Anweisung ausführen
                String sql = "DELETE FROM company.t_human_resources WHERE first_name = '" + firstname +
                        "' AND last_name = '" + lastname + "' AND date_of_birth = '" + geburtsdatum + "'";
                rowsAffected += stm.executeUpdate(sql);

                stm.close();
                dbConn.close();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fehler");
                alert.setHeaderText("Datensatz/Datensätze wurde(n) konnte nicht gelöscht werden!");
                alert.showAndWait();
                throw new RuntimeException(e);
            }
        }
        System.out.println(rowsAffected + " Zeilen gelöscht.");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Status");
        alert.setHeaderText(rowsAffected + " Datensatz/Datensätze wurde(n) erfolgreich gelöscht!");
        alert.showAndWait();

        // ListView aktualisieren
        items.removeAll(selectedItems); // Ausgewählte Elemente aus der ObservableList entfernen
        lvwData.setItems(items); // Aktualisierte Datenquelle der ListView zuweisen
    }

    @FXML
    public void onBtnGSONData() {
        try {
            FileReader reader = new FileReader("H:\\FSST\\5AHEL\\FSSTProj\\GuiCompanyDB\\GuiCompanyDB\\guiCompanyDB\\human_resources.json");
            Gson gsonObj = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

            HumanResources hrObj = gsonObj.fromJson(reader, HumanResources.class);


            lvwData.getItems().clear();


            try {
                dbConn = DriverManager.getConnection(login.getHost(), login.getUser(), login.getPassword());
                stm = dbConn.createStatement();

                for (Person person : hrObj.getPeople()) {
                    lvwData.getItems().add(person.toString());
                    System.out.println(person);

                    String sql = "INSERT INTO company.t_human_resources (first_name, last_name, gender, date_of_birth, email, street, housenumber, postal_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                    PreparedStatement preparedStatement = dbConn.prepareStatement(sql);
                    preparedStatement.setString(1, person.getFirst_name());
                    preparedStatement.setString(2, person.getLast_name());
                    preparedStatement.setString(3, person.getGender());
                    //preparedStatement.setDate(4, java.sql.Date.valueOf(person.getDate_of_birth())); // Assuming getDateOfBirth() returns a LocalDate
                    preparedStatement.setString(5, person.getEmail());
                    preparedStatement.setString(6, person.getStreet());
                    preparedStatement.setInt(7, person.getStreetNum());
                    preparedStatement.setInt(8, person.getPostal_code());

                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                }

                stm.close();
                dbConn.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Datensätze erfolgreich abgespeichert!");
                alert.showAndWait();


            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fehler!");
                alert.setHeaderText("Datensätze konnten nicht abgespeichert werden!");
                alert.showAndWait();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}