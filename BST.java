package project6;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * This class represents a binary search tree for generic types
 * @author zhaoruochen
 *
 * @param <E>, the elements in the tree, which must implement the comparable interface
 */
public class BST<E extends Comparable<E>> implements Collection<E>, Iterable<E>, Cloneable {
	private int size;
	private BSTNode<E> root;

	/**
	 * This is a private class for the nodes in the binary search tree.
	 * @author zhaoruochen
	 *
	 * @param <E>, the elements in the tree, which must implement the comparable interface
	 */
	private class BSTNode<E extends Comparable<E>> implements Comparable<BSTNode<E>> {
		protected E data;
		protected BSTNode<E> left;
		protected BSTNode<E> right;

		/**
		 * A private constructor for a tree node
		 * @param data, the data for the node
		 */
		private BSTNode(E data) {
			this.data = data;
		}

		/**
		 * A private constructor for a tree node
		 * @param data, the data for the node
		 * @param left, the left child of the node
		 * @param right, the right child of the node
		 */
		private BSTNode(E data, BSTNode<E> left, BSTNode<E> right) {
			this.data = data;
			this.left = left;
			this.right = right;
		}

		/**
		 * This is a method that compares two nodes based on their data fields
		 */
		@Override
		public int compareTo(BSTNode<E> other) {
			// TODO Auto-generated method stub
			return this.data.compareTo(other.data);
		}

	}

	/**
	 * This is a private class that produces a pre-order iterator over the binary search tree
	 * @author zhaoruochen
	 *
	 */
	private class preorderIterator implements Iterator<E> {
		// pre-order
		private Stack<BSTNode> stack;
		private ArrayList<BSTNode> arr;
		private int current = 0;

		/**
		 * This is an internal constructor that creates an instance of the pre-order iterator
		 */
		private preorderIterator() {
			stack = new Stack<BSTNode>();
			arr = new ArrayList<BSTNode>();
			stack.push(root);
			while (!stack.isEmpty()) {
				BSTNode b = stack.pop();
				System.out.println("b" + b.data);
				arr.add(b);
				if (b.right != null) {
					stack.push(b.right);
				}
				if (b.left != null) {
					stack.push(b.left);
				}
			}
		}

		/**
		 * This method checks if the iterator has a next node
		 * @return true if the iterator has a next node
		 */
		@Override
		public boolean hasNext() {
			return !(current == arr.size());
		}

		/**
		 * This method advances the iterator to the next node
		 * @return previous node before advancement
		 */
		@Override
		public E next() {
			E e = (E) arr.get(current).data;
			current = current + 1;
			return e;
		}
	}

	/**
	 * A public method that creates an instance of the pre-order iterator for the binary search tree
	 * @return an instance of the iterator, which is in pre-order
	 */
	public Iterator<E> preorderIterator() {
		Iterator<E> it = new preorderIterator();
		return it;
	}

	/**
	 * This is an private class that creates an iterator in the post-order, I referred partly to the code on programcreek.com
	 * @author zhaoruochen
	 *
	 */
	private class postorderIterator implements Iterator<E> {
		// post-order
		private Stack<BSTNode> stack;
		private ArrayList<BSTNode> arr;
		private int current = 0;

		/**
		 * An internal constructor that creates an instance of the post-order iterator, I referred to the programcreek.com website
		 */
		public postorderIterator() {
			stack = new Stack<BSTNode>();
			arr = new ArrayList<BSTNode>();
			if (root == null)
				return;
			stack.push(root);
			BSTNode last = null;
			while (!stack.isEmpty()) {
				BSTNode b = stack.peek();
				// go down the tree to find the leaves, either we start at the root, or
				// we haven't reached the leaves
				if (last == null || last.left == b || last.right == b) {
					// if there's a left node
					if (b.left != null) {
						stack.push(b.left);
					}
					// if there's a right node
					else if (b.right != null) {
						stack.push(b.right);
					}
					// if it's a leaf
					else {
						stack.pop();
						arr.add(b);
					}
				}
				// when i already found left leaves, go up
				else if (b.left == last && b.right != null) {
					stack.push(b.right);
				} else if (b.left == last && b.right == null) {
					stack.pop();
					arr.add(b);
				}
				// when I found right leaves, go up
				else if (b.right == last) {
					stack.pop();
					arr.add(b);
				}
				last = b;
			}
		}

		/**
		 * Checks if the iterator has a next node
		 * @return true if the iterator has a next node
		 */
		@Override
		public boolean hasNext() {
			return !(current == arr.size());
		}

		/**
		 * This method advances the iterator to the next node
		 * @return previous node before advancement
		 */
		@Override
		public E next() {
			E e = (E) arr.get(current).data;
			current = current + 1;
			return e;
		}
	}

	/**
	 * This method returns an instance of the iterator in the post-order
	 * @return an instance of post-order iterator
	 */
	public Iterator<E> postorderIterator() {
		Iterator<E> it = new postorderIterator();
		return it;
	}

	/**
	 * A private class of an in order iterator for the binary search tree
	 * @author zhaoruochen
	 *
	 */
	private class BSTiterator implements Iterator<E> {
		// in-order
		private Stack<BSTNode> stack;
		private ArrayList<BSTNode> arr;
		private int current = 0;

		/**
		 * A constructor that creates an instance of the in order iterator
		 */
		private BSTiterator() {
			arr = new ArrayList<BSTNode>();
			stack = new Stack<BSTNode>();
			BSTNode b = root;
			while (!stack.isEmpty() || b != null) {
				if (b != null) {
					stack.push(b);
					b = b.left;
				} else {
					BSTNode a = stack.pop();
					// System.out.println(a.data);
					arr.add(a);
					b = a.right;
				}
			}
		}

		/**
		 * This method checks if the iterator has a next node
		 * @return true if the iterator has a next node
		 */
		@Override
		public boolean hasNext() {
			return !(current == arr.size());
		}

		/**
		 * This method advances the iterator to the next node
		 * @return the previous node before advancement
		 */
		@Override
		public E next() {
			E e = (E) arr.get(current).data;
			current = current + 1;
			return e;
		}
	}

	/**
	 * This method returns an instance of the in order iterator for the binary search tree
	 * @return an instance of the in order iterator
	 */
	public Iterator<E> iterator() {
		Iterator<E> it = new BSTiterator();
		return it;
	}

	/**
	 * This method returns the size of the binary search tree
	 * @return size as an integer
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * This method checks if the binary search tree is empty
	 * @return true if it's empty
	 */
	@Override
	public boolean isEmpty() {
		if (root == null)
			return true;
		else
			return false;
	}

	/**
	 * This method checks if the binary search tree contains the object o
	 * @param o, the object to be checked
	 * @return true if the object is present
	 */
	@Override
	public boolean contains(Object o) {
		if (this.size == 0) {
			return false;
		}
		if (o == null) {
			throw new NullPointerException();
		}
		if (root.data.getClass() != o.getClass()) {
			throw new ClassCastException();
		}
		return contains(o, root);
	}

	/**
	 * This is a helper method that checks if the object is contained in a node's subtree
	 * @param o, the object to be checked
	 * @param b, the node whose subtree we check in
	 * @return true if the node's subtree contains the object
	 */
	private boolean contains(Object o, BSTNode b) {
		if (b == null)
			return false;
		else if (((Comparable<E>) o).compareTo((E) b.data) < 0)
			return contains(o, b.left);
		else if (((Comparable<E>) o).compareTo((E) b.data) > 0)
			return contains(o, b.right);
		else
			return true;
	}

	/**
	 * This method puts the binary search tree to an array in the in order traversal
	 * @return an array of objects, which are all objects in the BST in in order traversal
	 */
	@Override
	public Object[] toArray() {
		Object[] ob = new Object[this.size];
		Iterator it = this.iterator();
		int i = 0;
		while (it.hasNext()) {
			ob[i] = it.next();
			i++;
		}
		return ob;
	}

	/**
	 * Returns an array containing all of the elements in this collection, I refered to the linkedlist class in java source file
	 * @param T - the component type of the array to contain the collection
	 * @param a - the array into which the elements of this collection are to be stored, if it is big enough; otherwise, a new array of the same runtime type is allocated for this purpose.
	 * @return an array containing all of the elements in this collection
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		if (a.length < this.size)
			a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
		int i = 0;
		Iterator it = this.iterator();
		while (it.hasNext()) {
			a[i++] = (T) it.next();
		}
		if (a.length > this.size)
			a[size] = null;
		return a;
	}

	/**
	 * This method Ensures that this collection contains the specified element (optional operation), I referred to the class notes here
	 * @param e, the element whose presence in this collection is to be ensured
	 * @return true if this collection changed as a result of the call
	 * @throws NullPointerException if the element is null
	 * @throws ClassCastException if the class type and the element type are not the same
	 */
	@Override
	public boolean add(E e) {
		if (e == null) {
			throw new NullPointerException();
		}
		if (root == null) {
			root = new BSTNode(e);
			size++;
			return true;
		}
		if (root.data.getClass() != e.getClass()) {
			throw new ClassCastException();
		}
		if (this.contains(e)) {
			return false;
		}
		add(e, root);
		size++;
		return true;
	}

	/**
	 * A recursive private helper method that add an element to a node's subtree
	 * @param e, the element to be added
	 * @param root, the node whose subtree we want to add it in
	 * @return the node we created
	 */
	private BSTNode add(E e, BSTNode root) {
		if (root == null) {
			root = new BSTNode<E>(e);
			return root;
		} else if (root.data.compareTo(e) < 0) {
			root.right = add(e, root.right);
		} else {
			root.left = add(e, root.left);
		}
		return root;
	}

	/**
	 * Removes a single instance of the specified element from this collection, if it is present, I refered to the class notes here
	 * @param o - element to be removed from this collection, if present
	 * @return true if an element was removed
	 * @throws ClassCastException - if the type of the specified element is incompatible with this collection
	 * @throw NullPointerException - if the specified element is null and this collection does not permit null elements
	 */
	@Override
	public boolean remove(Object o) {
		if (o == null)
			throw new NullPointerException();
		if (o.getClass() != root.data.getClass())
			throw new ClassCastException();
		if (!this.contains(o))
			return false;
		remove(o, root);
		size--;
		return true;
	}

	/**
	 * A private helper method that removes an object from a node's subtree
	 * @param o, the object to remove
	 * @param root, the node whose subtree we want to remove it from
	 * @return the node that replaces the removed node
	 */
	private BSTNode remove(Object o, BSTNode root) {
		if (root == null)
			return null;
		if (root.data.compareTo(o) > 0) {
			// root is bigger, we find on the left side
			root.left = remove(o, root.left);
		} else if (root.data.compareTo(o) < 0) {
			root.right = remove(o, root.right);
		} else {
			// we found the reference to the node
			if (root.left != null && root.right != null) {
				// two children, replace the current node with new value
				BSTNode pre = getPredecessor(root);
				root.data = pre.data;
				root.right = remove(pre.data, root.left);
			} else if (root.left != null) {
				// no right children
				root = root.left;
			} else if (root.right != null) {
				// no left children
				root = root.right;
			} else {
				// no children at all
				root = null;
			}
		}
		return root;
	}

	/**
	 * A private helper method that gets the greatest element in the left subtree for a node
	 * @param n, the node whose subtree we choose from
	 * @return the node that is the predecessor of node n
	 */
	private BSTNode getPredecessor(BSTNode n) {
		if (n.left == null) {
			// this won't happen
			return null;
		} else {
			BSTNode current = n.left;
			while (current.right != null)
				current = current.right;
			return current;
		}
	}

	/**
	 * This method checks if the binary search tree contains all objects in a specific collection
	 * @return true if it does
	 * @param c, the collection we want to check
	 * @throws ClassCastException when the BST element type and the collection object type don't match
	 * @throws NullPointerException when the object we're checking is null
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object o : c) {
			if (root.data.getClass() != o.getClass()) {
				throw new ClassCastException();
			}
			if (o == null) {
				throw new NullPointerException();
			}
			if (!this.contains(o)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Add all elements from a specific collection to the tree, not implemented here
	 */
	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Remove all elements in a specific collection from the tree, not implemented here
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Retains only the elements in this collection that are contained in the specified collection, not implemented here
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Clears everything from the binary search tree
	 */
	@Override
	public void clear() {
		root = null;
		size = 0;
	}

	/**
	 * This method gets the instance of the element in the tree that equals to value
	 * @param value, the value we want to check
	 * @return the instance of the element in the tree which equals to value
	 */
	public E get(E value) {
		Iterator<E> it = this.iterator();
		while (it.hasNext()) {
			E e = it.next();
			if (e.equals(value)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * Puts the binary search tree to a string
	 * @return String that holds information on the tree
	 */
	public String toString() {
		String s = "";
		Iterator<E> it = this.iterator();
		while (it.hasNext()) {
			s += "[" + String.valueOf(it.next()) + "]" + ", ";
		}
		return s;
	}

	/**
	 * produces tree like string representation of this BST
	 * @author Prof. Joanna
	 * @return string containing tree-like representation of this bST
	 */
	public String toStringTreeFormat() {
		StringBuilder s = new StringBuilder();
		preOrderPrint(root, 0, s);
		return s.toString();
	}

	/**
	 * Uses pre-order traversal to produce a tree-like representation of this BST
	 * @param tree the root of the current subtree
	 * @param level depth of the current recursive call in the tree to determine the indentation of each item
	 * @param output the string that accumulated the string representation of this BST
	 */
	private void preOrderPrint(BSTNode<E> tree, int level, StringBuilder output) {
		if (tree != null) {
			String spaces = "\n";
			if (level > 0) {
				for (int i = 0; i < level - 1; i++)
					spaces += " ";
				spaces += "|--";
			}
			output.append(spaces);
			output.append(tree.data);
			preOrderPrint(tree.left, level + 1, output);
			preOrderPrint(tree.right, level + 1, output);
		} else {
			String spaces = "\n";
			if (level > 0) {
				for (int i = 0; i < level - 1; i++)
					spaces += " ";
				spaces += "|--";
			}
			output.append(spaces);
			output.append("null");
		}
	}

	/**
	 * returns the smallest element that is greater than or equal to an element
	 * @param e, the element we want to find the ceiling of
	 * @return the element that is the smallest among elements greater than or equal to e
	 * @throws ClassCastException when e is not of the same type as the binary search tree elements
	 * @throws NullPointerException when the element we want to find the ceiling of is null
	 */
	public E ceiling(E e) {
		if (root.data.getClass() != e.getClass()) {
			throw new ClassCastException();
		}
		if (e == null) {
			throw new NullPointerException();
		}
		if (ceilingHelper(root, e) != null) {
			return (E) ceilingHelper(root, e).data;
		} else {
			return null;
		}
	}

	/**
	 * A private helper method that finds the ceiling of an element in the subtree of a node
	 * @param b, the node whose subtree we want to find the ceiling in
	 * @param e, the element that we want to find the ceiling of
	 * @return the tree node containing the element which is the ceiling of e
	 */
	private BSTNode ceilingHelper(BSTNode b, E e) {
		if (b.data == null) {
			return null;
		}
		if (b.data.compareTo(e) > 0) {
			if (b.left != null)
				if (ceilingHelper(b.left, e) != null)
					return ceilingHelper(b.left, e);
				else
					return b;
			else
				return b;
		}
		if (b.data.compareTo(e) < 0) {
			if (b.right != null)
				return ceilingHelper(b.right, e);
			else
				return null;
		} else {
			return b;
		}
	}

	/**
	 * This method returns a shallow copy of the current BST
	 * @return a shallow copy of the current BST
	 * @throws CloneNotSupportedException when the BST cannot be cloned
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/**
	 * This method finds the smallest element in the BST
	 * @return the smallest element in the BST
	 * @throws NoSuchElementException when the BST is empty
	 */
	public E first() {
		if (root == null) {
			throw new NoSuchElementException();
		}
		// returns the lowest, leftmost, first element in inorder
		Iterator<E> it = this.iterator();
		return it.next();
	}

	/**
	 * returns the greatest element that is smaller than or equal to an element
	 * @param e, the element we want to find the floor of
	 * @return the element that is the greatest among elements smaller than or equal to e
	 * @throws ClassCastException when e is not of the same type as the binary search tree elements
	 * @throws NullPointerException when the element we want to find the floor of is null
	 */
	public E floor(E e) {
		if (root.data.getClass() != e.getClass()) {
			throw new ClassCastException();
		}
		if (e == null) {
			throw new NullPointerException();
		}
		return (E) floorHelper(root, e).data;
	}

	/**
	 * A private helper method that finds the floor of an element in the subtree of a node
	 * @param b, the node whose subtree we want to find the floor in
	 * @param e, the element that we want to find the floor of
	 * @return the tree node containing the element which is the floor of e
	 */
	private BSTNode floorHelper(BSTNode b, E e) {
		if (b.data.compareTo(e) < 0) {
			// if be is already smaller, find its larger
			if (b.right != null)
				if (floorHelper(b.right, e) != null)
					return floorHelper(b.right, e);
				else
					return b;
			else
				return b;
		}
		if (b.data.compareTo(e) > 0) {
			// if it's larger
			if (b.left != null)
				return floorHelper(b.left, e);
			else
				return null;
		} else {
			return b;
		}
	}

	/**
	 * returns the smallest element that is strictly greater than an element
	 * @param e, the element we want to find the higher of
	 * @return the element that is the smallest among elements greater than e
	 * @throws ClassCastException when e is not of the same type as the binary search tree elements
	 * @throws NullPointerException when the element we want to find the higher of is null
	 */
	public E higher(E e) {
		if (root.data.getClass() != e.getClass()) {
			throw new ClassCastException();
		}
		if (e == null) {
			return null;
		}
		if (higherHelper(root, e) != null) {
			return (E) higherHelper(root, e).data;
		} else {
			return null;
		}
	}

	/**
	 * A private helper method that finds the higher of an element in the subtree of a node
	 * @param b, the node whose subtree we want to find the higher in
	 * @param e, the element that we want to find the higher of
	 * @return the tree node containing the element which is the higher of e
	 */
	private BSTNode higherHelper(BSTNode b, E e) {
		if (b.data.compareTo(e) > 0) {
			if (b.left != null)
				if (higherHelper(b.left, e) != null)
					return higherHelper(b.left, e);
				else
					return b;
			else
				return b;
		} else {
			if (b.right != null)
				return higherHelper(b.right, e);
			else
				return null;
		}
	}

	/**
	 * finds the largest element in the BST
	 * @return the largest element in the BST
	 * @throws NoSuchElementException when the BST is empty
	 */
	public E last() {
		if (root == null) {
			throw new NoSuchElementException();
		}
		// highest, rightmost
		BSTNode b = root;
		while (b.right != null) {
			b = b.right;
		}
		return (E) b.data;
	}

	/**
	 * returns the greatest element that is strictly less than an element
	 * @param e, the element we want to find the lower of
	 * @return the element that is the greatest among elements less than e
	 * @throws ClassCastException when e is not of the same type as the binary search tree elements
	 * @throws NullPointerException when the element we want to find the lower of is null
	 */
	public E lower(E e) {
		if (root.data.getClass() != e.getClass()) {
			throw new ClassCastException();
		}
		if (e == null) {
			throw new NullPointerException();
		}
		return (E) lowerHelper(root, e).data;
	}

	/**
	 * A private helper method that finds the lower of an element in the subtree of a node
	 * @param b, the node whose subtree we want to find the lower in
	 * @param e, the element that we want to find the lower of
	 * @return the tree node containing the element which is the lower of e
	 */
	private BSTNode lowerHelper(BSTNode b, E e) {
		if (b.data.compareTo(e) < 0) {
			if (b.right != null)
				if (lowerHelper(b.right, e) != null)
					return lowerHelper(b.right, e);
				else
					return b;
			else
				return b;
		} else {
			if (b.left != null)
				return lowerHelper(b.left, e);
			else
				return null;
		}
	}

	/**
	 * A method that checks if the current BST is equal to an object o
	 * @param o, the object to be compared with the BST
	 * @return true if they are equal
	 */
	public boolean equals(Object o) {
		// only checks all the nodes!
		if (o == null) {
			if (root == null) {
				return true;
			} else
				return false;
		}
		if (o.getClass() != this.getClass()) {
			return false;
		}
		if (this.size() != ((BST) o).size)
			return false;
		Iterator it = this.iterator();
		while (it.hasNext()) {
			if (!((BST<E>) o).contains(it.next())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Not implemented in this class
	 */
	public int hashCode() {
		return super.hashCode();
	}
}
