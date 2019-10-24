package de.szut.simNil.binaryMaple;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * This class is an extension of AbstractNode and has two additional variables for its left and right child.
 * These children can either be of type BNode or null.
 * @param <T> type parameter of node value (for example Integer or String)
 * @author Jürgen Wolkenhauer
 * @author Simon Bullik
 * @author Nils Malte Kiele
 */
public class BNode<T extends Comparable<T>> extends AbstractNode<T> {
    /**
     * left child of node
     */
    @Nullable
    protected BNode<T> left = null;
    /**
     * right child of node
     */
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
