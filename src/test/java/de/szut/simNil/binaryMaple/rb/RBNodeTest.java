package de.szut.simNil.binaryMaple.rb;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RBNodeTest {
    @Test
    void setGetLeft() {
        RBNode<Integer> node = new RBNode<>();
        node.setLeft(new RBNode<>(-5));
        assertEquals(-5, node.getLeft().getValue());
    }

    @Test
    void setGetRight() {
        RBNode<Integer> node = new RBNode<>();
        node.setRight(new RBNode<>(140));
        assertEquals(140, node.getRight().getValue());
    }

    @Test
    void getSetColor() {
        RBNode<Integer> node = new RBNode<>();
        assertEquals(RBNode.Color.BLACK, node.getRight().getColor());
        node.setColor(RBNode.Color.RED);
        assertEquals(RBNode.Color.RED, node.getRight().getColor());
    }
}
