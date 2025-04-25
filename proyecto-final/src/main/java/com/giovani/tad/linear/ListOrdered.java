package com.giovani.tad.linear;

public class ListOrdered<T extends Comparable<T>> {
    NodeGeneric<T> head;
    int size;

    public ListOrdered() {
        this.head = null;
        this.size = 0;
    }

    public int getSize() {
        return size;
    }

    public void add(T valor) {
        var nuevo = new NodeGeneric<>(valor);
        if (isEmpty() || valor.compareTo(head.getValue()) < 0) {
            nuevo.setNext(head);
            head = nuevo;
        } else {
            var temp = head;
            while (temp.getNext() != null && valor.compareTo(temp.getNext().getValue()) > 0) {
                temp = temp.getNext();
            }
            nuevo.setNext(temp.getNext());
            temp.setNext(nuevo);
        }
        size++;
    }

    public T delete() throws Exception {
        if (isEmpty()) throw new Exception("Lista vacia!.");
        T value = head.getValue();
        var temp = head;
        head = temp.getNext();
        this.size--;
        return value;
    }

    public void print() {
        if (head == null) {
            System.out.print("Lista vacia!");
        } else {
            var temp = head;
            while (temp != null) {
                System.out.print(temp.getValue() + " - ");
                temp = temp.getNext();
            }
        }
        System.out.println(" ");
    }

    public boolean isEmpty() {
        return head == null;
    }
}
