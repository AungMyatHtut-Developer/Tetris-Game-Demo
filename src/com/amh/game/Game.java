package com.amh.game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Game implements Runnable{

    private GamePanel gamePanel;
    private Thread gameThread;
    private TrackWindow trackWindow;

    private final int FPS = 220;
    private final int UPS = 220;

    public static int TRACK_FPS = 0;
    public static int TRACK_UPS = 0;

    public static boolean isPaused = false;
    public static boolean isGamOver = false;
    public static boolean isRestart = false;

    private Tetromino tetromino;
    private int startX = 140, startY = 60;
    private Random random  = new Random();
    public static int nexBlock;
    public static int score;

    public static List<Tetromino> tetrominoBucket = new ArrayList<>();
    public volatile static int[][] tetrominoBucketArray = new int[21][10];

    public static Sound sound = new Sound();
    public static Sound GameMusic = new Sound();

    private Rectangle bounds;

    public Game() {
        initTetromino();
        gamePanel = new GamePanel(this);
        new GameWindow(gamePanel);
        gamePanel.requestFocus(true);

        //initTrackWindow();
        startGame();
    }

    private void initTrackWindow() {
        trackWindow = new TrackWindow(gamePanel);
    }

    private void initTetromino() {
        tetromino = new Tetromino(startX,startY, parseBlockFromNumber( random.nextInt(7)), this);
        nexBlock = random.nextInt(7);
        bounds = new Rectangle(500/2 - 60 , 700/2 + 30, 100, 50);

        GameMusic.loop(4);
    }

    public Rectangle getBonds(){
        return this.bounds;
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

    private void startGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void render(Graphics g) {
        tetromino.render(g);
        drawTeromio(g, tetrominoBucketArray);
    }

    public void drawTeromio(Graphics g, int[][] tetrominoBucketArray){

        for (int y = 0; y < tetrominoBucketArray.length; y++) {
            for (int x = 0; x < tetrominoBucketArray[y].length; x++) {

                g.setColor(new Color(64,64,64));
                if (tetrominoBucketArray[y][x] == 1) {
                    g.fillRect(x * 20 + 100, y * 20 + 60, 20, 20);
                    g.setColor(Color.white);
                    g.drawRect(x * 20 + 100, y * 20 + 60, 20, 20);
                }
            }
        }
    }

    public void update() {
        tetromino.update();
    }

    public void updateTrackedArray(StringBuilder data) {
//        trackWindow.updateTrackedArray(data);
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
        nexBlock = random.nextInt(7);
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

            if(!isPaused && !isGamOver){
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
            }else{
                lastCheckedTimeForFPS = System.currentTimeMillis();
                lastCheckedTimeForUPS = System.currentTimeMillis();
                gamePanel.repaint();
            }
        }

    }



    public void pauseGame(){
        this.isPaused = !isPaused;
        sound.play(5);
    }

    public void restartGame(){

            tetrominoBucketArray = new int[21][10];

            isGamOver = false;

            score = 0;
            GameMusic.loop(4);
            nexBlock = random.nextInt(7);

    }
}
