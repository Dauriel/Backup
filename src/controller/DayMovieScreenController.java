/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import modelo.Pelicula;
import accesoaBD.AccesoaBD;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    private Stage primaryStage;
    private final String PATH = "";
    @FXML
    private TilePane tile;
    @FXML
    private Button btnGoBack;
    @FXML
    private Button btnContinue;
    private String prevTitle;
    private Scene prevScene;

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
        File dir = new File(PATH);
        AccesoaBD db = new AccesoaBD();
        for (Pelicula p : db.getTodasPeliculas()) {
            Image img = new Image(p.getPathImage(), 200, 200, true, true);
            ImageView iview = new ImageView(img);
            iview.setFitWidth(200);
            iview.setFitHeight(200);
            iview.setPreserveRatio(true);
            Text txt = new Text(p.getTitulo());

            VBox box = new VBox(10, iview, txt);

            tile.getChildren().add(box);
        }
        primaryStage.setTitle("Choose Day and Movie");
    }

    @FXML
    private void btnGoBack_Click(ActionEvent event) {
        primaryStage.setTitle(prevTitle);
        primaryStage.setScene(prevScene);
    }

    @FXML
    private void btnContinue_Click(ActionEvent event) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/view/SessionScreen.fxml"));
            Parent root = (Parent) myLoader.load();
            SessionScreenController window;
            window = myLoader.<SessionScreenController>getController();
            window.initStage(primaryStage);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
