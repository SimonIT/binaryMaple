package de.szut.simNil.binaryMaple;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface InterfaceBinarySearchTree<T extends Comparable<T>> {
    void addValue(T value) throws BinarySearchTreeException;

    void delValue(@NotNull T value) throws BinarySearchTreeException;

    boolean hasValue(@NotNull T value);

    @Nullable
    AbstractNode<T> getNodeWithValue(@NotNull T value);

    default Integer getDepth() {
        return null;
    }

    default List<T> traverse(Order o) {
        return null;
    }

    public AbstractNode<T> getRoot();

    int getNodeCount();
}
