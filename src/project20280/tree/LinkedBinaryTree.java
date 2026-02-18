package project20280.tree;

import project20280.interfaces.Position;

import java.util.ArrayList;

/**
 * Concrete implementation of a binary tree using a node-based, linked structure.
 */
public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {

    static java.util.Random rnd = new java.util.Random();

    /** The root of the binary tree */
    protected Node<E> root = null;

    /** The number of nodes in the binary tree */
    private int size = 0;

    /** Constructs an empty binary tree. */
    public LinkedBinaryTree() {}

    public static LinkedBinaryTree<Integer> makeRandom(int n) {
        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<>();
        bt.root = randomTree(null, 1, n);
        bt.size = n;
        return bt;
    }

    public static <T extends Integer> Node<T> randomTree(Node<T> parent, Integer first, Integer last) {
        if (first > last) return null;
        Integer treeSize = last - first + 1;
        Integer leftCount = rnd.nextInt(treeSize);
        Node<T> root = new Node<>((T) (Integer) (first + leftCount), parent, null, null);
        root.setLeft(randomTree(root, first, first + leftCount - 1));
        root.setRight(randomTree(root, first + leftCount + 1, last));
        return root;
    }

    public static void main(String[] args) {
        LinkedBinaryTree<String> bt = new LinkedBinaryTree<>();
        String[] arr = {"A", "B", "C", "D", "E", null, "F", null, null, "G", "H", null, null, null, null};
        bt.createLevelOrder(arr);
        System.out.println(bt.toBinaryTreeString());
    }

    /** Factory function to create a new node storing element e. */
    protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right) {
        return new Node<>(e, parent, left, right);
    }

    /** Validates the position and returns it as a node. */
    protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node)) throw new IllegalArgumentException("Not valid position type");
        Node<E> node = (Node<E>) p;
        if (node.getParent() == node) throw new IllegalArgumentException("p is no longer in the tree");
        return node;
    }

    // ---- Required BinaryTree accessors ----

    @Override
    public int size() {
        return size;
    }

    @Override
    public Position<E> root() {
        return root;
    }

    @Override
    public Position<E> parent(Position<E> p) throws IllegalArgumentException {
        return validate(p).getParent();
    }

    @Override
    public Position<E> left(Position<E> p) throws IllegalArgumentException {
        return validate(p).getLeft();
    }

    @Override
    public Position<E> right(Position<E> p) throws IllegalArgumentException {
        return validate(p).getRight();
    }

    // ---- Update methods ----

    public Position<E> addRoot(E e) throws IllegalStateException {
        if (!isEmpty()) throw new IllegalStateException("Tree is not empty");
        root = createNode(e, null, null, null);
        size = 1;
        return root;
    }

    public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> parent = validate(p);
        if (parent.getLeft() != null) throw new IllegalArgumentException("p already has a left child");
        Node<E> child = createNode(e, parent, null, null);
        parent.setLeft(child);
        size++;
        return child;
    }

    public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> parent = validate(p);
        if (parent.getRight() != null) throw new IllegalArgumentException("p already has a right child");
        Node<E> child = createNode(e, parent, null, null);
        parent.setRight(child);
        size++;
        return child;
    }

    public E set(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        E old = node.getElement();
        node.setElement(e);
        return old;
    }

    public void attach(Position<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2)
            throws IllegalArgumentException {
        Node<E> node = validate(p);
        if (isInternal(p)) throw new IllegalArgumentException("p must be a leaf");

        size += t1.size() + t2.size();

        if (!t1.isEmpty()) {
            t1.root.setParent(node);
            node.setLeft(t1.root);
            t1.root = null;
            t1.size = 0;
        }

        if (!t2.isEmpty()) {
            t2.root.setParent(node);
            node.setRight(t2.root);
            t2.root = null;
            t2.size = 0;
        }
    }

    public E remove(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        if (numChildren(p) == 2) throw new IllegalArgumentException("p has two children");

        Node<E> child = (node.getLeft() != null ? node.getLeft() : node.getRight());
        if (child != null) child.setParent(node.getParent());

        if (node == root) {
            root = child;
        } else {
            Node<E> parent = node.getParent();
            if (node == parent.getLeft()) parent.setLeft(child);
            else parent.setRight(child);
        }

        size--;
        E old = node.getElement();
        node.setElement(null);
        node.setLeft(null);
        node.setRight(null);
        node.setParent(node); // defunct
        return old;
    }

    // ---- createLevelOrder (build complete tree shape) ----

    public void createLevelOrder(ArrayList<E> l) {
        root = null;
        size = 0;
        if (l == null || l.isEmpty()) return;
        root = createLevelOrderHelper(l, null, 0);
    }

    private Node<E> createLevelOrderHelper(ArrayList<E> l, Node<E> parent, int i) {
        if (i < 0 || i >= l.size()) return null;
        E e = l.get(i);
        if (e == null) return null;

        Node<E> node = createNode(e, parent, null, null);
        size++;

        node.setLeft(createLevelOrderHelper(l, node, 2 * i + 1));
        node.setRight(createLevelOrderHelper(l, node, 2 * i + 2));
        return node;
    }

    public void createLevelOrder(E[] arr) {
        root = null;
        size = 0;
        if (arr == null || arr.length == 0) return;
        root = createLevelOrderHelper(arr, null, 0);
    }

    private Node<E> createLevelOrderHelper(E[] arr, Node<E> parent, int i) {
        if (i < 0 || i >= arr.length) return null;
        E e = arr[i];
        if (e == null) return null;

        Node<E> node = createNode(e, parent, null, null);
        size++;

        node.setLeft(createLevelOrderHelper(arr, node, 2 * i + 1));
        node.setRight(createLevelOrderHelper(arr, node, 2 * i + 2));
        return node;
    }

    // ---- Height (edge-based: leaf = 0) ----

    @Override
    public int height(Position<E> v) throws IllegalArgumentException {
        Node<E> node = validate(v);
        return height(node);
    }

    private int height(Node<E> node) {
        if (node == null) return -1;
        return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
    }

    @Override
    public int height() {
        if (isEmpty()) return 0;
        return height(root);
    }

    @Override
    public String toString() {
        return positions().toString();
    }


    // ---- Optional BST insert (not used by your tests) ----

    public void insert(E e) {
        if (root == null) {
            addRoot(e);
        } else {
            addRecursive(root, e);
        }
    }

    @SuppressWarnings("unchecked")
    private Node<E> addRecursive(Node<E> p, E e) {
        if (p == null) {
            size++;
            return createNode(e, null, null, null);
        }

        Comparable<? super E> key = (Comparable<? super E>) e;
        int cmp = key.compareTo(p.getElement());

        if (cmp < 0) {
            Node<E> child = addRecursive(p.getLeft(), e);
            if (p.getLeft() == null) {
                p.setLeft(child);
                child.setParent(p);
            }
        } else if (cmp > 0) {
            Node<E> child = addRecursive(p.getRight(), e);
            if (p.getRight() == null) {
                p.setRight(child);
                child.setParent(p);
            }
        }
        return p;
    }

    public String toBinaryTreeString() {
        BinaryTreePrinter<E> btp = new BinaryTreePrinter<>(this);
        return btp.print();
    }

    /** Nested static class for a binary tree node. */
    public static class Node<E> implements Position<E> {
        private E element;
        private Node<E> left, right, parent;

        public Node(E e, Node<E> p, Node<E> l, Node<E> r) {
            element = e;
            left = l;
            right = r;
            parent = p;
        }

        @Override
        public E getElement() {
            return element;
        }

        public void setElement(E e) {
            element = e;
        }

        public Node<E> getLeft() {
            return left;
        }

        public void setLeft(Node<E> n) {
            left = n;
        }

        public Node<E> getRight() {
            return right;
        }

        public void setRight(Node<E> n) {
            right = n;
        }

        public Node<E> getParent() {
            return parent;
        }

        public void setParent(Node<E> n) {
            parent = n;
        }

        public String toString() {
            if (element == null) return "⦰";
            return element.toString();
        }
    }
}
