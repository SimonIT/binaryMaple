package de.szut.simNil.binaryMaple.rb;

import de.szut.simNil.binaryMaple.BNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RBNode<T extends Comparable<T>> extends BNode<T> {
    @NotNull
    private Color color;

    RBNode() {
        super();
        // initialize terminal nodes as black
        this.color = Color.BLACK;
    }

    public RBNode(@NotNull T value) {
        super(value);
        // initialize nodes with values as red
        this.color = Color.RED;
    }

    @Nullable
    public RBNode<T> getLeft() {
        return (RBNode<T>) left;
    }

    public void setLeft(@NotNull RBNode<T> left) {
        this.left = left;
    }

    @Nullable
    public RBNode<T> getRight() {
        return (RBNode<T>) right;
    }

    public void setRight(@NotNull RBNode<T> right) {
        this.right = right;
    }

    @NotNull
    public Color getColor() {
        return this.color;
    }

    public void setColor(@NotNull Color color) {
        this.color = color;
    }

    public enum Color {
        RED, BLACK
    }
}
