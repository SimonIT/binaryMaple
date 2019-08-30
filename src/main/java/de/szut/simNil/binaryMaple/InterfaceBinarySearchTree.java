import java.util.List;

/**
 * Interface fuer Binary Search Tress
 * 
 * @author wolke
 * @version 1.0, 29.08.2019
 * 
 */
public interface InterfaceBinarySearchTree<T extends Comparable<T>> {

	public abstract void addValue(T value)
			throws BinarySearchTreeException;

	/**
	 * @param value
	 *            the value we'd like to delete
	 * @throws BinarySearchTreeException
	 *             If the value is not in the tree.
	 */
	public abstract void delValue(T value)
			throws BinarySearchTreeException;

	/**
	 * @param value
	 *            The value we are searching
	 * @return True or False(we couldn't find it)
	 */
	public abstract boolean hasValue(T value);

	public abstract Integer getDepth();

	public abstract List<T> traverse(Order o);

}