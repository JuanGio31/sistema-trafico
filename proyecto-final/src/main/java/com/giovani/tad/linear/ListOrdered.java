package com.giovani.tad.linear;

import com.giovani.Vehiculo;

public class ListOrdered {
    NodeGeneric<Vehiculo> head;
    int size;

    public ListOrdered() {
        this.head = null;
        this.size = 0;
    }

    public int getSize() {
        return size;
    }

    public void add(Vehiculo valor) {
        var nuevo = new NodeGeneric<>(valor);
        if (isEmpty() || valor.compareTo(head.getValue()) > 0) {
            nuevo.setNext(head);
            head = nuevo;
        } else {
            var temp = head;
            while (temp.getNext() != null && valor.compareTo(temp.getNext().getValue()) < 0) {
                temp = temp.getNext();
            }
            nuevo.setNext(temp.getNext());
            temp.setNext(nuevo);
        }
        size++;
    }

    public Vehiculo delete() throws Exception {
        if (isEmpty()) throw new Exception("Lista vacia!.");
        Vehiculo value = head.getValue();
        var temp = head;
        head = temp.getNext();
        this.size--;
        return value;
    }

    public String print() {
        StringBuilder stringBuilder = new StringBuilder();
        if (head == null) {
            return "Lista vacia";
        } else {
            var temp = head;
            while (temp != null) {
                stringBuilder.append(temp.getValue().getTipo()).append(", ").append(temp.getValue().getPlaca()).append(" | ");
                temp = temp.getNext();
            }
        }
        return stringBuilder.toString();
    }

    public boolean isEmpty() {
        return head == null;
    }
}
