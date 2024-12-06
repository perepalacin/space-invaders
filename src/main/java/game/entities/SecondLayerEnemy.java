package game.entities;

import java.awt.image.BufferedImage;

public class SecondLayerEnemy extends Monster {
    private int hitPoints;
    private final int SCORE = 200;
    public int x;
    public int y;
    private BufferedImage sprite;

    public SecondLayerEnemy (int initialX, int initialY) {
        super("/sprites/enemyBlue3.png", initialX, initialY, 1);
    }
}
