package com.giovani;

import com.giovani.tad.linear.StackGeneric;
import com.giovani.tad.nonlinear.AVL;
import com.giovani.tad.nonlinear.Matriz;
import com.giovani.tad.nonlinear.TablaDispersion;

public class Main {
    public static void main(String[] args) throws Exception {
//        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//        for (int i = 0; i < str.length(); i++) {
//            int number = str.charAt(i) - 65;
//            System.out.print(number + " ");
//        }

        Simulacion simulacion = new Simulacion();
        simulacion.getMenu();
    }

    private static void testMatriz(int fila, int columna) {
        Matriz matriz = new Matriz(fila, columna);
        matriz.print();
    }

    private static void hashImplementation() {
        TablaDispersion dispersion = new TablaDispersion();
        dispersion.insertar("P123SAT", new Vehiculo("Ambulancia", "P123SAT", 3, 2));
        dispersion.insertar("P123SAQ", new Vehiculo("Particular", "P123SAQ", 3, 2));
        dispersion.insertar("P123SMP", new Vehiculo("Ambulancia", "P123SMP", 3, 2));
        dispersion.insertar("OO23SAT", new Vehiculo("Policia", "OO23SAT", 3, 2));

        System.out.println(dispersion.buscar("P123SAT").toString());

        dispersion.insertar("A123SAT", new Vehiculo("Ambulancia", "A123SAT", 3, 2));
        dispersion.insertar("A123SAQ", new Vehiculo("Particular", "A123SAQ", 3, 2));
        dispersion.insertar("D123SMP", new Vehiculo("Ambulancia", "D123SMP", 3, 2));
        dispersion.insertar("P123SAT", new Vehiculo("Publico", "P123SAT", 3, 2));

        System.out.println(dispersion.buscar("P123SAT").toString());
    }

    private static void avlImplementation() {
        AVL avl = new AVL();
        avl.insertar(10);
        avl.insertar(5);
        avl.insertar(13);
        avl.insertar(1);
        avl.insertar(6);
        avl.insertar(17);
        avl.preOrder();
        avl.inOrder();
        avl.postOrder();
    }

    private static void stackImplementation() {
        StackGeneric<Integer> pila = new StackGeneric<>();
        pila.push(10);
        pila.push(20);
        pila.push(30);
        pila.push(50);
        final int n = pila.size();
        try {
            for (int i = 0; i < n + 2; i++) {
                System.out.print(pila.pop() + " - ");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}