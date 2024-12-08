package game.effects;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import game.GameLoop;

public class SpeedEffect {
    public int x, y = -300;
    private final int WIDHT = 5;
    private int HEIGHT;
    private int speed;
    private static BufferedImage sprite;
    private float opacity = 0.5f;
    static {
        try {
            sprite = ImageIO.read(Asteroid.class.getResource("/effects/speed.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public SpeedEffect(int x) {
        this.x = x;
        speed = 15 + new Random().nextInt(5);
        this.HEIGHT = 150 * new Random().nextInt(7);
        this.opacity = (float) (3 + new Random().nextInt(5)) / 10;
    }

    public void update() {
        y += speed;
    }

    public void draw (Graphics2D g2) {
        if (sprite != null) {
            Composite originalComposite = g2.getComposite();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            g2.drawImage(sprite, x, y, WIDHT, HEIGHT, null);
            g2.setComposite(originalComposite);        }
    }

    public static SpeedEffect generateEffect () {
        return new SpeedEffect(new Random().nextInt(GameLoop.WIDTH));
    }
}
