package de.szut.simNil.binaryMaple.standard;

import de.szut.simNil.binaryMaple.BinarySearchTreeException;
import de.szut.simNil.binaryMaple.InterfaceBinarySearchTree;
import de.szut.simNil.binaryMaple.Order;

import java.util.List;

public class StandardBinarySearchTree<T extends Comparable<T>> implements InterfaceBinarySearchTree<T> {
    private StandardBinaryNode<T> root;

    public StandardBinarySearchTree() {
        this.root = new StandardBinaryNode<>();
    }

    public StandardBinarySearchTree(T value) {
        this.root = new StandardBinaryNode<>(value);
    }

    @Override
    public void addValue(T value) throws BinarySearchTreeException {
        root.addNode(new StandardBinaryNode<>(value));
    }

    @Override
    public void delValue(T value) throws BinarySearchTreeException {

    }

    @Override
    public boolean hasValue(T value) {
        return root.hasValue(value);
    }

    @Override
    public Integer getDepth() {
        return root.getDepth();
    }

    @Override
    public List<T> traverse(Order order) {
        return root.traverse(order);
    }

    public StandardBinaryNode<T> getRoot() {
        return this.root;
    }
}
