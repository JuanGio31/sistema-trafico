package com.giovani.tad.linear;

public class StackGeneric<T> {
    private int _size;
    private NodeGeneric<T> top;

    public StackGeneric() {
        this._size = 0;
        this.top = null;
    }

    public void push(T value) {
        var node = new NodeGeneric<>(value);
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

    public void limpiar() {
        this.top = null;
        this._size = 0;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return this._size;
    }

    public void set_size(int _size) {
        this._size = _size;
    }

    public NodeGeneric<T> getTop() {
        return top;
    }

    public void setTop(NodeGeneric<T> top) {
        this.top = top;
    }
}
//push, pop, peek
