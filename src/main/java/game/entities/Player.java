package game.entities;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.GameManager;
import game.KeyHandler;
import game.effects.Thrusts;

public class Player {
    private boolean leftCollision, rightCollision;
    private int lives;
    public int x;
    public int y;
    public int newY;
    public final static int HEIGHT = 25;
    public final static int WIDHT = 25;
    private BufferedImage sprite;
    private Thrusts thrusts;

    public Player(int initialX, int initialY){
        this.x = initialX;
        this.y = initialY;
        this.lives = 3;
        this.leftCollision = false;
        this.rightCollision = false;
        try {
            sprite = ImageIO.read(getClass().getResource("/sprites/playerShip1_green.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        thrusts = new Thrusts("/effects/fire16.png", "/effects/fire17.png", x+2, x+Player.WIDHT/2 +3, y+Player.HEIGHT-4, WIDHT/3, HEIGHT/2);
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
        thrusts.update(x+2, x+Player.WIDHT/2 +3, y+Player.HEIGHT-4);
    }

    public void draw (Graphics2D g2) {
        if (sprite != null) {
            g2.drawImage(sprite, x, y, WIDHT, HEIGHT, null);
        } else {
            g2.setColor(Color.GREEN);
            g2.fillRect(x, y, WIDHT, HEIGHT);
        }
        thrusts.draw(g2);
    }
}
