package com.giovani.tad.nonlinear;

import com.giovani.Utilidad;

import java.util.Random;

public class Matriz {
    private final Random rd = new Random();
    private final int fila;
    private final int columna;
    private final int celdas;
    private final NodoOrtogonal inicio;


    public Matriz(int fila, int columna) {
        this.fila = fila; //maximo filas ->26
        this.columna = columna;
        this.inicio = new NodoOrtogonal('C');
        this.celdas = fila * columna;
        init();
    }

    private void init() {
        crearCapaXY();
        crearSemaforo();
        crearBloqueos();
    }

    public void print() {
        printEncabezado();
        int idFila = 65;
        NodoOrtogonal temp;
        for (var i = inicio; i != null; i = i.getAbajo()) {
            Utilidad.printCyan((char) idFila);
            System.out.print("-[ ");
            for (var j = i; j != null; j = j.getDerecha()) {
                Utilidad.printColor(j.getSimbolo());
                if (j.getDerecha() == null) {
                    continue;
                }
                System.out.print(" -- ");
            }
            System.out.println(" ");
            temp = i;
            if (temp.getAbajo() != null) {
                while (temp != null) {
                    System.out.print("    :");
                    temp = temp.getDerecha();
                }
            }
            System.out.println(" ");
            idFila++;
        }
        System.out.println(" ");
    }

    private void crearSemaforo() {
        int totalSemaforos = (int) (celdas * 0.25);
        int contador = 0, maxIteraciones = 1000, iteracion = 0;
        do {
            int i = rd.nextInt(fila);
            int j = rd.nextInt(columna);
            var temp = getNodo(i, j);
            if (temp.getSimbolo() != 'V' || temp.getSimbolo() != 'R') {
                temp.setSimbolo('R');
                contador++;
            }
            iteracion++;
            if (iteracion == maxIteraciones) break;
        } while (contador != totalSemaforos);
    }

    private void crearBloqueos() {
        int totalBloqueos = (int) (celdas * 0.15);
        int contador = 0, maxIteraciones = 1000, iteracion = 0;
        do {
            int i = rd.nextInt(fila);
            int j = rd.nextInt(columna);
            var temp = getNodo(i, j);
            if (temp.getSimbolo() != 'V' || temp.getSimbolo() != 'R' || temp.getSimbolo() != 'B') {
                temp.setSimbolo('B');
                contador++;
            }
            iteracion++;
            if (iteracion == maxIteraciones) break;
        } while (contador != totalBloqueos);
    }

    private NodoOrtogonal getNodo(int fila, int columna) {
        var temp = inicio;
        for (int i = 0; i < fila - 1; i++) {
            temp = temp.getAbajo();
        }
        for (int i = 0; i < columna - 1; i++) {
            temp = temp.getDerecha();
        }
        return temp;
    }

    private void printEncabezado() {
        for (int i = 0; i < columna; i++) {
            if (i >= 10) {
                System.out.print(" | ");
                Utilidad.printPurpura((i + 1) + "");
                continue;
            }
            System.out.print("  | ");
            Utilidad.printPurpura((i + 1) + "");
        }
        System.out.println(" ");
        System.out.print(" =");
        for (int i = 0; i < columna; i++) {
            System.out.print("=====");
        }
        System.out.println(" ");
    }

    private void crearCapaXY() {
        crearEncabezado();
        crearPrimeraColumna();
        var filaSuperior = inicio;
        var filaActual = inicio.getAbajo();
        for (int i = 0; i < fila - 1; i++) {
            var temp = filaSuperior.getDerecha();
            for (int j = 0; j < columna - 1; j++) {
                var nuevo = new NodoOrtogonal('C');
                enlazarHV(temp, filaActual, nuevo);
                temp = temp.getDerecha();
                filaActual = filaActual.getDerecha();
            }
            filaSuperior = filaSuperior.getAbajo();
            filaActual = filaSuperior.getAbajo();
        }
    }

    private void crearEncabezado() {
        var temp = inicio;
        for (int i = 0; i < columna - 1; i++) {
            var nuevo = new NodoOrtogonal('C');
            enlazarH(temp, nuevo);
            temp = temp.getDerecha();
        }
    }

    private void crearPrimeraColumna() {
        var temp = inicio;
        for (int i = 0; i < fila - 1; i++) {
            var nuevo = new NodoOrtogonal('C');
            enlazarV(temp, nuevo);
            temp = temp.getAbajo();
        }
    }

    private void enlazarH(NodoOrtogonal izq, NodoOrtogonal der) {
        izq.setDerecha(der);
        der.setIzquierda(izq);
    }

    private void enlazarV(NodoOrtogonal arriba, NodoOrtogonal abajo) {
        arriba.setAbajo(abajo);
        abajo.setArriba(arriba);
    }

    private void enlazarHV(NodoOrtogonal arriba, NodoOrtogonal izquierda, NodoOrtogonal nuevo) {
        nuevo.setArriba(arriba);
        nuevo.setIzquierda(izquierda);
        arriba.setAbajo(nuevo);
        izquierda.setDerecha(nuevo);
    }
}
