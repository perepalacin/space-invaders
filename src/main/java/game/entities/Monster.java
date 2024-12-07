package game.entities;

import game.effects.Thrusts;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Monster {
    public final int SCORE;
    public int x;
    public int y;
    public static int HEIGHT = 25;
    public static int WIDHT = 25;
    private BufferedImage sprite;
    private final Thrusts thrusts;
    private final int thrustsNumber;
    private static final ArrayList<BufferedImage> sprites = new ArrayList<>();
    static {
        try {
            sprites.add(ImageIO.read(Monster.class.getResource("/sprites/enemyBlue2.png")));
            sprites.add(ImageIO.read(Monster.class.getResource("/sprites/enemyBlue3.png")));
            sprites.add(ImageIO.read(Monster.class.getResource("/sprites/enemyBlue5.png")));
        } catch (IOException e) {
            System.out.println("Failed to load the sprite image");
            e.printStackTrace();
        }
    }

    public Monster(int spirteNumber, int initialX, int initialY, int thrustsNumber, int SCORE) {
        x = initialX;
        y = initialY;
        this.SCORE = SCORE;
        this.thrustsNumber = thrustsNumber;
        sprite = sprites.get(spirteNumber);
        thrusts = switch (thrustsNumber) {
            case 1 ->
                    new Thrusts("/effects/fire04.png", "/effects/fire05.png", x + Player.WIDHT / 3, 0, y - 4, WIDHT / 3, HEIGHT / 2);
            case 2 ->
                    new Thrusts("/effects/fire04.png", "/effects/fire05.png", x + 2, x + Player.WIDHT / 2 + 3, y - 4, WIDHT / 3, HEIGHT / 2);
            default ->
                    new Thrusts("/effects/fire04.png", "/effects/fire05.png", x + 2, x + Player.WIDHT / 2 + 3, y - 4, WIDHT / 3, HEIGHT / 2);
        };
    }

    public void update(int direction, boolean moveDown) {
        x += 2*direction;
        if (moveDown) {
            y += 1;
        }
        if (thrustsNumber == 1) {
            thrusts.update(x + Player.WIDHT / 3, 0, y-10);
        } else {
            thrusts.update(x+2, x+Player.WIDHT/2 +3, y-10);
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
        thrusts.draw(g2);
    }

}
