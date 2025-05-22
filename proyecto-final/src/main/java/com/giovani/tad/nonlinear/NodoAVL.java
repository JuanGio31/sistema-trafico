package com.giovani.tad.nonlinear;

import com.giovani.Interseccion;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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

    public String textoGraphviz() {
        String texto = "\"" + dato.getId() + "\" [label=\"" + dato.getId() + "\n" + dato.getComplejidad() + "\"];\n";

        if (hijoIzquierdo != null) {
            texto += "\"" + dato.getId() + "\" -> \"" + hijoIzquierdo.dato.getId() + "\";\n";
            texto += hijoIzquierdo.textoGraphviz();
        }
        if (hijoDerecho != null) {
            texto += "\"" + dato.getId() + "\" -> \"" + hijoDerecho.dato.getId() + "\";\n";
            texto += hijoDerecho.textoGraphviz();
        }

        return texto;
    }
}
