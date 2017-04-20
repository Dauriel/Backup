/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PABLO
 */
public class SeatsController implements Initializable {

    private Stage primaryStage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void initStage(Stage stage) {
        primaryStage = stage;
        GridPane gridp = new GridPane();
        
        for (int i = 0; i < 19; i++) {
            for (int j = 0; i < 13; j++) {
                Pane x = new Pane();
                x.setStyle("-fx-background-color: red;");
                gridp.add(x, j, i);
            }
        }
        Scene aux = new Scene(gridp,800,600);
        primaryStage.setScene(aux);
    }

}
