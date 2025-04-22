package com.giovani;

import java.util.Scanner;

public class Traffic {
    private final Scanner scanner = new Scanner(System.in);

    public void init() {
        //getMenu();
        boolean loop = true;
        String input;
        do {
            input = Input.getEnter(scanner);
            if (input.length() == 1 && input.charAt(0) == 'q') loop = false;
            //mostrar reportes del simulador
        } while (loop);

        scanner.close();
    }

    private void getMenu() {
        int option = 0;
        do {
            printMenuOp();
            option = Input.getNumber(scanner, "Seleccione una opcion");
            switch (option) {
                case 1 -> System.out.println("Opcion 1, Iniciar Simulacion.");
                case 2 -> System.out.println("Opcion 2, Reportes.");
                case 3 -> System.out.println("Saliendo...");
                default -> System.out.println("Opcion no valida.");
            }
        } while (option != 3);
    }

    private void printMenuOp() {
        String options = """
                + - - SISTEMA DE TRAFICO. - - +
                | (1) INICIAR SIMULACION.     |
                | (2) REPORTES.               |
                | (3) SALIR.                  |
                + - - - - - - - - - - - - - - +
                """;
        System.out.println(options);
    }
}
