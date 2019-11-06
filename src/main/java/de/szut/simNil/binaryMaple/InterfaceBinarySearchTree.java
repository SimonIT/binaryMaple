package de.szut.simNil.binaryMaple;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


/**
 * This interface for binary search trees contains the most basic methods all binary search trees (such as Red-Black or
 * AVL trees) have in common.
 *
 * @param <T> type parameter of node values (for example Integer or String)
 * @author Jürgen Wolkenhauer
 * @author Simon Bullik
 * @author Nils Malte Kiele
 */
public interface InterfaceBinarySearchTree<T extends Comparable<T>> {
    /**
     * @param value value that should be inserted into the tree
     * @throws BinarySearchTreeException
     */
    void addValue(@NotNull T value) throws BinarySearchTreeException;

    /**
     * @param value value that should be deleted from the tree
     * @throws BinarySearchTreeException
     */
    void delValue(@NotNull T value) throws BinarySearchTreeException;

    /**
     * @param value value of node in question
     * @return true if value exists in tree, false otherwise
     */
    boolean hasValue(@NotNull T value);

    /**
     * @param value of node that should be returned
     * @return node with specified value if it exists or Null otherwise
     */
    @Nullable
    AbstractNode<T> getNodeWithValue(@NotNull T value);

    /**
     * @return height (number of levels) of tree
     */
    int getHeight();

    /**
     * @param order element of Enum order
     * @return list of all nodes in the tree in specified order
     */
    List<T> traverse(@NotNull Order order);

    /**
     * @return root of the tree or Null if the tree is empty
     */
    @Nullable
    AbstractNode<T> getRoot();

    /**
     * @return amount of nodes in tree
     */
    int getNodeCount();
}
