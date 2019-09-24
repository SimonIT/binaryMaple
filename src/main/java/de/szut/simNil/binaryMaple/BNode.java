package de.szut.simNil.binaryMaple;

import org.jetbrains.annotations.Nullable;


public class BNode<T extends Comparable<T>> extends AbstractNode<T> {
    @Nullable
    protected BNode<T> left = null;
    @Nullable
    protected BNode<T> right = null;

    public BNode() {
        super();
    }

    public BNode(@Nullable T value) {
        this.value = value;
    }

    @Nullable
    public BNode<T> getLeft() {
        return left;
    }

    public void setLeft(@Nullable BNode<T> left) {
        this.left = left;
    }

    @Nullable
    public BNode<T> getRight() {
        return right;
    }

    public void setRight(@Nullable BNode<T> right) {
        this.right = right;
    }
}
