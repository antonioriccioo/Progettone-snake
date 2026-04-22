package org.example.snake.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.example.snake.model.*;

public class GiocoController {

    @FXML
    private Label scoreLabel;

    @FXML
    private Pane gameArea;

    @FXML
    private Button btnAvvia;

    @FXML
    private Button btnPausa;

    @FXML
    private Button btnRiavvia;

    private Canvas canvas;
    private GraphicsContext gc;

    private final int gridWidth = 30;
    private final int gridHeight = 20;

    private Partita partita;

    private AnimationTimer timer;
    private long lastUpdate = 0;
    private final long updateInterval = 150_000_000; // 150 ms in nanosecondi

    @FXML
    public void initialize() {
        canvas = new Canvas();
        gc = canvas.getGraphicsContext2D();
        gameArea.getChildren().add(canvas);

        canvas.widthProperty().bind(gameArea.widthProperty());
        canvas.heightProperty().bind(gameArea.heightProperty());

        canvas.widthProperty().addListener(e -> aggiornaUI());
        canvas.heightProperty().addListener(e -> aggiornaUI());

        partita = new Partita(gridWidth, gridHeight);
        partita.reset();

        aggiornaUI();

        gameArea.setFocusTraversable(true);
        gameArea.requestFocus();

        gameArea.setOnMouseClicked(e -> gameArea.requestFocus());

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= updateInterval) {
                    if (partita.getStato() == GiocoStato.IN_CORSO) {
                        partita.aggiorna();
                        aggiornaUI();

                        if (partita.getStato() == GiocoStato.GAME_OVER) {
                            scoreLabel.setText("Game Over! Punteggio: " + partita.getPunteggio());
                            stop();
                        }
                    }
                    lastUpdate = now;
                }
            }
        };
    }

    @FXML
    private void gestisciTastiera(KeyEvent event) {
        System.out.println(event);
        switch (event.getCode()) {
            case UP:
                partita.getSerpente().setDirezione(Direzione.SU);
                break;
            case DOWN:partita.getSerpente().setDirezione(Direzione.GIU);
            break;
            case LEFT:partita.getSerpente().setDirezione(Direzione.SINISTRA);
            break;
            case RIGHT:partita.getSerpente().setDirezione(Direzione.DESTRA);
            break;
            default:
                break;
        }
    }

    @FXML
    private void onBtnAvviaClick() {
        if (partita.getStato() == GiocoStato.IN_ATTESA || partita.getStato() == GiocoStato.GAME_OVER) {
            partita.reset();
            partita.start();
            scoreLabel.setText("Punteggio: 0");
        }
        if (!timerIsRunning()) {
            timer.start();
        }
        gameArea.requestFocus();
    }

    @FXML
    private void onBtnPausaClick() {
        if (timerIsRunning()) {
            timer.stop();
        }
        gameArea.requestFocus();
    }

    @FXML
    private void onBtnRiavviaClick() {
        partita.reset();
        partita.start();
        scoreLabel.setText("Punteggio: 0");
        if (!timerIsRunning()) {
            timer.start();
        }
        aggiornaUI();
        gameArea.requestFocus();
    }

    private boolean timerIsRunning() {
        // AnimationTimer non ha metodo isRunning, quindi teniamo traccia con una variabile
        // Oppure proviamo a gestire con un flag
        // Qui useremo un flag semplice:
        return timer != null && lastUpdate != 0;
    }

    private void aggiornaUI() {
        double width = canvas.getWidth();
        double height = canvas.getHeight();

        double cellWidth = width / gridWidth;
        double cellHeight = height / gridHeight;

        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, width, height);

        Punto cibo = partita.getCibo().getPosizione();
        gc.setFill(Color.RED);
        gc.fillOval(cibo.getX() * cellWidth, cibo.getY() * cellHeight, cellWidth, cellHeight);

        gc.setFill(Color.GREEN);
        for (Punto p : partita.getSerpente().getCorpo()) {
            gc.fillRect(p.getX() * cellWidth, p.getY() * cellHeight, cellWidth, cellHeight);
        }

        if (partita.getStato() != GiocoStato.GAME_OVER) {
            scoreLabel.setText("Punteggio: " + partita.getPunteggio());
        }
    }

}
