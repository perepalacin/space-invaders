package game.entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Monster {
    private int hitPoints;
    private static int SCORE;
    public int x;
    public int y;
    public static int HEIGHT = 25;
    public static int WIDHT = 25;
    private BufferedImage sprite;

    public Monster(String spriteRoute, int initialX, int initialY) {
        x = initialX;
        y = initialY;
        System.out.println(spriteRoute);
        try {
            sprite = ImageIO.read(getClass().getResource(spriteRoute));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw (Graphics2D g2) {
        if (sprite != null) {
            g2.drawImage(sprite, x, y, WIDHT, HEIGHT, null);
        } else {
            // Fallback to drawing a rectangle if the sprite fails to load
            g2.setColor(Color.GREEN);
            g2.fillRect(x, y, WIDHT, HEIGHT);
        }
    }
}
