package com.giovani.tad.nonlinear;

import com.giovani.Vehiculo;
import com.giovani.tad.linear.StackGeneric;

public class TablaDispersion {
    private static int NUMERO_ELEMENTOS = 55;
    private Nodo[] elementos;
    int size;

    public TablaDispersion() {
        elementos = new Nodo[NUMERO_ELEMENTOS];
        this.size = 0;
    }

    public Vehiculo buscar(String placa) {
        int indice = getHashCode(placa);
        if (elementos[indice] == null) {
            return null;
        }
        Nodo temp = elementos[indice];
        while (temp != null) {
            if (temp.clave.equals(placa)) {
                return temp.valor;
            }
            temp = temp.siguiente;
        }
        return null;
    }

    public void insertar(String placa, Vehiculo vehiculo) {
        int indice = getHashCode(placa);
        var nuevo = new Nodo(placa, vehiculo);

        if (elementos[indice] != null) {
            var temp = elementos[indice];
            while (temp != null) {
                if (temp.clave.equals(placa)) {
                    System.out.println("Error, el vehículo ya existe!");
                    return;
                }
                temp = temp.siguiente;
            }
            nuevo.siguiente = elementos[indice];
        }
        elementos[indice] = nuevo;
        size++;

        if ((double) size / NUMERO_ELEMENTOS > 0.7) {
            rehashing();
        }
    }

//    public void borrar(String placa) {
//        if (placa != null) {
//            int indice = getHashCode(placa);
//            if (elementos[indice] == null) {
//                return;
//            }
//            if (elementos[indice].clave.equals(placa)) {
//                elementos[indice] = elementos[indice].siguiente;
//                size--;
//                return;
//            }
//            Nodo anterior = elementos[indice];
//            Nodo actual = anterior.siguiente;
//            while (actual != null) {
//                if (actual.clave.equals(placa)) {
//                    anterior.siguiente = actual.siguiente;
//                    size--;
//                    return;
//                }
//                anterior = actual;
//                actual = actual.siguiente;
//            }
//        }
//    }

    private int getHashCode(String placa) {
        return convertirCadena(placa) % NUMERO_ELEMENTOS;
    }

    private int convertirCadena(String clave) {
        int total = 0;
        for (int i = 0; i < clave.length(); i++) {
            total += clave.charAt(i);
        }
        return total;
    }

    private void rehashing() {
        size = 0;
        Nodo[] anterior = elementos;
        NUMERO_ELEMENTOS = (anterior.length * 2) + 1;
        elementos = new Nodo[NUMERO_ELEMENTOS];
        for (Nodo elemento : anterior) {
            Nodo actual = elemento;
            while (actual != null) {
                insertar(actual.clave, actual.valor);
                actual = actual.siguiente;
            }
        }
    }

    public void mostrarEstructura() {
        System.out.println("\nEstructura de la Tabla de Dispersión:");
        for (int i = 0; i < elementos.length; i++) {
            System.out.print("Índice " + i + ": ");
            if (elementos[i] == null) {
                System.out.println("vacío");
            } else {
                Nodo actual = elementos[i];
                while (actual != null) {
                    System.out.print("[" + actual.clave + "] -> ");
                    actual = actual.siguiente;
                }
                System.out.println("null");
            }
        }
    }

    public StackGeneric<Vehiculo> obtenerVehiculos() {
        StackGeneric<Vehiculo> lista = new StackGeneric<>();
        for (int i = 0; i < elementos.length; i++) {
            if (elementos[i] != null) {
                Nodo actual = elementos[i];
                while (actual != null) {
                    lista.push(actual.valor);
                    actual = actual.siguiente;
                }
            }
        }
        return lista;
    }

    public int getSize() {
        return size;
    }

    private static class Nodo {
        String clave;
        Vehiculo valor;
        Nodo siguiente;

        public Nodo(String clave, Vehiculo valor) {
            this.clave = clave;
            this.valor = valor;
            this.siguiente = null;
        }
    }
}
