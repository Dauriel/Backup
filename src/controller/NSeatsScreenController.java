/*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ian Ward
 */
public class NSeatsScreenController implements Initializable {

    private Stage primaryStage;
    private Scene prevScene;
    private String prevTitle;
    @FXML
    private TextField numberOfSeats;
    private Label errSeats;
    @FXML
    private Button btnGoBack;
    @FXML
    private Button minusButton;
    @FXML
    private Button plusButton;
    private int counter;
    private String tituloPelicula;
    private LocalDate chosenDate;
    private boolean one;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    void initStage(Stage stage, String pelicula, LocalDate dia, boolean prev) {
        primaryStage = stage;
        prevScene = stage.getScene();
        prevTitle = stage.getTitle();
        tituloPelicula = pelicula;
        chosenDate = dia;
        one = prev;

        primaryStage.setTitle("Enter Number of Seats");
        numberOfSeats.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                counter = parseInt(newValue);
                if (counter < 0) {
                    counter = 0;
                }
            } catch (NumberFormatException e) {
                counter = 0;
            }
        });
    }

    @FXML
    private void btnContinue_Clicked(ActionEvent event) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/view/SessionScreen.fxml"));
            Parent root = (Parent) myLoader.load();
            SessionScreenController window;
            window = myLoader.<SessionScreenController>getController();
            window.initStage(primaryStage, tituloPelicula, chosenDate, one, counter);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void btnGoBack_Clicked(ActionEvent event) {
        primaryStage.setTitle(prevTitle);
        primaryStage.setScene(prevScene);
    }

    @FXML
    private void substract(ActionEvent event) {
        counter--;
        if (counter < 0) {
            counter = 0;
        }
        numberOfSeats.setText("" + counter);
    }

    @FXML
    private void add(ActionEvent event) {
        counter++;
        numberOfSeats.setText("" + counter);
    }
}
