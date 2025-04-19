package com.giovani;

public class Vehiculo {
    private final String tipo;
    private final String Placa;
    private final int nivelUrgencia;
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
}
