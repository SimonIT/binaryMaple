package de.szut.simNil.binaryMaple.avl;

import de.szut.simNil.binaryMaple.AbstractBinarySearchTree;
import de.szut.simNil.binaryMaple.BinarySearchTreeException;
import org.jetbrains.annotations.NotNull;

import java.util.Stack;

/**
 * This class contains the logic for an AVL binary search tree. Values can be added and removed. To keep the tree
 * balanced, the balance factor (meaning the difference between left and right subtree of a node) of each node in the
 * tree has to be -1, 0 or 1. If after any interaction with the tree this rule is violated, actions are taken to
 * rebalance the AVL tree.
 *
 * @param <T> type parameter of node values (for example Integer or String)
 * @author Simon Bullik
 * @author Nils Malte Kiele
 */
public class AVLBinarySearchTree<T extends Comparable<T>> extends AbstractBinarySearchTree<T> {
    public AVLBinarySearchTree() {
        this.root = new AVLNode<>();
        this.nodeCount = 0;
    }

    public AVLBinarySearchTree(@NotNull T value) {
        this.root = new AVLNode<>(value);
        this.root.setLeft(new AVLNode<>());
        this.root.setRight(new AVLNode<>());
        this.nodeCount = 1;
    }

    /**
     * @param current node with absolute balance factor of more than 1
     * @param parent  parent node of current; if current is root, parent is a node with value null
     * @return node that takes the place of current after rotation
     */
    private AVLNode<T> rotate(AVLNode<T> current, AVLNode<T> parent) {
        /* The four possible rotations are pictured below. In each case, only three nodes as well as the directions from
         * their parents are considered (L = left, R = right). Note that all relevant nodes (current, its relevant child
         * and its relevant grandchild) might have further subtrees. The rules of handling these are described in detail
         * here: https://www.youtube.com/watch?v=jDM6_TnYIqE.
         *
         * name of rotation        LL        LR        RL        RR         =>    RESULT (balance restored for current
         *                                                                  =>            and all nodes in its subtrees)
         * current                 o         o         o         o          =>      o
         *                        /         /           \         \         =>     / \
         * child                 o         o             o         o        =>    o   o
         *                      /           \           /           \       =>
         * grandchild          o             o         o             o      =>
         */

        // a (first letter) and b (second letter) together form one of the four possible cases (true = L,  false = R)
        boolean a = current.getHeightRight() < current.getHeightLeft();
        AVLNode<T> child = a ? current.getLeft() : current.getRight();
        boolean b = child.getHeightRight() < child.getHeightLeft();
        AVLNode<T> grandchild = b ? child.getLeft() : child.getRight();

        // to avoid unnecessary code redundancy, the first step is to replace current as the local root for its subtree
        AVLNode<T> newLocalRoot = a == b ? child : grandchild;
        if (parent.getValue() == null) {
            this.root = newLocalRoot;
        } else if (current.getValue().compareTo(parent.getValue()) < 0) {
            parent.setLeft(newLocalRoot);
        } else {
            parent.setRight(newLocalRoot);
        }

        if (a && b) {   // LL
            current.setLeft(child.getRight());
            current.setHeightLeft(child.getHeightRight());
            child.setRight(current);
            child.setHeightRight(Math.max(current.getHeightLeft(), current.getHeightRight()) + 1);
            return child;
        } else if (!a && !b) {  // RR
            current.setRight(child.getLeft());
            current.setHeightRight(child.getHeightLeft());
            child.setLeft(current);
            child.setHeightLeft(Math.max(current.getHeightLeft(), current.getHeightRight()) + 1);
            return child;
        } else if (a && !b) {   // LR
            current.setLeft(grandchild.getRight());
            current.setHeightLeft(grandchild.getHeightRight());
            child.setRight(grandchild.getLeft());
            child.setHeightRight(grandchild.getHeightLeft());
            grandchild.setLeft(child);
            grandchild.setHeightLeft(Math.max(child.getHeightLeft(), child.getHeightRight()) + 1);
            grandchild.setRight(current);
            grandchild.setHeightRight(Math.max(current.getHeightLeft(), current.getHeightRight()) + 1);
            return grandchild;
        } else if (!a && b) {   // RL
            current.setRight(grandchild.getLeft());
            current.setHeightRight(grandchild.getHeightLeft());
            child.setLeft(grandchild.getRight());
            child.setHeightLeft(grandchild.getHeightRight());
            grandchild.setLeft(current);
            grandchild.setHeightLeft(Math.max(current.getHeightLeft(), current.getHeightRight()) + 1);
            grandchild.setRight(child);
            grandchild.setHeightRight(Math.max(child.getHeightLeft(), child.getHeightRight()) + 1);
            return grandchild;
        }

        return new AVLNode<>();
    }

    /**
     * @param current   node from which a search for imbalance going up until the root starts
     * @param ancestors stack of nodes starting with the parent of the current node and ending at the root
     */
    private void rebalanceAfterInsertion(AVLNode<T> current, Stack<AVLNode<T>> ancestors) {
        while (true) {
            if (Math.abs(current.getBalanceFactor()) > 1) {
                rotate(current, ancestors.isEmpty() ? new AVLNode<>() : ancestors.pop());
                // imbalance has been corrected and no heights of subtrees above this node will change
                return;
            }

            if (ancestors.isEmpty()) {
                return;
            }

            AVLNode<T> parent = ancestors.pop();
            if (current.getValue().compareTo(parent.getValue()) < 0) {
                parent.increaseHeightLeft();
            } else {
                parent.increaseHeightRight();
            }
            // if the max height of the parent's left and right subtree hasn't changed, all nodes are up to date now
            if (parent.getHeightLeft() == parent.getHeightRight()) {
                return;
            }
            current = parent;
        }
    }

    /**
     * @param value value that should be inserted into the tree
     * @throws BinarySearchTreeException
     */
    @Override
    public void addValue(@NotNull T value) throws BinarySearchTreeException {
        Stack<AVLNode<T>> ancestors = new Stack<>();
        AVLNode<T> current = (AVLNode<T>) this.root;
        while (current.getValue() != null) {
            int c = value.compareTo(current.getValue());
            if (c == 0) {
                throw new BinarySearchTreeException(String.format("Node with value %s already exists", value));
            }
            // update ancestors and current node
            ancestors.push(current);
            current = c < 0 ? current.getLeft() : current.getRight();
        }
        // insert the value into current (which is a leaf node) and create two new terminal nodes
        current.setValue(value);
        current.setLeft(new AVLNode<>());
        current.setRight(new AVLNode<>());
        // rebalancing of tree might be necessary to be a correct AVL tree
        rebalanceAfterInsertion(current, ancestors);
        // insertion of value was successful; update nodeCount
        ++this.nodeCount;
    }

    /**
     * @param current   node from which a search for imbalance going up until the root starts
     * @param value     value of current (in case it's a terminal node now, the value it had before is still important)
     * @param ancestors stack of nodes starting with the parent of the current node and ending at the root
     */
    private void rebalanceAfterDeletion(AVLNode<T> current, T value, Stack<AVLNode<T>> ancestors) {
        while (!ancestors.isEmpty()) {
            AVLNode<T> parent = ancestors.pop();
            int heightBefore = Math.max(parent.getHeightLeft(), parent.getHeightRight());
            if ((current.getValue() == null ? value : current.getValue()).compareTo(parent.getValue()) < 0) {
                parent.decreaseHeightLeft();
            } else {
                parent.decreaseHeightRight();
            }
            current = parent;

            if (Math.abs(current.getBalanceFactor()) > 1) {
                // current is a different node after rotation; max height of new current = max height of old current - 1
                current = rotate(current, ancestors.isEmpty() ? new AVLNode<>() : ancestors.peek());
            }

            int heightAfter = Math.max(current.getHeightLeft(), current.getHeightRight());
            if (heightBefore == heightAfter) {
                // no need to update any nodes above current because there are not affected anymore
                return;
            }
        }
    }

    /**
     * @param value value that should be deleted from the tree
     * @throws BinarySearchTreeException
     */
    @Override
    public void delValue(@NotNull T value) throws BinarySearchTreeException {
        Stack<AVLNode<T>> ancestors = new Stack<>();
        AVLNode<T> current = (AVLNode<T>) this.root;
        while (current.getValue() != null) {
            int c = value.compareTo(current.getValue());
            if (c == 0) {
                boolean currentHasLeftValue = current.getLeft().getValue() != null;
                boolean currentHasRightValue = current.getRight().getValue() != null;

                if (currentHasLeftValue && currentHasRightValue) {
                    ancestors.push(current);
                    AVLNode<T> nodeWithLargestValueInLeftSubtree = current.getLeft();
                    while (nodeWithLargestValueInLeftSubtree.getRight().getValue() != null) {
                        ancestors.push(nodeWithLargestValueInLeftSubtree);
                        nodeWithLargestValueInLeftSubtree = nodeWithLargestValueInLeftSubtree.getRight();
                    }
                    T valueOfDeletedNode = nodeWithLargestValueInLeftSubtree.getValue();

                    if (ancestors.peek().getValue().compareTo(current.getValue()) == 0) {
                        ancestors.peek().setLeft(nodeWithLargestValueInLeftSubtree.getLeft());
                        rebalanceAfterDeletion(ancestors.peek().getLeft(), valueOfDeletedNode, ancestors);
                    } else {
                        ancestors.peek().setRight(nodeWithLargestValueInLeftSubtree.getLeft());
                        rebalanceAfterDeletion(ancestors.peek().getRight(), valueOfDeletedNode, ancestors);
                    }
                    current.setValue(valueOfDeletedNode);
                } else if (currentHasLeftValue && currentHasRightValue) {
                    if (ancestors.isEmpty()) {
                        this.root = new AVLNode<>();
                    } else if (current.getValue().compareTo(ancestors.peek().getValue()) < 0) {
                        ancestors.peek().setLeft(new AVLNode<>());
                        rebalanceAfterDeletion(ancestors.peek().getLeft(), value, ancestors);
                    } else {
                        ancestors.peek().setRight(new AVLNode<>());
                        rebalanceAfterDeletion(ancestors.peek().getRight(), value, ancestors);
                    }
                } else {
                    AVLNode<T> child = currentHasLeftValue ? current.getLeft() : current.getRight();
                    if (ancestors.isEmpty()) {
                        this.root = child;
                    } else if (current.getValue().compareTo(ancestors.peek().getValue()) < 0) {
                        ancestors.peek().setLeft(child);
                    } else {
                        ancestors.peek().setRight(child);
                    }
                    rebalanceAfterDeletion(child, value, ancestors);
                }

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
