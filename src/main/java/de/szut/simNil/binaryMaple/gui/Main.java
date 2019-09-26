package de.szut.simNil.binaryMaple.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("style.fxml"));
        Parent root = loader.load();
        ((Controller) loader.getController()).setStage(stage);
        stage.setTitle("BinaryMaple");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
