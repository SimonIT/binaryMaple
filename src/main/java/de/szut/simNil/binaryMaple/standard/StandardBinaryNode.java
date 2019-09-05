package de.szut.simNil.binaryMaple.standard;

import de.szut.simNil.binaryMaple.BNode;
import de.szut.simNil.binaryMaple.BinarySearchTreeException;
import de.szut.simNil.binaryMaple.Order;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StandardBinaryNode<T extends Comparable<T>> extends BNode<T> {
    // package-wide access only because Tree needs to be able to create empty node but user shouldn't do that directly
    StandardBinaryNode() {
        super();    // TODO: bad style?
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
        if (this.getValue() == null) {
            // TODO: is there a better way to do this?
            this.value = node.value;
            this.left = node.left;
            this.right = node.right;
        } else if (node.getValue().compareTo(this.getValue()) < 0) {
            ((StandardBinaryNode<T>) this.left).addNode(node);
        } else if (node.getValue().compareTo(this.getValue()) > 0) {
            ((StandardBinaryNode<T>) this.right).addNode(node);
        } else {
            throw new BinarySearchTreeException(String.format("Node with value %s already exists", node.toString()));
        }
    }

    @NotNull
    public List<T> traverse(Order order) {
        List<T> result = new ArrayList<>();
        if (this.value == null) {
            return result;
        }

        if (order == Order.PREORDER) {
            result.add(this.value);
            result.addAll(((StandardBinaryNode<T>) this.left).traverse(Order.PREORDER));
            result.addAll(((StandardBinaryNode<T>) this.right).traverse(Order.PREORDER));
        } else if (order == Order.INORDER) {
            result.addAll(((StandardBinaryNode<T>) this.left).traverse(Order.INORDER));
            result.add(this.value);
            result.addAll(((StandardBinaryNode<T>) this.right).traverse(Order.INORDER));
        } else if (order == Order.POSTORDER) {
            result.addAll(((StandardBinaryNode<T>) this.left).traverse(Order.POSTORDER));
            result.addAll(((StandardBinaryNode<T>) this.right).traverse(Order.POSTORDER));
            result.add(this.value);
        } else if (order == Order.LEVELORDERR) {
            // TODO
        }

        return result;
    }

    public boolean hasValue(T value) {
        if (this.value == null) {
            return false;
        }
        if (value.compareTo(this.value) == 0) {
            return true;
        }
        if (value.compareTo(this.value) < 0) {
            return ((StandardBinaryNode<T>) this.left).hasValue(value);
        }
        return ((StandardBinaryNode<T>) this.right).hasValue(value);
    }

    public int getDepth() {
        if (this.value == null) {
            return 0;
        }
        return 1 + Math.max(((StandardBinaryNode<T>) this.left).getDepth(), ((StandardBinaryNode<T>) this.right).getDepth());
    }
}
