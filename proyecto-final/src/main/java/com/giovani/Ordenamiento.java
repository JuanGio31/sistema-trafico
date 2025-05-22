package com.giovani;

import com.giovani.tad.linear.StackGeneric;

public class Ordenamiento {
    public static Vehiculo[] bubbleSort(StackGeneric<Vehiculo> vehiculos) {
        Vehiculo[] array = new Vehiculo[vehiculos.size()];
        for (int i = 0; i < array.length; i++) {
            try {
                array[i] = vehiculos.pop();
            } catch (Exception ignored) {
            }
        }

        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j].getPlaca().compareTo(array[j + 1].getPlaca()) > 0) {
                    var temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        return array;
    }

    public static void quickSort(Vehiculo[] arr, int inicio, int fin) {
        if (inicio < fin) {
            int pivote = partition(arr, inicio, fin);
            quickSort(arr, inicio, pivote - 1);
            quickSort(arr, pivote + 1, fin);
        }
    }

    public static int partition(Vehiculo[] arr, int inicio, int fin) {
        Vehiculo pivote = arr[fin];
        int i = (inicio - 1); // Índice del elemento más pequeño

        for (int j = inicio; j < fin; j++) {
            if (arr[j].compareTo(pivote) < 0) {
                i++;
                Vehiculo temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        // Intercambiar arr[i+1] y arr[fin] (el pivote)
        Vehiculo temp = arr[i + 1];
        arr[i + 1] = arr[fin];
        arr[fin] = temp;

        return i + 1;
    }

    public static void ordenamientoPorSacudida(Vehiculo[] arr) {
        int n = arr.length;
        boolean swapped = true;
        int inicio = 0;
        int fin = n - 1;

        while (swapped) {
            swapped = false;
            // izquierda -> derecha
            for (int i = inicio; i < fin; i++) {
                if (arr[i].getTiempoDeViaje() > arr[i + 1].getTiempoDeViaje()) {
                    var temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapped = true;
                }
            }
            // Si no hubo intercambios en esta pasada, el arreglo está ordenado
            if (!swapped) {
                break;
            }
            swapped = false;
            fin--;
            // derecha -> izquierda
            for (int i = fin - 1; i >= inicio; i--) {
                if (arr[i].getTiempoDeViaje() > arr[i + 1].getTiempoDeViaje()) {
                    var temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapped = true;
                }
            }
            inicio++;
        }
    }
}
