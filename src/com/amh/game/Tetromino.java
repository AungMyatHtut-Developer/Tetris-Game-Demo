package com.amh.game;

import java.awt.*;

import static com.amh.game.CollisionManager.*;

public class Tetromino {

    private Game game;

    private int x,y,width, height;
    private Block block;
    private boolean isDisapper = false;


    public int aniTick, aniSpeed = 60, gravity = 20, movement = 20;

    public Tetromino(int x, int y, Block block, Game game) {
        this.x = x;
        this.y = y;
        this.width = block.getWidth();
        this.height = block.getHeight();
        this.block = block;
        this.game = game;
    }


    public void render(Graphics g) {
        renderBlock(g);
    }

    public void renderBlock(Graphics g) {

        if(!this.isDisapper)
        {
            int[][] properties = block.getProperties();
            for (int y = 0; y < properties.length; y++) {
                for (int x = 0; x < properties[y].length; x++) {

                    g.setColor(getColorForBlock());
                    if (properties[y][x] == 1) {
                        g.fillRect(x * 20 + this.x, y * 20 + this.y, 20, 20);
                        g.setColor(Color.white);
                        g.drawRect(x * 20 + this.x, y * 20 + this.y, 20, 20);
                    }
                }
            }
        }
    }

    public void renderNextBlock(Graphics g, Block block, int xDelta, int yDelta) {
        int[][] properties = block.getProperties();
        for (int y = 0; y < properties.length; y++) {
            for (int x = 0; x < properties[y].length; x++) {

                g.setColor(getColorForNextBlock(block));
                if (properties[y][x] == 1) {

                    g.fillRect(x * 20 + xDelta, y * 20 + yDelta, 20, 20);
                    g.setColor(Color.white);
                    g.drawRect(x * 20 + xDelta, y * 20 + yDelta, 20, 20);
                }
            }
        }
    }

    private Color getColorForNextBlock(Block block) {
        switch (block) {
            case I1,I2 -> {
                return Color.blue;
            }
            case J1,J2,J3,J4->{
                return Color.GREEN;
            }
            case L1,L2,L3,L4->{
                return Color.CYAN;
            }
            case O -> {
                return Color.yellow;
            }
            case Z1,Z2 -> {
                return Color.RED;
            }
            case T1,T2,T3,T4 -> {
                return Color.MAGENTA;
            }
            case S1,S2 -> {
                return Color.pink;
            }
            default -> {
                return Color.white;
            }
        }
    }


    private Color getColorForBlock() {
        switch (this.block) {
            case I1,I2 -> {
                return Color.blue;
            }
            case J1,J2,J3,J4->{
                return Color.GREEN;
            }
            case L1,L2,L3,L4->{
                return Color.CYAN;
            }
            case O -> {
                return Color.yellow;
            }
            case Z1,Z2 -> {
                return Color.RED;
            }
            case T1,T2,T3,T4 -> {
                return Color.MAGENTA;
            }
            case S1,S2 -> {
                return Color.pink;
            }
            default -> {
                return Color.white;
            }
        }
    }

    public void update() {
        updatePosition();
    }

    public void updatePosition() {

        if(Game.score > 100){
            aniSpeed = 20;
        } else if(Game.score > 60){
            aniSpeed = 30;
        } else if (Game.score > 40) {
            aniSpeed = 40;
        } else if (Game.score > 20) {
            aniSpeed = 50;
        }


        if (this.x <= 100) {
            this.x = 100;
        }

        if (this.x >= 300 - this.width) {
            this.x = 300 - this.width;
        }


        // Check if the Tetromino can move down
        if (!CollisionManager.canMoveHere(this, 0)) {
            // If not, add it to the bucket, update tracked array, and spawn a new Tetromino
            this.y -= gravity; // Move the Tetromino back to its previous position
            isDisapper = true;
            game.tetrominoBucket.add(this);
            CollisionManager.parseToArray(game.tetrominoBucket);
            game.updateTrackedArray(getTrackArrayData());

            deleteFullLine(game.tetrominoBucket);
            game.spawnNewTetromino();
            game.updateNextBlock();
        }else{
            if (this.y >= 460 - this.height){
                System.out.println("You caught me!");
                this.y = 460-this.height;

                //Add the tetromino to the bucket
                isDisapper = true;
                game.tetrominoBucket.add(this);

                // Update the tetromino bucket array and the tracked array
                CollisionManager.parseToArray(game.tetrominoBucket);
                game.updateTrackedArray(getTrackArrayData());

                deleteFullLine(game.tetrominoBucket);
                // Spawn a new tetromino and update the next block
                game.spawnNewTetromino();
                game.updateNextBlock();
            }
        }


        aniTick++;
        if (aniTick >= aniSpeed) {
                this.y += gravity;
                aniTick=0;
        }

    }


    public void moveLeft() {
        if(this.x - 1 > 100 && canMoveHere(this, -movement)){
            this.x -= movement;
        }

    }

    public void moveRight() {
       if(this.x + 1 < 300 - this.block.getWidth() && canMoveHere(this, movement)){
           this.x += movement;
       }
    }

    public void moveDown() {
        if(this.y + 1 < 460 - this.block.getHeight() && canMoveHere(this, 0) && canMoveDown(this,movement)){
            this.y += movement;
        }

    }


    public void transform() {

        switch (this.block) {
            case Block.J1 :
                this.block = Block.J2;
                this.width = Block.J2.getWidth();
                this.height = Block.J2.getHeight();
            break;
            case Block.J2 :
                this.block = Block.J3;
                this.width = Block.J3.getWidth();
                this.height = Block.J3.getHeight();
            break;
            case Block.J3 : this.block = Block.J4;
                this.width = Block.J4.getWidth();
                this.height = Block.J4.getHeight();
            break;
            case Block.J4 : this.block = Block.J1;
                this.width = Block.J1.getWidth();
                this.height = Block.J1.getHeight();
            break;


            case Block.L1 : this.block = Block.L2;
                this.width = Block.L2.getWidth();
                this.height = Block.L2.getHeight();
            break;
            case Block.L2 : this.block = Block.L3;
                this.width = Block.L3.getWidth();
                this.height = Block.L3.getHeight();
            break;
            case Block.L3 : this.block = Block.L4;
                this.width = Block.L4.getWidth();
                this.height = Block.L4.getHeight();
            break;
            case Block.L4 : this.block = Block.L1;
                this.width = Block.L1.getWidth();
                this.height = Block.L1.getHeight();
            break;

            case Block.T1 : this.block = Block.T2;
                this.width = Block.T2.getWidth();
                this.height = Block.T2.getHeight();
            break;
            case Block.T2 : this.block = Block.T3;
                this.width = Block.T3.getWidth();
                this.height = Block.T3.getHeight();
            break;
            case Block.T3 : this.block = Block.T4;
                this.width = Block.T4.getWidth();
                this.height = Block.T4.getHeight();
            break;
            case Block.T4 : this.block = Block.T1;
                this.width = Block.T1.getWidth();
                this.height = Block.T1.getHeight();
            break;

            case Block.I1 : this.block = Block.I2;
                this.width = Block.I2.getWidth();
                this.height = Block.I2.getHeight();
                break;
            case Block.I2 : this.block = Block.I1;
                this.width = Block.I1.getWidth();
                this.height = Block.I1.getHeight();
                break;

            case Block.Z1 : this.block = Block.Z2;
                this.width = Block.Z2.getWidth();
                this.height = Block.Z2.getHeight();
                break;
            case Block.Z2 : this.block = Block.Z1;
                this.width = Block.Z1.getWidth();
                this.height = Block.Z1.getHeight();
                break;

            case Block.S1 : this.block = Block.S2;
                this.width = Block.S2.getWidth();
                this.height = Block.S2.getHeight();
                break;
            case Block.S2 : this.block = Block.S1;
                this.width = Block.S1.getWidth();
                this.height = Block.S1.getHeight();
                break;

        }


    }

    public void updateBlock(Block block){
        this.block = block;
    }

    public Block getBlock() {
        return this.block;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
