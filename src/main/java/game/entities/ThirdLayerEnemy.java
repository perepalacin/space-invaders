package game.entities;

import java.awt.image.BufferedImage;

public class ThirdLayerEnemy extends Monster {
    private int hitPoints;
    private final int SCORE = 300;
    public int x;
    public int y;
    private BufferedImage sprite;

    public ThirdLayerEnemy (int initialX, int initialY) {
        super("/sprites/enemyBlue2.png", initialX, initialY, 1);
    }
}
