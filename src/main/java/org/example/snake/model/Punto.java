package org.example.snake.model;
// Rappresenta una singola cella della griglia di gioco.
// Ha due campi interi x e y che indicano colonna e riga.
// due punti per valore e non per riferimento in memoria.
public class Punto {
    private int x;
    private int y;

    public Punto(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    //FINITO
}
