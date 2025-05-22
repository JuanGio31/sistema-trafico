package com.giovani;

import com.giovani.tad.linear.DoublyLinkedList;

public class Vehiculo implements Comparable<Vehiculo> {
    private final String tipo;
    private final String placa;
    private final int nivelUrgencia;
    private int tiempoEspera;
    private int tiempoDeViaje;
    private Posicion actual;
    private Posicion origen;
    private Posicion destino;

    /**
     * Constructor de la Clase Vehículo.
     *
     * @param tipo          Tipo de Vehiculo
     * @param placa         Número de Placa
     * @param nivelUrgencia Prioridad
     * @param tiempoEspera  Tiempo de espera
     */
    public Vehiculo(String tipo, String placa, int nivelUrgencia, int tiempoEspera) {
        this.tipo = tipo;
        this.placa = placa;
        this.nivelUrgencia = nivelUrgencia;
        this.tiempoEspera = tiempoEspera;
        this.tiempoDeViaje = tiempoEspera;
    }

    public String getInfo() {
        int x = this.actual.getX();
        int y = this.actual.getY();
        String posicion = "(" + ((char) (y + 65)) + (x + 1) + ")";
        return tipo + " | " + placa + " | " + "Posicion: " + posicion;
    }

    public String getTipo() {
        return tipo;
    }

    public String getPlaca() {
        return placa;
    }

    @Override
    public int compareTo(Vehiculo otro) {
        if (this.getNivelUrgencia() != otro.getNivelUrgencia()) {
            return Integer.compare(otro.getNivelUrgencia(), this.getNivelUrgencia());
        } else {
            return Integer.compare(otro.getTiempoEspera(), this.getTiempoEspera());
        }
    }

    public int getPrioridad() {
        var tipoVehiculo = this.tipo.toUpperCase().trim().replaceAll(" ", "");
        return switch (tipoVehiculo) {
            case "AMBULANCIA" -> 0;
            case "POLICIA" -> 1;
            case "TRANSPORTEPUBLICO", "TRANSPORTE" -> 2;
            case "PARTICULAR" -> 3;
            default -> -1;
        };
    }

    public void setActual(int y, int x) {
        this.actual.setPosicion(y, x);
    }

    public boolean finRecorrido() {
        return origen.getX() == destino.getX() && origen.getY() == destino.getY();
    }

    public void setOrigen(Posicion origen) {
        this.origen = origen;
        this.actual = origen;
    }

    public Posicion getActual() {
        return actual;
    }

    public Posicion getDestino() {
        return destino;
    }

    public int getNivelUrgencia() {
        return nivelUrgencia;
    }

    public int getTiempoEspera() {
        return tiempoEspera;
    }

    public void incrementarTiempo() {
        this.tiempoDeViaje++;
    }

    public void setDestino(Posicion destino) {
        this.destino = destino;
    }

    public Posicion getMaxCoordenada() {
        int x = Math.max(origen.getX(), destino.getX());
        int y = Math.max(origen.getY(), destino.getY());
        return new Posicion(y, x);
    }

    public boolean estaDentroDelRango() {
        return this.getMaxCoordenada().getY() < 27 &&
                (this.getMaxCoordenada().getX() >= 0 && this.getMaxCoordenada().getX() < 27);
    }

    public int getTiempoDeViaje() {
        return tiempoDeViaje;
    }

    public void setTiempoEspera(int tiempoEspera) {
        this.tiempoEspera = tiempoEspera;
    }
}