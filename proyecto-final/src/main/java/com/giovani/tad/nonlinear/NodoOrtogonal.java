package com.giovani.tad.nonlinear;

import com.giovani.Interseccion;

public class NodoOrtogonal {
    private Interseccion interseccion;
    private NodoOrtogonal arriba;
    private NodoOrtogonal abajo;
    private NodoOrtogonal derecha;
    private NodoOrtogonal izquierda;

    public NodoOrtogonal(char simbolo) {
        this.interseccion = new Interseccion(simbolo);
        this.abajo = null;
        this.arriba = null;
        this.derecha = null;
        this.izquierda = null;
    }

    public char getSimbolo() {
        return interseccion.getSimbolo();
    }

    public void setSimbolo(char simbolo) {
        this.interseccion.setSimbolo(simbolo);
    }

    public NodoOrtogonal getArriba() {
        return arriba;
    }

    public void setArriba(NodoOrtogonal arriba) {
        this.arriba = arriba;
    }

    public NodoOrtogonal getAbajo() {
        return abajo;
    }

    public void setAbajo(NodoOrtogonal abajo) {
        this.abajo = abajo;
    }

    public NodoOrtogonal getDerecha() {
        return derecha;
    }

    public void setDerecha(NodoOrtogonal derecha) {
        this.derecha = derecha;
    }

    public NodoOrtogonal getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(NodoOrtogonal izquierda) {
        this.izquierda = izquierda;
    }
}
