package game;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GameLoop extends  JPanel implements Runnable{
    public static final int WIDTH = 720;
    public static final int HEIGHT = 720;
    final int FPS = 60;
    Thread gameThread;
    GameManager gm;

    public GameLoop() {
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setBackground(Color.black);
        this.setLayout(null);
        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);

        gm = new GameManager();
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
        if (!KeyHandler.pausePressed && !gm.gameOver) {
            gm.update();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        gm.draw(g2);
    }
}


