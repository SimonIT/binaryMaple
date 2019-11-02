package de.szut.simNil.binaryMaple.rb;

import de.szut.simNil.binaryMaple.BNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This class extends BNode and is used in red black trees. In addition to the functionality of BNode, every node of
 * this class contains a color (red or black) which is specified by an Enum within the class.
 *
 * @param <T> type parameter of node values (for example Integer or String)
 * @author Simon Bullik
 * @author Nils Malte Kiele
 */
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

    /**
     * This enum contains the two colors (RED and BLACK) an RBNode can have
     */
    public enum Color {
        RED, BLACK
    }
}
