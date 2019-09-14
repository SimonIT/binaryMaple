package de.szut.simNil.binaryMaple.gui;

import de.szut.simNil.binaryMaple.AbstractNode;
import de.szut.simNil.binaryMaple.BinarySearchTreeException;
import de.szut.simNil.binaryMaple.InterfaceBinarySearchTree;
import de.szut.simNil.binaryMaple.Order;
import de.szut.simNil.binaryMaple.rb.RedBlackBinarySearchTree;
import de.szut.simNil.binaryMaple.standard.StandardBinarySearchTree;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Random;

public class Main extends Application {

    private InterfaceBinarySearchTree<Integer> tree;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        tree = new StandardBinarySearchTree<>();

        TreeVisualizer visualizer = new TreeVisualizer(tree);

        ImageView imageView = new ImageView(visualizer.getGraphvizImage());

        HBox p = new HBox();

        VBox controlsBox = new VBox();

        ToggleGroup treeToggle = new ToggleGroup();

        RadioButton radioButtonStandardTree = new RadioButton();

        radioButtonStandardTree.setSelected(true);
        radioButtonStandardTree.setToggleGroup(treeToggle);

        radioButtonStandardTree.setOnAction(actionEvent -> {
            List<Integer> values = tree.traverse(Order.PREORDER);
            this.tree = new StandardBinarySearchTree<>();
            try {
                addValuesToTree(values);
            } catch (BinarySearchTreeException e) {
                e.printStackTrace();
            }
            visualizer.setTree(tree);
            imageView.setImage(visualizer.getGraphvizImage());
        });

        controlsBox.getChildren().add(radioButtonStandardTree);

        RadioButton radioButtonRedBlackTree = new RadioButton();

        radioButtonRedBlackTree.setToggleGroup(treeToggle);

        radioButtonRedBlackTree.setOnAction(actionEvent -> {
            List<Integer> values = tree.traverse(Order.PREORDER);
            this.tree = new RedBlackBinarySearchTree<>();
            try {
                addValuesToTree(values);
            } catch (BinarySearchTreeException e) {
                e.printStackTrace();
            }
            visualizer.setTree(tree);
            imageView.setImage(visualizer.getGraphvizImage());
        });

        controlsBox.getChildren().add(radioButtonRedBlackTree);

        TextField value = new TextField();

        controlsBox.getChildren().add(value);

        CheckBox checkShowNull = new CheckBox();

        checkShowNull.setOnAction(actionEvent -> {
            visualizer.setShowNullNodes(checkShowNull.isSelected());
            imageView.setImage(visualizer.getGraphvizImage());
        });

        controlsBox.getChildren().add(checkShowNull);

        Button addButton = new Button("Hinzufügen");

        addButton.setOnAction(event -> {
            try {
                tree.addValue(Integer.valueOf(value.getText()));
                imageView.setImage(visualizer.getGraphvizImage());
            } catch (BinarySearchTreeException e) {
                System.out.println(e.getMessage());
            }
        });

        controlsBox.getChildren().add(addButton);

        Button delButton = new Button("Löschen");

        delButton.setOnAction(event -> {
            try {
                tree.delValue(Integer.valueOf(value.getText()));
                imageView.setImage(visualizer.getGraphvizImage());
            } catch (BinarySearchTreeException e) {
                System.out.println(e.getMessage());
            }
        });

        controlsBox.getChildren().add(delButton);

        Button searchButton = new Button("Suchen");

        searchButton.setOnAction(event -> {
            if (value.getText().isEmpty())
                visualizer.setHighlightedNode(null);
            else
                visualizer.setHighlightedNode(tree.getNodeWithValue(Integer.valueOf(value.getText())));
            imageView.setImage(visualizer.getGraphvizImage());
        });

        controlsBox.getChildren().add(searchButton);

        Button collapseButton = new Button("Ein-/Ausklappen");

        collapseButton.setOnAction(event -> {
            AbstractNode collapseNode = tree.getNodeWithValue(Integer.valueOf(value.getText()));
            if (visualizer.isCollapsed(collapseNode))
                visualizer.removeCollapseNode(collapseNode);
            else
                visualizer.addCollapseNode(collapseNode);
            imageView.setImage(visualizer.getGraphvizImage());
        });

        controlsBox.getChildren().add(collapseButton);

        Button generateRandomButton = new Button("Zufall");

        generateRandomButton.setOnAction(event -> {
            Random random = new Random();
            for (int i = 0; i < Integer.parseInt(value.getText()); i++) {
                try {
                    tree.addValue(random.nextInt(Math.max(20, 2 * tree.getNodeCount())));
                } catch (BinarySearchTreeException e) {
                    i--;
                }
            }
            imageView.setImage(visualizer.getGraphvizImage());
        });

        controlsBox.getChildren().add(generateRandomButton);

        p.getChildren().add(controlsBox);

        p.getChildren().add(new ScrollPane(imageView));
        stage.setScene(new Scene(p));
        stage.show();
    }

    void addValuesToTree(List<Integer> integers) throws BinarySearchTreeException {
        for (int i : integers)
            tree.addValue(i);
    }
}
