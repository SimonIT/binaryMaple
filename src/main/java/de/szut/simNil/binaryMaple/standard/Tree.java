package de.szut.simNil.binaryMaple.standard;

import de.szut.simNil.binaryMaple.BinarySearchTreeException;
import de.szut.simNil.binaryMaple.InterfaceBinarySearchTree;
import de.szut.simNil.binaryMaple.Order;

import java.util.List;

public class Tree implements InterfaceBinarySearchTree {
    private Node<Integer> root;

    public Tree(Integer i) {
        this.root = new Node<>(i);
    }


    @Override
    public void addValue(Comparable value) throws BinarySearchTreeException {

    }

    @Override
    public void delValue(Comparable value) throws BinarySearchTreeException {

    }

    @Override
    public boolean hasValue(Comparable value) {
        return false;
    }

    @Override
    public Integer getDepth() {
        return null;
    }

    @Override
    public List traverse(Order o) {
        return null;
    }
}
