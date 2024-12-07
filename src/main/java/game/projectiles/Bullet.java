package game.projectiles;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Bullet {
    public final int X;
    public int y;
    private boolean isFriendly;
    public final int WIDTH = 3, HEIGTH = 15;
    private static BufferedImage friendlySprite;
    private static BufferedImage enemySprite;
    private BufferedImage sprite;
    static {
        try {
            friendlySprite = ImageIO.read(Bullet.class.getResource("/effects/laserGreen10.png"));
            enemySprite = ImageIO.read(Bullet.class.getResource("/effects/laserRed16.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bullet(int X, int y, boolean isFriendly) {
        if (isFriendly) {
            sprite = friendlySprite;
        } else {
            sprite = enemySprite;
        }
        this.isFriendly = isFriendly;
        this.X = X;
        this.y = y;
    }

    public void update() {
        if (isFriendly) {
            y -= 10;
        } else {
            y += 10;
        }
    }

    public void draw (Graphics2D g2) {
        if (sprite != null) {
            if (!isFriendly) {
                int centerX = X + WIDTH / 2;
                int centerY = y + HEIGTH / 2;
                AffineTransform original = g2.getTransform();
                g2.rotate(Math.toRadians(180), centerX, centerY);
                g2.drawImage(sprite, X, y, WIDTH, HEIGTH, null);
                g2.setTransform(original);
            } else {
                g2.drawImage(sprite, X, y, WIDTH, HEIGTH, null);
            }
        } else {
            g2.setColor(Color.GREEN);
            g2.fillRect(X, y, WIDTH, HEIGTH);
        }
    }
}
