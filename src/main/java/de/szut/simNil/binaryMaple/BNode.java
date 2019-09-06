package de.szut.simNil.binaryMaple;

import org.jetbrains.annotations.Nullable;

/**
 * Binary Tree Node with left and right child
 *
 * @author wolke
 * @version 1.1, 19.08.2019
 */


public class BNode<T extends Comparable<T>> extends AbstractNode<T> {
    protected BNode<T> left = null;
    protected BNode<T> right = null;

    public BNode() {
        super();
    }

    public BNode(T value) {
        this.value = value;
    }

    public BNode<T> getLeft() {
        return left;
    }

    public void setLeft(BNode<T> left) {
        this.left = left;
    }

    public BNode<T> getRight() {
        return right;
    }

    public void setRight(BNode<T> right) {
        this.right = right;
    }

}
