package com.giovani.tad.linear;


public class DoublyLinkedList<T> {
    private DoublyNode<T> head;
    private DoublyNode<T> tail;
    private int _size;

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this._size = 0;
    }

    public void addLast(T value) {
        var node = new DoublyNode<>(value);
        if (isEmpty()) {
            head = tail = node;
        } else {
            tail.setNext(node);
            node.setPrev(tail);
            tail = node;
        }
        this._size++;
    }

    public void addFirst(T value) {
        var node = new DoublyNode<>(value);
        if (isEmpty()) {
            head = tail = node;
        } else {
            node.setNext(head);
            head.setPrev(node);
            head = node;
        }
        this._size++;
    }

    public void print() {
        if (head == null) {
            System.out.println("La lista está vacia");
        } else {
            var current = head;
            while (current != null) {
                System.out.println(current.getValue() + " -> ");
                current = current.getNext();
            }
        }
    }

    public void printLastToFirst() {
        if (head == null) {
            System.out.println("La lista está vacia");
        } else {
            var current = tail;
            while (current != null) {
                System.out.print(current.getValue() + " -> ");
                current = current.getPrev();
            }
        }
    }

    public void removeFirst() {
        if (head == null) {
            System.out.println("Lista vacia!");
            return;
        }
        head = head.getNext();
        this._size--;
    }

    public void removeLast() {
        if (head == null) {
            System.out.println("Lista vacia!");
            return;
        }
        tail = tail.getPrev();
        tail.setNext(null);
        this._size--;
    }

    public T get(int index) throws Exception {
        if (isEmpty()) throw new Exception("Lista vacia");
        if (index < 0 || index >= this._size) {
            throw new Exception("Indice fuera de rango");
        }
        if (index == 0) return head.getValue();
        if (index == this._size - 1) return tail.getValue();
        if (this._size / 2 > index) {
            var current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
            return current.getValue();
        }
        var current = tail;
        for (int i = 0; i < this._size - index - 1; i++) {
            current = current.getPrev();
        }
        return current.getValue();
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return this._size;
    }

    public DoublyNode<T> getHead() {
        return head;
    }

    public DoublyNode<T> getTail() {
        return tail;
    }
}
