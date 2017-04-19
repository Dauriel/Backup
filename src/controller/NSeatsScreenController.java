/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    @FXML
    private Label errSeats;
    @FXML
    private Button btnGoBack;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    void initStage(Stage stage) {
        primaryStage = stage;
        prevScene = stage.getScene();
        prevTitle = stage.getTitle();
        
        primaryStage.setTitle("Enter Number of Seats");
       // primaryStage.setResizable(false);
    }

    @FXML
    private void btnContinue_Clicked(ActionEvent event) {
        if(true) errSeats.setText("Not enough seats.");
    }

    @FXML
    private void btnGoBack_Clicked(ActionEvent event) {
        primaryStage.setTitle(prevTitle);
        primaryStage.setScene(prevScene);
    }
}
