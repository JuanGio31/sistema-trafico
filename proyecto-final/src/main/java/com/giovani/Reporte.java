package com.giovani;

import com.giovani.tad.linear.StackGeneric;

public class Reporte {
    private StackGeneric<String> eventos;
    private Ciudad ciudad;

    public Reporte(StackGeneric<String> eventos, Ciudad ciudad) {
        this.eventos = eventos;
        this.ciudad = ciudad;
    }

    public void ver() {
        imprimirPila();
    }

    public void imprimirPila() {
        int n = Math.min(this.eventos.size(), 20);
        System.out.println("===========================");
        System.out.println("Ultimos " + n + " eventos");
        for (int i = 0; i < n; i++) {
            try {
                System.out.println(eventos.pop());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("===========================");
    }
}
