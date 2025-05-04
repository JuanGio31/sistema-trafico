package com.giovani;

import com.giovani.tad.linear.DoublyLinkedList;
import com.giovani.tad.linear.StackGeneric;
import com.giovani.tad.nonlinear.AVL;
import com.giovani.tad.nonlinear.Matriz;
import com.giovani.tad.nonlinear.NodoOrtogonal;

import java.util.Random;

public class Ciudad {
    private AVL avl;
    private Matriz matriz;
    private boolean aux = false;
    private final DoublyLinkedList<Vehiculo> vehiculosEnDestino;
    private final Random random;
    private StackGeneric<String> eventos;

    public Ciudad(int filas, int columnas, StackGeneric<String> eventos) {
        this.eventos = eventos;
        this.random = new Random();
        this.matriz = new Matriz(filas, columnas, random);
        this.matriz.setEventos(eventos);
        this.avl = new AVL();
        this.vehiculosEnDestino = new DoublyLinkedList<>();
    }

    public void agregar(Vehiculo vehiculo) {
        Posicion coordenada = vehiculo.getActual();
        Posicion posicion = vehiculo.getMaxCoordenada();
        int redimensiones = this.matriz.redimensionar(posicion.getY() + 1, posicion.getX() + 1);
        if (redimensiones == 1) {
            this.matriz.setColumna(posicion.getX() + 1);
        } else if (redimensiones == 2) {
            this.matriz.setFila(posicion.getY() + 1);
        } else if (redimensiones == 3) {
            this.matriz.setColumna(posicion.getX() + 1);
            this.matriz.setFila(posicion.getY() + 1);
        }
        var temp = this.matriz.posicioValida(coordenada.getY(), coordenada.getX());
        if (temp != null) {
            temp.getInterseccion().getCola().enqueue(vehiculo.getPrioridad(), vehiculo);
            var aux = avl.buscar(temp.getInterseccion());
            if (aux == null) {
                avl.insertar(temp.getInterseccion());
            }
        }
    }

    public void proceso() {
        Utilidad.limpiarPantalla();
        this.matriz.print();
        if (aux) {
            run();
        }
        aux = true;
    }

    public void run() {
        var inicio = this.matriz.getInicio();
        for (var i = inicio; i != null; i = i.getAbajo()) {
            for (var j = i; j != null; j = j.getDerecha()) {
                var interseccion = j.getInterseccion();
                if (!interseccion.estaBloqueado()) {
                    var vehiculo = interseccion.getCola().dequeue();
                    if (vehiculo != null) {
                        if (!vehiculo.finRecorrido()) {
                            boolean movido = mover(vehiculo);
                            if (!movido) {
                                System.out.println("El vehículo " + vehiculo.getPlaca() + " no pudo moverse. Camino bloqueado o semáforo en rojo.");
                            }
                        } else {
                            System.out.print(vehiculo.getTipo() + ", con placa: " + vehiculo.getPlaca());
                            System.out.println(" ha llegado a su destino.");
                            vehiculosEnDestino.addLast(vehiculo);
                        }
                    }
                    interseccion.aumentarProcesos();
                } else {
                    if (random.nextBoolean()) {
                        interseccion.aumentarProcesos();
                    }
                }
            }
        }
    }

    public Matriz getMatriz() {
        return matriz;
    }

    public void setMatriz(Matriz matriz) {
        this.matriz = matriz;
    }

    public boolean mover(Vehiculo vehiculo) {
        Posicion posActual = vehiculo.getActual();
        int x = posActual.getX();
        int y = posActual.getY();
        Posicion destino = vehiculo.getDestino();
        int[][] movimientos = {
                {y - 1, x}, // Arriba
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
        //ordenar el mov por la distancia mas corta
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
            NodoOrtogonal nodoDestino = this.matriz.posicioValida(nuevaPosY, nuevaPosX);
            if (nodoDestino != null) {
                vehiculo.setActual(nuevaPosY, nuevaPosX);
                this.matriz.encolar(nuevaPosY, nuevaPosX, vehiculo);
                avl.preOrder();
                return true;
            }
        }
        agregar(vehiculo);
        return false;
    }

    public DoublyLinkedList<Vehiculo> getVehiculosEnDestino() {
        return vehiculosEnDestino;
    }
}
