package org.example.snake.model;
import java.util.ArrayList;
// Rappresenta il serpente.
// Ad ogni movimento aggiunge una nuova testa davanti
// e rimuove la coda, a meno che non abbia appena mangiato.
// Controlla se è uscito dalla griglia o si è morso da solo.


public class Snake {

    private ArrayList<Punto> corpo = new ArrayList<>();
    private Direzione direzione = Direzione.DESTRA;
    private boolean deveCrescere = false;

    public Snake(int startX, int startY) {
        // Serpente iniziale lungo 3 celle
        corpo.add(new Punto(startX, startY));
        corpo.add(new Punto(startX - 1, startY));// testa
        corpo.add(new Punto(startX - 2, startY));   // coda
    }

    public ArrayList<Punto> getCorpo() {
        return corpo;
    }

    public Punto getTesta() {
        return corpo.get(0);
    }

    public void setDirezione(Direzione nuova) {
        // Evita inversioni di 180°
        if ((direzione == Direzione.SU && nuova == Direzione.GIU) ||
                (direzione == Direzione.GIU && nuova == Direzione.SU) ||
                (direzione == Direzione.DESTRA && nuova == Direzione.SINISTRA) ||
                (direzione == Direzione.SINISTRA && nuova == Direzione.DESTRA)) {
            return;
        }
        direzione = nuova;
    }

    public void cresci() {
        deveCrescere = true;
    }

    public void muovi() {
        Punto testa = getTesta();
        int x = testa.getX();
        int y = testa.getY();

        switch (direzione) {
            case SU:
                y--;
                break;
            case GIU:
                y++;
                break;
            case DESTRA:
                x++;
                break;
            case SINISTRA:
                x--;
                break;
        }

        // Aggiunge nuova testa in posizione 0
        corpo.add(0, new Punto(x, y));

        // Se non deve crescere, rimuove la coda
        if (!deveCrescere) {
            corpo.remove(corpo.size() - 1);
        } else {
            deveCrescere = false;
        }
    }

    public boolean haCollisioneConSeStesso() {
        Punto testa = getTesta();
        for (int i = 1; i < corpo.size(); i++) {
            Punto p = corpo.get(i);
            if (testa.getX() == p.getX() && testa.getY() == p.getY()) {
                return true;
            }
        }
        return false;
    }

    public boolean èFuoriDallaGriglia(int larghezza, int altezza) {
        Punto t = getTesta();
        return t.getX() < 0 || t.getX() >= larghezza ||
                t.getY() < 0 || t.getY() >= altezza;
    }
}

