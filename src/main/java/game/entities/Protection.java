package game.entities;

import game.GameLoop;
import game.GameManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Protection {
    private int lives;
    public final int x;
    public final int y;
    public final static int HEIGHT = 25;
    public final static int WIDHT = 25;
    private BufferedImage sprite;

    public Protection(int x, int y){
        this.x = x;
        this.y = y;
        this.lives = 3;
        try {
            sprite = ImageIO.read(getClass().getResource("/sprites/playerShip2_green.png"));
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
