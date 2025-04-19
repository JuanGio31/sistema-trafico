package com.giovani;

import com.giovani.tad.DoublyLinkedList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileManager {

    private static final Logger logger = Logger.getLogger(FileManager.class.getName());

    public DoublyLinkedList<Vehiculo> readCSV(String path) {
        DoublyLinkedList<Vehiculo> vehiculos = new DoublyLinkedList<>();
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                //tipo,placa,interseccionO,inteerseccionD,prioridad,tiempo_espera
                String[] data = myReader.nextLine().split(",");
                vehiculos.addLast(new Vehiculo(data[0], data[1], Integer.parseInt(data[4]), Integer.parseInt(data[5])));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "Error al leer el archivo.", e);
            return null;
        }

        return vehiculos;
    }
}
