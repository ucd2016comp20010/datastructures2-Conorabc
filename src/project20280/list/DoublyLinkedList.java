package project20280.list;

import project20280.interfaces.List;

import java.util.Iterator;

public class DoublyLinkedList<E> implements List<E> {

    private static class Node<E> {
        private E data;
        private Node<E> next;
        private Node<E> prev;

        public Node(E e, Node<E> p, Node<E> n) {
            data = e;
            prev = p;
            next = n;
        }

        public E getData() {
            return data;
        }

        public Node<E> getNext() {
            return next;
        }

        public Node<E> getPrev() {
            return prev;
        }

    }

    private Node<E> head;
    private Node<E> tail;
    private int size = 0;

    public DoublyLinkedList() {
        head = new Node<E>(null, null, null);
        tail = new Node<E>(null, head, null);
        head.next = tail;
    }

    private void addBetween(E e, Node<E> pred, Node<E> succ) {
        Node<E> newNode = new Node<E>(e, pred, succ);
        pred.next = succ.prev = newNode;
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public E get(int i) {
        Node<E> current = head.next;
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }
            for(int j = 0; j < i; j++) {
                current = current.next;
            }
        return current.data;
    }

    @Override
    public void add(int i, E e) {
        // TODO
    }

    @Override
    public E remove(int i) {
        // TODO
        return null;
    }

    private class DoublyLinkedListIterator<E> implements Iterator<E> {
        Node<E> curr = (Node<E>) head.next;

        @Override
        public boolean hasNext() {
            return curr != tail;
        }

        @Override
        public E next() {
            E res = curr.data;
            curr = curr.next;
            return res;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new DoublyLinkedListIterator<E>();
    }

    private E remove(Node<E> n) {
        if (n == null || n == head || n == tail || head == null || tail == null) {
            return null;
        }
            n.prev.next = n.next;
            n.next.prev = n.prev;
            size--;
        return n.getData();
    }


    public E first() {
        if (isEmpty()) {
            return null;
        }
        return head.next.getData();
    }

    public E last() {
        if (isEmpty()) {
            return null;
        }
        return tail.prev.getData();
    }

    @Override
    public E removeFirst() {
        // TODO
        return null;
    }

    @Override
    public E removeLast() {
        // TODO
        return null;
    }

    @Override
    public void addLast(E e) {
        Node<E> newNode = new Node<E>(e, tail, null);
        if (isEmpty()) {
            head = tail = newNode;
        }
        else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void addFirst(E e) {
        Node<E> newNode = new Node<E>(e, null, head);
        if (isEmpty()) {
            head = tail = newNode;
        }
        else{
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = head.next;
        while (curr != tail) {
            sb.append(curr.data);
            curr = curr.next;
            if (curr != tail) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        DoublyLinkedList<Integer> ll = new DoublyLinkedList<Integer>();
        ll.addFirst(0);
        ll.addFirst(1);
        ll.addFirst(2);
        ll.addLast(-1);
        System.out.println(ll);

        ll.removeFirst();
        System.out.println(ll);

        ll.removeLast();
        System.out.println(ll);

        for (Integer e : ll) {
            System.out.println("value: " + e);
        }
    }
}