package com.amh.game;

import java.util.List;

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



    public static void createArrayList(List<Tetromino> bucketList){
        for (Tetromino t : bucketList) {
            int[][] blockArray = t.getBlock().getProperties();
            for (int y = 0; y < blockArray.length; y++) {
                for (int x = 0; x < blockArray[y].length; x++) {
                    int data = blockArray[y][x];
                    if (data == 1) {

                        int codY = ((t.getY() - 60) / 20) + y;
                        int codX = ((t.getX() - 100) / 20) + x  ;

                        tetrominoBucketArray[codY][codX] = 1;
                    }
                }
            }
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
