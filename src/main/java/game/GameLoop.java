package game;


import game.entities.Player;
import game.projectiles.Bullet;

import java.awt.*;

import javax.swing.JPanel;

public class GameLoop extends  JPanel implements Runnable{
    public static final int WIDTH = 720;
    public static final int HEIGHT = 720;
    final int FPS = 60;
    public int highScore = 0;
    Thread gameThread;
    GameManager gm;

    public GameLoop() {
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setBackground(Color.black);
        this.setLayout(null);
        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);
        gm = new GameManager(highScore);
    }

    public void launchGame () {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        //Game loop
        final double drawInterval = 1000000000/FPS; // Nanoseconds per frame
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    private void update() {
        if (!gm.gameOver && !gm.gameWon) {
            gm.update();
        } else {
            if (KeyHandler.spacePressed) {
                highScore = gm.maxScore;
                gm = new GameManager(highScore);
                KeyHandler.spacePressed = false;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        gm.draw(g2);
    }
}


