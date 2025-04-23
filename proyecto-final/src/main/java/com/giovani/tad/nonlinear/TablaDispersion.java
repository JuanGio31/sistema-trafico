package com.giovani.tad.nonlinear;

import com.giovani.Vehiculo;

public class TablaDispersion {
    private int NUMERO_ELEMENTOS = 5;
    private Vehiculo elementos[];
    int size;

    public TablaDispersion() {
        elementos = new Vehiculo[NUMERO_ELEMENTOS];
        this.size = 0;
    }

    public Vehiculo buscar(String placa) {
        int indice = hash(placa);
        return elementos[indice];
    }

    public void insertar(String placa, Vehiculo vehiculo) {
        int indice = hash(placa);
        if (elementos[indice] == null) {
            elementos[indice] = vehiculo;
            size++;
        } else {
            System.out.println("Valor repetido!");
        }

        if ((double) size / NUMERO_ELEMENTOS > 0.6) {
            rehashing();
        }
    }

    private int hash(String placa) {
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
        var anterior = elementos;
        size = 0;
        NUMERO_ELEMENTOS = NUMERO_ELEMENTOS * 2;
        elementos = new Vehiculo[NUMERO_ELEMENTOS];
        for (Vehiculo vehiculo : anterior) {
            if (vehiculo == null) {
                continue;
            }
            insertar(vehiculo.getPlaca(), vehiculo);
        }
    }

    public int getSize() {
        return size;
    }

    class Nodo {
        String placa;
        Vehiculo valor;

        public Nodo(String placa, Vehiculo valor) {
            this.placa = placa;
            this.valor = valor;
        }
    }
}
