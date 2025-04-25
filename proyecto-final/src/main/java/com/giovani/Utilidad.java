package com.giovani;

import java.util.Scanner;

public class Utilidad {

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
        String reset = "\u001B[0m";
        switch (c) {
            case 'R' -> printRojo(c, reset);
            case 'C' -> printAzul(c, reset);
            case 'B' -> printNaranja(c, reset);
            case 'V' -> printVerde(c, reset);
        }
    }

    private static void printNaranja(char c, String reset) {
        String naranja = "\u001B[38;5;208m";
        System.out.print(naranja + c + reset);
    }

    private static void printAzul(char c, String reset) {
        String azul = "\u001B[34m";
        System.out.print(azul + c + reset);
    }

    private static void printRojo(char c, String reset) {
        String rojo = "\u001B[31m";
        System.out.print(rojo + c + reset);
    }

    private static void printVerde(char c, String reset) {
        String verde = "\u001B[32m";
        System.out.print(verde + c + reset);
    }

    public static void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
