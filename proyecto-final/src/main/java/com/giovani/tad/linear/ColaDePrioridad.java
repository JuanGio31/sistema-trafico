package com.giovani.tad.linear;

import com.giovani.Vehiculo;

public class ColaDePrioridad {
    private final ListOrdered[] elementos;
    private static final int MAX_PRIORIDAD = 5;

    public ColaDePrioridad() {
        this.elementos = new ListOrdered[MAX_PRIORIDAD + 1];
        for (int i = 0; i < elementos.length; i++) {
            if (i > 0) {
                elementos[i] = new ListOrdered();
            }
        }
    }

    public void enqueue(int prioridad, Vehiculo valor) {
        if (prioridad > 5 || prioridad < 1) {
            System.out.println("Fuera del rango de prioridades.");
        } else {
            elementos[prioridad].add(valor);
        }
    }

    public Vehiculo dequeue() {
        for (int i = MAX_PRIORIDAD; i >= 1; i--) {
            if (!elementos[i].isEmpty()) {
                try {
                    return elementos[i].delete();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return null;
                }
            }
        }
        return null;
    }
}
