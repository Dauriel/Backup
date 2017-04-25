/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import accesoaBD.AccesoaBD;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import modelo.Proyeccion;
import modelo.Reserva;
import modelo.Sala;

/**
 * FXML Controller class
 *
 * @author PABLO
 */
public class Confirmation1Controller implements Initializable {

    @FXML
    private Label pelicula;
    @FXML
    private Label dia;
    @FXML
    private Label sesion;
    @FXML
    private Label sitios;
    @FXML
    private Label name;
    @FXML
    private Label number;
    private Stage primaryStage;
    private String prevTitle;
    private Scene prevScene;
    private Proyeccion proyeccion;
    private Sala sala;
    private int counter;
    private AccesoaBD base = new AccesoaBD();
    private Reserva reserva;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void initStage(Stage stage, Proyeccion p, Reserva r) {
        primaryStage = stage;
        prevScene = stage.getScene();
        prevTitle = stage.getTitle();
        primaryStage.setTitle("Confirm your reservation");
        proyeccion = p;
        reserva = r;
        pelicula.setText(proyeccion.getPelicula().getTitulo());
        dia.setText(proyeccion.getDia().toString());
        sesion.setText(proyeccion.getHoraInicio());
        sitios.setText("" + reserva.getNumLocalidades());
        name.setText(reserva.getNombre());
        number.setText(reserva.getTelefono());
    }

    @FXML
    private void cancelButton(ActionEvent event) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/view/MainScreen.fxml"));
            Parent root = (Parent) myLoader.load();
            MainScreenController r = myLoader.<MainScreenController>getController();
            r.initStage(primaryStage);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void acceptButton(ActionEvent event) {
        sala = proyeccion.getSala();
        int x = sala.getEntradasVendidas();
        sala.setEntradasVendidas(x + reserva.getNumLocalidades());
        proyeccion.setSala(sala);
        proyeccion.addReserva(reserva);
        base.salvarProyeccion(proyeccion);
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/view/MainScreen.fxml"));
            Parent root = (Parent) myLoader.load();
            MainScreenController r = myLoader.<MainScreenController>getController();
            r.initStage(primaryStage);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
