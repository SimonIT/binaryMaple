package de.szut.simNil.binaryMaple;

/**
 * Astract class for a node
 * Takes any comparable as Value
 *
 * @author wolke
 * @version 1.1, 19.08.2019
 */

public abstract class AbstractNode<T extends Comparable<T>> {
    protected T value; // integer value

    public AbstractNode() {
    }

    public AbstractNode(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String toString() {
        return value.toString();
    }

}
