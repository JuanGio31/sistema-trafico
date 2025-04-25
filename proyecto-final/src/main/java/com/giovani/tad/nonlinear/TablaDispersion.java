package com.giovani.tad.nonlinear;

import com.giovani.Vehiculo;

public class TablaDispersion {
    private static final int NUMERO_ELEMENTOS = 15;
    private Nodo[] elementos;
    int size;

    public TablaDispersion() {
        elementos = new Nodo[NUMERO_ELEMENTOS];
        this.size = 0;
    }

    public Vehiculo buscar(String placa) {
        int indice = getHashCode(placa);
        if (elementos[indice].siguiete == null) {
            return elementos[indice].valor;
        }
        var temp = elementos[indice];
        while (temp != null) {
            if (temp.clave.equals(placa)) {
                return temp.valor;
            }
            temp = temp.siguiete;
        }
        return null;
    }

    public void insertar(String placa, Vehiculo vehiculo) {
        int indice = getHashCode(placa);
        var nuevo = new Nodo(placa, vehiculo);
        if (elementos[indice] == null) {
            elementos[indice] = nuevo;
            size++;
        } else {
            if (elementos[indice].clave.equals(placa)) {
                //elementos[indice] = nuevo;
                System.out.println("Error, el vehiculo ya existe!");
            } else {
                var temp = elementos[indice];
                while (temp.siguiete != null) {
                    temp = temp.siguiete;
                }
                temp.siguiete = nuevo;
                size++;
            }
        }
        if ((double) size / NUMERO_ELEMENTOS > 0.7) {
            rehashing();
        }
    }

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
        var anterior = elementos;
        elementos = new Nodo[anterior.length * 2];
        for (Nodo elemento : anterior) {
            if (elemento == null) {
                continue;
            }
            insertar(elemento.clave, elemento.valor);
        }
    }

    public int getSize() {
        return size;
    }

    private static class Nodo {
        String clave;
        Vehiculo valor;
        Nodo siguiete;

        public Nodo(String clave, Vehiculo valor) {
            this.clave = clave;
            this.valor = valor;
            this.siguiete = null;
        }
    }

}
