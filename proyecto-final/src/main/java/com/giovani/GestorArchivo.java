package com.giovani;

import com.giovani.tad.linear.DoublyLinkedList;
import com.giovani.tad.nonlinear.Matriz;
import com.giovani.tad.nonlinear.TablaDispersion;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GestorArchivo {
    private static final Logger logger = Logger.getLogger(GestorArchivo.class.getName());
    private final TablaDispersion tablaDispersion;
    private Ciudad ciudad;
    private int vehiculosRegistrados = 0;

    public GestorArchivo(TablaDispersion tablaDispersion, Ciudad ciudad) {
        this.tablaDispersion = tablaDispersion;
        this.ciudad = ciudad;
    }

    public int readCSV(String path) {
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            System.out.println("///////////////////////////////////////////////////////////////////////");
            Utilidad.printCyan("Ingreso de Vehiculos\n");
            while (myReader.hasNextLine()) {
                //tipo, placa, interseccionO, inteerseccionD, prioridad, tiempo_espera
                String[] data = myReader.nextLine().split(",");
                String tipo = data[0].toUpperCase();
                String placa = data[1];
                int prioridad = Integer.parseInt(data[4].trim());
                int tiempo = Integer.parseInt(data[5].trim());
                Vehiculo nuevo = new Vehiculo(tipo, placa, prioridad, tiempo);
                int filaOrigen = Utilidad.convertirCharAEntero(data[2].charAt(0));
                int colOrigen = Utilidad.validarColumna(data[2].substring(1));
                nuevo.setOrigen(new Posicion(filaOrigen, colOrigen));
                int filaDestino = Utilidad.convertirCharAEntero(data[3].charAt(0));
                int colDestino = Utilidad.validarColumna(data[3].substring(1));
                nuevo.setDestino(new Posicion(filaDestino, colDestino));
                agregar(nuevo);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "Error al leer el archivo.");
            System.out.println(" ");
            return -1;
        }
        System.out.println("///////////////////////////////////////////////////////////////////////");

        return vehiculosRegistrados;
    }

    private void agregar(Vehiculo nuevo) {
        if (tablaDispersion.buscar(nuevo.getPlaca()) == null) {
            if (nuevo.estaDentroDelRango()) {
                tablaDispersion.insertar(nuevo.getPlaca(), nuevo);
                this.ciudad.agregar(nuevo);
                mostrarAlertaIngreso(nuevo);
                vehiculosRegistrados++;
            } else {
                mostrarAlertaErrorCoordenada(nuevo);
            }
        } else {
            mostrarAlertaDuplicado(nuevo);
        }
    }

    private void mostrarAlertaDuplicado(Vehiculo v) {
        Utilidad.printCadenaEnRojo("Error, vehiculo con placa duplicada!");
        System.out.print("\tVehiculo: ");
        Utilidad.printCyan(v.getTipo());
        System.out.print(", con placa: ");
        Utilidad.printCyan(v.getPlaca());
        System.out.println(" ");
    }

    private void mostrarAlertaIngreso(Vehiculo v) {
        System.out.print("Se registro un nuevo vehiculo { ");
        System.out.print(v.getTipo() + ",\tcon placa: " + v.getPlaca());
        System.out.println(" } ");
    }

    private void mostrarAlertaErrorCoordenada(Vehiculo v) {
        Utilidad.printCadenaEnRojo("Error, vehiculo con coordenadas fuera del rango [0-26],[0-26]!");
        System.out.print("\tVehiculo: ");
        Utilidad.printCyan(v.getTipo());
        System.out.print(", con placa: ");
        Utilidad.printCyan(v.getPlaca());
        System.out.println(" ");
    }
}
