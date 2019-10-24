package de.szut.simNil.binaryMaple.standard;

import de.szut.simNil.binaryMaple.BNode;
import de.szut.simNil.binaryMaple.BinarySearchTreeException;
import de.szut.simNil.binaryMaple.InterfaceBinarySearchTree;
import de.szut.simNil.binaryMaple.Order;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * This class contains the logic for a simple binary search tree. Values can be added and removed and the existence of
 * values in the tree can be checked. The nodes of the tree can be traversed in the orders specified in enum Order. No
 * rebalancing measures are taken after insertions or deletions so the shape of the tree depends much on the insertion
 * order. In the worst case, this tree can decay into a list.
 *
 * @param <T> type parameter of node values (for example Integer or String)
 * @author Simon Bullik
 * @author Nils Malte Kiele
 */
public class StandardBinarySearchTree<T extends Comparable<T>> implements InterfaceBinarySearchTree<T> {
    /**
     * root node of tree
     */
    private BNode<T> root;
    /**
     * variable to hold the amount of nodes
     */
    private int nodeCount;

    public StandardBinarySearchTree() {
        this.root = new BNode<>();
        this.nodeCount = 0;
    }

    public StandardBinarySearchTree(@NotNull T value) {
        this.root = new BNode<>(value);
        this.root.setLeft(new BNode<>());
        this.root.setRight(new BNode<>());
        this.nodeCount = 1;
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
        System.out.println(this.getDepth());
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
                // node with value that should be deleted is found
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

    /**
     * @param value value of node in question
     * @return true if value exists in tree, false otherwise
     */
    @Override
    public boolean hasValue(@NotNull T value) {
        return getNodeWithValue(value) != null;
    }

    /**
     * @param value of node that should be returned
     * @return node with specified value if it exists or Null otherwise
     */
    @Override
    @Nullable
    public BNode<T> getNodeWithValue(@NotNull T value) {
        BNode<T> current = this.root;
        while (current.getValue() != null) {
            int c = value.compareTo(current.getValue());
            if (c == 0) {
                return current;
            }
            // update current node
            current = c < 0 ? current.getLeft() : current.getRight();
        }
        return null;
    }

    /**
     * @return depth (number of levels) of tree
     */
    @Override
    public int getDepth() {
        int depth = 0;
        Queue<BNode<T>> q = new LinkedList<>();
        q.add(this.root);
        while (!q.isEmpty()) {
            ++depth;
            int levelSize = q.size();
            while (levelSize-- > 0) {
                BNode<T> node = q.poll();
                if (node.getValue() != null) {
                    q.add(node.getLeft());
                    q.add(node.getRight());
                }
            }
        }
        return depth - 1;   // "- 1" compensates for terminal nodes
    }

    /**
     * @param order element of Enum order
     * @return list of all nodes in the tree in specified order
     */
    @Override
    public List<T> traverse(Order order) {
        List<T> result = new ArrayList<>();
        if (order == Order.PREORDER) {
            Stack<BNode<T>> s = new Stack<>();
            s.push(this.root);
            while (!s.isEmpty()) {
                BNode<T> p = s.pop();
                if (p.getValue() != null) {
                    result.add(p.getValue());
                    s.push(p.getRight());
                    s.push(p.getLeft());
                }
            }
        } else if (order == Order.INORDER) {
            Stack<BNode<T>> s = new Stack<>();
            BNode<T> node = this.root;
            while (true) {
                while (node.getValue() != null) {
                    s.push(node);
                    node = node.getLeft();
                }
                if (s.isEmpty()) {
                    break;
                }
                BNode<T> p = s.pop();
                result.add(p.getValue());
                node = p.getRight();
            }
        } else if (order == Order.POSTORDER) {
            Stack<BNode<T>> s = new Stack<>(), s2 = new Stack<>();
            s.push(this.root);
            while (!s.isEmpty()) {
                BNode<T> p = s.pop();
                if (p.getValue() != null) {
                    s2.push(p);
                    s.push(p.getLeft());
                    s.push(p.getRight());
                }
            }
            while (!s2.isEmpty()) {
                result.add(s2.pop().getValue());
            }
        } else if (order == Order.LEVELORDER) {
            Queue<BNode<T>> q = new LinkedList<>();
            q.add(root);
            while (!q.isEmpty()) {
                BNode<T> p = q.poll();
                if (p.getValue() != null) {
                    result.add(p.getValue());
                    q.add(p.getLeft());
                    q.add(p.getRight());
                }
            }
        }
        return result;
    }

    /**
     * @return root of the tree or Null if the tree is empty
     */
    @Override
    public BNode<T> getRoot() {
        return this.root;
    }

    /**
     * @return amount of nodes in tree
     */
    @Override
    public int getNodeCount() {
        return this.nodeCount;
    }
}
