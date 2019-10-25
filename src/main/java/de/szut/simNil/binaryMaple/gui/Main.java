package de.szut.simNil.binaryMaple.gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;

public class Main extends Application {

    @Getter
    private ObservableList<AbstractController> controllers = FXCollections.observableArrayList(new IntegerController(), new DoubleController(), new StringController());
    @Getter
    private Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        this.stage.setTitle("BinaryMaple");
        this.stage.setMaximized(true);
        this.changeController(this.controllers.get(0));
        this.stage.show();
    }

    void changeController(AbstractController controller) throws IOException {
        controller.setMain(this);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("style.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        this.stage.setScene(new Scene(root));
    }
}
