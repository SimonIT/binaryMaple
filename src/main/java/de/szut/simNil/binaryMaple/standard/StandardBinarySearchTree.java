package de.szut.simNil.binaryMaple.standard;

import de.szut.simNil.binaryMaple.*;

import java.util.*;

public class StandardBinarySearchTree<T extends Comparable<T>> implements InterfaceBinarySearchTree<T> {
    private BNode<T> root;
    private Integer nodeCount;

    public StandardBinarySearchTree() {
        this.root = new BNode<>();
        nodeCount = 0;
    }

    public StandardBinarySearchTree(T value) {
        // TODO: what if value is null?
        this.root = new BNode<>(value);
        this.root.setLeft(new BNode<>());
        this.root.setRight(new BNode<>());
        nodeCount = 1;
    }

    @Override
    public void addValue(T value) throws BinarySearchTreeException {
        ++nodeCount;
        BNode<T> current = this.root;
        while (current.getValue() != null) {
            int c = value.compareTo(current.getValue());
            if (c == 0) {
                throw new BinarySearchTreeException(String.format("Node with value %s already exists", value));
            }
            current = c < 0 ? current.getLeft() : current.getRight();
        }
        current.setValue(value);
        current.setLeft(new BNode<>());
        current.setRight(new BNode<>());
    }

    @Override
    public void delValue(T value) throws BinarySearchTreeException {
        // TODO: make method shorter & less redundant
        --nodeCount;
        BNode<T> current = this.root;
        BNode<T> parent = new BNode<>();
        while (current.getValue() != null) {
            int c = value.compareTo(current.getValue());
            if (c == 0) {
                boolean leftChildExists = current.getLeft().getValue() != null;
                boolean rightChildExists = current.getRight().getValue() != null;
                boolean currentIsLeftChild = parent.getValue() == null ? true : current.getValue().compareTo(parent.getValue()) < 0;
                if (!leftChildExists && !rightChildExists) {
                    if (parent.getValue() == null) {
                        this.root = new BNode<>();
                    } else {
                        if (currentIsLeftChild) {
                            parent.setLeft(new BNode<>());
                        } else {
                            parent.setRight(new BNode<>());
                        }
                    }
                } else if (leftChildExists && rightChildExists) {
                    parent = current;
                    BNode<T> smallestInRightTree = current.getRight();
                    while (smallestInRightTree.getLeft().getValue() != null) {
                        parent = smallestInRightTree;
                        smallestInRightTree = smallestInRightTree.getLeft();
                    }
                    current.setValue(smallestInRightTree.getValue());
                    if (parent.getValue() == current.getValue()) {
                        current.setRight(smallestInRightTree.getRight());
                    } else {
                        if (smallestInRightTree.getValue().compareTo(parent.getValue()) < 0) {
                            parent.setLeft(new BNode<>());
                        } else {
                            parent.setRight(new BNode<>());
                        }
                    }
                } else {
                    BNode<T> b = leftChildExists ? current.getLeft() : current.getRight();
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
        BNode<T> current = this.root;
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
    public Integer getDepth() {
        Integer depth = -1;
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
        return depth;
    }

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

    @Override
    public AbstractNode<T> getRoot() {
        return this.root;
    }

    public Integer getNodeCount() {
        return this.nodeCount;
    }
}