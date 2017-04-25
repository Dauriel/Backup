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
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.util.Duration;
import modelo.Proyeccion;
import modelo.Reserva;
import modelo.Sala;
import modelo.Sala.localidad;

/**
 * FXML Controller class
 *
 * @author PABLO
 */
public class SeatsController implements Initializable {

    private Stage primaryStage;
    @FXML
    private GridPane grid;
    private Proyeccion proyeccion;
    private Scene prevScene;
    private String prevTitle;
    private int counter = 0;
    @FXML
    private Label nombrePelicula;
    @FXML
    private Label horaPelicula;
    @FXML
    private Label diaPelicula;
    @FXML
    private Label seatsSelected;
    private Sala sala;
    private localidad[][] localidades;
    private int posicion1, posicion2;
    private boolean one;
    private AccesoaBD base = new AccesoaBD();
    private int counter2;
    private Reserva reserva;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void iniciar() {
        nombrePelicula.setText(proyeccion.getPelicula().getTitulo());
        diaPelicula.setText(proyeccion.getDia().toString());
        horaPelicula.setText(proyeccion.getHoraInicio());
        grid.setPrefSize(850, 600);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 30, 10, 10));
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
        for (int i = 1; i <= 12; i++) {
            for (int j = 1; j <= 18; j++) {
                Pane x = new Pane();
                String auxiliary = localidades[j - 1][i - 1].toString();
                if (auxiliary.equals("libre")) {
                    x.setStyle("-fx-background-color: darkgreen;");
                } else {
                    x.setStyle("-fx-background-color: indianred;");
                }
                x.setId((i - 1) + "-" + (j - 1));
                grid.add(x, i, j);
                if (!one) {
                    x.setOnMouseClicked(e -> {
                        String[] test = x.getId().split("-");
                        posicion1 = parseInt(test[0]); //columna
                        posicion2 = parseInt(test[1]); //fila
                        if (x.getStyle().contains("green")) {
                            counter++;
                            seatsSelected.setText("" + counter);
                            sala.updateLocalidad(posicion2, posicion1, localidad.vendida);
                            x.setStyle("-fx-background-color: darkblue;");
                        } else if (x.getStyle().contains("blue")) {
                            counter--;
                            seatsSelected.setText("" + counter);
                            sala.updateLocalidad(posicion2, posicion1, localidad.libre);
                            x.setStyle("-fx-background-color: darkgreen;");
                        }
                    });
                } else {
                    x.setOnMouseClicked(e -> {
                        String[] test = x.getId().split("-");
                        posicion1 = parseInt(test[0]);
                        posicion2 = parseInt(test[1]);
                        if (x.getStyle().contains("green")) {
                            if (counter > 0) {
                                counter--;
                                seatsSelected.setText("" + counter);
                                sala.updateLocalidad(posicion2, posicion1, localidad.vendida);
                                x.setStyle("-fx-background-color: darkblue;");
                            }
                        } else if (x.getStyle().contains("blue")) {
                            counter++;
                            seatsSelected.setText("" + counter);
                            sala.updateLocalidad(posicion2, posicion1, localidad.libre);
                            x.setStyle("-fx-background-color: darkgreen;");
                        }
                    });

                }
            }
        }
    }

    public void initStage(Stage stage, Proyeccion electa) {
        proyeccion = electa;
        sala = proyeccion.getSala();
        localidades = sala.getLocalidades();
        primaryStage = stage;
        prevScene = stage.getScene();
        prevTitle = stage.getTitle();
        one = false;
        iniciar();
    }

    public void initStage2(Stage stage, Proyeccion electa, Reserva r) {
        proyeccion = electa;
        sala = proyeccion.getSala();
        localidades = sala.getLocalidades();
        primaryStage = stage;
        prevScene = stage.getScene();
        prevTitle = stage.getTitle();
        one = true;
        counter = r.getNumLocalidades();
        counter2 = counter;
        seatsSelected.setText("" + counter2);
        reserva = r;
        iniciar();
    }

    @FXML
    private void backButtonClick(ActionEvent event) {
        primaryStage.setTitle(prevTitle);
        primaryStage.setScene(prevScene);
    }

    @FXML
    private void buyClick(ActionEvent event) {
        if (!one) {
            try {
                FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/view/Confirmation.fxml"));
                Parent root = (Parent) myLoader.load();
                ConfirmationController window;
                window = myLoader.<ConfirmationController>getController();
                window.initStage(primaryStage, proyeccion, sala, counter);
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (one) {
                if (counter != 0) {
                    Timeline flasher = new Timeline(
                            new KeyFrame(Duration.seconds(2.0), e -> {
                                seatsSelected.setStyle("-fx-background-color: indianred;");
                            }),
                            new KeyFrame(Duration.seconds(1.0), e -> {
                                seatsSelected.setStyle("-fx-background-color: #f7f7f7;");
                            })
                    );
                    flasher.play();
                } else {
                    try {
                        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/view/Confirmation.fxml"));
                        Parent root = (Parent) myLoader.load();
                        ConfirmationController window;
                        window = myLoader.<ConfirmationController>getController();
                        window.initStage2(primaryStage, proyeccion, sala, counter2, reserva);
                        Scene scene = new Scene(root);
                        primaryStage.setScene(scene);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
