package com.giovani;

import com.giovani.tad.linear.StackGeneric;

public class Reporte {
    private final StackGeneric<String> eventos;

    public Reporte(StackGeneric<String> eventos) {
        this.eventos = eventos;
    }

    public void ver() {
        mostrarEventos();
    }

    public void mostrarEventos() {
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

    public void mostrarReporteCiudad() {
        System.out.println("===========================");
        System.out.println("Reporte de la ciudad");
        //Ranking V, por prioridad y tiempo de cruce
        //Cantidad total de V que cruzaron por carril
        //tiempo promedio de espera por tipo de vehiculo
        //ultimos 20 eventos en pila
    }

    public void registrarEvento(String evento) {
        this.eventos.push(evento);
    }
}
