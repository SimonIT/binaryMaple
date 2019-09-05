package de.szut.simNil.binaryMaple;

/**
 * Interface fuer Binary Search StandardBinarySearchTree Node
 *
 * @author wolke
 * @version 1.0, 19.08.2019
 */

public class Node<N extends Node, T extends Comparable<T>> {
    protected N left; // left child
    protected N right; // right child
    protected T value; // integer value

    public Node() {
    }

    public Node(T value) {
        this.value = value;
    }

    public N getLeft() {
        return left;
    }

    public void setLeft(N left) {
        this.left = left;
    }

    public N getRight() {
        return right;
    }

    public void setRight(N right) {
        this.right = right;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String toString() {
        return value.toString();
    }

}
