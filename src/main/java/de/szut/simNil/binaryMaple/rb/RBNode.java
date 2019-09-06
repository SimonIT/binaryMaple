package de.szut.simNil.binaryMaple.rb;

import de.szut.simNil.binaryMaple.BNode;
import guru.nidi.graphviz.attribute.Color;

/**
 * Red Black Binary Tree Node with left and right child
 * and color
 *
 * @author wolke
 * @version 1.1, 19.08.2019
 */
public class RBNode<T extends Comparable<T>> extends BNode<T> {
    private Color color = Color.RED;

    RBNode() {
        super();
    }

    public RBNode(T value) {
        this.value = value;
    }

    public RBNode<T> getLeft() {
        return (RBNode<T>) left;
    }

    public void setLeft(RBNode<T> left) {
        this.left = left;
    }

    public RBNode<T> getRight() {
        return (RBNode<T>) right;
    }

    public void setRight(RBNode<T> right) {
        this.right = right;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
