package com.giovani;

import com.giovani.tad.linear.DoublyLinkedList;

public class Vehiculo implements Comparable<Vehiculo> {
    private final String tipo;
    private final String placa;
    private final int nivelUrgencia;
    private final int tiempoEspera;
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
    }

    @Override
    public String toString() {
        return "V{" + "T='" + tipo + '\'' + ", Placa='" +
                placa + '\'' + ", nivelUrgencia=" +
                nivelUrgencia + ", tiempoEspera=" +
                tiempoEspera + ", origen(" +
                origen.getX() + ", " + origen.getY() +
                "), destino(" + destino.getX() + ", " + destino.getY() +
                ")" + '}';
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
        int urgencia = Integer.compare(this.nivelUrgencia, otro.nivelUrgencia);
        if (urgencia == 0) {
            return Integer.compare(this.tiempoEspera, otro.tiempoEspera);
        }
        return urgencia;
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

    public Posicion getOrigen() {
        return origen;
    }

    public Posicion getActual() {
        return actual;
    }

    public Posicion getDestino() {
        return destino;
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

    public DoublyLinkedList<Posicion> getCoordenadasAdyacentes() {
        DoublyLinkedList<Posicion> coordenadas = new DoublyLinkedList<>();
        int x = this.getActual().getX();
        int y = this.getActual().getY();

        coordenadas.addLast(new Posicion(y - 1, x));    // Arriba
        coordenadas.addLast(new Posicion(y + 1, x));    // Abajo
        coordenadas.addLast(new Posicion(y, x - 1));    // Izquierda
        coordenadas.addLast(new Posicion(y, x + 1));    // Derecha

        return coordenadas;
    }
}