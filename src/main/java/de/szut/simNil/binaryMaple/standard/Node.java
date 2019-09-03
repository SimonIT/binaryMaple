package de.szut.simNil.binaryMaple.standard;

import de.szut.simNil.binaryMaple.AbstractNode;

import java.util.ArrayList;

public class Node<T extends Comparable<T>> extends AbstractNode<Node, T> {
    public Node(T value) {
        super(value);
    }

    public Node(T value, Node<T> left, Node<T> right) {
        super(value);
        this.left = left;
        this.right = right;
    }

    public void insert(Node<T> n) {
        if (n.getValue().compareTo(this.getValue()) < 0) {
            if (this.left == null) {
                this.setLeft(n);
            } else {
                this.left.insert(n);
            }
        } else {
            if (this.right == null) {
                this.right = n;
            } else {
                this.right.insert(n);
            }
        }
    }


    public ArrayList<Node<T>> traversePreOrder() {
        ArrayList<Node<T>> result = new ArrayList<>();

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
