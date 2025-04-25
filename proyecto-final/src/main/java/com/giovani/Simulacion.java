package com.giovani;

import com.giovani.tad.linear.DoublyLinkedList;
import com.giovani.tad.nonlinear.Ciudad;
import com.giovani.tad.nonlinear.TablaDispersion;

import java.util.Scanner;

public class Simulacion {
    private final Ciudad ciudad;
    private TablaDispersion dispersion;
    private final Scanner scanner = new Scanner(System.in);
    Object temp;

    public Simulacion() {
        this.ciudad = new Ciudad(8);
        this.dispersion = new TablaDispersion();
    }

    public void init() {
        boolean loop = true;
        String input;
        do {
            ciudad.print();
            ((DoublyLinkedList<?>) temp).print();
            input = Utilidad.getEnter(scanner);
            if (input.length() == 1 && input.charAt(0) == 'q') loop = false;
            //mostrar reportes del simulador
        } while (loop);
    }

    public void getMenu() {
        int option = -1;
        do {
            printMenuOp();
            option = Utilidad.getNumber(scanner, "Seleccione una opcion");
            switch (option) {
                case 1 -> {
                    cargaCSV();
                    init();
                }
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opcion no valida.");
            }
        } while (option != 0);
        scanner.close();
    }

    private void printMenuCargaCSV() {
        String options = """
                \t+ - CARGA CSV - +
                \t| (1)  SI.      |
                \t| (0)  NO.      |
                \t+ - - - - - - - +
                """;
        System.out.println(options);
    }

    private void cargaCSV() {
        int option = -1;
        do {
            printMenuCargaCSV();
            option = Utilidad.getNumber(scanner, "Seleccione una opcion");
            if (option == 1) {
                FileManager fileManager = new FileManager();
                do {
                    String path = Utilidad.getString(scanner, "Ingresar la ruta del archivo");
                    temp = fileManager.readCSV(path);
                } while (temp == null);
                break;
            } else if (option == 0) {
                break;
            } else {
                System.out.println("Opcion no valida.");
            }
        } while (true);

    }

    private void printMenuOp() {
        String options = """
                \t+ - - SISTEMA DE TRAFICO. - - +
                \t| (1) INICIAR SIMULACION.     |
                \t| (0) SALIR.                  |
                \t+ - - - - - - - - - - - - - - +
                """;
        System.out.println(options);
    }
}
