package com.giovani;

import com.giovani.tad.linear.ColaDePrioridad;
import com.giovani.tad.linear.StackGeneric;
import com.giovani.tad.nonlinear.Matriz;
import com.giovani.tad.nonlinear.TablaDispersion;

import java.util.Random;


public class Main {
    public static void main(String[] args) throws Exception {
        Simulacion simulacion = new Simulacion();
        simulacion.getMenu();
        //testEntrada();
    }

    private static void testMatriz(int fila, int columna) {
        Random random = new Random();
        Matriz matriz = new Matriz(fila, columna, random);
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

//    public static void testEntrada() {
//        TablaDispersion dispersion = new TablaDispersion();
//        var temp = new GestorArchivo(dispersion, );
//        var v = temp.readCSV("/home/giovanic/Documents/Tareas/1S2025/EDD/Proyectos/sistema-trafico/proyecto-final/temp.csv");
//        v.print();
//
//        System.out.println(" ");
//        ColaDePrioridad colaDePrioridad = new ColaDePrioridad();
//        try {
//            for (int i = 0; i < v.size(); i++) {
//                var p = v.get(i);
//                colaDePrioridad.enqueue(p.getPrioridad(), p);
//            }
//        } catch (Exception ignored) {
//        }
//        while (!colaDePrioridad.isEmpty()) {
//            System.out.println(colaDePrioridad.dequeue().toString());
//        }
//    }

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