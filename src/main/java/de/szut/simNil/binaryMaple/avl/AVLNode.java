package de.szut.simNil.binaryMaple.avl;

import de.szut.simNil.binaryMaple.BNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This class extends BNode and is used in AVL trees. In addition to the functionality of BNode, every node of
 * this class keeps track of the height of its left and right subtree. This information is relevant for rebalancing.
 *
 * @param <T> type parameter of node values (for example Integer or String)
 * @author Simon Bullik
 * @author Nils Malte Kiele
 */
public class AVLNode<T extends Comparable<T>> extends BNode<T> {
    @NotNull
    private int heightLeft, heightRight;

    AVLNode() {
        super();
        this.heightLeft = 0;
        this.heightRight = 0;
    }

    public AVLNode(@NotNull T value) {
        super(value);
        this.heightLeft = 0;
        this.heightRight = 0;
    }

    @Nullable
    public AVLNode<T> getLeft() {
        return (AVLNode<T>) left;
    }

    public void setLeft(@NotNull AVLNode<T> left) {
        this.left = left;
    }

    @Nullable
    public AVLNode<T> getRight() {
        return (AVLNode<T>) right;
    }

    void setRight(@NotNull AVLNode<T> right) {
        this.right = right;
    }

    int getHeightLeft() {
        return this.heightLeft;
    }

    void setHeightLeft(int heightLeft) {
        this.heightLeft = heightLeft;
    }

    int getHeightRight() {
        return this.heightRight;
    }

    void setHeightRight(int heightRight) {
        this.heightRight = heightRight;
    }

    void increaseHeightLeft() {
        ++this.heightLeft;
    }

    void decreaseHeightLeft() {
        --this.heightLeft;
    }

    void increaseHeightRight() {
        ++this.heightRight;
    }

    void decreaseHeightRight() {
        --this.heightRight;
    }

    /**
     * @return balance factor of a node instance (height of right subtree minus height of left subtree)
     */
    int getBalanceFactor() {
        return this.heightRight - this.heightLeft;
    }
}
