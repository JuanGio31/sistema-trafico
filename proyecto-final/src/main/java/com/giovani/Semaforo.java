package com.giovani;

public class Semaforo {
    private boolean esLibre;
    private int contador;

    public Semaforo(char c) {
        this.esLibre = c == 'V';
        this.contador = 0;
    }

    public char getColor() {
        if (this.esLibre) {
            return 'V';
        }
        return 'R';
    }

    public void cambiarEstado() {
        this.esLibre = !this.esLibre;
    }

    public void aumentarContador() {
        this.contador++;
        if (this.contador == 3) {
            cambiarEstado();
            this.contador = 0;
        }
    }

    public boolean getEstado() {
        return esLibre;
    }

    public String mostarEstado() {
        if (this.esLibre) {
            return "Semaforo en Verde";
        }
        return "Semaforo en Rojo";
    }
}
