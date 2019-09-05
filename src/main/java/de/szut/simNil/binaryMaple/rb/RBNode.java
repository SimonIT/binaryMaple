package de.szut.simNil.binaryMaple.rb;
/**
 * Red Black Binary Tree Node with left and right child
 * and color
 *
 * @author wolke
 * @version 1.1, 19.08.2019
 */

import de.szut.simNil.binaryMaple.AbstractNode;

import java.awt.*;


public class RBNode<T extends Comparable<T>> extends AbstractNode<T> {
    private RBNode<T> left = null;
    private RBNode<T> right = null;
    private Color color = Color.RED;

    RBNode() {
        super();
    }

    RBNode(T value) {
        this.value = value;
    }

    public RBNode<T> getLeft() {
        return left;
    }

    public void setLeft(RBNode<T> left) {
        this.left = left;
    }

    public RBNode<T> getRight() {
        return right;
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
