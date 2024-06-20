package com.amh.game;

import java.util.ArrayList;
import java.util.List;

import static com.amh.game.Game.score;
import static com.amh.game.Game.tetrominoBucketArray;

public class CollisionManager {

    private static final int COLLISION_CHECK = 1;

    public static boolean canMoveHere(Tetromino currentTetromino, int dx) {
        int[][] blockArray = currentTetromino.getBlock().getProperties();
        float tetrominoX = currentTetromino.getX() + dx; // Update x position with dx
        float tetrominoY = currentTetromino.getY();

        for (int y = 0; y < blockArray.length; y++) {
            for (int x = 0; x < blockArray[y].length; x++) {
                int data = blockArray[y][x];
                if (data == COLLISION_CHECK) {
                    float codY = ((tetrominoY - 60) / 20) + y;
                    float codX = ((tetrominoX - 100) / 20) + x;
                    // Check if the position is out of bounds or already occupied
                    if (codY >= 0 && codY < tetrominoBucketArray.length && codX >= 0 && codX < tetrominoBucketArray[(int) codY].length) {
                        if (tetrominoBucketArray[(int) codY][(int) codX] == COLLISION_CHECK) {
                            // Collision detected
                            return false;
                        }
                    } else {
                        // Out of bounds
                        return false;
                    }
                }
            }
        }
        // No collision detected
        return true;

    }

    public static boolean canMoveDown(Tetromino currentTetromino, int movement) {
        int[][] blockArray = currentTetromino.getBlock().getProperties();
        int tetrominoX = currentTetromino.getX();
        int tetrominoY = currentTetromino.getY() + movement; // Move down by one movement step

        for (int y = 0; y < blockArray.length; y++) {
            for (int x = 0; x < blockArray[y].length; x++) {
                int data = blockArray[y][x];
                if (data == COLLISION_CHECK) {
                    int codY = ((tetrominoY - 60) / 20) + y;
                    int codX = ((tetrominoX - 100) / 20) + x;
                    // Check if the position is out of bounds or already occupied
                    if (codY >= 0 && codY < tetrominoBucketArray.length && codX >= 0 && codX < tetrominoBucketArray[codY].length) {
                        if (tetrominoBucketArray[codY][codX] == COLLISION_CHECK) {
                            // Collision detected
                            return false;
                        }
                    } else {
                        // Out of bounds
                        return false;
                    }
                }
            }
        }
        // No collision detected
        return true;
    }


    public static void parseToArray(List<Tetromino> bucketList) {
        for (Tetromino t : bucketList) {
            int[][] blockArray = t.getBlock().getProperties();
            for (int y = 0; y < blockArray.length; y++) {
                for (int x = 0; x < blockArray[y].length; x++) {
                    int data = blockArray[y][x];
                    if (data == 1) {

                        int codY = ((t.getY() - 60) / 20) + y;
                        int codX = ((t.getX() - 100) / 20) + x;

                        if(codY < 20 && codX < 10){
                            tetrominoBucketArray[codY][codX] = 1;
                        }

                    }
                }
            }
        }
        Game.tetrominoBucket = new ArrayList<>();
    }

    public static void deleteFullLine(List<Tetromino> bucketList) {
        int value = 0;
        for (int y = 0; y < tetrominoBucketArray.length; y++) {
            for (int x = 0; x < tetrominoBucketArray[y].length; x++) {
                value += tetrominoBucketArray[y][x];

                if (value == 10) {
                    for (int i = 0; i < 10; i++) {
                        tetrominoBucketArray[y][i] = 0;
                    }

                    for(int deltaY = y; deltaY > 0 ; deltaY--){
                        for(int deltaX = 0 ; deltaX < 10 ; deltaX++){
                            tetrominoBucketArray[deltaY][deltaX] = tetrominoBucketArray[deltaY-1][deltaX];
                        }
                    }
                    ++score;
                }

            }

            System.out.println("Total Value : " + value);
            value = 0;
        }
    }

    public static StringBuilder getTrackArrayData() {
        StringBuilder trackedArray = new StringBuilder();
        for (int row = 0; row < tetrominoBucketArray.length; row++) {
            for (int col = 0; col < tetrominoBucketArray[row].length; col++) {
                // Print the element with a space, so it's easier to read

                trackedArray.append(tetrominoBucketArray[row][col] + "&nbsp; &nbsp;");
            }
            // Move to the next line after printing all columns in the current row
            trackedArray.append("\n");
        }
        return trackedArray;
    }
}
