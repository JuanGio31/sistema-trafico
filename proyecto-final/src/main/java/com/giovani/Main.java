package com.giovani;

import com.giovani.tad.linear.StackGeneric;
import com.giovani.tad.nonlinear.AVL;

public class Main {
    public static void main(String[] args) {
//        Traffic traffic = new Traffic();
//        traffic.init();
        avlImplementation();
    }

    private static void avlImplementation() {
        AVL avl = new AVL();
        avl.insertar(10);
        avl.insertar(5);
        avl.insertar(13);
        avl.insertar(1);
        avl.insertar(6);
        avl.insertar(17);
        avl.preOrder();
        avl.inOrder();
        avl.postOrder();
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