package de.szut.simNil.binaryMaple;

/**
 * This exception can be used when something goes wrong within a method of a binary search tree.
 * @author Jürgen Wolkenhauer
 */
public class BinarySearchTreeException extends Exception {
    /**
     * unique identification of class
     */
    private static final long serialVersionUID = -1341555983657868251L;

    public BinarySearchTreeException() {
        super();
    }

    /**
     * @param string the exception message that should be displayed
     */
    public BinarySearchTreeException(String string) {
        super(string);
    }
}
