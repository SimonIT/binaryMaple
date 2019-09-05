package de.szut.simNil.binaryMaple.gui;

import de.szut.simNil.binaryMaple.BinarySearchTreeException;
import de.szut.simNil.binaryMaple.InterfaceBinarySearchTree;
import de.szut.simNil.binaryMaple.standard.StandardBinarySearchTree;
import guru.nidi.graphviz.attribute.RankDir;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;

import static guru.nidi.graphviz.model.Factory.graph;

public class Main extends Application {

    static BufferedImage gr;

    public static void main(String[] args) {

        InterfaceBinarySearchTree<Integer> tree = new StandardBinarySearchTree<>();
        try {
            tree.addValue(1);
            tree.addValue(3);
            tree.addValue(10);
            tree.addValue(11);
            tree.addValue(2);
            tree.addValue(4);
            tree.addValue(7);
            tree.addValue(5);
            tree.addValue(6);
            tree.addValue(8);
            tree.addValue(9);
        } catch (BinarySearchTreeException e) {
            e.printStackTrace();
        }

        TreeVisualizer visualizer = new TreeVisualizer(tree);

        Graph g = graph("example1").directed()
            .graphAttr().with(RankDir.TOP_TO_BOTTOM)
            .with(
                visualizer.getNodes()
            );
        gr = Graphviz.fromGraph(g).render(Format.SVG).toImage();
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Pane p = new Pane();
        WritableImage i = new WritableImage(gr.getWidth(), gr.getHeight());
        SwingFXUtils.toFXImage(gr, i);
        p.getChildren().add(new ImageView(i));
        stage.setScene(new Scene(p));
        stage.show();
    }
}
