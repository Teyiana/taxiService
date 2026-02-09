package com.javacourse.solvd.taxi;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList<E> implements Iterable<E>{
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public void add(E element) {
        Node<E> newNode = new Node<>(element);
        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    public E get(int index) {
        return getNode(index).element;
    }

    public void remove(int index) {
        Node<E> toRemove = getNode(index);
        if (toRemove.prev != null) {
            Node<E> prev = toRemove.prev;
            prev.next = toRemove.next;
        } else {
            head = toRemove.next;
        }
        if (toRemove.next != null) {
            Node<E> next = toRemove.next;
            next.prev = toRemove.prev;
        } else {
            tail = toRemove.prev;
        }
        size--;
    }

    public Node<E> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        int mid = size / 2 + 1;

        if (index < mid) {
            Node<E> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;

        } else {
            Node<E> current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private Node<E> current = head;
            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E e = current.element;
                current = current.next;
                return e;
            }
        };
    }


    private class Node<E> {
        private final E element;
        private Node<E> next;
        private Node<E> prev;

        public Node(E element) {
            this.element = element;
        }
    }
}
