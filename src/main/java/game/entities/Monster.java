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
    private final int HEIGHT = 25;
    private final int WIDHT = 25;
    private BufferedImage sprite;

    public Monster(String spriteRoute, int x, int y) {
        x = x;
        y = y;
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
