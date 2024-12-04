package game.entities;
import game.GameLoop;
import game.KeyHandler;
import game.GameManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player {
    private boolean leftCollision, rightCollision;
    private int lives;
    public int x;
    public int y;
    public int newY;
    private final int HEIGHT = 25;
    private final int WIDHT = 25;
    private BufferedImage sprite;

    public Player(){
        this.x = GameLoop.WIDTH / 2;
        this.y = GameManager.bottom_y + HEIGHT;
        this.lives = 3;
        this.leftCollision = false;
        this.rightCollision = false;
        try {
            sprite = ImageIO.read(getClass().getResource("/sprites/playerShip1_green.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            x += 10;
            KeyHandler.rightPressed = false;
        }
        if (KeyHandler.leftPressed && !leftCollision) {
            x -= 10;
            KeyHandler.leftPressed = false;
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
