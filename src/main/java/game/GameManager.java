package game;

import game.entities.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class GameManager {
    private final int WIDTH = 700;
    private final int HEIGHT = 620;
    public static int left_x; //Left limit of the playable area
    public static int right_x; // Right limit of the playable area
    public static int top_y;
    public static int bottom_y;
    public int monstersDirection = 1; // 1 = right, -1 = left
    private int monstersMoveDownCounter = 0;
    private boolean monstersMoveDown = false;


    public boolean gameOver;
    public Player player;
    public ArrayList<Monster> monsters;
    public ArrayList<Protection> protections;

    public GameManager () {
        left_x = (GameLoop.WIDTH - WIDTH) / 2;
        right_x = WIDTH + (GameLoop.WIDTH - WIDTH) / 2;
        bottom_y = HEIGHT;
        top_y = (GameLoop.HEIGHT - HEIGHT) / 2;
        player = new Player(GameLoop.WIDTH / 2, bottom_y + Player.HEIGHT);
        monsters = new ArrayList<>();
        int monsterXPos;
        int row = 0;
        for (int i = 0; i < 3; i++) {
            monsterXPos = Monster.WIDHT;
            for (int j = 0; j < 20; j++) {
                if (j == 10) {
                    row = row + 1;
                    monsterXPos = Monster.WIDHT;
                }
                switch (i) {
                    case 0: monsters.add(new FirstLayerEnemy(monsterXPos, top_y + row * Monster.HEIGHT + row*15)); break;
                    case 1: monsters.add(new SecondLayerEnemy(monsterXPos, top_y + row * Monster.HEIGHT + row*15)); break;
                    case 2: monsters.add(new ThirdLayerEnemy(monsterXPos, top_y + row * Monster.HEIGHT + row*15)); break;
                }
                monsterXPos += Monster.WIDHT * 2;
            }
            row++;
        }

        protections = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            if ((i % 5) < 4) {
                protections.add(new Protection(left_x*2 + (WIDTH) / 14 * i, bottom_y - Protection.HEIGHT));
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage sprite;
        try {
            sprite = ImageIO.read(getClass().getResource("/background/purple.png"));
        } catch (IOException e) {
            sprite = null;
            e.printStackTrace();
        }
        if (sprite != null) {
            g2.drawImage(sprite, 0, 0, GameLoop.WIDTH, GameLoop.HEIGHT, null);
        } else {
            // Fallback to drawing a rectangle if the sprite fails to load
            g2.setColor(Color.BLUE);
            g2.fillRect(0, 0, GameLoop.WIDTH, GameLoop.HEIGHT);
        }
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("SCORE",  30, (GameLoop.HEIGHT - HEIGHT) / 4);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("HIGH-SCORE", GameLoop.WIDTH - 120, (GameLoop.HEIGHT - HEIGHT) / 4);
        player.draw(g2);
        monsters.forEach(monster -> monster.draw(g2));
        protections.forEach(protection -> protection.draw(g2));
    }

    public void updateMonster() {
        int minX = right_x, maxX = left_x;
        if (monstersDirection == 1) {
            maxX = monsters.stream().mapToInt(monster -> monster.x).max().orElse(right_x);
        } else {
            minX = monsters.stream().mapToInt(monsters -> monsters.x).min().orElse(left_x);
        }
        if (minX < left_x || maxX + Monster.WIDHT > right_x) {
            monstersDirection *= -1;
            monstersMoveDown = true;
        } else {
            if (monstersMoveDown) {
                monstersMoveDownCounter++;
                if (monstersMoveDownCounter > Monster.HEIGHT / 2) {
                    monstersMoveDownCounter = 0;
                    monstersMoveDown = false;
                }
            }
        }
        monsters.forEach(monster -> monster.update(monstersDirection, monstersMoveDown));
    }

    public void update() {
        player.update();
        updateMonster();
    }


}


