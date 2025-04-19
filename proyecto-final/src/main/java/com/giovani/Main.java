package com.giovani;

import com.giovani.tad.StackGeneric;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opt = Input.getNumber(scanner, "Ingrese un numero");
        System.out.println(opt);
        String message = Input.getString(scanner, "Ingresar nombre");
        System.out.println(message);
        scanner.close();
    }

    private static void stackImplementation() {
        StackGeneric<Integer> pila = new StackGeneric<>();
        pila.push(10);
        pila.push(20);
        pila.push(30);
        pila.push(50);
        final int n = pila.size();
        try {
            for (int i = 0; i < n + 2; i++) {
                System.out.print(pila.pop() + " - ");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}