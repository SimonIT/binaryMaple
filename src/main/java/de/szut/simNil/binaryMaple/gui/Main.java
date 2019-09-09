package de.szut.simNil.binaryMaple.gui;

import de.szut.simNil.binaryMaple.BinarySearchTreeException;
import de.szut.simNil.binaryMaple.InterfaceBinarySearchTree;
import de.szut.simNil.binaryMaple.standard.StandardBinarySearchTree;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private InterfaceBinarySearchTree<Integer> tree;
    private boolean showNullNodes = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        tree = new StandardBinarySearchTree<>();

        TreeVisualizer visualizer = new TreeVisualizer(tree);

        ImageView imageView = new ImageView(visualizer.getGraphvizImage(showNullNodes));

        HBox p = new HBox();

        VBox gridPane = new VBox();

        TextField value = new TextField();

        gridPane.getChildren().add(value);

        CheckBox checkShowNull = new CheckBox();

        checkShowNull.setOnAction(actionEvent -> {
            showNullNodes = checkShowNull.isSelected();
            imageView.setImage(visualizer.getGraphvizImage(this.showNullNodes));
        });

        gridPane.getChildren().add(checkShowNull);

        Button addButton = new Button("Hinzufügen");

        addButton.setOnAction(event -> {
            try {
                tree.addValue(Integer.valueOf(value.getText()));
                imageView.setImage(visualizer.getGraphvizImage(this.showNullNodes));
            } catch (BinarySearchTreeException e) {
                System.out.println(e.getMessage());
            }
        });

        gridPane.getChildren().add(addButton);

        Button delButton = new Button("Löschen");

        delButton.setOnAction(event -> {
            try {
                tree.delValue(Integer.valueOf(value.getText()));
                imageView.setImage(visualizer.getGraphvizImage(this.showNullNodes));
            } catch (BinarySearchTreeException e) {
                System.out.println(e.getMessage());
            }
        });

        gridPane.getChildren().add(delButton);

        p.getChildren().add(gridPane);

        p.getChildren().add(new ScrollPane(imageView));
        stage.setScene(new Scene(p));
        stage.show();
    }
}
