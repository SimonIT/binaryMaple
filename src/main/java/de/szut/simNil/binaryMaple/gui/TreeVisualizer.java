package de.szut.simNil.binaryMaple.gui;

import de.szut.simNil.binaryMaple.AbstractNode;
import de.szut.simNil.binaryMaple.BNode;
import de.szut.simNil.binaryMaple.InterfaceBinarySearchTree;
import de.szut.simNil.binaryMaple.rb.RBNode;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Node;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static guru.nidi.graphviz.model.Factory.*;

public class TreeVisualizer {
    public static Style circleStyle = Style.SOLID;
    private int nullNodeNumber = 0;
    private InterfaceBinarySearchTree tree;
    private List<Node> nodes = new ArrayList<>();

    public TreeVisualizer(@NotNull InterfaceBinarySearchTree tree) {
        this.tree = tree;
    }

    @NotNull
    public List<Node> getNodes(boolean showNullNodes) {
        this.nodes.clear();
        this.nullNodeNumber = 0;
        addNode(this.tree.getRoot(), showNullNodes);
        return this.nodes;
    }

    private void addNode(@Nullable AbstractNode node, boolean showNullNodes) {
        if (node == null || node.getValue() == null) return;

        Node root = node(node.toString()).with(circleStyle);

        if (node instanceof BNode) {

            if (node instanceof RBNode) root = root.with(((RBNode) node).getColor());

            root = addBNode(root, ((BNode) node).getLeft(), showNullNodes);

            root = addBNode(root, ((BNode) node).getRight(), showNullNodes);

        }

        this.nodes.add(root);
    }

    @NotNull
    private Node addBNode(@NotNull Node root, @Nullable BNode node, boolean showNullNodes) {
        if (node != null && node.getValue() != null) {
            Node graphNode = node(node.toString());

            root = root.link(to(graphNode));

            addNode(node, showNullNodes);
        } else {
            if (showNullNodes)
                root = root.link(to(node(String.format("null%d", nullNodeNumber++)).with(Shape.RECTANGLE, Label.of("null"))));
        }
        return root;
    }

    @NotNull
    public Image getGraphvizImage(boolean showNullNodes) {
        return SwingFXUtils.toFXImage(Graphviz.fromGraph(graph().with(this.getNodes(showNullNodes))).render(Format.SVG).toImage(), null);
    }
}
