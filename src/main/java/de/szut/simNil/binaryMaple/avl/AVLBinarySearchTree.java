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


    private AVLNode<T> rotate(AVLNode<T> current, AVLNode<T> parent) {
        // ab can be LL, RR, LR or RL (L = left, R = right) -> path from unbalanced node (L = true | R = false)
        boolean a = current.getHeightRight() < current.getHeightLeft();
        AVLNode<T> child = a ? current.getLeft() : current.getRight();
        boolean b = child.getHeightRight() < child.getHeightLeft();
        AVLNode<T> grandchild = b ? child.getLeft() : child.getRight();

        if (a && b) {   // LL
            System.out.println("LL");
            if (parent.getValue() == null) {
                this.root = child;
            } else if (current.getValue().compareTo(parent.getValue()) < 0) {
                parent.setLeft(child);
            } else {
                parent.setRight(child);
            }
            current.setLeft(child.getRight());
            current.setHeightLeft(child.getHeightRight());
            child.setRight(current);
            child.setHeightRight(Math.max(current.getHeightLeft(), current.getHeightRight()) + 1);
            // parent.setHeightLeft(Math.max(child.getHeightLeft(), child.getHeightRight()) + 1);
            return child;
        } else if (!a && !b) {  // RR
            System.out.println("RR");
            if (parent.getValue() == null) {
                this.root = child;
            } else if (current.getValue().compareTo(parent.getValue()) < 0) {
                parent.setLeft(child);
            } else {
                parent.setRight(child);
            }
            current.setRight(child.getLeft());
            current.setHeightRight(child.getHeightLeft());
            child.setLeft(current);
            child.setHeightLeft(Math.max(current.getHeightLeft(), current.getHeightRight()) + 1);
            // parent.setHeightRight(Math.max(child.getHeightLeft(), child.getHeightRight()) + 1);
            return child;
        } else if (a && !b) {   // LR
            System.out.println("LR");
            if (parent.getValue() == null) {
                this.root = grandchild;
            } else if (current.getValue().compareTo(parent.getValue()) < 0) {
                parent.setLeft(grandchild);
            } else {
                parent.setRight(grandchild);
            }
            current.setLeft(grandchild.getRight());
            current.setHeightLeft(grandchild.getHeightRight());
            child.setRight(grandchild.getLeft());
            child.setHeightRight(grandchild.getHeightLeft());
            grandchild.setLeft(child);
            grandchild.setRight(current);
            grandchild.setHeightLeft(Math.max(child.getHeightLeft(), child.getHeightRight()) + 1);
            grandchild.setHeightRight(Math.max(current.getHeightLeft(), current.getHeightRight()) + 1);
            // parent.setHeightLeft(Math.max(grandchild.getHeightLeft(), grandchild.getHeightRight()) + 1);
            return grandchild;
        } else if (!a && b) {   // RL
            System.out.println("RL");
            if (parent.getValue() == null) {
                this.root = grandchild;
            } else if (current.getValue().compareTo(parent.getValue()) < 0) {
                parent.setLeft(grandchild);
            } else {
                parent.setRight(grandchild);
            }
            current.setRight(grandchild.getLeft());
            current.setHeightRight(grandchild.getHeightLeft());
            child.setLeft(grandchild.getRight());
            child.setHeightLeft(grandchild.getHeightRight());
            grandchild.setRight(child);
            grandchild.setLeft(current);
            grandchild.setHeightLeft(Math.max(current.getHeightLeft(), current.getHeightRight()) + 1);
            grandchild.setHeightRight(Math.max(child.getHeightLeft(), child.getHeightRight()) + 1);
            // parent.setHeightLeft(Math.max(grandchild.getHeightLeft(), grandchild.getHeightRight()) + 1);
            return grandchild;
        }

        return new AVLNode<>();
    }


    private void rebalanceInsertion(AVLNode<T> current, T value, Stack<AVLNode<T>> ancestors) {
        while (true) {
            if (Math.abs(current.getBalanceFactor()) > 1) {
                rotate(current, ancestors.isEmpty() ? new AVLNode<>() : ancestors.pop());
                return;
            }

            if (ancestors.isEmpty()) {
                return;
            }
            AVLNode<T> parent = ancestors.pop();
            boolean isLeftChild = current.getValue().compareTo(parent.getValue()) < 0;
            current = parent;

            if (isLeftChild) {
                current.increaseHeightLeft();
                if (current.getHeightRight() >= current.getHeightLeft()) {
                    return;
                }
            } else {
                current.increaseHeightRight();
                if (current.getHeightLeft() >= current.getHeightRight()) {
                    return;
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


    private void rebalanceDeletion(AVLNode<T> current, T value, Stack<AVLNode<T>> ancestors) {
        System.out.println();
        boolean heightChanges = true;   // TODO: get better name
        while (heightChanges) {
            if (ancestors.isEmpty()) {
                return;
            }
            AVLNode<T> parent = ancestors.pop();
            boolean isLeftChild = (current.getValue() == null ? value : current.getValue()).compareTo(parent.getValue()) < 0;
            current = parent;
            if (isLeftChild) {
                current.decreaseHeightLeft();
                if (current.getHeightRight() > current.getHeightLeft()) {
                    heightChanges = false;
                }
            } else {
                current.decreaseHeightRight();
                if (current.getHeightLeft() > current.getHeightRight()) {
                    heightChanges = false;
                }
            }

            System.out.println(current.getValue());

            if (Math.abs(current.getBalanceFactor()) > 1) {
                Stack<AVLNode<T>> ancestorsCopy = (Stack<AVLNode<T>>) ancestors.clone();
                T childValue = current.getValue();
                Queue<Integer> relevantHeightsBefore = new LinkedList<>();
                while (!ancestorsCopy.isEmpty()) {
                    AVLNode<T> parents2 = ancestorsCopy.pop();  // TODO: terrible name...
                    int height = childValue.compareTo(parents2.getValue()) < 0 ? parents2.getHeightLeft() : parents2.getHeightRight();
                    relevantHeightsBefore.add(height);
                    childValue = parents2.getValue();
                }
                current = rotate(current, ancestors.isEmpty() ? new AVLNode<>() : ancestors.peek());
                while (!ancestors.isEmpty()) {
                    System.out.print("current: ");
                    System.out.println(current.getValue());
                    parent = ancestors.pop();
                    int xx = relevantHeightsBefore.poll();
                    System.out.print("=> ");
                    System.out.print(xx);
                    System.out.print(" | ");
                    System.out.println(Math.max(current.getHeightLeft(), current.getHeightRight()) + 1);
                    if (Math.max(current.getHeightLeft(), current.getHeightRight()) + 1 == xx) {return;}

                    if (current.getValue().compareTo(parent.getValue()) < 0) {
                        parent.decreaseHeightLeft();
                        if (parent.getHeightRight() > parent.getHeightLeft()) {return;}
                    } else {
                        parent.decreaseHeightRight();
                        if (parent.getHeightLeft() > parent.getHeightRight()) {return;}
                    }
                    current = parent;
                }
                return;
            }
        }
    }

    @Override
    public void delValue(@NotNull T value) throws BinarySearchTreeException {
        traverse(Order.PREORDER);
        Stack<AVLNode<T>> ancestors = new Stack<>();
        AVLNode<T> current = this.root;
        while (current.getValue() != null) {
            int c = value.compareTo(current.getValue());
            if (c == 0) {
                boolean hasLeftValue = current.getLeft().getValue() != null;
                boolean hasRightValue = current.getRight().getValue() != null;

                if (!hasLeftValue && !hasRightValue) {
                    if (ancestors.isEmpty()) {
                        this.root = new AVLNode<>();
                    } else if (current.getValue().compareTo(ancestors.peek().getValue()) < 0) {
                        ancestors.peek().setLeft(new AVLNode<>());
                        rebalanceDeletion(ancestors.peek().getLeft(), value, ancestors);
                    } else {
                        ancestors.peek().setRight(new AVLNode<>());
                        rebalanceDeletion(ancestors.peek().getRight(), value, ancestors);
                    }
                } else if (hasLeftValue && hasRightValue) {
                    ancestors.push(current);
                    AVLNode<T> nodeWithLargestValueInLeftSubtree = current.getLeft();
                    while (nodeWithLargestValueInLeftSubtree.getRight().getValue() != null) {
                        ancestors.push(nodeWithLargestValueInLeftSubtree);
                        nodeWithLargestValueInLeftSubtree = nodeWithLargestValueInLeftSubtree.getRight();
                    }

                    T valueOfDeletedNode = nodeWithLargestValueInLeftSubtree.getValue();

                    if (ancestors.peek().getValue() == current.getValue()) {
                        ancestors.peek().setLeft(nodeWithLargestValueInLeftSubtree.getLeft());
                        rebalanceDeletion(ancestors.peek().getLeft(), valueOfDeletedNode,  ancestors);
                    } else {
                        ancestors.peek().setRight(nodeWithLargestValueInLeftSubtree.getLeft());
                        rebalanceDeletion(ancestors.peek().getRight(), valueOfDeletedNode, ancestors);
                    }
                    current.setValue(valueOfDeletedNode);
                } else {
                    AVLNode<T> child = hasLeftValue ? current.getLeft() : current.getRight();
                    if (ancestors.isEmpty()) {
                        this.root = child;
                    } else if (current.getValue().compareTo(ancestors.peek().getValue()) < 0) {
                        ancestors.peek().setLeft(child);
                    } else {
                        ancestors.peek().setRight(child);
                    }
                    rebalanceDeletion(child, value, ancestors);
                }

                List<T> l = this.traverse(Order.PREORDER);
                for (T i : l) {
                    AVLNode<T> x = this.getNodeWithValue(i);
                    if (x.getHeightLeft() != this.getDepth2(x.getLeft())) {
                        System.out.println("ALARM LEFT");
                        System.out.println(i);
                        System.out.println(x.getHeightLeft());
                        System.out.println(this.getDepth2(x.getLeft()));
                    }
                    if (x.getHeightRight() != this.getDepth2(x.getRight())) {
                        System.out.println("ALARM RIGHT");
                        System.out.println(i);
                        System.out.println(x.getHeightRight());
                        System.out.println(this.getDepth2(x.getRight()));
                    }
                }

                --this.nodeCount;
                return;
            } else {
                ancestors.push(current);
                current = c < 0 ? current.getLeft() : current.getRight();
            }
        }
        throw new BinarySearchTreeException(String.format("Node with value %s cannot be deleted because it does not exist", value));

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
    public int getDepth() {
        int depth = 0;
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
        return depth - 1;   // "- 1" compensates for terminal nodes
    }

    public Integer getDepth2(AVLNode<T> x) {
        Integer depth = -1;
        Queue<AVLNode<T>> q = new LinkedList<>();
        q.add(x);
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
