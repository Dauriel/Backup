/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import accesoaBD.AccesoaBD;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modelo.Proyeccion;
import modelo.Reserva;

/**
 * FXML Controller class
 *
 * @author PABLO
 */
public class TicketFromRController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Stage primaryStage;
    private String prevTitle;
    private Scene prevScene;
    @FXML
    private Button findButton;
    @FXML
    private Label notFound;
    @FXML
    private TextField phoneText;
    @FXML
    private Text phoneShortText;
    @FXML
    private Text nameShortText;
    @FXML
    private TextField nameText;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    void initStage(Stage stage) {
        primaryStage = stage;
        prevScene = stage.getScene();
        prevTitle = stage.getTitle();

        phoneText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (phoneText.getText().length() > 9) {
                phoneText.setText(oldValue);
            }
        });

    }

    @FXML
    private void findClick(ActionEvent event) {
        boolean check = checker();
        if (check) {
            Proyeccion p = reservaExistente();
            if (p != null) {
               notFound.setText("");
               
            } else {
                notFound.setText("Reservation not found");
            }
        }

    }

    private boolean phoneChecker() {
        if (!(phoneText == null || phoneText.getText().length() == 0)) {
            try {
                // Do all the validation you need here such as
                int number = Integer.parseInt(phoneText.getText());
                if (phoneText.getText().length() < 9) {
                    phoneShortText.setText("Phone Number Too Short");
                    return false;
                }
            } catch (NumberFormatException e) {
                phoneShortText.setText("Invalid Phone Number");
                return false;
            }
        } else if (phoneText.getText().length() == 0) {
            phoneShortText.setText("This field can't be empty");
            return false;
        }
        phoneShortText.setText("");
        return true;
    }

    private boolean nameChecker() {
        if (nameText.getText().length() == 0) {
            nameShortText.setText("This field can't be empty");
            return false;
        } else {
            nameShortText.setText("");
            return true;
        }
    }

    private boolean checker() {
        boolean name = nameChecker();
        return (phoneChecker() && name);
    }

    @FXML
    private void btnGoBack_Click(ActionEvent event) {
        primaryStage.setTitle(prevTitle);
        primaryStage.setScene(prevScene);
    }

    private Proyeccion reservaExistente() {
        AccesoaBD aux = new AccesoaBD();
        for (int i = 1; i < 10; i++) {
            LocalDate date = LocalDate.of(2017, 4, i);
            for (Proyeccion p : aux.getProyeccionesDia(date)) {
                for (Reserva r : p.getReservas()) {
                    if (r.getNombre().equals(nameText.getText()) && r.getTelefono().equals(phoneText.getText())) {
                        return p;
                    }
                }
            }
        }
        return null;
    }

}
