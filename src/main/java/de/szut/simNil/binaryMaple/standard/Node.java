package de.szut.simNil.binaryMaple;

import java.util.ArrayList;

public class Node<T extends Comparable<T>> extends AbstractNode<T> {
    public Node(T value, AbstractNode<T> left, AbstractNode<T> right) {
        this.value = (value);
        this.left = (left);
        this.right = (right);
    }

    public void insert(Node<T> n) {
        if (n.getValue().compareTo(this.getValue()) < 0) {
            if (this.left == null) {
                this.setLeft(n);
            } else {
                ((Node<T>) this.left).insert(n);
            }
        } else {
            if (this.right == null) {
                this.right = n;
            } else {
                this.right.insert(n);
            }
        }
    }


    public ArrayList<AbstractNode<T>> traversePreOrder() {
        ArrayList<AbstractNode<T>> result = new ArrayList<>();

        result.add(this);
        if (this.left != null) {
            result.addAll(this.left.traversePreOrder());
        }
        if (this.right != null) {
            result.addAll(this.right.traversePreOrder());
        }

        return result;
    }

    @Override
    public String toString() {
        return null;
    }
}
