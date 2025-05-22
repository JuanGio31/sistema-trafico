package com.giovani;

import com.giovani.tad.linear.StackGeneric;
import com.giovani.tad.nonlinear.AVL;
import com.giovani.tad.nonlinear.TablaDispersion;

public class Reporte {
    private final StackGeneric<String> eventos;
    private TablaDispersion tablaDispersion;
    private Ciudad ciudad;
    private AVL arbol;

    public Reporte(Ciudad ciudad, TablaDispersion tablaDispersion) {
        this.eventos = new StackGeneric<>();
        this.ciudad = ciudad;
        this.tablaDispersion = tablaDispersion;
        this.arbol = ciudad.getArbol();
    }

    public void mostrarEventos(StackGeneric<String> eventos) {
        int n = Math.min(eventos.size(), 10);
        System.out.println("====== EVENTOS RECIENTES =========");
        for (int i = 0; i < n; i++) {
            try {
                String temp = eventos.pop();
                System.out.println(temp);
                this.eventos.push(temp);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("===========================");
        try {
            while (!eventos.isEmpty()) {
                this.eventos.push(eventos.pop());
            }
        } catch (Exception ignored) {
        }
        eventos.limpiar();
    }

    public void mostrarEventos() {
        int n = Math.min(this.eventos.size(), 20);
        System.out.println("\n-------------------- Ultimos " + n + " eventos --------------------");
        for (int i = 0; i < n; i++) {
            try {
                System.out.println(eventos.pop());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void mostrarReporteCiudad() {
        System.out.println("////////////////////////////////////////////////////////");
        System.out.println("-------------------- REPORTE CIUDAD --------------------");
        mostrarVehiculosOrdenados();
        mostrarTotalDeProcesos();
        mostrarArbol();
        mostrarEventos();
        System.out.println("////////////////////////////////////////////////////////\n");
    }

    private void mostrarTotalDeProcesos() {
        System.out.println("\n-------------------- Vehiculos procesados en cada Interseccion --------------------");
        for (int i = 0; i < ciudad.getFilas(); i++) {
            for (int j = 0; j < ciudad.getColumnas(); j++) {
                var interseccion = ciudad.getInterseccion(j, i);
                if (interseccion != null) {
                    if (interseccion.getCola() != null) {
                        System.out.print(Utilidad.getPos(j, i) + "[");
                        Utilidad.printCyan(interseccion.getCola().getNumProcesos() + "");
                        System.out.print("] - ");
                    }
                }
            }
            System.out.println(" ");
        }
    }

    private void mostrarVehiculosOrdenados() {
        System.out.println("\n-------------------- Vehiculos ordenados por placa --------------------");
        Vehiculo[] temp = Ordenamiento.bubbleSort(tablaDispersion.obtenerVehiculos());
        for (int i = 0; i < temp.length; i++) {
            System.out.print(i + 1);
            System.out.print(".- Placa: " + temp[i].getPlaca() + " |");
            System.out.println(" Vehiculo: " + temp[i].getTipo());
        }
        System.out.println(" ");

        System.out.println("-------------------- Vehiculos ordenados por prioridad --------------------");
        Ordenamiento.quickSort(temp, 0, temp.length - 1);
        for (int i = 0; i < temp.length; i++) {
            System.out.print(i + 1);
            System.out.print(".- Prioridad: " + temp[i].getPrioridad() + " |");
            System.out.print(" Placa: " + temp[i].getPlaca() + " |");
            System.out.println(" Vehiculo: " + temp[i].getTipo());
        }
        System.out.println(" ");

        System.out.println("-------------------- Vehiculos ordenados por tiempo de espera --------------------");
        Ordenamiento.ordenamientoPorSacudida(temp);
        for (int i = 0; i < temp.length; i++) {
            System.out.print(i + 1);
            System.out.print(".- Tiempo de espera: " + temp[i].getTiempoDeViaje() + " |");
            System.out.print(" Prioridad: " + temp[i].getPrioridad() + " |");
            System.out.print(" Placa: " + temp[i].getPlaca() + " |");
            System.out.println(" Vehiculo: " + temp[i].getTipo());
        }
        System.out.println(" ");
    }

    private void mostrarArbol() {
        System.out.println("\n-------------------- Arbol de procesos --------------------");
        this.arbol.preOrder();
        this.arbol.inOrder();
        this.arbol.postOrder();
        this.arbol.dibujar();
    }
}
