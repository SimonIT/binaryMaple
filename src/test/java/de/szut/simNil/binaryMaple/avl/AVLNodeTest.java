package de.szut.simNil.binaryMaple.avl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AVLNodeTest {
    @Test
    void setGetLeft() {
        AVLNode<Integer> node = new AVLNode<>();
        node.setLeft(new AVLNode<>(-5));
        assertEquals(-5, node.getLeft().getValue());
    }

    @Test
    void setGetRight() {
        AVLNode<Integer> node = new AVLNode<>();
        node.setRight(new AVLNode<>(140));
        assertEquals(140, node.getRight().getValue());
    }

    @Test
    void setGetIncreaseDecreaseHeightLeftRight() {
        AVLNode<Integer> node = new AVLNode<>();

        assertEquals(0, node.getHeightLeft());
        assertEquals(0, node.getHeightRight());

        node.setHeightLeft(235);
        assertEquals(235, node.getHeightLeft());
        assertEquals(0, node.getHeightRight());

        node.setHeightRight(6);
        assertEquals(235, node.getHeightLeft());
        assertEquals(6, node.getHeightRight());

        node.increaseHeightLeft();
        assertEquals(236, node.getHeightLeft());
        assertEquals(6, node.getHeightRight());

        node.increaseHeightRight();
        assertEquals(236, node.getHeightLeft());
        assertEquals(7, node.getHeightRight());


        node.decreaseHeightRight();
        assertEquals(236, node.getHeightLeft());
        assertEquals(6, node.getHeightRight());

        node.decreaseHeightLeft();
        assertEquals(235, node.getHeightLeft());
        assertEquals(6, node.getHeightRight());
    }

    @Test
    void checkBalanceFactor() {
        AVLNode<Integer> node = new AVLNode<>();

        node.setHeightLeft(25);
        node.setHeightRight(16);
        assertEquals(16 - 25, node.getBalanceFactor());

        node.setHeightRight(1515);
        node.setHeightLeft(14);
        assertEquals(1515 - 14, node.getBalanceFactor());

        node.setHeightLeft(1);
        node.setHeightRight(1);
        assertEquals(0, node.getBalanceFactor());
    }
}
