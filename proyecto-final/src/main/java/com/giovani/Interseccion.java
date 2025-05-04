package com.giovani;

import com.giovani.tad.linear.ColaDePrioridad;

/**
 * Clase Interseccion, que representa a un nodo de la matriz ortogonal
 */
public class Interseccion {
    private char simbolo;
    private ColaDePrioridad cola;
    private boolean bloqueo;
    private Semaforo semaforo;

    public Interseccion(char simbolo) {
        this.simbolo = simbolo;
        this.bloqueo = false;
        this.semaforo = null;
        this.cola = new ColaDePrioridad();
    }

    public char getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(char simbolo) {
        if (simbolo == 'B') {
            this.bloqueo = true;
        } else if (simbolo == 'R') {
            if (semaforo == null) {
                semaforo = new Semaforo('R');
            }
            this.bloqueo = true;
        } else if (simbolo == 'V') {
            if (semaforo == null) {
                semaforo = new Semaforo('V');
            }
            this.bloqueo = false;
        }
        this.simbolo = simbolo;
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
            if (this.semaforo.getEstado()) {
                setSimbolo('V');
            } else {
                setSimbolo('R');
            }
        }
    }

    public int getComplejidad() {
        if (cola != null) {
            return cola.getTam();
        }
        return 0;
    }

    public boolean estaBloqueado() {
        return this.bloqueo;
    }
}
