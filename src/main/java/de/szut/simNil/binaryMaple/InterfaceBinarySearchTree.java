package de.szut.simNil.binaryMaple;

import java.util.List;

/**
 * Interface fuer Binary Search Tress
 *
 * @author wolke
 * @version 1.0, 29.08.2019
 */
public interface InterfaceBinarySearchTree<T extends Comparable<T>> {

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

    Integer getDepth();

    List<T> traverse(Order o);

}
