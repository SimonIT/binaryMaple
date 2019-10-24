package de.szut.simNil.binaryMaple.rb;

import de.szut.simNil.binaryMaple.BinarySearchTreeException;
import de.szut.simNil.binaryMaple.InterfaceBinarySearchTree;
import de.szut.simNil.binaryMaple.Order;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class RedBlackBinarySearchTree<T extends Comparable<T>> implements InterfaceBinarySearchTree<T> {
    private RBNode<T> root;
    private Integer nodeCount;

    public RedBlackBinarySearchTree() {
        this.root = new RBNode<>();
        nodeCount = 0;
    }

    public RedBlackBinarySearchTree(@NotNull T value) {
        this.root = new RBNode<>(value);
        this.root.setLeft(new RBNode<>());
        this.root.setRight(new RBNode<>());
        nodeCount = 1;
    }

    private void rebalanceInsertion(RBNode<T> current, Stack<RBNode<T>> ancestors) {
        if (ancestors.isEmpty()) {
            System.out.println("insertion case 0");
            current.setColor(RBNode.Color.BLACK);
            return;
        }

        RBNode<T> parent = ancestors.pop();

        if (parent.getColor() == RBNode.Color.BLACK) {
            System.out.println("insertion case 1");
            return;
        }

        if (ancestors.isEmpty()) {
            System.out.println("insertion case 2");
            parent.setColor(RBNode.Color.BLACK);
            return;
        }

        RBNode<T> grandparent = ancestors.pop();

        boolean currentIsLeftChild = current.getValue().compareTo(parent.getValue()) < 0;
        boolean parentIsLeftChild = parent.getValue().compareTo(grandparent.getValue()) < 0;
        RBNode<T> pibling = parentIsLeftChild ? grandparent.getRight() : grandparent.getLeft();

        if (pibling.getColor() == RBNode.Color.BLACK) {
            if (currentIsLeftChild != parentIsLeftChild) {
                System.out.println("insertion case 3");
                if (parentIsLeftChild) {
                    grandparent.setLeft(current);
                    parent.setRight(current.getLeft());
                    current.setLeft(parent);
                } else {
                    grandparent.setRight(current);
                    parent.setLeft(current.getRight());
                    current.setRight(parent);
                }
                parent = current;   // current is not needed for insertion case E4
            }

            System.out.println("insertion case 4");
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
            System.out.println("insertion case 5");
            parent.setColor(RBNode.Color.BLACK);
            pibling.setColor(RBNode.Color.BLACK);
            grandparent.setColor(RBNode.Color.RED);
            rebalanceInsertion(grandparent, ancestors);
        }
    }

    @Override
    public void addValue(@NotNull T value) throws BinarySearchTreeException {
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
        ++this.nodeCount;
    }

    // current = n (on Wikipedia)
    public void rebalanceDeletion(T deletedValue, Stack<RBNode<T>> ancestors) {
        if (ancestors.isEmpty()) {
            System.out.println("deletion case 0");
            return;
        }

        RBNode<T> parent = ancestors.pop();
        boolean isLeftChild = deletedValue.compareTo(parent.getValue()) < 0;
        RBNode<T> sibling = isLeftChild ? parent.getRight() : parent.getLeft();

        if (sibling.getColor() == RBNode.Color.RED) {
            System.out.println("deletion case 2");
            if (ancestors.isEmpty()) {
                this.root = sibling;
            } else {
                RBNode<T> grandparent = ancestors.peek();
                if (parent.getValue().compareTo(grandparent.getValue()) < 0) {
                    grandparent.setLeft(sibling);
                } else {
                    grandparent.setRight(sibling);
                }
            }

            if (isLeftChild) {
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
            rebalanceDeletion(deletedValue, ancestors);
            return;
        }

        if (sibling.getValue() == null || sibling.getLeft().getColor() == RBNode.Color.BLACK && sibling.getRight().getColor() == RBNode.Color.BLACK) {
            if (parent.getColor() == RBNode.Color.BLACK) {
                System.out.println("deletion case 1");
                sibling.setColor(RBNode.Color.RED);
                rebalanceDeletion(parent.getValue(), ancestors);
                return;
            } else {
                System.out.println("deletion case 3");
                parent.setColor(RBNode.Color.BLACK);
                sibling.setColor(RBNode.Color.RED);
                return;
            }
        }

        RBNode<T> farNephew = isLeftChild ? sibling.getRight() : sibling.getLeft();

        if (farNephew.getColor() == RBNode.Color.RED) {
            System.out.println("deletion case 5");
            if (ancestors.isEmpty()) {
                this.root = sibling;
            } else {
                RBNode<T> grandparent = ancestors.peek();
                if (parent.getValue().compareTo(grandparent.getValue()) < 0) {
                    grandparent.setLeft(sibling);
                } else {
                    grandparent.setRight(sibling);
                }
            }

            if (isLeftChild) {
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

        System.out.println("deletion case 4");
        RBNode<T> closeNephew = isLeftChild ? sibling.getLeft() : sibling.getRight(); // must be red
        if (isLeftChild) {
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
        rebalanceDeletion(deletedValue, ancestors);
    }

    @Override
    public void delValue(@NotNull T value) throws BinarySearchTreeException {
        Stack<RBNode<T>> ancestors = new Stack<>();
        RBNode<T> current = this.root;
        while (current.getValue() != null) {
            int c = value.compareTo(current.getValue());
            if (c == 0) {
                boolean hasLeftValue = current.getLeft().getValue() != null;
                boolean hasRightValue = current.getRight().getValue() != null;
                boolean isLeftChild = ancestors.isEmpty() || current.getValue().compareTo(ancestors.peek().getValue()) < 0;
                boolean updateCurrentValueAtEnd = false;
                T newCurrentValue = current.getValue();

                RBNode<T> r;    // node to be deleted
                if (hasLeftValue && hasRightValue) {
                    ancestors.push(current);
                    RBNode<T> nodeWithLargestValueInLeftSubtree = current.getLeft();
                    while (nodeWithLargestValueInLeftSubtree.getRight().getValue() != null) {
                        ancestors.push(nodeWithLargestValueInLeftSubtree);
                        nodeWithLargestValueInLeftSubtree = nodeWithLargestValueInLeftSubtree.getRight();
                    }

                    r = nodeWithLargestValueInLeftSubtree;
                    // update stuff because r != current
                    hasLeftValue = r.getLeft().getValue() != null;
                    hasRightValue = r.getRight().getValue() != null;
                    isLeftChild = r.getValue().compareTo(ancestors.peek().getValue()) < 0;

                    // save value of r before it will be deleted
                    updateCurrentValueAtEnd = true;
                    newCurrentValue = r.getValue();
                } else {
                    r = current;
                }

                if (r.getColor() == RBNode.Color.RED) {
                    // easy deletion case 1
                    if (ancestors.isEmpty()) {
                        this.root = new RBNode<>();
                    } else if (isLeftChild) {
                        ancestors.peek().setLeft(new RBNode<>());
                    } else {
                        ancestors.peek().setRight(new RBNode<>());
                    }
                } else if (hasLeftValue || hasRightValue) {   // can only have one child at most
                    // easy deletion case 2
                    RBNode<T> child = hasLeftValue ? r.getLeft() : r.getRight();
                    if (ancestors.isEmpty()) {
                        this.root = child;
                    } else if (isLeftChild) {
                        ancestors.peek().setLeft(child);
                    } else {
                        ancestors.peek().setRight(child);
                    }
                    child.setColor(RBNode.Color.BLACK);
                } else {
                    if (ancestors.isEmpty()) {
                        this.root = new RBNode<>();
                    } else if (isLeftChild) {
                        ancestors.peek().setLeft(new RBNode<>());
                        rebalanceDeletion(r.getValue(), ancestors);
                    } else {
                        ancestors.peek().setRight(new RBNode<>());
                        rebalanceDeletion(r.getValue(), ancestors);
                    }
                }
                if (updateCurrentValueAtEnd) {
                    current.setValue(newCurrentValue);
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
    public RBNode<T> getNodeWithValue(@NotNull T value) {
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
    public int getDepth() {
        int depth = 0;
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
        return depth - 1;   // "- 1" compensates for terminal nodes
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
    public RBNode<T> getRoot() {
        return this.root;
    }

    @Override
    public int getNodeCount() {
        return this.nodeCount;
    }
}
