package de.szut.simNil.binaryMaple;

import java.util.List;

/**
 * Interface fuer Binary Search Tress
 *
 * @author wolke
 * @version 1.1, 29.08.2019
 */
public interface InterfaceBinarySearchTree<T extends Comparable<T>> {

    /**
     * @param value the value we'd like to add
     * @throws BinarySearchTreeException should usually not occur
     */
    void addValue(T value)
        throws BinarySearchTreeException;

    /**
     * @param value the value we'd like to delete
     * @throws BinarySearchTreeException If the value is not in the tree.
     */
    void delValue(T value)
        throws BinarySearchTreeException;

    /**
     * @param value The value we are searching
     * @return True or False(we couldn't find it)
     */
    boolean hasValue(T value);

    /**
     * @return height of the tree
     */
    default Integer getDepth() {
        return null;
    }

    /**
     * @return list with all values in correct order
     * LEVELORDER not NECCESARY
     */
    default List<T> traverse(Order o) {
        return null;
    }

}
