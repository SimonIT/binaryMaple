package de.szut.simNil.binaryMaple.standard;

import de.szut.simNil.binaryMaple.BinaryNode;
import de.szut.simNil.binaryMaple.BinarySearchTreeException;
import de.szut.simNil.binaryMaple.Order;

import java.util.ArrayList;
import java.util.List;

public class StandardBinaryNode<T extends Comparable<T>> extends BinaryNode<StandardBinaryNode<T>, T> {
    // package-wide access only because Tree needs to be able to create empty node but user shouldn't do that directly
    StandardBinaryNode() {
        super(null);    // TODO: bad style?
    }

    public StandardBinaryNode(T value) {
        super(value);
        this.left = new StandardBinaryNode<>();
        this.right = new StandardBinaryNode<>();
    }

    public StandardBinaryNode(T value, StandardBinaryNode<T> left, StandardBinaryNode<T> right) {
        super(value);
        this.left = left;
        this.right = right;
    }

    public void addNode(StandardBinaryNode<T> node) throws BinarySearchTreeException {
        if (this.value == null) {
            // TODO: is there a better way to do this?
            this.value = node.value;
            this.left = node.left;
            this.right = node.right;
        } else if (node.getValue().compareTo(this.getValue()) < 0) {
            this.left.addNode(node);
        } else if (node.getValue().compareTo(this.getValue()) > 0) {
            this.right.addNode(node);
        } else {
            throw new BinarySearchTreeException(String.format("Node with value %s already exists", node.toString()));
        }
    }


    public List<T> traverse(Order order) {
        List<T> result = new ArrayList<>();
        if (this.value == null) {return result;}

        if (order == Order.PREORDER) {
            result.add(this.value);
            if (this.left != null) {
                result.addAll(this.left.traverse(Order.PREORDER));
            }
            if (this.right != null) {
                result.addAll(this.right.traverse(Order.PREORDER));
            }
        } else if (order == Order.INORDER) {
            if (this.left != null) {
                result.addAll(this.left.traverse(Order.INORDER));
            }
            result.add(this.value);
            if (this.right != null) {
                result.addAll(this.right.traverse(Order.INORDER));
            }
        } else if (order == Order.POSTORDER) {
            if (this.left != null) {
                result.addAll(this.left.traverse(Order.POSTORDER));
            }
            if (this.right != null) {
                result.addAll(this.right.traverse(Order.POSTORDER));
            }
            result.add(this.value);
        }

        return result;
    }

    public boolean hasValue(T value) {
        if (this.value == null) {return false;}
        if (value.compareTo(this.value) == 0) {
            return true;
        }
        if (value.compareTo(this.value) < 0) {
            return this.left.hasValue(value);
        }
        return this.right.hasValue(value);
    }

    public Integer getDepth() {
        if (this.value == null) {return 0;}
        return 1 + Math.max(this.left.getDepth(), this.right.getDepth());
    }

    // TODO: do we need this?
    @Override
    public String toString() {
        return null;
    }
}
