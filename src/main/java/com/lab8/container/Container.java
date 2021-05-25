package com.lab8.container;


public interface Container<E> extends Iterable<E>{
    void add(E element);
    boolean contains(E element);
    boolean remove(E element);
    int size();
}
