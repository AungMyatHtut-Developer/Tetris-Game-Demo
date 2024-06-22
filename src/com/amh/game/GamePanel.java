package com.amh.game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;import java.util.Random;

public class GamePanel extends JPanel {

    private Game game;
    private Random random;

    private BufferedImage backgroundImage;

    public GamePanel(Game game) {
        this.game = game;
        setGameWidthAndHeight();

        addKeyListener(new KeyboardHandler(game));
        MouseHandler mouseHandler = new MouseHandler(game);
        addMouseMotionListener(mouseHandler);
        addMouseListener(mouseHandler);

        backgroundImage = LoadImage.getSprite("background");
    }


    public void setGameWidthAndHeight() {

        Dimension dimension = new Dimension(500, 700);

        setPreferredSize(dimension);
        setMaximumSize(dimension);
        setMinimumSize(dimension);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawGrid(g);
        g.drawImage(backgroundImage, 0,0,500, 700, null);

        drawFPSAndUPS(g);
        drawBorder(g);
        showNextBlockPane(g);
        drawScorePane(g);

        game.render(g);

        if(Game.isPaused){
            drawPauseOverlay(g);
        }

        if(Game.isGamOver){
            drawGameOverOverlay(g);
        }
    }

    public void drawPauseOverlay(Graphics g){
        Graphics2D g2D = (Graphics2D) g;
        float transparent = 0.5f;
        g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparent));
        g2D.setColor(Color.black);
        g2D.fillRect(0,0,500,700);

        g.setColor(Color.white);
        g.setFont(new Font(Font.DIALOG, Font.BOLD, 32));
        g.drawString("PAUSED", 500/2 - 60, 700/2);
    }

    public void drawGameOverOverlay(Graphics g){
        Graphics2D g2D = (Graphics2D) g;
        float transparent = 0.5f;
        g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparent));
        g2D.setColor(Color.black);
        g2D.fillRect(0,0,500,700);

        float tran = 1f;
        g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, tran));

        g.setColor(Color.white);
        g.setFont(new Font(Font.DIALOG, Font.BOLD, 32));
        g.drawString("GameOver", 500/2 -80, 700/2);

        g.setColor(Color.DARK_GRAY);
        g.fillRect(500/2 - 60 , 700/2 + 30, 100, 50);

        g.setColor(Color.WHITE);
        g.drawString("Start", 500/2 - 48, 700/2 + 65);
        g.drawRect(500/2 - 60 , 700/2 + 30, 100, 50);
    }

    public void drawFPSAndUPS(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
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
        g.setColor(Color.WHITE);
        g.drawRect(100,60, 200, 400);
//        for (int y = 0; y < 20; y++) {
//            for (int x = 0; x < 10; x++) {
//                g.setColor(Color.WHITE);
//                g.drawRect(x*20 + 100, y*20 + 60, 20,20);
//            }
//        }
    }

    public void showNextBlockPane(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawRect(340, 60, 120, 120);
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
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
        g.setColor(Color.WHITE);
        g.drawRect(340, 200, 120, 100);
        drawScore(g);
    }

    public void drawScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("score : "+ Game.score, 345, 220);
    }



}
