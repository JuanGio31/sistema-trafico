package com.giovani.tad.nonlinear;

public class Interseccion {
    private char simbolo;
    private Interseccion arriba, abajo;

    public Interseccion(char simbolo) {
        this.simbolo = simbolo;
    }

    public char getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(char simbolo) {
        this.simbolo = simbolo;
    }

    public Interseccion getArriba() {
        return arriba;
    }

    public void setArriba(Interseccion arriba) {
        this.arriba = arriba;
    }

    public Interseccion getAbajo() {
        return abajo;
    }

    public void setAbajo(Interseccion abajo) {
        this.abajo = abajo;
    }
}
