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
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import modelo.Pelicula;
import accesoaBD.AccesoaBD;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Ian Ward
 */
public class DayMovieScreenController implements Initializable {

    private VBox aux;
    private Stage primaryStage;
    @FXML
    private TilePane tile;
    @FXML
    private Button btnGoBack;
    private String prevTitle;
    private Scene prevScene;
    @FXML
    private DatePicker date;
    //Movie Info
    String tituloPelicula;
    LocalDate dia;
    @FXML
    private Label errDate;
    private LocalDate chosenDate;
    private boolean one;
    @FXML
    private ScrollPane scroll;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    void initStage(Stage stage, boolean prev) {
        one = prev;
        primaryStage = stage;
        prevScene = stage.getScene();
        prevTitle = stage.getTitle();
        primaryStage.setTitle("Choose Day and Movie");
    }

    @FXML
    private void btnGoBack_Click(ActionEvent event) {
        primaryStage.setTitle(prevTitle);
        primaryStage.setScene(prevScene);
    }

    private void btnContinue_Click(ActionEvent event) {
        continueClick();
    }

    @FXML
    private void dateEntered(ActionEvent event) {
        dia = date.getValue();
        updateMovies();
    }

    private void updateMovies() {
        chosenDate = date.getValue();
        AccesoaBD db = new AccesoaBD();
        List<Pelicula> list = db.getPeliculas(chosenDate);
        if (!list.isEmpty()) {
            for (Pelicula p : list) {
                Image img = new Image(p.getPathImage(), 200, 200, true, true);
                ImageView iview = new ImageView(img);
                iview.setFitWidth(200);
                iview.setFitHeight(200);
                iview.setPreserveRatio(true);
                String titulo = p.getTitulo();
                Text txt = new Text(titulo);

                Tooltip t = new Tooltip(titulo);
                Tooltip.install(iview, t);
                Tooltip.install(txt, t);

                VBox box = new VBox(10, iview, txt);
                box.setAlignment(Pos.CENTER);
                tile.getChildren().add(box);

                box.setOnMouseClicked(e -> {
                    aux = (VBox) e.getSource();
                    continueClick();
                });
                scroll.setFitToHeight(true);
                scroll.setFitToWidth(true);
            }
        } else {
            errDate.setText("No Movies Found");
        }
    }

    private void continueClick() {
        tituloPelicula = ((Text) aux.getChildren().get(1)).getText();
        if (one) {
            reserva();
        } else {
            asientos();
        }
    }

    private void reserva() {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/view/SessionScreen.fxml"));
            Parent root = (Parent) myLoader.load();
            SessionScreenController window;
            window = myLoader.<SessionScreenController>getController();
            window.initStage(primaryStage, tituloPelicula, chosenDate, one, 0);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void asientos() {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/view/NSeatsScreen.fxml"));
            Parent root = (Parent) myLoader.load();
            NSeatsScreenController window;
            window = myLoader.<NSeatsScreenController>getController();
            window.initStage(primaryStage, tituloPelicula, chosenDate, one);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
