package com.giovani;

public class Posicion {
    private int y;
    private int x;

    public Posicion(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public void setPosicion(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    @Override
    public String toString() {
        return "Posicion{" + "Fila: " + y + ", Columna: " + x + '}';
    }
}
