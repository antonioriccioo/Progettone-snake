package org.example.snake.model;

import java.util.Random;

/**
 * Rappresenta il cibo sulla griglia.
 * Può rigenerarsi in una posizione casuale.
 */
public class Cibo {

    private Punto posizione;
    private Random random = new Random();

    public Cibo(int larghezza, int altezza) {
        rigenera(larghezza, altezza);
    }

    public Punto getPosizione() {
        return posizione;
    }

    public void rigenera(int larghezza, int altezza) {
        int x = random.nextInt(larghezza);
        int y = random.nextInt(altezza);
        posizione = new Punto(x, y);
    }
    //FINITO
}