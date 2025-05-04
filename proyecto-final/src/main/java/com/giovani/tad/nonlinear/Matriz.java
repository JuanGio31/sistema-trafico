package com.giovani.tad.nonlinear;

import com.giovani.Utilidad;
import com.giovani.Vehiculo;
import com.giovani.tad.linear.StackGeneric;

import java.util.Random;

public class Matriz {
    private StackGeneric<String> eventos;
    private static final float PORCENTAJE_SEMAFORO = 0.25f;
    private static final float PORCENTAJE_BLOQUEOS = 0.15f;
    private final Random rd;
    private int fila;
    private int columna;
    private int celdas;
    private NodoOrtogonal inicio;


    public Matriz(int fila, int columna, Random random) {
        this.rd = random;
        this.fila = fila; //maximo filas ->26
        this.columna = columna;
        this.inicio = new NodoOrtogonal('C');
        this.celdas = fila * columna;
        crearCapaXY(fila, columna, inicio);
    }

    public void crearObstaculos() {
        this.celdas = fila * columna;
        crearSemaforo();
        crearBloqueos();
    }

    public int redimensionar(int filaMax, int columnaMax) {
        if (filaMax < fila && columnaMax < columna) return 0;
        int redimensiones = 0;
        if (columnaMax > columna) {
            expandirEnHorizontal(inicio, columnaMax);
            redimensiones = 1;
        }
        if (filaMax > fila) {
            expandirEnVertical(inicio, filaMax, columnaMax);
            if (redimensiones == 1) {
                redimensiones = 3;
            } else {
                redimensiones = 2;
            }
        }
        return redimensiones;
    }

    private void expandirEnVertical(NodoOrtogonal actual, int filaMax, int columnaMax) {
        NodoOrtogonal ultimaFila = actual;
        while (ultimaFila.getAbajo() != null) {
            ultimaFila = ultimaFila.getAbajo();
        }
        for (int i = 0; i < filaMax - fila; i++) {
            NodoOrtogonal primerNodoNuevaFila = new NodoOrtogonal('C');
            enlazarV(ultimaFila, primerNodoNuevaFila);
            NodoOrtogonal nodoFilaSuperior = ultimaFila.getDerecha();
            NodoOrtogonal nodoActualNuevaFila = primerNodoNuevaFila;

            int columnasActuales = Math.max(columna, columnaMax);
            for (int j = 0; j < columnasActuales - 1; j++) {
                NodoOrtogonal nuevo = new NodoOrtogonal('C');
                enlazarHV(nodoFilaSuperior, nodoActualNuevaFila, nuevo);
                nodoFilaSuperior = nodoFilaSuperior.getDerecha();
                nodoActualNuevaFila = nodoActualNuevaFila.getDerecha();
            }
            ultimaFila = primerNodoNuevaFila;
        }
    }

    private void expandirEnHorizontal(NodoOrtogonal actual, int columnaMax) {
        NodoOrtogonal filaActual = actual;
        while (filaActual != null) {
            NodoOrtogonal nodoColumnaFinal = filaActual;
            while (nodoColumnaFinal.getDerecha() != null) {
                nodoColumnaFinal = nodoColumnaFinal.getDerecha();
            }
            for (int j = 0; j < columnaMax - columna; j++) {
                NodoOrtogonal nuevo = new NodoOrtogonal('C');
                enlazarH(nodoColumnaFinal, nuevo);
                nodoColumnaFinal = nodoColumnaFinal.getDerecha();
            }
            filaActual = filaActual.getAbajo();
        }
    }

    public void print() {
        printEncabezado();
        int idFila = 65;
        NodoOrtogonal temp;
        for (var i = inicio; i != null; i = i.getAbajo()) {
            Utilidad.printCyan((char) idFila + "");
            System.out.print("-[ ");
            for (var j = i; j != null; j = j.getDerecha()) {
                Utilidad.printCharColor(j.getSimbolo());
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

    public NodoOrtogonal posicioValida(int fila, int columna) {
        if (fila < 0 || fila >= this.fila || columna < 0 || columna >= this.columna) return null;
        var temp = getNodo(fila, columna);
        if (temp == null || temp.getInterseccion().estaBloqueado()) return null;
        return temp;
    }

    public void encolar(int fila, int columna, Vehiculo v) {
        var nodo = getNodo(fila, columna);
        if (nodo != null) {
//            if (v.getPrioridad() != -1)
            nodo.getInterseccion().getCola().enqueue(v.getPrioridad(), v);
            nodo.getInterseccion().aumentarProcesos();
            printMensaje(v);
            return;
        }
        Utilidad.printCadenaEnRojo("Posicion no disponible");
    }

    private void printMensaje(Vehiculo v) {
        System.out.println("////////////////////////////////////////////////////////////");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("El vehiculo: ").append(v.getTipo()).append(", ");
        stringBuilder.append("con placa: ").append(v.getPlaca());
        stringBuilder.append(" con origen: (");
        int x = v.getOrigen().getX();
        int y = v.getOrigen().getY();
        stringBuilder.append(((char) (y + 65))).append(x + 1).append(")");

        stringBuilder.append(" se desplaza a: (");
        x = v.getActual().getX();
        y = v.getActual().getY();
        stringBuilder.append(((char) (y + 65))).append(x + 1).append(")");
        x = v.getDestino().getX();
        y = v.getDestino().getY();
        stringBuilder.append(" con destino: (");
        stringBuilder.append(((char) (y + 65))).append(x + 1).append(")\n");
        eventos.push(stringBuilder.toString());
    }

    private void crearSemaforo() {
        int totalSemaforos = (int) (celdas * PORCENTAJE_SEMAFORO);
        int contador = 0, maxIteraciones = 1000, iteracion = 0;
        do {
            int i = rd.nextInt(fila);
            int j = rd.nextInt(columna);
            var temp = getNodo(i, j);
            if (temp.getSimbolo() == 'C') {
                if (temp.getInterseccion().getCola().getTam() == 0) {
                    temp.setSimbolo('R');
                    contador++;
                }
            }
            iteracion++;
            if (iteracion == maxIteraciones) break;
        } while (contador != totalSemaforos);
    }

    private void crearBloqueos() {
        int totalBloqueos = (int) (celdas * PORCENTAJE_BLOQUEOS);
        int contador = 0, maxIteraciones = 1000, iteracion = 0;
        do {
            int i = rd.nextInt(fila);
            int j = rd.nextInt(columna);
            var temp = getNodo(i, j);
            if (temp.getSimbolo() == 'C') {
                if (temp.getInterseccion().getCola().getTam() == 0) {
                    temp.setSimbolo('B');
                    temp.getInterseccion().setCola(null);
                    contador++;
                }
            }
            iteracion++;
            if (iteracion == maxIteraciones) break;
        } while (contador != totalBloqueos);
    }

    public NodoOrtogonal getNodo(int _fila, int _columna) {
        var temp = inicio;
        for (int i = 0; i < _fila; i++) {
            if (temp.getAbajo() != null) {
                temp = temp.getAbajo();
            }
        }
        for (int i = 0; i < _columna; i++) {
            if (temp.getDerecha() != null) {
                temp = temp.getDerecha();
            }
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

    private void crearCapaXY(int y, int x, NodoOrtogonal actual) {
        crearPrimeraFila(x, actual);
        crearPrimeraColumna(y, actual);
        var filaSuperior = actual;
        var filaActual = actual.getAbajo();
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

    private void crearPrimeraFila(int x, NodoOrtogonal actual) {
        var temp = actual;
        for (int i = 0; i < x - 1; i++) {
            var nuevo = new NodoOrtogonal('C');
            enlazarH(temp, nuevo);
            temp = temp.getDerecha();
        }
    }

    private void crearPrimeraColumna(int y, NodoOrtogonal actual) {
        var temp = actual;
        for (int i = 0; i < y - 1; i++) {
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

    public NodoOrtogonal getInicio() {
        return inicio;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public StackGeneric<String> getEventos() {
        return eventos;
    }

    public void setEventos(StackGeneric<String> eventos) {
        this.eventos = eventos;
    }
}
