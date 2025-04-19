package com.giovani;

import java.util.Scanner;

public class Input {

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
}
