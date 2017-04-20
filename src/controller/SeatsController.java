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
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.VPos;

/**
 * FXML Controller class
 *
 * @author PABLO
 */
public class SeatsController implements Initializable {

    private Stage primaryStage;
    @FXML
    private GridPane grid;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void initStage(Stage stage) {
        grid.setPrefSize(600, 850);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10,30,10,10));
        grid.setAlignment(Pos.CENTER);
        for (int i = 1; i < 19; i++) {
            Text x = new Text();
            x.setText("" + i);
            grid.add(x, 0, i);
            grid.setHalignment(x, HPos.RIGHT);
        }
        for (int i = 1; i < 13; i++) {
            Text x = new Text();
            x.setText("" + i);
            grid.add(x, i, 0);
            grid.setHalignment(x, HPos.CENTER);
            grid.setValignment(x, VPos.BOTTOM);
        }
        for (int i = 1; i < 13; i++) {
            for (int j = 1; j < 19; j++) {
                Pane x = new Pane();
                x.setStyle("-fx-background-color: indianred;");
                grid.add(x, i, j);
            }
        }
    }
}
