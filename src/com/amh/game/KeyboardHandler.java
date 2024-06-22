package com.amh.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardHandler implements KeyListener {

    private Game game;

    public KeyboardHandler(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> game.getTetromino().moveLeft();
            case KeyEvent.VK_RIGHT -> game.getTetromino().moveRight();
            case KeyEvent.VK_DOWN -> game.getTetromino().moveDown();
            case KeyEvent.VK_UP -> {
                game.getTetromino().transform();
                Game.sound.play(1);
            }
            case KeyEvent.VK_P -> game.pauseGame();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
