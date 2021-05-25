package com.lab8.container;

import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

@NoArgsConstructor
public class BinaryTree<E extends Comparable<E>> implements Container<E> {
    private Node<E> rootNode;
    private int size;

    @Override
    public void add(E element) {
        Node<E> newNode = new Node<>(element);
        if (Objects.isNull(rootNode)) {
            rootNode = newNode;
            size++;
        } else {
            Node<E> currentNode = rootNode;
            Node<E> parentNode;
            while (true) {
                parentNode = currentNode;
                if (element.compareTo(currentNode.item) == 0) {
                    size++;
                    return;
                } else if (element.compareTo(currentNode.item) < 0) {
                    currentNode = currentNode.left;
                    if (Objects.isNull(currentNode)) {
                        parentNode.left = newNode;
                        size++;
                        return;
                    }
                } else {
                    currentNode = currentNode.right;
                    if (Objects.isNull(currentNode)) {
                        parentNode.right = newNode;
                        size++;
                        return;
                    }
                }
            }
        }
    }

    @Override
    public boolean contains(E element) {
        Node<E> currentNode = rootNode;
        while (element.compareTo(currentNode.item) != 0) {
            if (element.compareTo(currentNode.item) < 0) {
                currentNode = currentNode.left;
            } else {
                currentNode = currentNode.right;
            }
            if (Objects.isNull(currentNode)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean remove(E element) {
        Node<E> currentNode = rootNode;
        Node<E> parentNode = rootNode;
        boolean isLeft = true;
        while (element.compareTo(currentNode.item) != 0) {
            parentNode = currentNode;
            if (element.compareTo(currentNode.item) < 0) {
                isLeft = true;
                currentNode = currentNode.left;
            } else {
                isLeft = false;
                currentNode = currentNode.right;
            }
            if (Objects.isNull(currentNode)) {
                return false;
            }
        }

        if (Objects.isNull(currentNode.left) && Objects.isNull(currentNode.right)) {
            if (currentNode == rootNode) {
                rootNode = null;
            } else if (isLeft) {
                parentNode.left = null;
            } else {
                parentNode.right = null;
            }
        } else if (Objects.isNull(currentNode.right)) {
            if (currentNode == rootNode) {
                rootNode = currentNode.left;
            } else if (isLeft) {
                parentNode.left = currentNode.left;
            } else {
                parentNode.right = currentNode.left;
            }
        } else if (Objects.isNull(currentNode.left)) {
            if (currentNode == rootNode) {
                rootNode = currentNode.right;
            } else if (isLeft) {
                parentNode.left = currentNode.right;
            } else {
                parentNode.right = currentNode.right;
            }
        } else {
            Node<E> heir = receiveHeir(currentNode);
            if (currentNode == rootNode) {
                rootNode = heir;
            } else if (isLeft) {
                parentNode.left = heir;
            } else {
                parentNode.right = heir;
            }
        }
        size--;
        return true;
    }

    private Node<E> receiveHeir(Node<E> node) {
        Node<E> parentNode = node;
        Node<E> heirNode = node;
        Node<E> currentNode = node.right;
        while (currentNode != null) {
            parentNode = heirNode;
            heirNode = currentNode;
            currentNode = currentNode.left;
        }
        if (heirNode != node.right) {
            parentNode.left = heirNode.right;
            heirNode.right = node.right;
        }
        return heirNode;
    }

    @Override
    public int size() {
        return size;
    }

    private static class Node<E> {
        E item;
        Node<E> left;
        Node<E> right;

        public Node(E item) {
            this.item = item;
        }
    }

    private class Itr implements Iterator<E> {
        private Object[] elements;
        private int itrSize;

        public Itr() {
            elements = new Object[size];
            if (!Objects.isNull(rootNode)) {
                elements[0] = rootNode.item;
                itrSize = 1;
                traverse(rootNode);
                itrSize = 0;
            }
        }

        @Override
        public boolean hasNext() {
            return itrSize < size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public E next() {
            E element = (E) elements[itrSize];
            itrSize++;
            return element;
        }

        private void traverse(Node<E> node) {
            if (!Objects.isNull(node.left)) {
                elements[itrSize] = node.left.item;
                itrSize++;
                traverse(node.left);
            }
            if (!Objects.isNull(node.right)) {
                elements[itrSize] = node.right.item;
                itrSize++;
                traverse(node.right);
            }

        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    @Override
    public String toString() {
        Object[] elements = new Itr().elements;
        return Arrays.toString(elements);
    }
}
