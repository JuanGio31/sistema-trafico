package com.giovani.tad.linear;

import com.giovani.Vehiculo;

public class ColaDePrioridad {
    private static final int NUM_PRIORIDADES = 5;
    private final ListOrdered[] colas;
    private int tam, procesos;

    public ColaDePrioridad() {
        this.colas = new ListOrdered[NUM_PRIORIDADES];
        for (int i = 0; i < NUM_PRIORIDADES; i++) {
            this.colas[i] = new ListOrdered();
        }
        this.tam = 0;
        procesos = 0;
    }

    public void enqueue(int prioridad, Vehiculo vehiculo) {
        if (prioridad < 0 || prioridad >= NUM_PRIORIDADES) {
            throw new IllegalArgumentException("Prioridad inválida. Debe estar entre 0 y 4");
        }
        colas[prioridad].add(vehiculo);
        tam++;
        procesos++;
    }

    public Vehiculo dequeue() {
        if (isEmpty()) {
            return null;
        }
        for (int i = 0; i < NUM_PRIORIDADES; i++) {
            if (!colas[i].isEmpty()) {
                try {
                    tam--;
                    return colas[i].delete();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return null;
                }
            }
        }
        return null;
    }

    public void print() {
        if (isEmpty()) {
            System.out.println("Cola vacia");
        }
        for (int i = 0; i < NUM_PRIORIDADES; i++) {
            if (!colas[i].isEmpty()) {
                colas[i].print();
            }
        }
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "Cola vacia";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < NUM_PRIORIDADES; i++) {
            if (!colas[i].isEmpty()) {
                stringBuilder.append(colas[i].toString()).append(" | ");
            }
        }
        return stringBuilder.toString();
    }

    public boolean isEmpty() {
        return tam == 0;
    }


    public int getTam() {
        return tam;
    }

    public int getNumProcesos() {
        return procesos;
    }
}