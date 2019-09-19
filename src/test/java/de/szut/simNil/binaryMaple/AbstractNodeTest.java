package de.szut.simNil.binaryMaple;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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

    @Test
    void testEquals() {
        assertEquals(new AbstractNode<>(8) {
        }, new AbstractNode<>(8) {
        });

        assertEquals(new AbstractNode<Integer>() {
        }, new AbstractNode<Integer>() {
        });

        assertNotEquals(new AbstractNode<Integer>() {
        }, new AbstractNode<>(8) {
        });

        assertNotEquals(new AbstractNode<>(8.5) {
        }, new AbstractNode<>(8) {
        });

        assertNotEquals(new AbstractNode<>(8.5) {
        }, "");
    }
}
