package de.szut.simNil.binaryMaple.rb;

import de.szut.simNil.binaryMaple.BinarySearchTreeException;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class RedBlackBinarySearchTreeTest {

    @Test
    void addValue() {
        // check insertion case 0 and leaf node colors
        RedBlackBinarySearchTree<Integer> tree = new RedBlackBinarySearchTree<>();
        try {
            tree.addValue(8);
        } catch (BinarySearchTreeException e) {
            e.printStackTrace();
        }

        assertEquals(8, tree.getRoot().getValue());
        assertEquals(RBNode.Color.BLACK, tree.getRoot().getColor());

        assertNull(tree.getRoot().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getLeft());
        assertNull(tree.getRoot().getLeft().getRight());
        assertEquals(RBNode.Color.BLACK, tree.getRoot().getLeft().getColor());

        assertNull(tree.getRoot().getRight().getValue());
        assertNull(tree.getRoot().getRight().getLeft());
        assertNull(tree.getRoot().getRight().getRight());
        assertEquals(RBNode.Color.BLACK, tree.getRoot().getRight().getColor());

        // check insertion case 1
        try {
            tree.addValue(17);
        } catch (BinarySearchTreeException e) {
            e.printStackTrace();
        }
        assertEquals(8, tree.getRoot().getValue());
        assertEquals(RBNode.Color.BLACK, tree.getRoot().getColor());

        assertNull(tree.getRoot().getLeft().getValue());

        assertEquals(17, tree.getRoot().getRight().getValue());
        assertEquals(RBNode.Color.RED, tree.getRoot().getRight().getColor());

        // check insertion case 3 and case 4
        try {
            tree.addValue(13);
        } catch (BinarySearchTreeException e) {
            e.printStackTrace();
        }
        assertEquals(13, tree.getRoot().getValue());
        assertEquals(RBNode.Color.BLACK, tree.getRoot().getColor());

        assertEquals(8, tree.getRoot().getLeft().getValue());
        assertEquals(RBNode.Color.RED, tree.getRoot().getLeft().getColor());
        assertNull(tree.getRoot().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getRight().getValue());

        assertEquals(17, tree.getRoot().getRight().getValue());
        assertEquals(RBNode.Color.RED, tree.getRoot().getRight().getColor());
        assertNull(tree.getRoot().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getRight().getRight().getValue());

        // check insertion case 5
        try {
            tree.addValue(-3);
        } catch (BinarySearchTreeException e) {
            e.printStackTrace();
        }

        assertEquals(13, tree.getRoot().getValue());
        assertEquals(RBNode.Color.BLACK, tree.getRoot().getColor());

        assertEquals(8, tree.getRoot().getLeft().getValue());
        assertEquals(RBNode.Color.BLACK, tree.getRoot().getLeft().getColor());
        assertNull(tree.getRoot().getLeft().getRight().getValue());

        assertEquals(-3, tree.getRoot().getLeft().getLeft().getValue());
        assertEquals(RBNode.Color.RED, tree.getRoot().getLeft().getLeft().getColor());
        assertNull(tree.getRoot().getLeft().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getLeft().getRight().getValue());

        assertEquals(17, tree.getRoot().getRight().getValue());
        assertEquals(RBNode.Color.BLACK, tree.getRoot().getRight().getColor());
        assertNull(tree.getRoot().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getRight().getRight().getValue());
    }

    @Test
    void delValue() {
    }

    @Test
    void hasValue() {
    }

    @Test
    void getNodeWithValue() {
    }

    @Test
    void getDepth() {
    }

    @Test
    void traverse() {
    }

    @Test
    void getRoot() {
    }

    @Test
    void getNodeCount() {
    }

    @Test
    void testEquals() {
    }
}
