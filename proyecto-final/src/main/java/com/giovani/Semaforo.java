package com.giovani;

public class Semaforo {
    private boolean estado;
    private int contador;

    public Semaforo(char c) {
        this.estado = c == 'V';
        this.contador = 0;
    }

    private void cambiarEstado() {
        this.estado = !this.estado;
    }

    public void aumentarContador() {
        this.contador++;
        if (this.contador == 3) {
            cambiarEstado();
            this.contador = 0;
        }
    }

    public boolean getEstado() {
        return estado;
    }

    public String mostarEstado() {
        if (this.estado) {
            return "Semaforo en Verde";
        }
        return "Semaforo en Rojo";
    }
}
