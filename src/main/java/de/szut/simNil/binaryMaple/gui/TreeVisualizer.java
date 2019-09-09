package de.szut.simNil.binaryMaple.gui;

import de.szut.simNil.binaryMaple.AbstractNode;
import de.szut.simNil.binaryMaple.BNode;
import de.szut.simNil.binaryMaple.InterfaceBinarySearchTree;
import de.szut.simNil.binaryMaple.rb.RBNode;
import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Node;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;
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

    @Getter
    @Setter
    private AbstractNode highlightedNode;

    @Getter
    @Setter
    private boolean showNullNodes = false;

    public TreeVisualizer(@NotNull InterfaceBinarySearchTree tree) {
        this.tree = tree;
    }

    @NotNull
    public List<Node> getNodes() {
        this.nodes.clear();
        this.nullNodeNumber = 0;
        addNode(this.tree.getRoot());
        return this.nodes;
    }

    private void addNode(@Nullable AbstractNode node) {
        if (node == null || node.getValue() == null) return;

        Node root = node(node.toString()).with(circleStyle);

        if (node instanceof BNode) {

            if (node instanceof RBNode) root = root.with(Style.FILLED, ((RBNode) node).getColor());

            if (((BNode) node).getLeft().getValue() == null && ((BNode) node).getRight().getValue() == null)
                root = root.with(Color.GREEN);

            root = addBNode(root, ((BNode) node).getLeft());

            root = addBNode(root, ((BNode) node).getRight());

        }

        if (this.highlightedNode != null && highlightedNode.equals(node)) root = root.with(Style.FILLED, Color.PINK);

        this.nodes.add(root);
    }

    @NotNull
    private Node addBNode(@NotNull Node root, @Nullable BNode node) {
        if (node != null && node.getValue() != null) {
            Node graphNode = node(node.toString());

            root = root.link(to(graphNode));

            addNode(node);
        } else {
            if (this.showNullNodes)
                root = root.link(to(node(String.format("null%d", nullNodeNumber++)).with(Shape.RECTANGLE, Label.of("null"))));
        }
        return root;
    }

    @NotNull
    public Image getGraphvizImage() {
        return SwingFXUtils.toFXImage(Graphviz.fromGraph(graph().with(this.getNodes())).render(Format.SVG).toImage(), null);
    }
}
