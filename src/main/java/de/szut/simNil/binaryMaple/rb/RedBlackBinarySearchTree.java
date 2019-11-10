package de.szut.simNil.binaryMaple.rb;

import de.szut.simNil.binaryMaple.AbstractBinarySearchTree;
import de.szut.simNil.binaryMaple.BinarySearchTreeException;
import org.jetbrains.annotations.NotNull;

import java.util.Stack;

/**
 * This class contains the logic for a red black binary search tree. Values can be added and removed. To keep the tree
 * balanced, the conditions of a red black tree have to be met after any interaction with the tree. The insertion and
 * deletion cases used to balance the tree are described here → https://de.wikipedia.org/wiki/Rot-Schwarz-Baum
 *
 * @param <T> type parameter of node values (for example Integer or String)
 * @author Simon Bullik
 * @author Nils Malte Kiele
 */
public class RedBlackBinarySearchTree<T extends Comparable<T>> extends AbstractBinarySearchTree<T> {
    public RedBlackBinarySearchTree() {
        this.root = new RBNode<>();
        this.nodeCount = 0;
    }

    public RedBlackBinarySearchTree(@NotNull T value) {
        this.root = new RBNode<>(value);
        this.root.setLeft(new RBNode<>());
        this.root.setRight(new RBNode<>());
        this.nodeCount = 1;
    }

    /**
     * @param current   node that might violate the conditions of a red black tree
     * @param ancestors stack of nodes starting with the parent of the current node and ending at the root
     */
    private void rebalanceAfterInsertion(@NotNull RBNode<T> current, @NotNull Stack<RBNode<T>> ancestors) {
        if (ancestors.isEmpty()) {
            // insertion case 0
            current.setColor(RBNode.Color.BLACK);
            return;
        }

        RBNode<T> parent = ancestors.pop();

        if (parent.getColor() == RBNode.Color.BLACK) {
            // insertion case 1
            return;
        }

        if (ancestors.isEmpty()) {
            // insertion case 2
            parent.setColor(RBNode.Color.BLACK);
            return;
        }

        RBNode<T> grandparent = ancestors.pop();

        boolean currentIsLeftChild = current.getValue().compareTo(parent.getValue()) < 0;
        boolean parentIsLeftChild = parent.getValue().compareTo(grandparent.getValue()) < 0;
        // "pibling" here refers to the gender-neutral sibling of the parent of the current node
        RBNode<T> pibling = parentIsLeftChild ? grandparent.getRight() : grandparent.getLeft();

        if (pibling.getColor() == RBNode.Color.BLACK) {
            if (currentIsLeftChild != parentIsLeftChild) {
                // insertion case 3
                if (parentIsLeftChild) {
                    grandparent.setLeft(current);
                    parent.setRight(current.getLeft());
                    current.setLeft(parent);
                } else {
                    grandparent.setRight(current);
                    parent.setLeft(current.getRight());
                    current.setRight(parent);
                }
                // update parent; current is not needed for insertion case 4 and thus doesn't need to get updated
                parent = current;
            }

            // insertion case 4
            if (parentIsLeftChild) {
                grandparent.setLeft(parent.getRight());
                parent.setRight(grandparent);
            } else {
                grandparent.setRight(parent.getLeft());
                parent.setLeft(grandparent);
            }
            parent.setColor(RBNode.Color.BLACK);
            grandparent.setColor(RBNode.Color.RED);
            if (ancestors.isEmpty()) {
                this.root = parent;
            } else {
                RBNode<T> newGrandparent = ancestors.pop();
                if (parent.getValue().compareTo(newGrandparent.getValue()) < 0) {
                    newGrandparent.setLeft(parent);
                } else {
                    newGrandparent.setRight(parent);
                }
            }
        } else {
            // insertion case 5
            parent.setColor(RBNode.Color.BLACK);
            pibling.setColor(RBNode.Color.BLACK);
            grandparent.setColor(RBNode.Color.RED);
            rebalanceAfterInsertion(grandparent, ancestors);
        }
    }

    /**
     * @param value value that should be inserted into the tree
     * @throws BinarySearchTreeException
     */
    @Override
    public void addValue(@NotNull T value) throws BinarySearchTreeException {
        Stack<RBNode<T>> ancestors = new Stack<>();
        RBNode<T> current = (RBNode<T>) this.root;
        while (current.getValue() != null) {
            int c = value.compareTo(current.getValue());
            if (c == 0) {
                throw new BinarySearchTreeException(String.format("Node with value %s already exists", value));
            }
            // update ancestors and current node
            ancestors.push(current);
            current = c < 0 ? current.getLeft() : current.getRight();
        }
        // change current node from a leaf node (black) to an internal node (red) and insert new value
        current.setColor(RBNode.Color.RED);
        current.setValue(value);
        // create new leaf nodes
        current.setLeft(new RBNode<>());
        current.setRight(new RBNode<>());
        // rebalancing of tree might be necessary to meet all conditions of a red black tree
        rebalanceAfterInsertion(current, ancestors);
        // insertion of value was successful; update nodeCount
        ++this.nodeCount;
    }

    /**
     * @param currentValue value of node that might violate the conditions of a red black tree
     * @param ancestors    stack of nodes starting with the parent of the current (now leaf) node and ending at the root
     */
    public void rebalanceAfterDeletion(@NotNull T currentValue, @NotNull Stack<RBNode<T>> ancestors) {
        if (ancestors.isEmpty()) {
            // deletion case 0
            return;
        }

        RBNode<T> parent = ancestors.pop();
        boolean currentIsLeftChild = currentValue.compareTo(parent.getValue()) < 0;
        RBNode<T> sibling = currentIsLeftChild ? parent.getRight() : parent.getLeft();

        if (sibling.getColor() == RBNode.Color.RED) {
            // deletion case 2
            if (ancestors.isEmpty()) {
                this.root = sibling;
            } else if (parent.getValue().compareTo(ancestors.peek().getValue()) < 0) {
                ancestors.peek().setLeft(sibling);
            } else {
                ancestors.peek().setRight(sibling);
            }
            if (currentIsLeftChild) {
                parent.setRight(sibling.getLeft());
                sibling.setLeft(parent);
            } else {
                parent.setLeft(sibling.getRight());
                sibling.setRight(parent);
            }
            parent.setColor(RBNode.Color.RED);
            sibling.setColor(RBNode.Color.BLACK);
            ancestors.push(sibling);
            ancestors.push(parent);
            rebalanceAfterDeletion(currentValue, ancestors);
            return;
        }

        if (sibling.getValue() == null || sibling.getLeft().getColor() == RBNode.Color.BLACK && sibling.getRight().getColor() == RBNode.Color.BLACK) {
            if (parent.getColor() == RBNode.Color.BLACK) {
                // deletion case 1
                sibling.setColor(RBNode.Color.RED);
                rebalanceAfterDeletion(parent.getValue(), ancestors);
            } else {
                // deletion case 3
                parent.setColor(RBNode.Color.BLACK);
                sibling.setColor(RBNode.Color.RED);
            }
            return;
        }

        RBNode<T> farNephew = currentIsLeftChild ? sibling.getRight() : sibling.getLeft();

        if (farNephew.getColor() == RBNode.Color.RED) {
            // deletion case 5
            if (ancestors.isEmpty()) {
                this.root = sibling;
            } else if (parent.getValue().compareTo(ancestors.peek().getValue()) < 0) {
                ancestors.peek().setLeft(sibling);
            } else {
                ancestors.peek().setRight(sibling);
            }
            if (currentIsLeftChild) {
                parent.setRight(sibling.getLeft());
                sibling.setLeft(parent);
            } else {
                parent.setLeft(sibling.getRight());
                sibling.setRight(parent);
            }
            sibling.setColor(parent.getColor());
            parent.setColor(RBNode.Color.BLACK);
            farNephew.setColor(RBNode.Color.BLACK);
            return;
        }

        // deletion case 4
        RBNode<T> closeNephew = currentIsLeftChild ? sibling.getLeft() : sibling.getRight(); // must be red
        if (currentIsLeftChild) {
            parent.setRight(closeNephew);
            sibling.setLeft(closeNephew.getRight());
            closeNephew.setRight(sibling);
        } else {
            parent.setLeft(closeNephew);
            sibling.setRight(closeNephew.getLeft());
            closeNephew.setLeft(sibling);
        }
        sibling.setColor(RBNode.Color.RED);
        closeNephew.setColor(RBNode.Color.BLACK);
        ancestors.push(parent);
        rebalanceAfterDeletion(currentValue, ancestors);
    }

    /**
     * @param value value that should be deleted from the tree
     * @throws BinarySearchTreeException
     */
    @Override
    public void delValue(@NotNull T value) throws BinarySearchTreeException {
        Stack<RBNode<T>> ancestors = new Stack<>();
        RBNode<T> current = (RBNode<T>) this.root;
        while (current.getValue() != null) {
            int c = value.compareTo(current.getValue());
            if (c == 0) {
                // in case the current node has two children, it will attain the value of the physically removed node
                T newCurrentValue = current.getValue();
                // this node will be physically removed from the tree
                RBNode<T> doomed = current;
                // if current node has two children, update doomed node and its ancestors
                if (current.getLeft().getValue() != null && current.getRight().getValue() != null) {
                    // update doomed node to be the node with the largest value in the left subtree of the current node
                    ancestors.push(current);
                    doomed = current.getLeft();
                    while (doomed.getRight().getValue() != null) {
                        ancestors.push(doomed);
                        doomed = doomed.getRight();
                    }
                    // save value of doomed value to later
                    newCurrentValue = doomed.getValue();
                }

                boolean doomedHasLeftValue = doomed.getLeft().getValue() != null;
                boolean doomedHasRightValue = doomed.getRight().getValue() != null;
                // when there is no parent to compare the doomed node to, this value does not matter
                boolean doomedIsLeftChild = ancestors.isEmpty() || doomed.getValue().compareTo(ancestors.peek().getValue()) < 0;

                if (doomed.getColor() == RBNode.Color.RED) {
                    // easy deletion case 1
                    if (ancestors.isEmpty()) {
                        this.root = new RBNode<>();
                    } else if (doomedIsLeftChild) {
                        ancestors.peek().setLeft(new RBNode<>());
                    } else {
                        ancestors.peek().setRight(new RBNode<>());
                    }
                } else if (doomedHasLeftValue || doomedHasRightValue) { // remember: doomed node has at most one child
                    // easy deletion case 2
                    RBNode<T> child = doomedHasLeftValue ? doomed.getLeft() : doomed.getRight();
                    if (ancestors.isEmpty()) {
                        this.root = child;
                    } else if (doomedIsLeftChild) {
                        ancestors.peek().setLeft(child);
                    } else {
                        ancestors.peek().setRight(child);
                    }
                    child.setColor(RBNode.Color.BLACK);
                } else {
                    if (ancestors.isEmpty()) {
                        this.root = new RBNode<>();
                    } else if (doomedIsLeftChild) {
                        ancestors.peek().setLeft(new RBNode<>());
                    } else {
                        ancestors.peek().setRight(new RBNode<>());
                    }
                    // rebalancing of tree is necessary to meet all conditions of a red black tree
                    rebalanceAfterDeletion(doomed.getValue(), ancestors);
                }
                current.setValue(newCurrentValue);
                // deletion of value was successful; update nodeCount and return
                --this.nodeCount;
                return;
            } else {
                // update ancestors and current node
                ancestors.push(current);
                current = c < 0 ? current.getLeft() : current.getRight();
            }
        }
        throw new BinarySearchTreeException(String.format("Node with value %s cannot be deleted because it does not exist", value));
    }
}
