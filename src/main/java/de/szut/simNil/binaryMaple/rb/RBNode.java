package de.szut.simNil.binaryMaple.rb;

import de.szut.simNil.binaryMaple.BNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RBNode<T extends Comparable<T>> extends BNode<T> {
    @NotNull
    private Color color;

    RBNode() {
        super();
        this.color = Color.BLACK;   // initialize terminal nodes as black
    }

    public RBNode(@NotNull T value) {
        this.value = value;
        this.color = Color.RED; // initialize nodes with values as red
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
