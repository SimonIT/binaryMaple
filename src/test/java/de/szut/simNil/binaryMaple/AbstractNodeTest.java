package de.szut.simNil.binaryMaple;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AbstractNodeTest {
    @Test
    void setGetValue() {
        AbstractNode<Integer> node = new AbstractNode<>() {
        };
        node.setValue(69);
        assertEquals(69, node.getValue());
    }

    @Test
    void testToString() {
        assertEquals("null", new AbstractNode() {
        }.toString());
        assertEquals("4", new AbstractNode<>(4) {
        }.toString());
    }
}
