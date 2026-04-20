package org.example.snake.model;
// Aggrega Snake, Cibo, GiocoStato e il punteggio.
// Ad ogni tick fa muovere il serpente, controlla collisioni,
// controlla se ha mangiato il cibo e aggiorna il punteggio.

public class Partita {

    private final int larghezza;   // larghezza griglia
    private final int altezza;     // altezza griglia
    private Snake serpente;
    private Cibo cibo;
    private GiocoStato stato;
    private int punteggio;

    public Partita(int larghezza, int altezza) {
        this.larghezza = larghezza;
        this.altezza = altezza;
        reset();
    }

    public void reset() {
        //posizione griglia serpente
        int startX = larghezza / 2;
        int startY = altezza / 2;
        serpente = new Snake(startX, startY);
        cibo = new Cibo(larghezza, altezza);
        stato = GiocoStato.IN_ATTESA;
        punteggio = 0;
    }

    public void start() {
        if (stato == GiocoStato.IN_ATTESA) {
            stato = GiocoStato.IN_CORSO;
        }
    }

    public GiocoStato getStato() {
        return stato;
    }

    public int getPunteggio() {
        return punteggio;
    }

    public Snake getSerpente() {
        return serpente;
    }

    public Cibo getCibo() {
        return cibo;
    }

    /**
     * Aggiorna lo stato della partita ad ogni tick.
     * Muove il serpente, verifica collisioni, verifica se ha mangiato il cibo,
     * aggiorna punteggio e stato di gioco.
     */
    public void aggiorna() {
        if (stato != GiocoStato.IN_CORSO) {
            return; // se non in corso, non fare nulla
        }

        serpente.muovi();

        if (serpente.èFuoriDallaGriglia(larghezza, altezza)) {
            stato = GiocoStato.GAME_OVER;
            return;
        }

        if (serpente.haCollisioneConSeStesso()) {
            stato = GiocoStato.GAME_OVER;
            return;
        }

        // Controlla se serpente ha mangiato il cibo
        Punto testa = serpente.getTesta();
        Punto posCibo = cibo.getPosizione();
        if (testa.getX() == posCibo.getX() && testa.getY() == posCibo.getY()) {
            serpente.cresci();
            punteggio += 1;
            cibo.rigenera(larghezza, altezza);
        }
    }
}

