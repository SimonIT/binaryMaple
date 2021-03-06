package de.szut.simNil.binaryMaple.standard;

import de.szut.simNil.binaryMaple.BinarySearchTreeException;
import de.szut.simNil.binaryMaple.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StandardBinarySearchTreeTest {
    @Test
    void addValue() {
        StandardBinarySearchTree<Integer> tree = new StandardBinarySearchTree<>();
        try {
            tree.addValue(5);
            tree.addValue(2);
            tree.addValue(1);
        } catch (BinarySearchTreeException e) {
            e.printStackTrace();
        }
        assertEquals(5, tree.getRoot().getValue());
        assertEquals(2, tree.getRoot().getLeft().getValue());
        assertEquals(1, tree.getRoot().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getLeft().getRight().getValue());
        assertNull(tree.getRoot().getLeft().getRight().getValue());
        assertNull(tree.getRoot().getRight().getValue());
    }

    @Test
    void delValue() {
        StandardBinarySearchTree<Integer> tree = new StandardBinarySearchTree<>();
        try {
            tree.addValue(17);
            tree.addValue(10);
            tree.addValue(-6);
            tree.addValue(-2);
            tree.addValue(14);
            tree.addValue(24);
            tree.addValue(29);
            tree.delValue(29);  // node with value 29 has no child
            tree.delValue(-6);  // node with value -6 has one child
            tree.delValue(10);  // node with value 10 has two children
        } catch (BinarySearchTreeException e) {
            e.printStackTrace();
        }

        assertEquals(17, tree.getRoot().getValue());
        assertEquals(-2, tree.getRoot().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getLeft().getValue());
        assertEquals(14, tree.getRoot().getLeft().getRight().getValue());
        assertNull(tree.getRoot().getLeft().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getRight().getRight().getValue());
        assertEquals(24, tree.getRoot().getRight().getValue());
        assertNull(tree.getRoot().getRight().getLeft().getValue());
        assertNull(tree.getRoot().getRight().getRight().getValue());
    }

    // the following tests actually test the functionality of AbstractBinarySearchTree but use addValue and delValue
    @Test
    void hasValue() {
        StandardBinarySearchTree<Integer> tree = new StandardBinarySearchTree<>();
        try {
            tree.addValue(5);
            tree.addValue(-2);
            tree.addValue(-134);
            tree.addValue(2525);
            tree.addValue(34);
        } catch (BinarySearchTreeException e) {
            e.printStackTrace();
        }
        assertTrue(tree.hasValue(-134));
        assertFalse(tree.hasValue(1));
    }

    @Test
    void getNodeWithValue() {
        StandardBinarySearchTree<String> tree = new StandardBinarySearchTree<>();
        try {
            tree.addValue("This is a node");
        } catch (BinarySearchTreeException e) {
            e.printStackTrace();
        }
        assertEquals("This is a node", tree.getNodeWithValue("This is a node").getValue());
    }

    @Test
    void getHeight() {
        StandardBinarySearchTree<Double> tree = new StandardBinarySearchTree<>() {
        };
        try {
            tree.addValue(-6.78);
            tree.addValue(-12.36);
            tree.addValue(14.51);
            tree.addValue(-4.97);
            tree.addValue(20.9234165);
            tree.addValue(-9.92934127356);
            tree.addValue(-31.5261);
            tree.addValue(-5.23341);
            tree.addValue(-6.0);
            tree.addValue(2.5123);
            tree.addValue(10.1);
        } catch (BinarySearchTreeException e) {
            e.printStackTrace();
        }
        assertEquals(5, tree.getHeight());
    }

    @Test
    void checkTraversalsWithArbitraryValues() {
        StandardBinarySearchTree<Integer> tree = new StandardBinarySearchTree<>() {
        };
        try {
            tree.addValue(5);
            tree.addValue(435);
            tree.addValue(-1234);
            tree.addValue(3);
            tree.addValue(25);
            tree.addValue(32452);
            tree.addValue(0);
            tree.addValue(32);
            tree.addValue(224);
            tree.addValue(11);
            tree.addValue(16);
            tree.addValue(-5351);
            tree.addValue(-223);
            tree.addValue(-77);
            tree.addValue(54311);
            tree.addValue(66);
            tree.addValue(61);
        } catch (BinarySearchTreeException e) {
            e.printStackTrace();
        }

        List<Integer> expectedPreOrder = new ArrayList<>();
        expectedPreOrder.add(5);
        expectedPreOrder.add(-1234);
        expectedPreOrder.add(-5351);
        expectedPreOrder.add(3);
        expectedPreOrder.add(0);
        expectedPreOrder.add(-223);
        expectedPreOrder.add(-77);
        expectedPreOrder.add(435);
        expectedPreOrder.add(25);
        expectedPreOrder.add(11);
        expectedPreOrder.add(16);
        expectedPreOrder.add(32);
        expectedPreOrder.add(224);
        expectedPreOrder.add(66);
        expectedPreOrder.add(61);
        expectedPreOrder.add(32452);
        expectedPreOrder.add(54311);

        List<Integer> expectedInOrder = new ArrayList<>();
        expectedInOrder.add(-5351);
        expectedInOrder.add(-1234);
        expectedInOrder.add(-223);
        expectedInOrder.add(-77);
        expectedInOrder.add(0);
        expectedInOrder.add(3);
        expectedInOrder.add(5);
        expectedInOrder.add(11);
        expectedInOrder.add(16);
        expectedInOrder.add(25);
        expectedInOrder.add(32);
        expectedInOrder.add(61);
        expectedInOrder.add(66);
        expectedInOrder.add(224);
        expectedInOrder.add(435);
        expectedInOrder.add(32452);
        expectedInOrder.add(54311);

        List<Integer> expectedPostOrder = new ArrayList<>();
        expectedPostOrder.add(-5351);
        expectedPostOrder.add(-77);
        expectedPostOrder.add(-223);
        expectedPostOrder.add(0);
        expectedPostOrder.add(3);
        expectedPostOrder.add(-1234);
        expectedPostOrder.add(16);
        expectedPostOrder.add(11);
        expectedPostOrder.add(61);
        expectedPostOrder.add(66);
        expectedPostOrder.add(224);
        expectedPostOrder.add(32);
        expectedPostOrder.add(25);
        expectedPostOrder.add(54311);
        expectedPostOrder.add(32452);
        expectedPostOrder.add(435);
        expectedPostOrder.add(5);

        List<Integer> expectedLevelOrder = new ArrayList<>();
        expectedLevelOrder.add(5);
        expectedLevelOrder.add(-1234);
        expectedLevelOrder.add(435);
        expectedLevelOrder.add(-5351);
        expectedLevelOrder.add(3);
        expectedLevelOrder.add(25);
        expectedLevelOrder.add(32452);
        expectedLevelOrder.add(0);
        expectedLevelOrder.add(11);
        expectedLevelOrder.add(32);
        expectedLevelOrder.add(54311);
        expectedLevelOrder.add(-223);
        expectedLevelOrder.add(16);
        expectedLevelOrder.add(224);
        expectedLevelOrder.add(-77);
        expectedLevelOrder.add(66);
        expectedLevelOrder.add(61);

        assertEquals(expectedPreOrder, tree.traverse(Order.PREORDER));
        assertEquals(expectedInOrder, tree.traverse(Order.INORDER));
        assertEquals(expectedPostOrder, tree.traverse(Order.POSTORDER));
        assertEquals(expectedLevelOrder, tree.traverse(Order.LEVELORDER));
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
}
