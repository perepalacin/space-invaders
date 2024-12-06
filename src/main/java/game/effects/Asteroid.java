package game.effects;

import game.entities.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Asteroid {
    private int x, y;
    private final int WIDHT = 10;
    private final int HEIGHT = 10;
    private BufferedImage sprite;

    public Asteroid(int x, int y) {
        this.x = x;
        this.y = y;
        int asteroidSprite = new Random().nextInt(3);
        try {
            sprite = switch (asteroidSprite) {
                case 0 ->
                        ImageIO.read(getClass().getResource("/effects/meteorGrey_big1.png"));
                case 1 ->
                        ImageIO.read(getClass().getResource("/effects/meteorGrey_big2.png"));
                case 2 ->
                        ImageIO.read(getClass().getResource("/effects/meteorGrey_big3.png"));
                default ->
                        ImageIO.read(getClass().getResource("/effects/meteorGrey_big1.png"));
            };
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void update() {
        x += 1;
        y += 2;
    }

    public void draw (Graphics2D g2) {
        if (sprite != null) {
            g2.drawImage(sprite, x, y, WIDHT, HEIGHT, null);
        } else {
            g2.setColor(Color.GRAY);
            g2.fillRect(x, y, WIDHT, HEIGHT);
        }
    }

}
