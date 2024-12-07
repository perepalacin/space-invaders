package game.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import game.effects.Impact;
import game.effects.Thrusts;

public class Protection {
    public final int x;
    public final int y;
    public final static int HEIGHT = 25;
    public final static int WIDHT = 25;
    public int lives;
    private static BufferedImage sprite = null;
    private final Thrusts thrusts;
    private final static ArrayList<BufferedImage> damageSprites = new ArrayList<>();
    private BufferedImage damageSpirte = null;
    static {
        try {
            sprite = ImageIO.read(Protection.class.getResource("/sprites/playerShip2_green.png"));
            damageSprites.add(ImageIO.read(Protection.class.getResource("/effects/playerShip2_damage1.png")));
            damageSprites.add(ImageIO.read(Protection.class.getResource("/effects/playerShip2_damage2.png")));
            damageSprites.add(ImageIO.read(Protection.class.getResource("/effects/playerShip2_damage3.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Protection(int x, int y){
        this.x = x;
        this.y = y;
        this.lives = 4;
        thrusts = new Thrusts("/effects/fire16.png", "/effects/fire17.png", x+Player.WIDHT/3, 0, y+Player.HEIGHT-1, WIDHT/3, HEIGHT/2);
    }

    public void update() {

        thrusts.update();
    }

    public void draw (Graphics2D g2) {
        if (sprite != null) {
            g2.drawImage(sprite, x, y, WIDHT, HEIGHT, null);
        } else {
            g2.setColor(Color.GREEN);
            g2.fillRect(x, y, WIDHT, HEIGHT);
        }
        if (lives < 4) {
            damageSpirte = damageSprites.get(3 - lives);
            if (damageSpirte != null) {
                g2.drawImage(damageSpirte, x, y, WIDHT, HEIGHT, null);
            }
        }
        thrusts.draw(g2);
    }

}
