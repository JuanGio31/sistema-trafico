package com.giovani.tad.nonlinear;

import com.giovani.Utilidad;
import com.giovani.Vehiculo;
import com.giovani.tad.linear.DoublyLinkedList;

public class Ciudad {
    private final TablaDispersion dispersion;
    private final DoublyLinkedList<DoublyLinkedList<Interseccion>> matriz;

    public Ciudad(int tamanio) {
        this.dispersion = new TablaDispersion();
        matriz = new DoublyLinkedList<>();
        init(tamanio);
        try {
            getInterseccion(2, 2).setSimbolo('R');
            getInterseccion(4, 4).setSimbolo('V');
            getInterseccion(5, 6).setSimbolo('B');
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void init(int tamanio) {
        for (int i = 0; i < tamanio; i++) {
            matriz.addLast(new DoublyLinkedList<>());
        }
        try {
            for (int i = 0; i < tamanio; i++) {
                for (int j = 0; j < tamanio; j++) {
                    matriz.get(i).addLast(new Interseccion('C'));
                    if (i > 0) {
                        int indiceFilaSuperior = i - 1;
                        enalceArribaAbajo(matriz.get(indiceFilaSuperior).get(j), matriz.get(i).get(j));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void enalceArribaAbajo(Interseccion arriba, Interseccion abajo) {
        arriba.setAbajo(abajo);
        abajo.setArriba(arriba);
    }

    public void print() {
        Utilidad.limpiarPantalla();
        try {
            for (int i = 0; i < matriz.size(); i++) {
                for (int j = 0; j < matriz.get(0).size(); j++) {
                    Utilidad.printColor(matriz.get(i).get(j).getSimbolo());
                    if (j == matriz.get(i).size() - 1) {
                        continue;
                    }
                    System.out.print(" -- ");
                }
                System.out.println(" ");
                if (i < matriz.size() - 1) {
                    for (int j = 0; j < matriz.get(0).size(); j++) {
                        System.out.print(":    ");
                    }
                }
                System.out.println(" ");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Interseccion getInterseccion(int fila, int columna) throws Exception {
        return matriz.get(fila).get(columna);
    }

    public void expandir(int fila, int columna) {

    }

    public DoublyLinkedList<DoublyLinkedList<Interseccion>> getMatriz() {
        return matriz;
    }

    public TablaDispersion getDispersion() {
        return dispersion;
    }
}
