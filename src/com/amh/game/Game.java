package com.amh.game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game implements Runnable{

    private GamePanel gamePanel;
    private Thread gameThread;

    private final int FPS = 120;
    private final int UPS = 60;

    public static int TRACK_FPS = 0;
    public static int TRACK_UPS = 0;

    private Tetromino tetromino;
    private int startX = 140, startY = 60;
    private Random random  = new Random();
    public static int nexBlock;

    public List<Tetromino> tetrominoBucket = new ArrayList<>();

    public Game() {
        initTetromino();
        gamePanel = new GamePanel(this);
        new GameWindow(gamePanel);
        gamePanel.requestFocus(true);

        startGame();
    }

    private void initTetromino() {
        tetromino = new Tetromino(startX,startY,  Block.Z1, this);
        nexBlock = random.nextInt(6);
    }

    private void startGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void render(Graphics g) {
        tetromino.render(g);

        for (Tetromino t : tetrominoBucket) {
            t.drawInBucket(g, t.getBlock(), t.getX(), t.getY());
        }
    }

    public void update() {
        tetromino.update();
    }

    public void spawnNewTetromino() {
        tetromino = new Tetromino(startX,startY,  drawNextBlock(), this);
    }

    public Block drawNextBlock() {

        int nextBlock = nexBlock;

        switch (nextBlock) {
            case 0: return Block.L1;
            case 1: return Block.J1;
            case 2: return Block.S1;
            case 3: return Block.Z1;
            case 4: return Block.O;
            case 5: return Block.I1;
            default:return Block.T1;
        }

    }

    public void updateNextBlock() {
        nexBlock = random.nextInt(6);
    }

    public Tetromino getTetromino() {
        return this.tetromino;
    }


    @Override
    public void run() {

        int frames = 0;
        int updates = 0;

        long gameFPS = 1_000_000_000/ FPS;
        long gameUPS = 1_000_000_000/ UPS;

        long currentTime = 0;
        long lastCheckedTimeForFPS = 0;
        long lastCheckedTimeForUPS = 0;



        long currentTimeToTrackFPSAndUPS = 0;
        long lastCheckTimeToTrackFPSAndUPS = 0;


        while (true) {

            currentTime = System.nanoTime();
            if (currentTime - lastCheckedTimeForFPS >= gameFPS) {
                lastCheckedTimeForFPS = currentTime;
                gamePanel.repaint();
                frames++;
            }


            if (currentTime - lastCheckedTimeForUPS >= gameUPS) {
                lastCheckedTimeForUPS = currentTime;
                //update Game
                update();
                updates++;
            }

            currentTimeToTrackFPSAndUPS = System.nanoTime();
            if (currentTimeToTrackFPSAndUPS - lastCheckTimeToTrackFPSAndUPS >= 1_000_000_000) {
                lastCheckTimeToTrackFPSAndUPS = currentTimeToTrackFPSAndUPS;
                System.out.println("FPS : "+ frames + " UPS : "+ updates);

                TRACK_FPS = frames;
                TRACK_UPS = updates;
                frames = 0;
                updates = 0;
            }


        }

    }
}
