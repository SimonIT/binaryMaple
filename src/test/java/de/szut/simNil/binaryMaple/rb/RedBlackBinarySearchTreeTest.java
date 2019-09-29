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
    void delValueEasyCase1() {
        RedBlackBinarySearchTree<Integer> tree = new RedBlackBinarySearchTree<>();
        try {
            tree.addValue(3);
            tree.addValue(4);
            tree.addValue(-5);
            tree.addValue(7);
            tree.delValue(7);
        } catch (BinarySearchTreeException e) {
            e.printStackTrace();
        }

        assertEquals(3, tree.getRoot().getValue());
        assertEquals(RBNode.Color.BLACK, tree.getRoot().getColor());

        assertEquals(-5, tree.getRoot().getLeft().getValue());
        assertEquals(RBNode.Color.BLACK, tree.getRoot().getLeft().getColor());
        assertNull(tree.getRoot().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getRight().getValue());


        assertEquals(4, tree.getRoot().getRight().getValue());
        assertEquals(RBNode.Color.BLACK, tree.getRoot().getRight().getColor());
        assertNull(tree.getRoot().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getRight().getRight().getValue());
    }

    @Test
    void delValueEasyCase2() {
        RedBlackBinarySearchTree<Integer> tree = new RedBlackBinarySearchTree<>();
        try {
            tree.addValue(3);
            tree.addValue(4);
            tree.addValue(-5);
            tree.addValue(7);
            tree.delValue(4);
        } catch (BinarySearchTreeException e) {
            e.printStackTrace();
        }

        assertEquals(3, tree.getRoot().getValue());
        assertEquals(RBNode.Color.BLACK, tree.getRoot().getColor());

        assertEquals(-5, tree.getRoot().getLeft().getValue());
        assertEquals(RBNode.Color.BLACK, tree.getRoot().getLeft().getColor());
        assertNull(tree.getRoot().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getRight().getValue());


        assertEquals(7, tree.getRoot().getRight().getValue());
        assertEquals(RBNode.Color.BLACK, tree.getRoot().getRight().getColor());
        assertNull(tree.getRoot().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getRight().getRight().getValue());
    }

    @Test
    void delValueCase0() {
        RedBlackBinarySearchTree<Integer> tree = new RedBlackBinarySearchTree<>();
        try {
            tree.addValue(3);
            tree.delValue(3);
        } catch (BinarySearchTreeException e) {
            e.printStackTrace();
        }

        assertNull(tree.getRoot().getValue());
    }

    @Test
    void delValueCase1() {
        RedBlackBinarySearchTree<Integer> tree = new RedBlackBinarySearchTree<>();
        try {
            tree.addValue(3);
            tree.addValue(4);
            tree.addValue(-5);
            tree.addValue(7);
            tree.delValue(7);   // assuming that easy case 1 works
            tree.delValue(4);
        } catch (BinarySearchTreeException e) {
            e.printStackTrace();
        }

        assertEquals(3, tree.getRoot().getValue());
        assertEquals(RBNode.Color.BLACK, tree.getRoot().getColor());

        assertEquals(-5, tree.getRoot().getLeft().getValue());
        assertEquals(RBNode.Color.RED, tree.getRoot().getLeft().getColor());
        assertNull(tree.getRoot().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getRight().getValue());

        assertNull(tree.getRoot().getRight().getValue());
    }

    @Test
    void delValueCase2() {
        // deletion case 3 is also tested because it is immediately followed by this scenario
        RedBlackBinarySearchTree<Integer> tree = new RedBlackBinarySearchTree<>();
        try {
            tree.addValue(3);
            tree.addValue(4);
            tree.addValue(-5);
            tree.addValue(7);
            tree.addValue(10);
            tree.addValue(13);
            tree.delValue(-5);
        } catch (BinarySearchTreeException e) {
            e.printStackTrace();
        }

        assertEquals(7, tree.getRoot().getValue());
        assertEquals(RBNode.Color.BLACK, tree.getRoot().getColor());

        assertEquals(3, tree.getRoot().getLeft().getValue());
        assertEquals(RBNode.Color.BLACK, tree.getRoot().getLeft().getColor());
        assertNull(tree.getRoot().getLeft().getLeft().getValue());

        assertEquals(4, tree.getRoot().getLeft().getRight().getValue());
        assertEquals(RBNode.Color.RED, tree.getRoot().getLeft().getRight().getColor());
        assertNull(tree.getRoot().getLeft().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getRight().getRight().getValue());

        assertEquals(10, tree.getRoot().getRight().getValue());
        assertEquals(RBNode.Color.BLACK, tree.getRoot().getRight().getColor());
        assertNull(tree.getRoot().getRight().getLeft().getValue());

        assertEquals(13, tree.getRoot().getRight().getRight().getValue());
        assertEquals(RBNode.Color.RED, tree.getRoot().getRight().getRight().getColor());
        assertNull(tree.getRoot().getRight().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getRight().getRight().getRight().getValue());
    }

    @Test
    void delValueCase3() {
        RedBlackBinarySearchTree<Integer> tree = new RedBlackBinarySearchTree<>();
        try {
            tree.addValue(3);
            tree.addValue(4);
            tree.addValue(-5);
            tree.addValue(7);
            tree.addValue(8);
            tree.addValue(9);
            tree.delValue(9);   // assuming that easy case 1 works
            tree.delValue(4);
        } catch (BinarySearchTreeException e) {
            e.printStackTrace();
        }

        assertEquals(3, tree.getRoot().getValue());
        assertEquals(RBNode.Color.BLACK, tree.getRoot().getColor());

        assertEquals(-5, tree.getRoot().getLeft().getValue());
        assertEquals(RBNode.Color.BLACK, tree.getRoot().getLeft().getColor());
        assertNull(tree.getRoot().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getRight().getValue());

        assertEquals(7, tree.getRoot().getRight().getValue());
        assertEquals(RBNode.Color.BLACK, tree.getRoot().getRight().getColor());
        assertNull(tree.getRoot().getRight().getLeft().getValue());

        assertEquals(8, tree.getRoot().getRight().getRight().getValue());
        assertEquals(RBNode.Color.RED, tree.getRoot().getRight().getRight().getColor());
        assertNull(tree.getRoot().getRight().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getRight().getRight().getRight().getValue());
    }

    @Test
    void delValueCase4() {
        // deletion case 5 is also tested because it is immediately followed by this scenario
        RedBlackBinarySearchTree<Integer> tree = new RedBlackBinarySearchTree<>();
        try {
            tree.addValue(2);
            tree.addValue(7);
            tree.addValue(-6);
            tree.addValue(5);
            tree.delValue(-6);
        } catch (BinarySearchTreeException e) {
            e.printStackTrace();
        }

        assertEquals(5, tree.getRoot().getValue());
        assertEquals(RBNode.Color.BLACK, tree.getRoot().getColor());

        assertEquals(2, tree.getRoot().getLeft().getValue());
        assertEquals(RBNode.Color.BLACK, tree.getRoot().getLeft().getColor());
        assertNull(tree.getRoot().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getRight().getValue());

        assertEquals(7, tree.getRoot().getRight().getValue());
        assertEquals(RBNode.Color.BLACK, tree.getRoot().getRight().getColor());
        assertNull(tree.getRoot().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getRight().getRight().getValue());
    }

    @Test
    void delValueCase5() {
        RedBlackBinarySearchTree<Integer> tree = new RedBlackBinarySearchTree<>();
        try {
            tree.addValue(2);
            tree.addValue(7);
            tree.addValue(-6);
            tree.addValue(10);
            tree.delValue(-6);
        } catch (BinarySearchTreeException e) {
            e.printStackTrace();
        }

        assertEquals(7, tree.getRoot().getValue());
        assertEquals(RBNode.Color.BLACK, tree.getRoot().getColor());

        assertEquals(2, tree.getRoot().getLeft().getValue());
        assertEquals(RBNode.Color.BLACK, tree.getRoot().getLeft().getColor());
        assertNull(tree.getRoot().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getRight().getValue());

        assertEquals(10, tree.getRoot().getRight().getValue());
        assertEquals(RBNode.Color.BLACK, tree.getRoot().getRight().getColor());
        assertNull(tree.getRoot().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getRight().getRight().getValue());
    }

    @Test
    void delValue() {
        // done in separate tests
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
