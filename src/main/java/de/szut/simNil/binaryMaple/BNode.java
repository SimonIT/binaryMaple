package de.szut.simNil.binaryMaple;

import org.jetbrains.annotations.Nullable;

/**
 * Binary Tree Node with left and right child
 *
 * @author wolke
 * @version 1.1, 19.08.2019
 */


public class BNode<T extends Comparable<T>> extends AbstractNode<T> {
    @Nullable
    protected BNode<T> left = null;
    @Nullable
    protected BNode<T> right = null;

    public BNode() {
        super();
    }

    public BNode(T value) {
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BNode && super.equals(obj)) {
            if (this.getLeft() != null && ((BNode) obj).getLeft() != null)
                if (!this.getLeft().equals(((BNode) obj).getLeft()))
                    return false;
            if (this.getRight() != null && ((BNode) obj).getRight() != null)
                return this.getRight().equals(((BNode) obj).getRight());
            return true;
        }
        return false;
    }
}
