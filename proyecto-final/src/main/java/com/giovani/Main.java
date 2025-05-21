package com.giovani;

import com.giovani.tad.nonlinear.TablaDispersion;


public class Main {
    public static void main(String[] args) throws Exception {
        Simulacion simulacion = new Simulacion();
        simulacion.getMenu();
//        testCiudad();
    }

    private static void testCiudad() {
        Ciudad ciudad = new Ciudad(5, 5);
        ciudad.imprimirEstructura();
        System.out.println("==================================");
        ciudad.redimensionarEstructura(10, 10);
        ciudad.imprimirEstructura();
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
}