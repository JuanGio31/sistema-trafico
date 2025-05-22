package com.giovani;

import com.giovani.tad.linear.ColaDePrioridad;

/**
 * Clase Interseccion, que representa a un nodo de la matriz ortogonal
 */
public class Interseccion {
    private String id;
    private ColaDePrioridad cola;
    private boolean esLibre;
    private Semaforo semaforo;
    private boolean esDestino;

    public Interseccion() {
        this.esLibre = true;
        this.semaforo = null;
        this.cola = new ColaDePrioridad();
        this.esDestino = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public char getSimbolo() {
        if (this.semaforo != null) {
            return this.semaforo.getColor();
        }
        if (!this.esLibre) {
            return 'B';
        }
        if (cola.isEmpty()) {
            return '*';
        }
        return 'C';
    }

    public ColaDePrioridad getCola() {
        return cola;
    }

    public void setCola(ColaDePrioridad cola) {
        this.cola = cola;
    }

    public int vehiculosProcesados() {
        if (this.cola == null) {
            return -1;
        }
        return this.cola.getNumProcesos();
    }

    public Semaforo getSemaforo() {
        return semaforo;
    }

    public void aumentarProcesos() {
        if (semaforo != null) {
            this.semaforo.aumentarContador();
        }
    }

    public int getComplejidad() {
        if (cola != null) {
            return cola.getNumProcesos();
        }
        return 0;
    }

    public boolean isLibre() {
        if (this.semaforo != null) {
            return this.semaforo.getEstado();
        }
        return esLibre;
    }

    public void setEsLibre(boolean esLibre) {
        this.esLibre = esLibre;
    }

    public void setSemaforo(Semaforo semaforo) {
        this.semaforo = semaforo;
    }

    public boolean isEsDestino() {
        return esDestino;
    }

    public void setEsDestino(boolean esDestino) {
        this.esDestino = esDestino;
    }

    public void mostrarEstado() {
        if (this.semaforo != null) {
            System.out.println("Interseccion: " + this.semaforo.mostarEstado());
            cola.print();
        } else if (!this.esLibre) {
            System.out.println("Existe un bloqueo en esta interseccion");
        } else {
            this.cola.print();
        }
    }
}
