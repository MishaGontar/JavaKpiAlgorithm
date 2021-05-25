package com.lab8.hashtable;

import com.lab8.container.Container;

import java.lang.reflect.Array;
import java.util.Arrays;

public class HashTable<E, T extends Container> {
    private T[] table;
    private final Class<T> typeContainer;
    private int capacity = 16;
    private int size = 0;
    private final double loadFactor;

    @SuppressWarnings("unchecked")
    public HashTable(Class<T> typeContainer) {
        this.typeContainer = typeContainer;
        this.loadFactor = 0.75;
        table = (T[]) Array.newInstance(typeContainer, capacity);
    }

    @SuppressWarnings("unchecked")
    public HashTable(Class<T> typeContainer, double loadFactor) {
        this.typeContainer = typeContainer;
        this.loadFactor = loadFactor;
        table = (T[]) Array.newInstance(typeContainer, capacity);
    }

    private int hash(E element) {
        return Math.abs(element.hashCode()) % capacity;
    }

    @SuppressWarnings("unchecked")
    public boolean add(E element) {
        extension();
        int hash = hash(element);
        if (table[hash] == null && hash < capacity) {
            try {
                table[hash] = typeContainer.getConstructor().newInstance();
            } catch (ReflectiveOperationException exc) {
                return false;
            }
        }
        if (table[hash].size() > 0) {
            if (table[hash].contains(element))
                return false;
        }
        size++;
        table[hash].add(element);
        return true;
    }

    @SuppressWarnings("unchecked")
    public boolean contains(E element) {
        int hash = hash(element);
        if (table[hash] == null) {
            return false;
        }
        return table[hash].contains(element);
    }

    @SuppressWarnings("unchecked")
    public boolean remove(E element) {
        int hash = hash(element);
        if (table[hash] == null) {
            return false;
        }
        size--;
        return table[hash].remove(element);
    }

    private void extension() {
        if (Double.compare(size / loadFactor, capacity) >= 0) {
            table = Arrays.copyOf(table, capacity <<= 1);
        }
    }

    @Override
    public String toString() {
        String[] elements = new String[size];
        int c = 0;
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                for (Object element : table[i]) {
                    elements[c] = element.toString();
                    c++;
                }
            }
        }
        return Arrays.toString(elements);
    }

    public String toStringTable() {
        return Arrays.deepToString(table);
    }
}
