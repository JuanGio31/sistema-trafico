package com.giovani;

public class Vehiculo implements Comparable<Vehiculo> {
    private final String tipo;
    private final String Placa;
    private int nivelUrgencia;
    private final int tiempoEspera;

    /**
     * Constructor de la Clase Vehiculo.
     *
     * @param tipo          Tipo de Vehiculo
     * @param placa         Numero de Placa
     * @param nivelUrgencia Prioridad
     * @param tiempoEspera  Tiempo de espera
     */
    public Vehiculo(String tipo, String placa, int nivelUrgencia, int tiempoEspera) {
        this.tipo = tipo;
        Placa = placa;
        this.nivelUrgencia = nivelUrgencia;
        this.tiempoEspera = tiempoEspera;
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "tipo='" + tipo + '\'' +
                ", Placa='" + Placa + '\'' +
                ", nivelUrgencia=" + nivelUrgencia +
                ", tiempoEspera=" + tiempoEspera +
                '}';
    }

    public String getTipo() {
        return tipo;
    }

    public String getPlaca() {
        return Placa;
    }

    public int getNivelUrgencia() {
        return nivelUrgencia;
    }

    public int getTiempoEspera() {
        return tiempoEspera;
    }

    @Override
    public int compareTo(Vehiculo otro) {
        int urgencia = Integer.compare(this.nivelUrgencia, otro.nivelUrgencia);
        if (urgencia == 0) {
            return Integer.compare(this.tiempoEspera, otro.tiempoEspera);
        }
        return urgencia;
    }
}
