package de.szut.simNil.binaryMaple;

import org.jetbrains.annotations.Nullable;

/**
 * Astract class for a node
 * Takes any comparable as Value
 *
 * @author wolke
 * @version 1.1, 19.08.2019
 */

public abstract class AbstractNode<T extends Comparable<T>> {
    @Nullable
    protected T value; // integer value

    public AbstractNode() {
    }

    public AbstractNode(@Nullable T value) {
        this.value = value;
    }

    @Nullable
    public T getValue() {
        return value;
    }

    public void setValue(@Nullable T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AbstractNode) {
            if (this.getValue() != null && ((AbstractNode) obj).getValue() != null) {
                return ((AbstractNode) obj).getValue().equals(this.getValue());
            } else return this.getValue() == null && ((AbstractNode) obj).getValue() == null;
        }
        return false;
    }

    public String toString() {
        return getValue() != null ? getValue().toString() : "null";
    }

}
