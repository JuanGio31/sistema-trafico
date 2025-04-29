package com.giovani;

import com.giovani.tad.nonlinear.Matriz;

public class Ciudad {
    private Matriz matriz;

    public Ciudad(int filas, int columnas) {
        this.matriz = new Matriz(filas, columnas);
    }

    public void print() {
        Utilidad.limpiarPantalla();
        this.matriz.print();
    }
}
