/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import accesoaBD.AccesoaBD;
import java.io.IOException;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modelo.Proyeccion;
import modelo.Reserva;

/**
 * FXML Controller class
 *
 * @author PABLO
 */
public class ReservaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Stage primaryStage;
    private String prevTitle;
    private Scene prevScene;
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
    private Proyeccion proyeccion;
    private Reserva reserva;
    private int localidades;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    void initStage(Stage stage, int numLocalidades, Proyeccion p) {
        primaryStage = stage;
        localidades = numLocalidades;
        prevScene = stage.getScene();
        prevTitle = stage.getTitle();
        primaryStage.setTitle("Complete your reservation");
        proyeccion = p;
        phoneText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (phoneText.getText().length() > 9) {
                phoneText.setText(oldValue);
            }
        });

    }

    @FXML
    private void findClick(ActionEvent event) {
        if (checker()) {
            if (reservaExistente()) {
                notFound.setText("Duplicate Name and Phone");
            } else {
                try {
                    reserva = new Reserva(nameText.getText(), phoneText.getText(), localidades);
                    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/view/Confirmation_1.fxml"));
                    Parent root = (Parent) myLoader.load();
                    Confirmation1Controller r = myLoader.<Confirmation1Controller>getController();
                    r.initStage(primaryStage, proyeccion, reserva);
                    Scene scene = new Scene(root);
                    primaryStage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

    private boolean reservaExistente() {
        AccesoaBD aux = new AccesoaBD();
        for (int i = 1; i < 10; i++) {
            LocalDate date = LocalDate.of(2017, 4, i);
            for (Proyeccion p : aux.getProyeccionesDia(date)) {
                for (Reserva r : p.getReservas()) {
                    if (r.getNombre().equals(nameText.getText()) && r.getTelefono().equals(phoneText.getText())) {
                        reserva = r;
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
