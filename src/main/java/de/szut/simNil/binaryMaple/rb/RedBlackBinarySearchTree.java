package de.szut.simNil.binaryMaple.rb;

import de.szut.simNil.binaryMaple.*;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class RedBlackBinarySearchTree<T extends Comparable<T>> implements InterfaceBinarySearchTree<T> {
    private RBNode<T> root;
    private Integer nodeCount;

    public RedBlackBinarySearchTree() {
        this.root = new RBNode<>();
        nodeCount = 0;
    }

    public RedBlackBinarySearchTree(T value) {
        // TODO: what if value is null?
        this.root = new RBNode<>(value);
        this.root.setLeft(new RBNode<>());
        this.root.setRight(new RBNode<>());
        nodeCount = 1;
    }

    private void rebalanceInsertion(RBNode<T> current, Stack<RBNode<T>> ancestors) {
        // E0
        if (ancestors.isEmpty()) {
            current.setColor(RBNode.Color.BLACK);
            System.out.println("CASE E0");
            return;
        }

        RBNode<T> parent = ancestors.pop();

        // E1
        if (parent.getColor() == RBNode.Color.BLACK) {
            System.out.println("CASE E1");
            return;
        }

        RBNode<T> grandparent = ancestors.pop();

        boolean currentIsLeftChild = current.getValue().compareTo(parent.getValue()) < 0;
        boolean parentIsLeftChild = parent.getValue().compareTo(grandparent.getValue()) < 0;
        RBNode<T> pibling = parentIsLeftChild ? grandparent.getRight() : grandparent.getLeft();

        if (pibling.getColor() == RBNode.Color.BLACK) {
            if (currentIsLeftChild != parentIsLeftChild) {
                // E3
                System.out.println("CASE E3");
                if (parentIsLeftChild) {
                    grandparent.setLeft(current);
                    parent.setRight(current.getLeft());
                    current.setLeft(parent);
                } else {
                    grandparent.setRight(current);
                    parent.setLeft(current.getRight());
                    current.setRight(parent);
                }
                RBNode temp = current;
                current = parent;
                parent = temp;
            }

            // E4
            System.out.println("CASE E4");
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
            // E5
            System.out.println("CASE E5");
            parent.setColor(RBNode.Color.BLACK);
            pibling.setColor(RBNode.Color.BLACK);
            grandparent.setColor(RBNode.Color.RED);
            rebalanceInsertion(grandparent, ancestors);
        }
    }

    @Override
    public void addValue(T value) throws BinarySearchTreeException {
        ++nodeCount;
        Stack<RBNode<T>> ancestors = new Stack<>();
        RBNode<T> current = this.root;
        while (current.getValue() != null) {
            int c = value.compareTo(current.getValue());
            if (c == 0) {
                throw new BinarySearchTreeException(String.format("Node with value %s already exists", value));
            }
            ancestors.push(current);
            current = c < 0 ? current.getLeft() : current.getRight();
        }
        current.setColor(RBNode.Color.RED);
        current.setValue(value);
        current.setLeft(new RBNode<>());
        current.setRight(new RBNode<>());
        rebalanceInsertion(current, ancestors);
    }

    @Override
    public void delValue(T value) throws BinarySearchTreeException {
        // TODO: make method shorter & less redundant
        --nodeCount;
        RBNode<T> current = this.root;
        RBNode<T> parent = new RBNode<>();
        while (current.getValue() != null) {
            int c = value.compareTo(current.getValue());
            if (c == 0) {
                boolean leftChildExists = current.getLeft().getValue() != null;
                boolean rightChildExists = current.getRight().getValue() != null;
                boolean currentIsLeftChild = parent.getValue() == null || current.getValue().compareTo(parent.getValue()) < 0;
                if (!leftChildExists && !rightChildExists) {
                    if (parent.getValue() == null) {
                        this.root = new RBNode<>();
                    } else {
                        if (currentIsLeftChild) {
                            parent.setLeft(new RBNode<>());
                        } else {
                            parent.setRight(new RBNode<>());
                        }
                    }
                } else if (leftChildExists && rightChildExists) {
                    parent = current;
                    RBNode<T> smallestInRightTree = current.getRight();
                    while (smallestInRightTree.getLeft().getValue() != null) {
                        parent = smallestInRightTree;
                        smallestInRightTree = smallestInRightTree.getLeft();
                    }
                    current.setValue(smallestInRightTree.getValue());
                    if (parent.getValue() == current.getValue()) {
                        current.setRight(smallestInRightTree.getRight());
                    } else {
                        if (smallestInRightTree.getValue().compareTo(parent.getValue()) < 0) {
                            parent.setLeft(new RBNode<>());
                        } else {
                            parent.setRight(new RBNode<>());
                        }
                    }
                } else {
                    RBNode<T> b = leftChildExists ? current.getLeft() : current.getRight();
                    if (parent.getValue() == null) {
                        this.root = b;
                    } else {
                        if (currentIsLeftChild) {
                            parent.setLeft(b);
                        } else {
                            parent.setRight(b);
                        }
                    }
                }
                return;
            } else {
                parent = current;
                current = c < 0 ? current.getLeft() : current.getRight();
            }
        }
        throw new BinarySearchTreeException(String.format("Node with value %s cannot be deleted because it does not exist", value));
    }

    @Override
    public boolean hasValue(T value) {
        RBNode<T> current = this.root;
        while (current.getValue() != null) {
            int c = value.compareTo(current.getValue());
            if (c == 0) {
                return true;
            }
            current = c < 0 ? current.getLeft() : current.getRight();
        }
        return false;
    }

    @Override
    @Nullable
    public RBNode<T> getNodeWithValue(T value) {
        RBNode<T> current = this.root;
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
        Queue<RBNode<T>> q = new LinkedList<>();
        q.add(this.root);
        while (!q.isEmpty()) {
            ++depth;
            int levelSize = q.size();
            while (levelSize-- > 0) {
                RBNode<T> node = q.poll();
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
            Stack<RBNode<T>> s = new Stack<>();
            s.push(this.root);
            while (!s.isEmpty()) {
                RBNode<T> p = s.pop();
                if (p.getValue() != null) {
                    result.add(p.getValue());
                    s.push(p.getRight());
                    s.push(p.getLeft());
                }
            }
        } else if (order == Order.INORDER) {
            Stack<RBNode<T>> s = new Stack<>();
            RBNode<T> node = this.root;
            while (true) {
                while (node.getValue() != null) {
                    s.push(node);
                    node = node.getLeft();
                }
                if (s.isEmpty()) {
                    break;
                }
                RBNode<T> p = s.pop();
                result.add(p.getValue());
                node = p.getRight();
            }
        } else if (order == Order.POSTORDER) {
            Stack<RBNode<T>> s = new Stack<>(), s2 = new Stack<>();
            s.push(this.root);
            while (!s.isEmpty()) {
                RBNode<T> p = s.pop();
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
            Queue<RBNode<T>> q = new LinkedList<>();
            q.add(root);
            while (!q.isEmpty()) {
                RBNode<T> p = q.poll();
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
    public AbstractNode<T> getRoot() {
        return this.root;
    }

    public Integer getNodeCount() {
        return this.nodeCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RedBlackBinarySearchTree) {
            return this.getRoot().equals(((RedBlackBinarySearchTree) obj).getRoot());
        }
        return false;
    }
}
