package com.giovani.tad;

public class StackGeneric<T> {
    private int _size;
    private Node<T> top;

    public StackGeneric() {
        this._size = 0;
        this.top = null;
    }

    public void push(T value) {
        var node = new Node<>(value);
        if (top != null) {
            node.setNext(top);
        }
        top = node;
        this._size++;
    }

    public T peek() {
        return top.getValue();
    }

    public T pop() throws Exception {
        if (isEmpty()) throw new Exception("Pila vacia!.");
        T value = top.getValue();
        var temp = top;
        top = temp.getNext();
        this._size--;
        return value;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return this._size;
    }
}
//push, pop, peek
