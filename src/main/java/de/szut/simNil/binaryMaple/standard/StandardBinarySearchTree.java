package de.szut.simNil.binaryMaple.standard;

import de.szut.simNil.binaryMaple.*;
import org.jetbrains.annotations.NotNull;

/**
 * This class contains the logic for a simple binary search tree. Values can be added and removed. No rebalancing
 * measures are taken after insertions or deletions so the shape of the tree depends much on the insertion order. In the
 * worst case, this tree decays into a list.
 *
 * @param <T> type parameter of node values (for example Integer or String)
 * @author Simon Bullik
 * @author Nils Malte Kiele
 */
public class StandardBinarySearchTree<T extends Comparable<T>> extends AbstractBinarySearchTree<T> {
    public StandardBinarySearchTree() {
        super();
    }

    public StandardBinarySearchTree(@NotNull T value) {
        super(value);
    }

    /**
     * @param value value that should be inserted into the tree
     * @throws BinarySearchTreeException
     */
    @Override
    public void addValue(@NotNull T value) throws BinarySearchTreeException {
        BNode<T> current = this.root;
        while (current.getValue() != null) {
            int c = value.compareTo(current.getValue());
            if (c == 0) {
                throw new BinarySearchTreeException(String.format("Node with value %s already exists", value));
            }
            // update current node
            current = c < 0 ? current.getLeft() : current.getRight();
        }
        // change value of current from null to the value that should be inserted and add terminal left and right child
        current.setValue(value);
        current.setLeft(new BNode<>());
        current.setRight(new BNode<>());
        ++this.nodeCount;
    }

    /**
     * @param value value that should be deleted from the tree
     * @throws BinarySearchTreeException
     */
    @Override
    public void delValue(@NotNull T value) throws BinarySearchTreeException {
        BNode<T> current = this.root;
        BNode<T> parent = new BNode<>();
        while (current.getValue() != null) {
            int c = value.compareTo(current.getValue());
            if (c == 0) {
                // node with value that should be deleted is found and referenced by current
                boolean hasLeftValue = current.getLeft().getValue() != null;
                boolean hasRightValue = current.getRight().getValue() != null;
                if (!hasLeftValue && !hasRightValue) {
                    if (parent.getValue() == null) {
                        this.root = new BNode<>();
                    } else if (current.getValue().compareTo(parent.getValue()) < 0) {
                        parent.setLeft(new BNode<>());
                    } else {
                        parent.setRight(new BNode<>());
                    }
                } else if (hasLeftValue && hasRightValue) {
                    parent = current;
                    BNode<T> nodeWithLargestValueInLeftSubtree = current.getLeft();
                    while (nodeWithLargestValueInLeftSubtree.getRight().getValue() != null) {
                        parent = nodeWithLargestValueInLeftSubtree;
                        nodeWithLargestValueInLeftSubtree = nodeWithLargestValueInLeftSubtree.getRight();
                    }
                    current.setValue(nodeWithLargestValueInLeftSubtree.getValue());
                    if (parent.getValue().compareTo(current.getValue()) == 0) {
                        parent.setLeft(nodeWithLargestValueInLeftSubtree.getLeft());
                    } else {
                        parent.setRight(new BNode<>());
                    }
                } else {
                    BNode<T> child = hasLeftValue ? current.getLeft() : current.getRight();
                    if (parent.getValue() == null) {
                        this.root = child;
                    } else if (current.getValue().compareTo(parent.getValue()) < 0) {
                        parent.setLeft(child);
                    } else {
                        parent.setRight(child);
                    }
                }
                // node has been successfully deleted
                --this.nodeCount;
                return;
            } else {
                // update current and parent nodes
                parent = current;
                current = c < 0 ? current.getLeft() : current.getRight();
            }
        }
        throw new BinarySearchTreeException(String.format("Node with value %s cannot be deleted because it does not exist", value));
    }
}
