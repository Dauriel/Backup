/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import accesoaBD.AccesoaBD;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
public class ConfirmationController implements Initializable {

    @FXML
    private Label pelicula;
    @FXML
    private Label dia;
    @FXML
    private Label sesion;
    @FXML
    private Label sitios;
    private Stage primaryStage;
    private String prevTitle;
    private Scene prevScene;
    private Proyeccion proyeccion;
    private Sala sala;
    private int counter;
    private AccesoaBD base = new AccesoaBD();
    boolean one;
    private Reserva reserva;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void initStage(Stage stage, Proyeccion p, Sala s, int c) {
        primaryStage = stage;
        prevScene = stage.getScene();
        prevTitle = stage.getTitle();
        proyeccion = p;
        sala = s;
        counter = c;
        pelicula.setText(proyeccion.getPelicula().getTitulo());
        dia.setText(proyeccion.getDia().toString());
        sesion.setText(proyeccion.getHoraInicio());
        sitios.setText("" + counter);
        one = true;
    }

    public void initStage2(Stage stage, Proyeccion p, Sala s, int c, Reserva r) {
        primaryStage = stage;
        prevScene = stage.getScene();
        prevTitle = stage.getTitle();
        primaryStage.setTitle("Confirm your reservation");
        proyeccion = p;
        sala = s;
        counter = c;
        pelicula.setText(proyeccion.getPelicula().getTitulo());
        dia.setText(proyeccion.getDia().toString());
        sesion.setText(proyeccion.getHoraInicio());
        sitios.setText("" + counter);
        one = false;
        reserva = r;
    }

    @FXML
    private void cancelButton(ActionEvent event) {
        primaryStage.setTitle(prevTitle);
        primaryStage.setScene(prevScene);
    }

    @FXML
    private void acceptButton(ActionEvent event) {
        if (one) {
            sala.setEntradasVendidas(sala.getEntradasVendidas() + counter);
        } else {
            ArrayList<Reserva> list;
            list = proyeccion.getReservas();
            list.remove(reserva);
            proyeccion.setReservas(list);
        }
        proyeccion.setSala(sala);
        base.salvarProyeccion(proyeccion);
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/view/Print.fxml"));
            Parent root = (Parent) myLoader.load();
            PrintController r = myLoader.<PrintController>getController();
            r.initStage(primaryStage, proyeccion, sala, counter);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
