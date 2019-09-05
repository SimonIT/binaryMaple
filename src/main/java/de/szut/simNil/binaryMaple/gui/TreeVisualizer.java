package de.szut.simNil.binaryMaple.gui;

import de.szut.simNil.binaryMaple.AbstractNode;
import de.szut.simNil.binaryMaple.BNode;
import de.szut.simNil.binaryMaple.InterfaceBinarySearchTree;
import de.szut.simNil.binaryMaple.rb.RBNode;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.model.Node;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static guru.nidi.graphviz.model.Factory.node;
import static guru.nidi.graphviz.model.Factory.to;

public class TreeVisualizer {
    public static Style circleStyle = Style.SOLID;
    private InterfaceBinarySearchTree tree;
    private List<Node> nodes = new ArrayList<>();

    public TreeVisualizer(@NotNull InterfaceBinarySearchTree tree) {
        this.tree = tree;
    }

    @NotNull
    public List<Node> getNodes() {
        this.nodes.clear();
        addNode(this.tree.getRoot());
        return this.nodes;
    }

    private void addNode(@Nullable AbstractNode node) {
        if (node == null || node.getValue() == null) return;

        Node root = node(node.toString()).with(circleStyle);

        if (node instanceof BNode) {

            root = addBNode(root, ((BNode) node).getLeft());

            root = addBNode(root, ((BNode) node).getRight());

        } else if (node instanceof RBNode) {

            root = root.with(((RBNode) node).getColor());

            root = addRBNode(root, ((RBNode) node).getLeft());

            root = addRBNode(root, ((RBNode) node).getRight());
        }

        this.nodes.add(root);
    }

    @NotNull
    private Node addBNode(@NotNull Node root, @Nullable BNode node) {
        if (node != null && node.getValue() != null) {
            Node graphNode = node(node.toString());

            root = root.link(to(graphNode));

            addNode(node);
        }
        return root;
    }

    @NotNull
    private Node addRBNode(@NotNull Node root, @Nullable RBNode node) {
        if (node != null && node.getValue() != null) {
            Node graphNode = node(node.toString());

            root = root.link(to(graphNode));

            addNode(node);
        }
        return root;
    }
}
