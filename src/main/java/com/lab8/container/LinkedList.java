package com.lab8.container;

import lombok.NoArgsConstructor;

import java.util.Iterator;
import java.util.Objects;

@NoArgsConstructor
public class LinkedList<E> implements Container<E> {
    private int size = 0;
    private Node<E> first;
    private Node<E> last;

    @Override
    public void add(E element) {
        Node<E> lastNode = last;
        Node<E> newNode = new Node<>(lastNode, element, null);
        last = newNode;
        if (lastNode == null) {
            first = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    @Override
    public boolean contains(E element) {
        return !Objects.isNull(getNode(element));
    }

    @Override
    public boolean remove(E element) {
        Node<E> node = getNode(element);
        if (Objects.isNull(node))
            return false;
        if (Objects.isNull(node.prev)) {
            removeFirst();
        } else if (Objects.isNull(node.next)) {
            removeLast();
        } else {
            Node<E> before = node.prev;
            Node<E> after = node.next;
            before.next = after;
            after.prev = before;
            size--;
        }
        return true;
    }

    public void removeLast() {
        Node<E> node = last.prev;
        if (Objects.isNull(node)) {
            first = null;
        } else {
            last = node;
            node.next = null;
        }
        size--;
    }

    public void removeFirst() {
        Node<E> node = first.next;
        if (Objects.isNull(node)) {
            last = null;
        } else {
            first = node;
            node.prev = null;
        }
        size--;
    }

    @Override
    public int size() {
        return size;
    }

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E item, Node<E> next) {
            super();
            this.next = next;
            this.item = item;
            this.prev = prev;
        }

    }

    private Node<E> node(int index) {
        if (index < 0 || index > size)
            throw new IllegalArgumentException(String.format("index: %d; size: %d", index, size));
        Node<E> node;
        if (index < size >> 1) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;

    }

    private Node<E> getNode(E element) {
        Node<E> firstNode = first;
        Node<E> lastNode = last;
        for (int i = 0; i <= size >> 1; i++) {
            if (Objects.equals(firstNode.item, element)) {
                return node(i);
            } else if (Objects.equals(lastNode.item, element)) {
                return node(size - 1 - i);
            }
            firstNode = firstNode.next;
            lastNode = lastNode.prev;
        }
        return null;
    }

    private class Itr implements Iterator<E> {
        Node<E> f = first;

        Itr() {
        }

        @Override
        public boolean hasNext() {
            return f != null;
        }

        @Override
        public E next() {
            E item = f.item;
            f = f.next;
            return item;
        }

    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    @Override
    public String toString() {
        String[] s = new String[size];
        Node<E> f = first;
        for (int i = 0; i < size; i++) {
            s[i] = f.item.toString();
            f = f.next;
        }
        return "[" + String.join(", ", s) + "]";
    }
}
