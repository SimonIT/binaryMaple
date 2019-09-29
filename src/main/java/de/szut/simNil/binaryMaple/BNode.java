package de.szut.simNil.binaryMaple;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class BNode<T extends Comparable<T>> extends AbstractNode<T> {
    @Nullable
    protected BNode<T> left = null;
    @Nullable
    protected BNode<T> right = null;

    public BNode() {
        super();
    }

    public BNode(@NotNull T value) {
        super(value);
    }

    @Nullable
    public BNode<T> getLeft() {
        return left;
    }

    public void setLeft(@NotNull BNode<T> left) {
        this.left = left;
    }

    @Nullable
    public BNode<T> getRight() {
        return right;
    }

    public void setRight(@NotNull BNode<T> right) {
        this.right = right;
    }
}
