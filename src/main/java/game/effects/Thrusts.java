package game.effects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class Thrusts {

    HashMap<String, BufferedImage> sprites = new HashMap<>();
    private boolean currentThrustSprite = true; // it is a boolean but in reality it is true = sprite 1 -> false = sprite 2
    private int getCurrentThrustSpriteCounter = 0;
    private int leftX, rightX, y, WIDTH, HEIGHT;

    public Thrusts(String sprite1Route, String sprite2Route, int leftX, int rightX, int y, int width, int height) {
        this.leftX = leftX;
        this.rightX = rightX;
        this.y = y;
        this.WIDTH = width;
        this.HEIGHT = height;
        try {
            BufferedImage thrust1 = ImageIO.read(getClass().getResource(sprite1Route));
            BufferedImage thrust2 = ImageIO.read(getClass().getResource(sprite2Route));
            sprites.put("thrust1", thrust1);
            sprites.put("thrust2", thrust2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw (Graphics2D g2) {
        if (!sprites.isEmpty()) {
            if (currentThrustSprite) {
                BufferedImage thrust = sprites.get("thrust1");
                if (thrust != null) {
                    int randomY = new Random().nextInt(2);
                    if (leftX != 0) {
                        g2.drawImage(thrust, leftX, y + randomY, WIDTH, HEIGHT, null);
                    }
                    if (rightX != 0) {
                        g2.drawImage(thrust, rightX, y + randomY, WIDTH, HEIGHT, null);
                    }
                }
            } else {
                BufferedImage thrust = sprites.get("thrust2");
                if (thrust != null) {
                    int randomY = new Random().nextInt(2);
                    if (leftX != 0) {
                        g2.drawImage(thrust, leftX, y + randomY, WIDTH, HEIGHT, null);
                    }
                    if (rightX != 0) {
                        g2.drawImage(thrust, rightX, y + randomY, WIDTH, HEIGHT, null);
                    }
                }
            }
        } else {
            g2.setColor(Color.GREEN);
            g2.fillRect(leftX, y, WIDTH, HEIGHT);
            g2.fillRect(rightX, y, WIDTH, HEIGHT);
        }
    }

    public void update(int leftX, int rightX, int y) {
        this.leftX = leftX;
        this.rightX = rightX;
        this.y = y;
        this.update();
    }

    public void update() {
        getCurrentThrustSpriteCounter++;
        if (getCurrentThrustSpriteCounter == 10) {
            currentThrustSprite = !currentThrustSprite;
            getCurrentThrustSpriteCounter = 0;
        }
    }
}
