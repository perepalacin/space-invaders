package game.entities;

import java.awt.image.BufferedImage;

public class FirstLayerEnemy extends Monster{
    private int hitPoints;
    private final int SCORE = 100;
    public int x;
    public int y;
    private BufferedImage sprite;

    public FirstLayerEnemy (int initialX, int initialY) {
        super("/sprites/enemyBlue5.png", initialX, initialY);
    }
}
