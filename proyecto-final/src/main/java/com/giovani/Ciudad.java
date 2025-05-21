package com.giovani;

import com.giovani.tad.linear.DoublyLinkedList;

public class Ciudad {
    private final DoublyLinkedList<DoublyLinkedList<Interseccion>> matriz;
    private final DoublyLinkedList<Vehiculo> vehiculosEnDestino;
    private static final int FILA_MAX = 27;
    private static final int COLUMNA_MAX = 27;
    private static final float SEMAFOROS_MAX = 0.25f;
    private static final float BLOQUEOS_MAX = 0.18f;

    public Ciudad(int filas, int columnas) {
        this.matriz = new DoublyLinkedList<>();
        this.vehiculosEnDestino = new DoublyLinkedList<>();
        crearEstructura2D(filas, columnas);
    }

    public void encolarVehiculo(Vehiculo vehiculo, int x, int y) {
        Interseccion interseccion = getInterseccion(x, y);
        if (interseccion != null) {
            interseccion.getCola().enqueue(vehiculo.getPrioridad(), vehiculo);
        } else {
            System.out.println("====");
        }
    }

    public void agregarVehiculoEnCiudad(Vehiculo vehiculo) {
        Posicion coordenada = vehiculo.getActual();
        Posicion max = vehiculo.getMaxCoordenada();
        redimensionarEstructura(max.getY(), max.getX());
        var interseccion = getInterseccion(coordenada.getX(), coordenada.getY());
        if (interseccion != null) {
            interseccion.getCola().enqueue(vehiculo.getPrioridad(), vehiculo);
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
            if (getInterseccion(nuevaPosX, nuevaPosY) != null) {
                vehiculo.setActual(nuevaPosY, nuevaPosX);
                encolarVehiculo(vehiculo, nuevaPosX, nuevaPosY);
                return true;
            }
        }
        agregarVehiculoEnCiudad(vehiculo);
        return false;
    }

    public void generarMovimiento() {
        try {
            for (int i = 0; i < matriz.size(); i++) {
                for (int j = 0; j < matriz.get(i).size(); j++) {
                    var interseccion = getInterseccion(j, i);
                    if (!interseccion.isLibre()) {
                        continue;
                    }
                    var vehiculo = interseccion.getCola().dequeue();
                    if (vehiculo == null) {
                        continue;
                    }
                    if (!vehiculo.finRecorrido()) {
                        if (moverVehiculo(vehiculo)) {
                            System.out.println(vehiculo.getOrigen().toString() + " ==== " + vehiculo.getDestino().toString());
                        } else {
                            System.out.println("No se movio");
                        }
                    } else {
                        System.out.println("Vehiculo: " + vehiculo.getTipo() + " termino de recorrer la ciudad.\n");
                        vehiculosEnDestino.addLast(vehiculo);
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
            if (!interseccion.isLibre()) {
                return null;
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            //System.out.println("Coordenadas fuera de rango.");
            return null;
        }
        return interseccion;
    }

    private void crearEstructura2D(int filas, int columnas) {
        for (int i = 0; i < filas; i++) {
            DoublyLinkedList<Interseccion> lista = new DoublyLinkedList<>();
            for (int j = 0; j < columnas; j++) {
                var interseccion = new Interseccion();
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
            int filas = Math.max(y, matriz.size());
            int columnas = Math.max(x, matriz.get(0).size());
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
                lista.addLast(interseccion);
            }
        }
    }

    private void expandirEnVertical(int filas) throws Exception {
        for (int i = matriz.size(); i < filas; i++) {
            DoublyLinkedList<Interseccion> lista = new DoublyLinkedList<>();
            for (int j = 0; j < matriz.get(0).size(); j++) {
                var interseccion = new Interseccion();
                lista.addLast(interseccion);
            }
            matriz.addLast(lista);
        }
    }

    private void generarSemaforos(int semaforos, int columnas, int filas) {
        int MAX = 1000, contador = 0, x = 0, y = 0, semaforosEnTablero = 0;
        do {
            x = Utilidad.getNumeroRandom(0, columnas - 1);
            y = Utilidad.getNumeroRandom(0, filas - 1);
            var interseccion = getInterseccion(x, y);
            if (interseccion != null && interseccion.isLibre()) {
                if (!interseccion.isEsDestino()) {
                    interseccion.setSemaforo(new Semaforo('V'));
                    semaforosEnTablero++;
                }
            }
            if (semaforos == semaforosEnTablero) break;
            contador++;
        } while (contador < MAX);
    }

    private void generarBloqueos(int bloqueos, int columnas, int filas) {
        int MAX = 1000, contador = 0, x = 0, y = 0, bloqueosEnTablero = 0;
        do {
            x = Utilidad.getNumeroRandom(0, columnas - 1);
            y = Utilidad.getNumeroRandom(0, filas - 1);
            var interseccion = getInterseccion(x, y);
            if (interseccion != null && interseccion.isLibre()) {
                if (!interseccion.isEsDestino()) {
                    interseccion.setEsLibre(false);
                    interseccion.setCola(null);
                    bloqueosEnTablero++;
                }
            }
            if (bloqueos == bloqueosEnTablero) break;
            contador++;
        } while (contador < MAX);
    }

    public void generarObstaculos() {
        try {
            int totalCeldas = this.matriz.size() * this.matriz.get(0).size();
            int totalSemaforos = (int) (totalCeldas * SEMAFOROS_MAX);
            int totalBloqueos = (int) (totalCeldas * BLOQUEOS_MAX);
            generarSemaforos(totalSemaforos, this.matriz.get(0).size(), this.matriz.size());
            generarBloqueos(totalBloqueos, this.matriz.get(0).size(), this.matriz.size());
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
}