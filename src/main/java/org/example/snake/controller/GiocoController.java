package org.example.snake.controller;


// In initialize() crea il Canvas, la Partita e il Renderer.
// Ad ogni tick aggiorna la Partita, ridisegna e aggiorna la label.
// Ascolta la tastiera e passa direzione, pausa e reset alla Partita.

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class GiocoController {



    @FXML
    public Pane gameArea;
    @FXML
    public Label scoreLabel;
    @FXML
    public void gestisciTastiera(KeyEvent keyEvent) {
    }
}