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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static guru.nidi.graphviz.model.Factory.*;

public class TreeVisualizer<T extends Comparable<T>> {
    private int duplicateNodeNumber = 0;
    @Setter
    private InterfaceBinarySearchTree<T> tree;
    private List<Node> nodes = new ArrayList<>();

    @Getter
    @Setter
    private AbstractNode<T> highlightedNode;

    private Graphviz graphviz;

    private List<AbstractNode<T>> collapseNodes = new ArrayList<>();

    @Getter
    @Setter
    private boolean showNullNodes = false;

    public TreeVisualizer(@NotNull InterfaceBinarySearchTree<T> tree) {
        this.tree = tree;
    }

    public void addCollapseNode(AbstractNode<T> node) {
        this.collapseNodes.add(node);
    }

    public boolean isCollapsed(AbstractNode<T> node) {
        return this.collapseNodes.contains(node);
    }

    public void removeCollapseNode(AbstractNode<T> node) {
        this.collapseNodes.remove(node);
    }

    @NotNull
    public List<Node> getNodes() {
        this.nodes.clear();
        this.duplicateNodeNumber = 0;
        addNode(this.tree.getRoot());
        return this.nodes;
    }

    private void addNode(@Nullable AbstractNode<T> node) {
        if (node == null || node.getValue() == null) {
            return;
        }

        Node root = node(node.toString()).with(Style.FILLED, Color.WHITE.fill(), Color.BLACK.font());

        if (node instanceof BNode) {
            if (node instanceof RBNode) {
                switch (((RBNode) node).getColor()) {
                    case RED:
                        root = root.with(Color.RED.fill());
                        break;
                    case BLACK:
                        root = root.with(Color.BLACK.fill(), Color.WHITE.font());
                        break;
                }
            }

            BNode<T> left = ((BNode<T>) node).getLeft();
            BNode<T> right = ((BNode<T>) node).getRight();
            if (left != null && right != null && left.getValue() == null && right.getValue() == null) {
                root = root.with(Color.GREEN);
            }

            root = addBNode(root, ((BNode) node).getLeft());

            root = addBNode(root, ((BNode) node).getRight());

        }

        if (this.highlightedNode != null && this.highlightedNode.getValue() != null && this.highlightedNode.getValue().compareTo(node.getValue()) == 0) {
            root = root.with(Color.PINK);
        }

        this.nodes.add(root);
    }

    @NotNull
    private Node addBNode(@NotNull Node root, @Nullable BNode node) {
        if (node != null && node.getValue() != null) {
            if (!this.collapseNodes.contains(node)) {

                Node graphNode = node(node.toString());

                root = root.link(to(graphNode));

                addNode(node);
            } else {
                Node nodeCollapse = node(String.format("collapse%d", duplicateNodeNumber++)).with(Label.of(""), Shape.TRIANGLE);
                this.nodes.add(nodeCollapse);
                root = root.link(to(nodeCollapse));
            }
        } else {
            if (this.showNullNodes) {
                Node nodeNull = node(String.format("null%d", duplicateNodeNumber++)).with(Shape.RECTANGLE, Label.of("null"));
                this.nodes.add(nodeNull);
                root = root.link(to(nodeNull));
            }
        }
        return root;
    }

    public void createGraphviz() {
        this.graphviz = Graphviz.fromGraph(graph().with(this.getNodes()).graphAttr().with(Color.rgba("00000000").background()));
    }

    @NotNull
    public Image getGraphvizImage() {
        return SwingFXUtils.toFXImage(this.graphviz.render(Format.SVG).toImage(), null);
    }

    public void saveGraphviz(File file, Format format) throws IOException {
        this.graphviz.render(format).toFile(file);
    }
}
