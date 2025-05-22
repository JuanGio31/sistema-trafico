package com.giovani;

import com.giovani.tad.linear.DoublyLinkedList;
import com.giovani.tad.linear.StackGeneric;
import com.giovani.tad.nonlinear.AVL;

public class Ciudad {
    private static final int FILA_MAX = 27;
    private static final int COLUMNA_MAX = 27;
    private static final float PORCENTAJE_SEMAFOROS = 0.25f;
    private static final float PORCENTAJE_BLOQUEOS = 0.15f;
    private final DoublyLinkedList<DoublyLinkedList<Interseccion>> matriz;
    private final DoublyLinkedList<Vehiculo> vehiculosEnDestino;
    private final StackGeneric<String> eventos;
    private AVL arbol;

    public Ciudad(int filas, int columnas) {
        this.matriz = new DoublyLinkedList<>();
        this.vehiculosEnDestino = new DoublyLinkedList<>();
        eventos = new StackGeneric<>();
        crearEstructura2D(filas, columnas);
        this.arbol = new AVL();
    }

    public void encolarVehiculo(Vehiculo vehiculo, Interseccion interseccion) {
        interseccion.getCola().enqueue(vehiculo.getPrioridad(), vehiculo);
    }

    public void agregarVehiculoEnCiudad(Vehiculo vehiculo) {
        Posicion coordenada = vehiculo.getActual();
        Posicion max = vehiculo.getMaxCoordenada();
        redimensionarEstructura(max.getY() + 1, max.getX() + 1);
        var interseccion = getInterseccion(coordenada.getX(), coordenada.getY());
        if (interseccion != null) {
            if (interseccion.isLibre()) {
                interseccion.getCola().enqueue(vehiculo.getPrioridad(), vehiculo);
            }
        }
    }

    private boolean moverVehiculo(Vehiculo vehiculo) {
        Posicion posActual = vehiculo.getActual();
        int x = posActual.getX();
        int y = posActual.getY();
        Posicion destino = vehiculo.getDestino();
        int[][] movimientos = {{y - 1, x}, // Arriba
                {y + 1, x}, // Abajo
                {y, x - 1}, // Izquierda
                {y, x + 1}  // Derecha
        };
        int[] distancias = new int[4];
        for (int i = 0; i < 4; i++) {
            int nuevaY = movimientos[i][0];
            int nuevaX = movimientos[i][1];
            distancias[i] = Math.abs(nuevaX - destino.getX()) + Math.abs(nuevaY - destino.getY());
        }
        for (int i = 0; i < 4; i++) {
            for (int j = i + 1; j < 4; j++) {
                if (distancias[i] > distancias[j]) {
                    int tempDist = distancias[i];
                    distancias[i] = distancias[j];
                    distancias[j] = tempDist;

                    int[] tempMov = movimientos[i];
                    movimientos[i] = movimientos[j];
                    movimientos[j] = tempMov;
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            int nuevaPosY = movimientos[i][0];
            int nuevaPosX = movimientos[i][1];
            var interseccion = getInterseccion(nuevaPosX, nuevaPosY);
            if (interseccion != null) {
                if (interseccion.isLibre()) {
                    vehiculo.setActual(nuevaPosY, nuevaPosX);
                    encolarVehiculo(vehiculo, interseccion);
                    return true;
                }
            }
        }
        agregarVehiculoEnCiudad(vehiculo);
        return false;
    }

    public void generarAcciones() {
        try {
            for (int i = 0; i < matriz.size(); i++) {
                for (int j = 0; j < matriz.get(i).size(); j++) {
                    var interseccion = getInterseccion(j, i);
                    if (interseccion.getCola() != null) {
                        if (interseccion.getId() == null) {
                            interseccion.setId(Utilidad.getPos(j, i));
                        }
                        ingresarEnArbol(interseccion);
                        if (interseccion.getSemaforo() != null) {
                            if (Utilidad.getBoolRandom()) {
                                String estado = "De " + interseccion.getSemaforo().mostarEstado() + " a ";
                                interseccion.getSemaforo().cambiarEstado();
                                estado += interseccion.getSemaforo().mostarEstado();
                                eventos.push("CAMBIO DE SEMAFORO{ "
                                        + Utilidad.getPos(j, i) + " | " + estado + " }");
                            }
                        }
                        var vehiculo = interseccion.getCola().dequeue();
                        if (vehiculo == null) {
                            continue;
                        }
                        vehiculo.incrementarTiempo();
                        if (!interseccion.isLibre()) {
                            continue;
                        }
                        if (!vehiculo.finRecorrido()) {
                            if (moverVehiculo(vehiculo)) {
                                registarEventoDeMovimiento(vehiculo);
                                vehiculo.setTiempoEspera(0);
                            } else {
                                System.out.println("No se movio");
                            }
                        } else {
                            eventos.push("FIN DE RECORRIDO{ Vehiculo: "
                                    + vehiculo.getTipo() + " | Placa: " + vehiculo.getPlaca()
                                    + " | Tiempo de recorrido: " + vehiculo.getTiempoDeViaje() + " }");
                            vehiculosEnDestino.addLast(vehiculo);
                        }
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }

    public Interseccion getInterseccion(int x, int y) {
        Interseccion interseccion;
        try {
            interseccion = matriz.get(y).get(x);
        } catch (Exception exception) {
            //System.out.println(exception.getMessage());
            Utilidad.printCadenaEnRojo("Error, coordenadas fuera de rango.");
            return null;
        }
        return interseccion;
    }

    private void crearEstructura2D(int filas, int columnas) {
        for (int i = 0; i < filas; i++) {
            DoublyLinkedList<Interseccion> lista = new DoublyLinkedList<>();
            for (int j = 0; j < columnas; j++) {
                var interseccion = new Interseccion();
//                interseccion.setId(Utilidad.getPos(j, i));
                lista.addLast(interseccion);
            }
            matriz.addLast(lista);
        }
    }

    private void imprimirEncabezado() throws Exception {
        for (int i = 0; i < matriz.get(0).size(); i++) {
            if (i >= 10) {
                System.out.print(" | ");
                Utilidad.printPurpura((i + 1) + "");
                continue;
            }
            System.out.print("  | ");
            Utilidad.printPurpura((i + 1) + "");
        }
        System.out.println(" ");
        System.out.print(" =");
        for (int i = 0; i < matriz.get(0).size(); i++) {
            System.out.print("=====");
        }
        System.out.println(" ");
    }

    public void imprimirEstructura() {
        try {
            imprimirEncabezado();
            int idFila = 65;
            for (int i = 0; i < matriz.size(); i++) {
                Utilidad.printCyan((char) idFila + "");
                System.out.print("-[ ");
                for (int j = 0; j < matriz.get(i).size(); j++) {
                    Utilidad.printCharColor(matriz.get(i).get(j).getSimbolo());
                    if (j == matriz.get(i).size() - 1) {
                        continue;
                    }
                    System.out.print(" -- ");
                }
                System.out.println(" ");
                if (i != matriz.size() - 1) {
                    for (int j = 0; j < matriz.get(i).size(); j++) {
                        System.out.print("    :");
                    }
                }
                System.out.println(" ");
                idFila++;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void redimensionarEstructura(int y, int x) {
        try {
            int filas = Math.max(y, getFilas());
            int columnas = Math.max(x, getColumnas());
            if (columnas < COLUMNA_MAX) {
                expandirEnHorizontal(columnas);
            }
            if (filas < FILA_MAX) {
                expandirEnVertical(filas);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void expandirEnHorizontal(int columnas) throws Exception {
        for (int i = 0; i < matriz.size(); i++) {
            var lista = matriz.get(i);
            for (int j = matriz.get(i).size(); j < columnas; j++) {
                var interseccion = new Interseccion();
//                interseccion.setId(Utilidad.getPos(j, i));
                lista.addLast(interseccion);
            }
        }
    }

    private void expandirEnVertical(int filas) throws Exception {
        for (int i = matriz.size(); i < filas; i++) {
            DoublyLinkedList<Interseccion> lista = new DoublyLinkedList<>();
            for (int j = 0; j < matriz.get(0).size(); j++) {
                var interseccion = new Interseccion();
//                interseccion.setId(Utilidad.getPos(j, i));
                lista.addLast(interseccion);
            }
            matriz.addLast(lista);
        }
    }

    private void generarSemaforos(int semaforos, int columnas, int filas) {
        int MAX = 1000, contador = -1, semaforosEnTablero = 0, x, y;
        do {
            contador++;
            x = Utilidad.getNumeroRandom(0, columnas - 1);
            y = Utilidad.getNumeroRandom(0, filas - 1);
            var interseccion = getInterseccion(x, y);
            if (interseccion == null || !interseccion.isLibre()) continue;
            interseccion.setSemaforo(new Semaforo('V'));
            semaforosEnTablero++;
            if (semaforos == semaforosEnTablero) break;
        } while (contador < MAX);
    }

    private void generarBloqueos(int bloqueos, int columnas, int filas) {
        int MAX = 1000, contador = -1, bloqueosEnTablero = 0, x, y;
        do {
            contador++;
            x = Utilidad.getNumeroRandom(0, columnas - 1);
            y = Utilidad.getNumeroRandom(0, filas - 1);
            var interseccion = getInterseccion(x, y);
            if (interseccion == null || !interseccion.isLibre() || interseccion.isEsDestino()) continue;
            interseccion.setEsLibre(false);
            interseccion.setCola(null);
            bloqueosEnTablero++;
            if (bloqueos == bloqueosEnTablero) break;
        } while (contador < MAX);
    }

    public void generarObstaculos() {
        try {
            int totalCeldas = getFilas() * getColumnas();
            int totalSemaforos = (int) (totalCeldas * PORCENTAJE_SEMAFOROS);
            int totalBloqueos = (int) (totalCeldas * PORCENTAJE_BLOQUEOS);
            generarSemaforos(totalSemaforos, getColumnas(), getFilas());
            generarBloqueos(totalBloqueos, getColumnas(), getFilas());
        } catch (Exception ignored) {
        }
    }

    public int getFilas() {
        return matriz.size();
    }

    public int getColumnas() {
        try {
            return matriz.get(0).size();
        } catch (Exception e) {
            return -1;
        }
    }

    public DoublyLinkedList<Vehiculo> getVehiculosEnDestino() {
        return vehiculosEnDestino;
    }

    private void registarEventoDeMovimiento(Vehiculo v) {
        int x, y;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MOVIMIENTO{ ").append("El vehiculo: ").append(v.getTipo()).append(", ");
        stringBuilder.append("con placa: ").append(v.getPlaca());
        stringBuilder.append(" se desplaza a: (");
        x = v.getActual().getX();
        y = v.getActual().getY();
        stringBuilder.append(((char) (y + 65))).append(x + 1).append(")");
        x = v.getDestino().getX();
        y = v.getDestino().getY();
        stringBuilder.append(" con destino: (");
        stringBuilder.append(((char) (y + 65))).append(x + 1).append(") }");
        eventos.push(stringBuilder.toString());
    }

    public StackGeneric<String> getEventos() {
        return eventos;
    }

    private void ingresarEnArbol(Interseccion interseccion) {
        var temp = arbol.buscar(interseccion);
        if (temp == null) {
            arbol.insertar(interseccion);
        }
    }

    public AVL getArbol() {
        return arbol;
    }
}