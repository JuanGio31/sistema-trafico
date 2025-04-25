package com.giovani;

import com.giovani.tad.linear.ColaDePrioridad;
import com.giovani.tad.linear.ListOrdered;
import com.giovani.tad.linear.StackGeneric;
import com.giovani.tad.nonlinear.AVL;
import com.giovani.tad.nonlinear.Ciudad;
import com.giovani.tad.nonlinear.TablaDispersion;

import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception {
//        Simulacion traffic = new Simulacion();
//        traffic.getMenu();
//        avlImplementation();
//        hashImplementation();
//        testCiudad();

//        int min = 1;
//        int max = 5;
//        ColaDePrioridad cola = new ColaDePrioridad();
//        for (int i = 0; i < 10; i++) {
//            int numeroAleatorioEntero = (int) (Math.random() * (max - min + 1)) + min;
//            int valor = (int) (Math.random() * (100 - 10 + 1)) + 10;
//            System.out.print("[" + numeroAleatorioEntero + "," + valor + "] ");
//            cola.enqueue(numeroAleatorioEntero, valor);
//        }
//        System.out.println(" ");
//
//        for (int i = 0; i < 10; i++) {
//            System.out.print("[ ,");
//            System.out.print(cola.dequeue().toString());
//            System.out.print("] ");
//        }
//        System.out.println(" ");

        /*
        int base = 65;
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < str.length(); i++) {
            int number = str.charAt(i) - 65;
            System.out.print(number + " ");
        }
*/

    }

    private static void testCiudad() {
        Ciudad ciudad = new Ciudad(10);
        ciudad.print();
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