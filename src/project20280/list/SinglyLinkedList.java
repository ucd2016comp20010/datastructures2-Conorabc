package project20280.list;

import project20280.interfaces.List;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SinglyLinkedList<E> implements List<E> {

    private static class Node<E> {

        private final E element;            // reference to the element stored at this node

        /**
         * A reference to the subsequent node in the list
         */
        private Node<E> next;         // reference to the subsequent node in the list

        /**
         * Creates a node with the given element and next node.
         *
         * @param e the element to be stored
         * @param n reference to a node that should follow the new node
         */
        public Node(E e, Node<E> n) {
            element = e;
            next = n;
        }

        // Accessor methods

        /**
         * Returns the element stored at the node.
         *
         * @return the element stored at the node
         */
        public E getElement() {
            return element;
        }

        /**
         * Returns the node that follows this one (or null if no such node).
         *
         * @return the following node
         */
        public Node<E> getNext() {
            return next;
        }

        // Modifier methods

        /**
         * Sets the node's next reference to point to Node n.
         *
         * @param n the node that should follow this one
         */
        public void setNext(Node<E> n) {
            next = n;
        }
    } //----------- end of nested Node class -----------

    /**
     * The head node of the list
     */
    private Node<E> head = null;               // head node of the list (or null if empty)



    //new Linux test

    /**
     * Number of nodes in the list
     */
    private int size = 0;                      // number of nodes in the list

    public SinglyLinkedList() {
    }              // constructs an initially empty list

    //@Override
    public int size() {
        return size;
    }

    //@Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public E get(int position) {
        if(position < 0 || position >= size) {
            throw new IndexOutOfBoundsException();
        }
            Node<E> node = head;
            for (int i = 0; i < position; i++) {
                node = node.getNext();
            }
            return node.getElement();
    }

    @Override
    public void add(int position, E e) {
        if(position < 0 || position > size) {
            throw new IndexOutOfBoundsException();
        }
        else if(position == 0 || size == 0){
            addFirst(e);
            return;
        }
        else {
            Node<E> node = head;
            for (int i = 0; i < position - 1; i++) {
                node = node.next;
            }

            node.setNext(new Node<>(e, node.getNext()));
            size++;
        }
    }


    @Override
    public void addFirst(E e) {
        head = new Node<E>(e, head);
        size++;
    }

    @Override
    public void addLast(E e) {
        Node<E> newest = new Node<E>(e, null);
        if (isEmpty()) {
            head = newest;
        }
        else{
            Node<E> last = head;
        while (last.getNext() != null) {
            last = last.getNext();
        }
        last.setNext(newest);
    }
        size++;
    }

    @Override
    public E remove(int position) {
        if(position < 0 || position >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (position == 0) {
            return removeFirst();
        }
            Node<E> node = head;
            for (int i = 0; i < position - 1; i++) {
                node = node.getNext();
            }
            Node<E> toRemove = node.getNext();
            E data = toRemove.getElement();
            node.setNext(toRemove.getNext());
            size--;
            return data;
    }

    @Override
    public E removeFirst() {
        if(size == 0) {
            return null;
        }
        else {
            Node<E> data = head;
            head = head.getNext();
            size--;
            return data.getElement();
        }

    }

    @Override
    public E removeLast() {
        if(head == null) {
            throw new NoSuchElementException();
        }
        if(size == 1) {
            E data = head.getElement();
            head = null;
            size--;
            return data;
        }

            Node<E> last = head;
            for(int i = 0; i < size-2; i++) {
                last = last.next;
            }
            E data = last.next.getElement();
            last.next = null;
            size--;
            return data;
    }

    //@Override
    public Iterator<E> iterator() {
        return new SinglyLinkedListIterator();
    }

    private class SinglyLinkedListIterator implements Iterator<E> {
        Node<E> curr = head;

        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public E next() {
            E res = curr.getElement();
            curr = curr.next;
            return res;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = head;
        while (curr != null) {
            sb.append(curr.getElement());
            if (curr.getNext() != null)
                sb.append(", ");
            curr = curr.getNext();
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        SinglyLinkedList<Integer> ll = new SinglyLinkedList<Integer>();
        System.out.println("ll " + ll + " isEmpty: " + ll.isEmpty());
        //LinkedList<Integer> ll = new LinkedList<Integer>();

        ll.addFirst(0);
        ll.addFirst(1);
        ll.addFirst(2);
        ll.addFirst(3);
        ll.addFirst(4);
        ll.addLast(-1);
        //ll.removeLast();
        //ll.removeFirst();
        //System.out.println("I accept your apology");
        //ll.add(3, 2);
        System.out.println(ll);
        ll.remove(5);
        System.out.println(ll);

    }
}
