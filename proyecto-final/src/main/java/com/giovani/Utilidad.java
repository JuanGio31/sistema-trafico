package com.giovani;

import java.util.Scanner;

public class Utilidad {
    private static final String RESET = "\u001B[0m";

    public static int getNumber(Scanner scanner, String message) {
        int opt;
        while (true) {
            try {
                System.out.print(message + " > ");
                opt = Integer.parseInt(scanner.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("La entrada debe ser un numero");
            }
        }
        return opt;
    }

    public static String getString(Scanner scanner, String message) {
        System.out.print(message + " > ");
        return scanner.nextLine();
    }

    public static String getEnter(Scanner scanner) {
        System.out.print("Presiona enter para continuar > ");
        return scanner.nextLine();
    }

    public static void printColor(char c) {
        switch (c) {
            case 'R' -> printRojo(c);
            case 'C' -> printAzul(c);
            case 'B' -> printNaranja(c);
            case 'V' -> printVerde(c);
        }
    }

    private static void printNaranja(char c) {
        String naranja = "\u001B[38;5;208m";
        System.out.print(naranja + c + RESET);
    }

    private static void printAzul(char c) {
        String azul = "\u001B[34m";
        System.out.print(azul + c + RESET);
    }

    public static void printCyan(char c) {
        String cyan = "\u001B[36m";
        System.out.print(cyan + c + RESET);
    }

    public static void printPurpura(String c) {
        String purpura = "\033[0;35m";
        System.out.print(purpura + c + RESET);
    }

    private static void printRojo(char c) {
        String rojo = "\u001B[31m";
        System.out.print(rojo + c + RESET);
    }

    private static void printVerde(char c) {
        String verde = "\u001B[32m";
        System.out.print(verde + c + RESET);
    }

    public static void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
