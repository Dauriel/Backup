/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import accesoaBD.AccesoaBD;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modelo.Proyeccion;
import modelo.Sala;

/**
 * FXML Controller class
 *
 * @author Ian Ward
 */
public class SessionScreenController implements Initializable {

    private Stage primaryStage;
    private Scene prevScene;
    private String prevTitle;
    @FXML
    private Button btnGoBack;
    @FXML
    private GridPane grid;
    private Proyeccion paracolor;
    private Sala sala;
    private Pane newPane;
    private AccesoaBD db = new AccesoaBD();
    private boolean one;
    private String[] horas = new String[4];
    private String peliculaelegida, horaelegida;
    private LocalDate diaelegido;
    private int asiento;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    void initStage(Stage stage, String pelicula, LocalDate dia, boolean prev, int asientos) {
        peliculaelegida = pelicula;
        diaelegido = dia;
        asiento = asientos;
        primaryStage = stage;
        one = prev;
        prevScene = stage.getScene();
        prevTitle = stage.getTitle();
        primaryStage.setTitle("Choose Session");
        grid.setHgap(10);
        grid.setVgap(10);
        List<Proyeccion> list = db.getProyeccion(pelicula, dia);
        ObservableList<Node> children = grid.getChildren();
        int u = 0;
        for (Proyeccion p : list) {
            horas[u] = p.getHoraInicio();
            u++;
        }
        u = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 1; j < 3; j++) {
                Label aux = new Label();
                Pane x = new Pane();
                paracolor = db.getProyeccion(pelicula, dia, horas[u]);
                sala = paracolor.getSala();
                x.setId("" + u);
                int auxiliar =sala.getCapacidad() - sala.getEntradasVendidas();
                if (auxiliar > 0 && asiento <= auxiliar) {
                    x.setStyle("-fx-background-color: palegreen;");
                    aux.setText(horas[u]);
                } else {
                    x.setStyle("-fx-background-color: indianred;");
                    aux.setText("Session full");
                }
                grid.add(x, i, j);
                grid.add(aux, i, j);
                grid.setHalignment(aux, HPos.CENTER);

                x.setOnMouseClicked(e -> {
                    newPane = (Pane) e.getSource();
                    continueClick();
                });
                u++;
            }
        }

    }

    @FXML
    private void btnGoBack_Click(ActionEvent event) {
        primaryStage.setTitle(prevTitle);
        primaryStage.setScene(prevScene);
    }

    private void continueClick() {
        int c = parseInt(newPane.getId());
        horaelegida = horas[c];
        paracolor = db.getProyeccion(peliculaelegida, diaelegido, horaelegida);
        if (!one) {
            selectNumber();
        } else {
            selectSeats();
        }
    }

    private void selectNumber() {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/view/Reserva.fxml"));
            Parent root = (Parent) myLoader.load();
            ReservaController window;
            window = myLoader.<ReservaController>getController();
            window.initStage(primaryStage, asiento, paracolor);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void selectSeats() {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/view/Seats.fxml"));
            Parent root = (Parent) myLoader.load();
            SeatsController r = myLoader.<SeatsController>getController();
            r.initStage(primaryStage, paracolor);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
