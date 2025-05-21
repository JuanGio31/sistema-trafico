package com.giovani;

import com.giovani.tad.linear.ColaDePrioridad;

/**
 * Clase Interseccion, que representa a un nodo de la matriz ortogonal
 */
public class Interseccion {
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
            return cola.getTam();
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
}
