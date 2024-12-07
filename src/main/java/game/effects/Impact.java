package game.effects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Impact {
    private static final ArrayList<BufferedImage> friendlySprites = new ArrayList<>();
    private static final ArrayList<BufferedImage> enemySprites = new ArrayList<>();
    private BufferedImage sprite;
    private final int X, Y;
    private final int WIDHT = 40, HEIGHT = 40;
    public int animationCounter = 0;
    public boolean smallExplosion;
    private boolean isFriendly;
    static {
        try {
            friendlySprites.add(ImageIO.read(Impact.class.getResource("/effects/laserGreen14.png")));
            friendlySprites.add(ImageIO.read(Impact.class.getResource("/effects/laserGreen15.png")));
            enemySprites.add(ImageIO.read(Impact.class.getResource("/effects/laserRed08.png")));
            enemySprites.add(ImageIO.read(Impact.class.getResource("/effects/laserRed09.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Impact(int x, int y, boolean smallExplosion, boolean isFriendly) {
        X = x;
        Y = y;
        this.isFriendly = isFriendly;
        this.smallExplosion = smallExplosion;
        if (isFriendly) {
            sprite = friendlySprites.get(0);
        } else {
            sprite = enemySprites.get(0);
        }
    }

    public void update() {
        if (animationCounter > 3) {
            if(isFriendly) {
                sprite = friendlySprites.get(1);
            } else {
                sprite = enemySprites.get(1);
            }
        }
        animationCounter++;

    }

    public void draw(Graphics2D g2) {
        if (sprite != null) {
            if (smallExplosion) {
                g2.drawImage(sprite, X - WIDHT/4, Y - HEIGHT/4, WIDHT / 2, HEIGHT / 2, null);
            } else {
                g2.drawImage(sprite, X -WIDHT /2, Y - HEIGHT / 2, WIDHT, HEIGHT, null);
            }
        } else {
            g2.setColor(Color.GREEN);
            g2.fillRect(X, Y, WIDHT, HEIGHT);
        }
    }

}
