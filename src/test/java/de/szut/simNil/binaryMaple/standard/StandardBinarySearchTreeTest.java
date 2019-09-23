package de.szut.simNil.binaryMaple.standard;

import de.szut.simNil.binaryMaple.BinarySearchTreeException;
import de.szut.simNil.binaryMaple.rb.RedBlackBinarySearchTree;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StandardBinarySearchTreeTest {

    @Test
    void addValue() {
    }

    @Test
    void delValue() {
    }

    @Test
    void hasValue() {
    }

    @Test
    void getDepth() {
    }

    @Test
    void traverse() {
    }

    @Test
    void getRoot() {
        assertNotNull(new StandardBinarySearchTree<>().getRoot());
        assertNull(new StandardBinarySearchTree<>().getRoot().getValue());
        assertEquals(7, new StandardBinarySearchTree<>(7).getRoot().getValue());
    }

    @Test
    void getNodeCount() throws BinarySearchTreeException {
        StandardBinarySearchTree<Integer> tree = new StandardBinarySearchTree<>();
        assertEquals(0, tree.getNodeCount());
        tree.addValue(-1);
        assertEquals(1, tree.getNodeCount());
        for (int i = 0; i < 99; i++)
            tree.addValue(i);
        assertEquals(100, tree.getNodeCount());
        tree.delValue(0);
        assertEquals(99, tree.getNodeCount());
    }

    @Test
    void getNodeWithValue() {
    }

    @Test
    void testEquals() {
        assertEquals(new StandardBinarySearchTree<>(), new StandardBinarySearchTree<>());
        assertEquals(new StandardBinarySearchTree<>(5), new StandardBinarySearchTree<>(5));
        assertNotEquals(new StandardBinarySearchTree<>(5), new StandardBinarySearchTree<>(6));
        assertNotEquals(new StandardBinarySearchTree<>(5), new StandardBinarySearchTree<>(5.1));
        assertNotEquals(new StandardBinarySearchTree<>(), new RedBlackBinarySearchTree<>());
    }
}
