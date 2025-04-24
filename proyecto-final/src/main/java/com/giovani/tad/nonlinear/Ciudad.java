package com.giovani.tad.nonlinear;

import com.giovani.tad.linear.DoublyLinkedList;

public class Ciudad {
    private final TablaDispersion dispersion;
    private final DoublyLinkedList<DoublyLinkedList<Interseccion>> matriz;

    public Ciudad() {
        this.dispersion = new TablaDispersion();
        matriz = new DoublyLinkedList<>();
        init();
        try {
            getInterseccion(2, 2).setSimbolo('a');
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void init() {
        for (int i = 0; i < 5; i++) {
            matriz.addLast(new DoublyLinkedList<>());
        }
        try {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    matriz.get(i).addLast(new Interseccion('.'));
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
        try {
            for (int i = 0; i < matriz.size(); i++) {
                for (int j = 0; j < matriz.get(0).size(); j++) {
                    System.out.print(matriz.get(i).get(j).getSimbolo());
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

    public DoublyLinkedList<DoublyLinkedList<Interseccion>> getMatriz() {
        return matriz;
    }

    public TablaDispersion getDispersion() {
        return dispersion;
    }
}
