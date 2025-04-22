package com.giovani.tad.linear;


public class DoublyNode<T> {
    private T value;
    private DoublyNode<T> next;
    private DoublyNode<T> prev;

    public DoublyNode(T value) {
        this.value = value;
        this.next = null;
        this.prev = null;
    }

    public DoublyNode(T value, DoublyNode<T> next, DoublyNode<T> prev) {
        this.value = value;
        this.next = next;
        this.prev = prev;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public DoublyNode<T> getNext() {
        return next;
    }

    public void setNext(DoublyNode<T> next) {
        this.next = next;
    }

    public DoublyNode<T> getPrev() {
        return prev;
    }

    public void setPrev(DoublyNode<T> prev) {
        this.prev = prev;
    }
}
