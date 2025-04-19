package com.giovani.tad;


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
                System.out.print(current.getValue() + " -> ");
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

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return this._size;
    }
}
