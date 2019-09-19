package de.szut.simNil.binaryMaple;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BNodeTest {

    @Test
    void setGetLeft() {
        BNode<Integer> node = new BNode<>();
        node.setLeft(new BNode<>(5));
        assertEquals(5, node.getLeft().getValue());
    }

    @Test
    void setGetRight() {
        BNode<Integer> node = new BNode<>();
        node.setRight(new BNode<>(10));
        assertEquals(10, node.getRight().getValue());
    }
}
