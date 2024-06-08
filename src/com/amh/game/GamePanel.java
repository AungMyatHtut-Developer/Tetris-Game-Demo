package com.amh.game;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GamePanel extends JPanel {

    private Game game;
    private Random random;

    public GamePanel(Game game) {
        this.game = game;
        setGameWidthAndHeight();

        addKeyListener(new KeyboardHandler(game));
        MouseHandler mouseHandler = new MouseHandler();
        addMouseMotionListener(mouseHandler);
        addMouseListener(mouseHandler);
    }


    public void setGameWidthAndHeight() {

        Dimension dimension = new Dimension(500, 500);

        setPreferredSize(dimension);
        setMaximumSize(dimension);
        setMinimumSize(dimension);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        drawGrid(g);
        drawFPSAndUPS(g);
        drawBorder(g);
        showNextBlockPane(g);
        drawScorePane(g);

        game.render(g);
    }

    public void drawFPSAndUPS(Graphics g) {
        g.setColor(Color.GREEN);
        g.drawString("FPS : "+ Game.TRACK_FPS + " UPS : "+ Game.TRACK_UPS, 20,20);
    }

    public void drawGrid(Graphics g) {
        for (int y = 0; y < 25; y++) {
            for (int x = 0; x < 25; x++) {
                g.setColor(Color.DARK_GRAY);
//                g.setColor(getRandomColor());
                g.fillRect(x * 20, y * 20, 20,20);
            }
        }

    }

    public Color getRandomColor() {
        random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        return new Color(r,g,b);
    }

    public void drawBorder(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(100,60, 200, 400);
//        for (int y = 0; y < 20; y++) {
//            for (int x = 0; x < 10; x++) {
//                g.setColor(Color.WHITE);
//                g.drawRect(x*20 + 100, y*20 + 60, 20,20);
//            }
//        }
    }

    public void showNextBlockPane(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(340, 60, 120, 120);
        g.setColor(Color.WHITE);
        g.drawString("Next Block :", 345, 80);

        int nextBlock = Game.nexBlock;
        game.getTetromino().renderNextBlock(g,parseBlockFromNumber(nextBlock) , 360, 100 );
    }

    public Block parseBlockFromNumber(int id) {
        switch (id) {
            case 0: return Block.L1;
            case 1: return Block.J1;
            case 2: return Block.S1;
            case 3: return Block.Z1;
            case 4: return Block.O;
            case 5: return Block.I1;
            default:return Block.T1;
        }
    }

    public void drawScorePane(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(340, 200, 120, 100);
        drawScore(g);
    }

    public void drawScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("score : ", 345, 220);
    }
}
