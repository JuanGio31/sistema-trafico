package com.giovani;

import java.util.Scanner;

public class Utilidad {
    private static final String RESET = "\u001B[0m";
    private static final String ROJO = "\u001B[31m";
    private static final String PURPURA = "\033[0;35m";
    private static final String NARANJA = "\u001B[38;5;208m";
    private static final String CYAN = "\u001B[36m";
    private static final String VERDE = "\u001B[32m";
    private static final String AZUL = "\u001B[34m";

    public static int getNumber(Scanner scanner, String message) {
        int opt;
        while (true) {
            try {
                System.out.print(CYAN + message + RESET);
                System.out.print(" > ");
                opt = Integer.parseInt(scanner.nextLine());
                break;
            } catch (Exception e) {
                printCadenaEnRojo("La entrada debe ser un numero");
            }
        }
        return opt;
    }

    public static String getString(Scanner scanner, String message) {
        System.out.print(message + " > ");
        return scanner.nextLine();
    }

    public static String getEnter(Scanner scanner) {
        System.out.print(CYAN + "Presiona enter para continuar" + RESET + " > ");
        return scanner.nextLine();
    }

    public static void printCharColor(char c) {
        switch (c) {
            case 'R' -> System.out.print(ROJO + c + RESET);
            case 'C' -> System.out.print(AZUL + c + RESET);
            case 'B' -> System.out.print(NARANJA + c + RESET);
            case 'V' -> System.out.print(VERDE + c + RESET);
        }
    }

    public static void printCyan(String c) {
        System.out.print(CYAN + c + RESET);
    }

    public static void printPurpura(String c) {
        System.out.print(PURPURA + c + RESET);
    }

    public static void printCadenaEnRojo(String str) {
        System.out.println(ROJO + str + RESET);
    }

    public static void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static int convertirCharAEntero(char a) {
        char temp = Character.toUpperCase(a);
        return temp - 'A';
    }

    public static int validarColumna(String string) {
        int num = 0;
        try {
            num = Integer.parseInt(string);
            if (num >= 1) {
                num--;
            }
            return num;
        } catch (Exception e) {
            num = -1;
        }
        return num;
    }
}
