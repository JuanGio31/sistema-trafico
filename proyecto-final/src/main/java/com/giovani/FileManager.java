package com.giovani;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileManager {

    private static final Logger logger = Logger.getLogger(FileManager.class.getName());

    public LinkedList<Vehiculo> readCSV(String path) {
        LinkedList<Vehiculo> vehiculos = new LinkedList<>();
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                //tipo,placa,interseccionO,inteerseccionD,prioridad,tiempo_espera
                String[] data = myReader.nextLine().split(",");
                vehiculos.add(new Vehiculo(data[0], data[1], Integer.parseInt(data[4]), Integer.parseInt(data[5])));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "Error al leer el archivo.", e);
            return null;
        }

        return vehiculos;
    }
}
