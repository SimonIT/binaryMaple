package de.szut.simNil.binaryMaple;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * This abstract class implements the interface for a binary search tree and contains the logic all binary search trees
 * have in common. This includes the ability of all nodes being traversed in the orders specified in enum Order.
 *
 * @param <T> type parameter of node values (for example Integer or String)
 * @author Simon Bullik
 * @author Nils Malte Kiele
 */
public abstract class AbstractBinarySearchTree<T extends Comparable<T>> implements InterfaceBinarySearchTree<T> {
    /**
     * root node of tree
     */
    protected BNode<T> root;
    /**
     * variable to hold the amount of nodes
     */
    protected int nodeCount;

    public AbstractBinarySearchTree() {
        this.root = new BNode<>();
        this.nodeCount = 0;
    }

    public AbstractBinarySearchTree(@NotNull T value) {
        this.root = new BNode<>(value);
        this.root.setLeft(new BNode<>());
        this.root.setRight(new BNode<>());
        this.nodeCount = 1;
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
    public AbstractNode<T> getNodeWithValue(@NotNull T value) {
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
        // "- 1" compensates for terminal nodes
        return depth - 1;
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
