package com.amh.game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadImage {

public static BufferedImage getSprite(String atlasName) {
        BufferedImage image = null;
        try (InputStream inputStream = LoadImage.class.getResourceAsStream("/img/"+atlasName+".png")){
            if (inputStream == null) {
                throw new IOException("Image not found!");
            }
            image = ImageIO.read(inputStream);
            System.out.println("We found the image");

        } catch (Exception e) {
            System.out.println("Exception happened when loading Sprite. Error : "+ e);
        }
        return image;
    }
}
