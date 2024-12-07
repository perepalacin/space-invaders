package game.entities;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import game.GameManager;
import game.KeyHandler;
import game.effects.Thrusts;

public class Player {
    private boolean leftCollision = false, rightCollision = false;
    public int lives = 3;
    public int x;
    public static int Y;
    public int newY;
    public final static int HEIGHT = 25;
    public final static int WIDHT = 25;
    private static BufferedImage sprite = null;
    private final static ArrayList<BufferedImage> damageSprites = new ArrayList<>();
    private BufferedImage damageSpirte = null;
    static {
        try {
            sprite = ImageIO.read(Player.class.getResource("/sprites/playerShip1_green.png"));
            damageSprites.add(ImageIO.read(Protection.class.getResource("/effects/playerShip1_damage2.png")));
            damageSprites.add(ImageIO.read(Protection.class.getResource("/effects/playerShip1_damage3.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private final Thrusts thrusts;

    public Player(int initialX, int initialY){
        this.x = initialX;
        this.Y = initialY;
        thrusts = new Thrusts("/effects/fire16.png", "/effects/fire17.png", x+2, x+Player.WIDHT/2 +3, Y+Player.HEIGHT-4, WIDHT/3, HEIGHT/2);
    }

    public void checkCollisions() {
        leftCollision = false;
        rightCollision = false;
        if (x <= GameManager.left_x) {
            leftCollision = true;
        }
        if (x + WIDHT >= GameManager.right_x) {
            rightCollision = true;
        }
    }

    public void update() {
        checkCollisions();
        if (KeyHandler.rightPressed && !rightCollision) {
            x += 3;
        }
        if (KeyHandler.leftPressed && !leftCollision) {
            x -= 3;
        }
        thrusts.update(x+2, x+Player.WIDHT/2 +3, Y+Player.HEIGHT-4);
    }

    public void draw (Graphics2D g2) {
        if (sprite != null) {
            g2.drawImage(sprite, x, Y, WIDHT, HEIGHT, null);
        } else {
            g2.setColor(Color.GREEN);
            g2.fillRect(x, Y, WIDHT, HEIGHT);
        }
        if (lives < 3) {
            damageSpirte = damageSprites.get(2 - lives);
            if (damageSpirte != null) {
                g2.drawImage(damageSpirte, x, Y, WIDHT, HEIGHT, null);
            }
        }
        thrusts.draw(g2);
    }
}
