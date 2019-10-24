package de.szut.simNil.binaryMaple;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This abstract class
 * @param <T> type parameter of node value (for example Integer or String)
 * @author Jürgen Wolkenhauer
 * @author Simon Bullik
 * @author Nils Malte Kiele
 */
public abstract class AbstractNode<T extends Comparable<T>> {
    /**
     * value of node, must be of type T
     */
    @Nullable
    private T value;

    AbstractNode() {
    }

    AbstractNode(@NotNull T value) {
        this.value = value;
    }

    @Nullable
    public T getValue() {
        return value;
    }

    public void setValue(@NotNull T value) {
        this.value = value;
    }

    /**
     * @return value of node as string
     */
    public String toString() {
        return getValue() != null ? getValue().toString() : "null";
    }
}
