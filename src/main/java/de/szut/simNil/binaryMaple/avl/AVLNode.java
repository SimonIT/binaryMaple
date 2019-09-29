package de.szut.simNil.binaryMaple.avl;

import de.szut.simNil.binaryMaple.BNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AVLNode<T extends Comparable<T>> extends BNode<T> {
    @NotNull
    private Integer heightLeft, heightRight;

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

    Integer getHeightLeft() {
        return this.heightLeft;
    }

    void setHeightLeft(@NotNull Integer heightLeft) {
        this.heightLeft = heightLeft;
    }

    Integer getHeightRight() {
        return this.heightRight;
    }

    void setHeightRight(@NotNull Integer heightRight) {
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
}
