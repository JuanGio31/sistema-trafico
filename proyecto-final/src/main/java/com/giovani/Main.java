package com.giovani;

import com.giovani.tad.DoublyLinkedList;

public class Main {
    public static void main(String[] args) {
//        FileManager fileManager = new FileManager();
//        LinkedList<Vehiculo> v = fileManager.readCSV("/home/giovanic/Documents/Tareas/1S2025/EDD/Proyectos/sistema-trafico/proyecto-final/prueba.csv");
//        System.out.println(v.toString());
        DoublyLinkedList<Integer> lista = new DoublyLinkedList<Integer>();
        for (int i = 0; i < 10; i++) {
            lista.addLast(i);
        }
        lista.print();
        System.out.println(" ");
        lista.printLastToFirst();
    }
}