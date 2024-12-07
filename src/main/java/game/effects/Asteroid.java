package game.effects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Asteroid {
    public int x, y;
    private final int WIDHT;
    private final int HEIGHT;
    private int angle = 0;
    private int rotationSpeed;
    private static final ArrayList<BufferedImage> sprites = new ArrayList<>();
    private final BufferedImage sprite;
    static {
        try {
            sprites.add(ImageIO.read(Asteroid.class.getResource("/effects/meteorGrey_big1.png")));
            sprites.add(ImageIO.read(Asteroid.class.getResource("/effects/meteorGrey_big2.png")));
            sprites.add(ImageIO.read(Asteroid.class.getResource("/effects/meteorGrey_big3.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Asteroid(int startingX, int rotationSpeed) {
        this.x = startingX;
        this.y = -10;
        int sizeMultiplier = new Random().nextInt(2 + 1);
        this.WIDHT = 10 * sizeMultiplier;
        this.HEIGHT = 10 * sizeMultiplier;
        this.rotationSpeed = rotationSpeed;
        int asteroidSprite = new Random().nextInt(3);
        sprite = sprites.get(asteroidSprite);
    }

    public void update() {
        x += 1;
        y += 1;
        angle += rotationSpeed;
    }

    public void draw (Graphics2D g2) {
        if (sprite != null) {
            int centerX = x + WIDHT / 2;
            int centerY = y + HEIGHT / 2;
            AffineTransform original = g2.getTransform();
            g2.rotate(Math.toRadians(angle), centerX, centerY);
            g2.drawImage(sprite, x, y, WIDHT, HEIGHT, null);
            g2.setTransform(original);
        } else {
            g2.setColor(Color.GRAY);
            g2.fillRect(x, y, WIDHT, HEIGHT);
        }
    }

    public static Asteroid generateAsteroid () {
        int chance = new Random().nextInt(150);
        if (chance < 6) {
            return new Asteroid(new Random().nextInt(1000) - 500, new Random().nextInt(3) + 1);
        }
        return null;
    }

}
