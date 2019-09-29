package de.szut.simNil.binaryMaple.avl;

import de.szut.simNil.binaryMaple.BinarySearchTreeException;
import de.szut.simNil.binaryMaple.InterfaceBinarySearchTree;
import de.szut.simNil.binaryMaple.Order;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class AVLBinarySearchTree<T extends Comparable<T>> implements InterfaceBinarySearchTree<T> {
    private AVLNode<T> root;
    private Integer nodeCount;

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

    private void rebalanceInsertion(AVLNode<T> current, T newValue, Stack<AVLNode<T>> ancestors) {
        boolean heightChanges = true;   // TODO: get better name
        while (heightChanges) {
            if (Math.abs(current.getHeightRight() - current.getHeightLeft()) > 1) {
                // ab can be LL, RR, LR or RL (L = left, R = right) -> path from unbalanced node
                boolean a = newValue.compareTo(current.getValue()) < 0;
                AVLNode<T> child = a ? current.getLeft() : current.getRight();
                boolean b = newValue.compareTo(child.getValue()) < 0;
                AVLNode<T> grandchild = b ? child.getLeft() : child.getRight();

                if (a && b) {   // LL
                    System.out.println("LL");
                    if (ancestors.isEmpty()) {
                        this.root = child;
                    } else if (current.getValue().compareTo(ancestors.peek().getValue()) < 0) {
                        ancestors.peek().setLeft(child);
                    } else {
                        ancestors.peek().setRight(child);
                    }
                    current.setLeft(child.getRight());
                    current.setHeightLeft(child.getHeightRight());
                    child.setRight(current);
                    child.setHeightRight(Math.max(current.getHeightLeft(), current.getHeightRight()) + 1);
                } else if (!a && !b) {  // RR
                    System.out.println("RR");
                    if (ancestors.isEmpty()) {
                        this.root = child;
                    } else if (current.getValue().compareTo(ancestors.peek().getValue()) < 0) {
                        ancestors.peek().setLeft(child);
                    } else {
                        ancestors.peek().setRight(child);
                    }
                    current.setRight(child.getLeft());
                    current.setHeightRight(child.getHeightLeft());
                    child.setLeft(current);
                    child.setHeightLeft(Math.max(current.getHeightLeft(), current.getHeightRight()) + 1);
                } else if (a && !b) {   // LR
                    System.out.println("LR");
                    if (ancestors.isEmpty()) {
                        this.root = grandchild;
                    } else if (current.getValue().compareTo(ancestors.peek().getValue()) < 0) {
                        ancestors.peek().setLeft(grandchild);
                    } else {
                        ancestors.peek().setRight(grandchild);
                    }
                    current.setLeft(grandchild.getRight());
                    current.setHeightLeft(grandchild.getHeightRight());
                    child.setRight(grandchild.getLeft());
                    child.setHeightRight(grandchild.getHeightLeft());
                    grandchild.setLeft(child);
                    grandchild.setRight(current);
                    grandchild.setHeightLeft(Math.max(child.getHeightLeft(), child.getHeightRight()) + 1);
                    grandchild.setHeightRight(Math.max(current.getHeightLeft(), current.getHeightRight()) + 1);
                } else if (!a && b) {   // RL
                    System.out.println("RL");
                    if (ancestors.isEmpty()) {
                        this.root = grandchild;
                    } else if (current.getValue().compareTo(ancestors.peek().getValue()) < 0) {
                        ancestors.peek().setLeft(grandchild);
                    } else {
                        ancestors.peek().setRight(grandchild);
                    }
                    current.setRight(grandchild.getLeft());
                    current.setHeightRight(grandchild.getHeightLeft());
                    child.setLeft(grandchild.getRight());
                    child.setHeightLeft(grandchild.getHeightRight());
                    grandchild.setRight(child);
                    grandchild.setLeft(current);
                    grandchild.setHeightLeft(Math.max(current.getHeightLeft(), current.getHeightRight()) + 1);
                    grandchild.setHeightRight(Math.max(child.getHeightLeft(), child.getHeightRight()) + 1);
                }
                break;
            }

            if (ancestors.isEmpty()) {
                break;
            }
            AVLNode<T> parent = ancestors.pop();
            boolean isLeftChild = current.getValue().compareTo(parent.getValue()) < 0;
            current = parent;
            if (isLeftChild) {
                current.increaseHeightLeft();
                if (current.getHeightRight() >= current.getHeightLeft()) {
                    heightChanges = false;
                }
            } else {
                current.increaseHeightRight();
                if (current.getHeightLeft() >= current.getHeightRight()) {
                    heightChanges = false;
                }
            }
        }
    }

    @Override
    public void addValue(@NotNull T value) throws BinarySearchTreeException {
        Stack<AVLNode<T>> ancestors = new Stack<>();
        AVLNode<T> current = this.root;
        while (current.getValue() != null) {
            int c = value.compareTo(current.getValue());
            if (c == 0) {
                throw new BinarySearchTreeException(String.format("Node with value %s already exists", value));
            }
            ancestors.push(current);
            current = c < 0 ? current.getLeft() : current.getRight();
        }
        current.setValue(value);
        current.setLeft(new AVLNode<>());
        current.setRight(new AVLNode<>());
        rebalanceInsertion(current, value, ancestors);
        ++this.nodeCount;
    }

    @Override
    public void delValue(@NotNull T value) throws BinarySearchTreeException {
        // TODO: delete AVL node
    }

    @Override
    public boolean hasValue(@NotNull T value) {
        return getNodeWithValue(value) != null;
    }

    @Override
    @Nullable
    public AVLNode<T> getNodeWithValue(@NotNull T value) {
        AVLNode<T> current = this.root;
        while (current.getValue() != null) {
            int c = value.compareTo(current.getValue());
            if (c == 0) {
                return current;
            }
            current = c < 0 ? current.getLeft() : current.getRight();
        }
        return null;
    }

    @Override
    public Integer getDepth() {
        Integer depth = -1;
        Queue<AVLNode<T>> q = new LinkedList<>();
        q.add(this.root);
        while (!q.isEmpty()) {
            ++depth;
            int levelSize = q.size();
            while (levelSize-- > 0) {
                AVLNode<T> node = q.poll();
                if (node.getValue() != null) {
                    q.add(node.getLeft());
                    q.add(node.getRight());
                }
            }
        }
        return depth;
    }

    @Override
    public List<T> traverse(Order order) {
        List<T> result = new ArrayList<>();
        if (order == Order.PREORDER) {
            Stack<AVLNode<T>> s = new Stack<>();
            s.push(this.root);
            while (!s.isEmpty()) {
                AVLNode<T> p = s.pop();
                if (p.getValue() != null) {
                    result.add(p.getValue());
                    s.push(p.getRight());
                    s.push(p.getLeft());
                }
            }
        } else if (order == Order.INORDER) {
            Stack<AVLNode<T>> s = new Stack<>();
            AVLNode<T> node = this.root;
            while (true) {
                while (node.getValue() != null) {
                    s.push(node);
                    node = node.getLeft();
                }
                if (s.isEmpty()) {
                    break;
                }
                AVLNode<T> p = s.pop();
                result.add(p.getValue());
                node = p.getRight();
            }
        } else if (order == Order.POSTORDER) {
            Stack<AVLNode<T>> s = new Stack<>(), s2 = new Stack<>();
            s.push(this.root);
            while (!s.isEmpty()) {
                AVLNode<T> p = s.pop();
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
            Queue<AVLNode<T>> q = new LinkedList<>();
            q.add(root);
            while (!q.isEmpty()) {
                AVLNode<T> p = q.poll();
                if (p.getValue() != null) {
                    result.add(p.getValue());
                    q.add(p.getLeft());
                    q.add(p.getRight());
                }
            }
        }
        return result;
    }

    @Override
    public AVLNode<T> getRoot() {
        return this.root;
    }

    public int getNodeCount() {
        return this.nodeCount;
    }
}
