package com.giovani.tad.nonlinear;

import com.giovani.Interseccion;

public class NodoAVL {
    private Interseccion dato;
    private int fe;
    private NodoAVL hijoIzquierdo;
    private NodoAVL hijoDerecho;

    public NodoAVL(Interseccion complejidad) {
        this.dato = complejidad;
        this.hijoIzquierdo = null;
        this.hijoDerecho = null;
        this.fe = 0;
    }

    public int getComplejidad() {
        return dato.getComplejidad();
    }

    public void setComplejidad(Interseccion interseccion) {
        this.dato = interseccion;
    }

    public int getFe() {
        return fe;
    }

    public void setFe(int fe) {
        this.fe = fe;
    }

    public NodoAVL getHijoIzquierdo() {
        return hijoIzquierdo;
    }

    public void setHijoIzquierdo(NodoAVL hijoIzquierdo) {
        this.hijoIzquierdo = hijoIzquierdo;
    }

    public NodoAVL getHijoDerecho() {
        return hijoDerecho;
    }

    public void setHijoDerecho(NodoAVL hijoDerecho) {
        this.hijoDerecho = hijoDerecho;
    }

    public Interseccion getDato() {
        return dato;
    }

    public void setDato(Interseccion dato) {
        this.dato = dato;
    }
}
