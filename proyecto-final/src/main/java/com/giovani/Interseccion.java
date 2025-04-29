package com.giovani;

import com.giovani.tad.linear.ColaDePrioridad;

public class Interseccion {
    private char simbolo;
    private ColaDePrioridad cola;

    public Interseccion(char simbolo) {
        this.simbolo = simbolo;
        this.cola = new ColaDePrioridad();
    }

    public char getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(char simbolo) {
        this.simbolo = simbolo;
    }

    public ColaDePrioridad getCola() {
        return cola;
    }

    public void setCola(ColaDePrioridad cola) {
        this.cola = cola;
    }
}
