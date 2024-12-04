package game;

import game.entities.Player;

import java.awt.*;

public class GameManager {
    private final int WIDTH = 700;
    private final int HEIGHT = 620;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;
    public static int dropInterval = 60; // 60 frames == 1sec

    public boolean gameOver;
    public Player player;

    public GameManager () {
        left_x = (GameLoop.WIDTH - WIDTH) / 2;
        right_x = WIDTH + (GameLoop.WIDTH - WIDTH) / 2;
        bottom_y = HEIGHT;
        top_y = (GameLoop.HEIGHT - HEIGHT) / 2;
        player = new Player();
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x-4, top_y-4, WIDTH+ 8, HEIGHT+8);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("SCORE",  30, (GameLoop.HEIGHT - HEIGHT) / 4);

        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("HIGH-SCORE", GameLoop.WIDTH - 120, (GameLoop.HEIGHT - HEIGHT) / 4);
        player.draw(g2);
    }

    public void update() {
        System.out.println("Updated!");
        player.update();
    }
}


