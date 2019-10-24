package de.szut.simNil.binaryMaple.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static final KeyCombination[] combinations = new KeyCombination[]{
        new KeyCodeCombination(KeyCode.E, KeyCombination.ALT_DOWN),
    };

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("style.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.setStage(stage);
        stage.setTitle("BinaryMaple");
        stage.setMaximized(true);
        stage.setScene(new Scene(root));
        stage.show();

        stage.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (combinations[0].match(keyEvent)) {
                controller.addValue();
                keyEvent.consume();
            }
        });
    }
}
