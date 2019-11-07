package de.szut.simNil.binaryMaple.avl;

import de.szut.simNil.binaryMaple.BinarySearchTreeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AVLBinarySearchTreeTest {

    @Test
    void addValue() {
        // heights of subtrees are not tested with this method because they need to work correctly to rotate this often
        AVLBinarySearchTree<Integer> tree = new AVLBinarySearchTree<>();
        try {
            tree.addValue(9);
        } catch (BinarySearchTreeException e) {
            e.printStackTrace();
        }

        assertEquals(9, tree.getRoot().getValue());
        assertNull(tree.getRoot().getLeft().getValue());
        assertNull(tree.getRoot().getRight().getValue());

        try {
            tree.addValue(3);
        } catch (BinarySearchTreeException e) {
            e.printStackTrace();
        }

        assertEquals(9, tree.getRoot().getValue());

        assertEquals(3, tree.getRoot().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getRight().getValue());
        assertNull(tree.getRoot().getRight().getValue());

        // rotation case LL
        try {
            tree.addValue(1);
        } catch (BinarySearchTreeException e) {
            e.printStackTrace();
        }

        assertEquals(3, tree.getRoot().getValue());

        assertEquals(1, tree.getRoot().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getRight().getValue());

        assertEquals(9, tree.getRoot().getRight().getValue());
        assertNull(tree.getRoot().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getRight().getRight().getValue());

        try {
            tree.addValue(10);
        } catch (BinarySearchTreeException e) {
            e.printStackTrace();
        }

        assertEquals(3, tree.getRoot().getValue());

        assertEquals(1, tree.getRoot().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getRight().getValue());

        assertEquals(9, tree.getRoot().getRight().getValue());
        assertNull(tree.getRoot().getRight().getLeft().getValue());

        assertEquals(10, tree.getRoot().getRight().getRight().getValue());
        assertNull(tree.getRoot().getRight().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getRight().getRight().getRight().getValue());

        // rotation case RR
        try {
            tree.addValue(15);
        } catch (BinarySearchTreeException e) {
            e.printStackTrace();
        }

        assertEquals(3, tree.getRoot().getValue());

        assertEquals(1, tree.getRoot().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getRight().getValue());

        assertEquals(10, tree.getRoot().getRight().getValue());

        assertEquals(9, tree.getRoot().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getRight().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getRight().getLeft().getRight().getValue());

        assertEquals(15, tree.getRoot().getRight().getRight().getValue());
        assertNull(tree.getRoot().getRight().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getRight().getRight().getRight().getValue());

        // rotation case RL
        try {
            tree.addValue(5);
        } catch (BinarySearchTreeException e) {
            e.printStackTrace();
        }

        assertEquals(9, tree.getRoot().getValue());

        assertEquals(3, tree.getRoot().getLeft().getValue());

        assertEquals(1, tree.getRoot().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getLeft().getRight().getValue());

        assertEquals(5, tree.getRoot().getLeft().getRight().getValue());
        assertNull(tree.getRoot().getLeft().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getRight().getRight().getValue());

        assertEquals(10, tree.getRoot().getRight().getValue());
        assertNull(tree.getRoot().getRight().getLeft().getValue());

        assertEquals(15, tree.getRoot().getRight().getRight().getValue());
        assertNull(tree.getRoot().getRight().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getRight().getRight().getRight().getValue());

        assertEquals(15, tree.getRoot().getRight().getRight().getValue());
        assertNull(tree.getRoot().getRight().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getRight().getRight().getRight().getValue());

        try {
            tree.addValue(6);
        } catch (BinarySearchTreeException e) {
            e.printStackTrace();
        }

        assertEquals(9, tree.getRoot().getValue());

        assertEquals(3, tree.getRoot().getLeft().getValue());

        assertEquals(1, tree.getRoot().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getLeft().getRight().getValue());

        assertEquals(5, tree.getRoot().getLeft().getRight().getValue());
        assertNull(tree.getRoot().getLeft().getRight().getLeft().getValue());

        assertEquals(6, tree.getRoot().getLeft().getRight().getRight().getValue());
        assertNull(tree.getRoot().getLeft().getRight().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getRight().getRight().getRight().getValue());

        assertEquals(10, tree.getRoot().getRight().getValue());
        assertNull(tree.getRoot().getRight().getLeft().getValue());

        assertEquals(15, tree.getRoot().getRight().getRight().getValue());
        assertNull(tree.getRoot().getRight().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getRight().getRight().getRight().getValue());

        assertEquals(15, tree.getRoot().getRight().getRight().getValue());
        assertNull(tree.getRoot().getRight().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getRight().getRight().getRight().getValue());

        try {
            tree.addValue(4);
        } catch (BinarySearchTreeException e) {
            e.printStackTrace();
        }

        assertEquals(9, tree.getRoot().getValue());

        assertEquals(3, tree.getRoot().getLeft().getValue());

        assertEquals(1, tree.getRoot().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getLeft().getRight().getValue());

        assertEquals(5, tree.getRoot().getLeft().getRight().getValue());

        assertEquals(4, tree.getRoot().getLeft().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getRight().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getRight().getLeft().getRight().getValue());

        assertEquals(6, tree.getRoot().getLeft().getRight().getRight().getValue());
        assertNull(tree.getRoot().getLeft().getRight().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getRight().getRight().getRight().getValue());

        assertEquals(10, tree.getRoot().getRight().getValue());
        assertNull(tree.getRoot().getRight().getLeft().getValue());

        assertEquals(15, tree.getRoot().getRight().getRight().getValue());
        assertNull(tree.getRoot().getRight().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getRight().getRight().getRight().getValue());

        assertEquals(15, tree.getRoot().getRight().getRight().getValue());
        assertNull(tree.getRoot().getRight().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getRight().getRight().getRight().getValue());

        try {
            tree.addValue(0);
        } catch (BinarySearchTreeException e) {
            e.printStackTrace();
        }

        assertEquals(9, tree.getRoot().getValue());

        assertEquals(3, tree.getRoot().getLeft().getValue());

        assertEquals(1, tree.getRoot().getLeft().getLeft().getValue());

        assertEquals(0, tree.getRoot().getLeft().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getLeft().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getLeft().getLeft().getRight().getValue());

        assertEquals(5, tree.getRoot().getLeft().getRight().getValue());

        assertEquals(4, tree.getRoot().getLeft().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getRight().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getRight().getLeft().getRight().getValue());

        assertEquals(6, tree.getRoot().getLeft().getRight().getRight().getValue());
        assertNull(tree.getRoot().getLeft().getRight().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getRight().getRight().getRight().getValue());

        assertEquals(10, tree.getRoot().getRight().getValue());
        assertNull(tree.getRoot().getRight().getLeft().getValue());

        assertEquals(15, tree.getRoot().getRight().getRight().getValue());
        assertNull(tree.getRoot().getRight().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getRight().getRight().getRight().getValue());

        assertEquals(15, tree.getRoot().getRight().getRight().getValue());
        assertNull(tree.getRoot().getRight().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getRight().getRight().getRight().getValue());

        // rotation case LR
        try {
            tree.addValue(8);
        } catch (BinarySearchTreeException e) {
            e.printStackTrace();
        }

        assertEquals(5, tree.getRoot().getValue());

        assertEquals(3, tree.getRoot().getLeft().getValue());

        assertEquals(1, tree.getRoot().getLeft().getLeft().getValue());

        assertEquals(0, tree.getRoot().getLeft().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getLeft().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getLeft().getLeft().getRight().getValue());

        assertEquals(4, tree.getRoot().getLeft().getRight().getValue());
        assertNull(tree.getRoot().getLeft().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getRight().getRight().getValue());

        assertEquals(9, tree.getRoot().getRight().getValue());

        assertEquals(6, tree.getRoot().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getRight().getLeft().getLeft().getValue());

        assertEquals(8, tree.getRoot().getRight().getLeft().getRight().getValue());
        assertNull(tree.getRoot().getRight().getLeft().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getRight().getLeft().getRight().getRight().getValue());

        assertEquals(10, tree.getRoot().getRight().getRight().getValue());
        assertNull(tree.getRoot().getRight().getRight().getLeft().getValue());

        assertEquals(15, tree.getRoot().getRight().getRight().getRight().getValue());
        assertNull(tree.getRoot().getRight().getRight().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getRight().getRight().getRight().getRight().getValue());
    }

    @Test
    void delValue() {
        // LL, LR, RL and RR rotations are test with test addValue()
        // this test requires two subsequent rotations to rebalance the tree (tricky edge case)
        AVLBinarySearchTree<Integer> tree = new AVLBinarySearchTree<>();
        try {
            tree.addValue(50);
            tree.addValue(25);
            tree.addValue(75);
            tree.addValue(10);
            tree.addValue(30);
            tree.addValue(60);
            tree.addValue(80);
            tree.addValue(5);
            tree.addValue(15);
            tree.addValue(27);
            tree.addValue(55);
            tree.addValue(1);

            tree.delValue(80);
        } catch (BinarySearchTreeException e) {
            e.printStackTrace();
        }

        assertEquals(25, tree.getRoot().getValue());

        assertEquals(10, tree.getRoot().getLeft().getValue());

        assertEquals(5, tree.getRoot().getLeft().getLeft().getValue());

        assertEquals(1, tree.getRoot().getLeft().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getLeft().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getLeft().getLeft().getRight().getValue());

        assertEquals(15, tree.getRoot().getLeft().getRight().getValue());
        assertNull( tree.getRoot().getLeft().getRight().getLeft().getValue());
        assertNull( tree.getRoot().getLeft().getRight().getRight().getValue());

        assertEquals(50, tree.getRoot().getRight().getValue());

        assertEquals(30, tree.getRoot().getRight().getLeft().getValue());

        assertEquals(27, tree.getRoot().getRight().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getRight().getLeft().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getRight().getLeft().getLeft().getRight().getValue());

        assertEquals(60, tree.getRoot().getRight().getRight().getValue());

        assertEquals(55, tree.getRoot().getRight().getRight().getLeft().getValue());
        assertNull( tree.getRoot().getRight().getRight().getLeft().getLeft().getValue());
        assertNull( tree.getRoot().getRight().getRight().getLeft().getRight().getValue());

        assertEquals(75, tree.getRoot().getRight().getRight().getRight().getValue());
        assertNull(tree.getRoot().getRight().getRight().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getRight().getRight().getRight().getRight().getValue());
    }
}
