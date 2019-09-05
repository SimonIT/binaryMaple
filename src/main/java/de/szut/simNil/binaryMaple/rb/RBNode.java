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


public class RBNode<T extends Comparable<T>> extends AbstractNode {
    private RBNode left = null;
    private RBNode right = null;
    private Color color = Color.RED;

    RBNode() {
        super();
    }

    RBNode(T value) {
        this.value = value;
    }

    public RBNode getLeft() {
        return left;
    }

    public void setLeft(RBNode left) {
        this.left = left;
    }

    public RBNode getRight() {
        return right;
    }

    public void setRight(RBNode right) {
        this.right = right;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
