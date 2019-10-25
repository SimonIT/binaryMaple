package de.szut.simNil.binaryMaple.gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        new KeyCodeCombination(KeyCode.H, KeyCombination.ALT_DOWN), // Add
        new KeyCodeCombination(KeyCode.L, KeyCombination.ALT_DOWN), // Delete
        new KeyCodeCombination(KeyCode.S, KeyCombination.ALT_DOWN), // Search
        new KeyCodeCombination(KeyCode.K, KeyCombination.ALT_DOWN), // Collapse
        new KeyCodeCombination(KeyCode.Z, KeyCombination.ALT_DOWN), // Random
        new KeyCodeCombination(KeyCode.E, KeyCombination.ALT_DOWN), // Standard
        new KeyCodeCombination(KeyCode.R, KeyCombination.ALT_DOWN), // Red Black
        new KeyCodeCombination(KeyCode.A, KeyCombination.ALT_DOWN), // AVL
        new KeyCodeCombination(KeyCode.T, KeyCombination.ALT_DOWN), // Terminal
        new KeyCodeCombination(KeyCode.B, KeyCombination.ALT_DOWN), // Leafs
        new KeyCodeCombination(KeyCode.G, KeyCombination.ALT_DOWN), // Grass
    };

    Stage stage;
    ObservableList<AbstractController> controllers = FXCollections.observableArrayList(new IntegerController(), new DoubleController(), new StringController());

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        changeController(controllers.get(0));
    }

    void changeController(AbstractController controller) throws IOException {
        controller.setMain(this);
        if (stage.isShowing()) stage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("style.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        controller.setStage(stage);
        stage.setTitle("BinaryMaple");
        stage.setMaximized(true);
        stage.setScene(new Scene(root));
        stage.show();

        stage.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (combinations[0].match(keyEvent)) {
                controller.addValue();
                keyEvent.consume();
            } else if (combinations[1].match(keyEvent)) {
                controller.delValue();
                keyEvent.consume();
            } else if (combinations[2].match(keyEvent)) {
                controller.searchValue();
                keyEvent.consume();
            } else if (combinations[3].match(keyEvent)) {
                controller.collapseAtValue();
                keyEvent.consume();
            } else if (combinations[4].match(keyEvent)) {
                controller.generateValueTimes();
                keyEvent.consume();
            } else if (combinations[5].match(keyEvent)) {
                controller.standardTree.setSelected(true);
                keyEvent.consume();
            } else if (combinations[6].match(keyEvent)) {
                controller.redBlackTree.setSelected(true);
                keyEvent.consume();
            } else if (combinations[7].match(keyEvent)) {
                controller.avlTree.setSelected(true);
                keyEvent.consume();
            } else if (combinations[8].match(keyEvent)) {
                controller.showNullCheckBox.setSelected(!controller.showNullCheckBox.selectedProperty().getValue());
                keyEvent.consume();
            } else if (combinations[9].match(keyEvent)) {
                controller.showLeafsGreenCheckBox.setSelected(!controller.showLeafsGreenCheckBox.selectedProperty().getValue());
                keyEvent.consume();
            } else if (combinations[10].match(keyEvent)) {
                controller.showGrassCheckBox.setSelected(!controller.showGrassCheckBox.selectedProperty().getValue());
                keyEvent.consume();
            }
        });
    }
}
