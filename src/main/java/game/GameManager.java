package game;

import game.effects.Asteroid;
import game.effects.Impact;
import game.effects.SpeedEffect;
import game.entities.*;
import game.projectiles.Bullet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

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
    private Bullet playerBullet;
    private Bullet enemyBullet;
    private Impact friendlyImpact;
    private Impact enemyImpact;
    public final int PlayerRowHeight;
    public final int ProtectionsRowHeight;
    public int currentScore = 0;
    public int maxScore;

    public boolean gameOver;
    public boolean gameWon;
    public Player player;
    public ArrayList<Monster> monsters = new ArrayList<>();;
    public ArrayList<Protection> protections = new ArrayList<>();
    public ArrayList<Asteroid> asteroids = new ArrayList<>();
    public ArrayList<SpeedEffect> speedEffects = new ArrayList<>();
    private static Font font = null;
    static {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, GameManager.class.getResourceAsStream("/font/arcade.ttf")).deriveFont(24f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public GameManager (int highScore) {
        left_x = (GameLoop.WIDTH - WIDTH) / 2;
        right_x = WIDTH + (GameLoop.WIDTH - WIDTH) / 2;
        bottom_y = HEIGHT;
        ProtectionsRowHeight = bottom_y - Protection.HEIGHT;
        PlayerRowHeight = bottom_y + Player.HEIGHT;
        top_y = (GameLoop.HEIGHT - HEIGHT) / 2;
        maxScore = highScore;
        player = new Player(GameLoop.WIDTH / 2, bottom_y + Player.HEIGHT);
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

        for (int i = 0; i < 14; i++) {
            if ((i % 5) < 4) {
                protections.add(new Protection(left_x*2 + (WIDTH) / 14 * i, bottom_y - Protection.HEIGHT));
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage sprite = null;
        g2.setFont(Objects.requireNonNullElseGet(font, () -> new Font("Monospaced", Font.PLAIN, 24)));
        g2.setColor(Color.decode("#012a42"));
        g2.fillRect(0, 0, GameLoop.WIDTH, GameLoop.HEIGHT);

        g2.setColor(Color.WHITE);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("SCORE",  30, (GameLoop.HEIGHT - HEIGHT) / 4);
        g2.drawString(String.valueOf(currentScore), 30, (GameLoop.HEIGHT - HEIGHT) / 2);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("HIGH SCORE", GameLoop.WIDTH - 160, (GameLoop.HEIGHT - HEIGHT) / 4);
        g2.drawString(String.valueOf(maxScore), GameLoop.WIDTH - 160, (GameLoop.HEIGHT - HEIGHT) / 2);
        g2.drawString("Lives   x  " + String.valueOf(player.lives), 30, (GameLoop.HEIGHT - 20));
        asteroids.forEach(asteroid -> asteroid.draw(g2));
        player.draw(g2);
        monsters.forEach(monster -> monster.draw(g2));
        protections.forEach(protection -> protection.draw(g2));

        if (playerBullet != null) {
            playerBullet.draw(g2);
        }

        if (friendlyImpact != null) {
            friendlyImpact.draw(g2);
        }

        if (enemyBullet != null) {
            enemyBullet.draw(g2);
        }

        if (enemyImpact != null) {
            enemyImpact.draw(g2);
        }
        if (!speedEffects.isEmpty()) {
            speedEffects.forEach(speedEffect -> speedEffect.draw(g2));
        }
        if (gameOver) {
            int x = GameLoop.WIDTH/2 -50;
            int y = top_y + 320;
            g2.setColor(Color.BLACK);
            g2.fillRect(x - 100, y - 35, 310, 60);
            g2.setColor(Color.WHITE);
            g2.drawString("GAME   OVER!", x, y - 10);
            g2.drawString("PRESS   SPACE   TO  RESTART", x - 80, y + 15);
        } else if (gameWon) {
            int x = GameLoop.WIDTH/2 -50;
            int y = top_y + 320;
            g2.setColor(Color.BLACK);
            g2.fillRect(x - 100, y - 35, 310, 60);
            g2.setColor(Color.WHITE);
            g2.drawString("YOU   WON!", x, y - 10);
            g2.drawString("PRESS   SPACE   TO  REPLAY", x - 80, y + 15);
        }
    }

    public void updateMonsters() {
        int minX = right_x, maxX = left_x;
        for (Monster monster : monsters) {
            if (monster.x >= maxX) {
                maxX = monster.x;
            }
            if (monster.x <= minX) {
                minX = monster.x;
            }
            if (monster.y+ Monster.HEIGHT >= ProtectionsRowHeight) {
                gameOver=true;
            }
        }
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

    public void checkPlayerBulletCollision() {
        boolean impacted = false;
        if (enemyBullet != null) {
            if (enemyBullet.X <= playerBullet.X && playerBullet.X <= enemyBullet.X + enemyBullet.WIDTH && enemyBullet.y <= playerBullet.y && playerBullet.y <= enemyBullet.y + enemyBullet.HEIGTH) {
                friendlyImpact = new Impact(playerBullet.X, playerBullet.y, true, true);
                impacted = true;
                playerBullet = null;
                enemyBullet = null;
            }
        }
        if (!impacted) {
            if (!protections.isEmpty() && playerBullet.y >= ProtectionsRowHeight && playerBullet.y <= ProtectionsRowHeight + Protection.HEIGHT) {
                for (int i = protections.size()-1; i >= 0; i--) {
                    if ((playerBullet.y >= protections.get(i).y) && (playerBullet.y <= protections.get(i).y + Protection.HEIGHT) && (playerBullet.X >= protections.get(i).x) && (playerBullet.X <= protections.get(i).x + Protection.WIDHT)) {
                        if (protections.get(i).lives == 1) {
                            friendlyImpact = new Impact(protections.get(i).x + Protection.WIDHT/2, protections.get(i).y + Protection.HEIGHT/ 2, false, true);
                            protections.remove(i);
                        } else {
                            friendlyImpact = new Impact(playerBullet.X, playerBullet.y, true, true);
                            protections.get(i).lives --;
                        }
                        playerBullet = null;
                        impacted = true;
                        break;
                    }
                }
            }
        }
        if (!impacted) {
            for (int i = monsters.size() -1; i>=0; i--) {
                if ((playerBullet.y >= monsters.get(i).y) && (playerBullet.y <= monsters.get(i).y + Monster.HEIGHT) && (playerBullet.X >= monsters.get(i).x) && (playerBullet.X <= monsters.get(i).x + Monster.WIDHT)) {
                    friendlyImpact = new Impact(playerBullet.X, playerBullet.y, false, true);
                    currentScore += monsters.get(i).SCORE;
                    if (currentScore >= maxScore) {
                        maxScore = currentScore;
                    }
                    monsters.remove(i);
                    playerBullet = null;
                    impacted = true;
                    break;
                }
            }
            if (monsters.size() == 0) {
                gameWon = true;
            }
        }
        if (!impacted) {
            if (playerBullet.y < -10) {
                playerBullet = null;
            }
        }
    }

    public void checkEnemyBulletCollision() {
        boolean impacted = false;
        if (!protections.isEmpty() && enemyBullet.y >= ProtectionsRowHeight && enemyBullet.y <= ProtectionsRowHeight + Protection.HEIGHT) {
            for (int i = protections.size()-1; i >= 0; i--) {
                if ((enemyBullet.y >= protections.get(i).y) && (enemyBullet.y <= protections.get(i).y + Protection.HEIGHT) && (enemyBullet.X >= protections.get(i).x) && (enemyBullet.X <= protections.get(i).x + Protection.WIDHT)) {
                    if (protections.get(i).lives == 1) {
                        enemyImpact = new Impact(protections.get(i).x + Protection.WIDHT/2, protections.get(i).y + Protection.HEIGHT/ 2, false, false);
                        protections.remove(i);
                    } else {
                        enemyImpact = new Impact(enemyBullet.X, enemyBullet.y, true, false);
                        protections.get(i).lives --;
                    }
                    enemyBullet = null;
                    impacted = true;
                    break;
                }
            }
        }
        if (!impacted) {
            if ((enemyBullet.y >= Player.Y) && (enemyBullet.y <= Player.Y + Player.HEIGHT) && (enemyBullet.X >= player.x) && (enemyBullet.X <= player.x + Player.WIDHT)) {
                enemyImpact = new Impact(enemyBullet.X, enemyBullet.y, false, false);
                enemyBullet = null;
                impacted = true;
                if (player.lives == 1) {
                    gameOver = true;
                } else {
                    player.lives--;
                }
            }
        }
        if (!impacted) {
            if (enemyBullet.y > GameLoop.HEIGHT) {
                enemyBullet = null;
            }
        }
    }

    public void generateAsteroids () {
        Asteroid newAsteroid = Asteroid.generateAsteroid();
        if (newAsteroid != null) {
            for (int i = asteroids.size() - 1; i >= 0; i--) {
                if (asteroids.get(i).x > right_x || asteroids.get(i).y > bottom_y) {
                    asteroids.remove(i);
                }
            }
            asteroids.add(newAsteroid);
        }
        asteroids.forEach(Asteroid::update);
    }

    public void generateSpeedEffects () {
        SpeedEffect newEffect = SpeedEffect.generateEffect();
        if (newEffect != null) {
            for (int i = speedEffects.size() - 1; i >= 0; i--) {
                if (speedEffects.get(i).y > bottom_y) {
                    speedEffects.remove(i);
                }
            }
            speedEffects.add(newEffect);
        }
        speedEffects.forEach(SpeedEffect::update);
    }


    public void updateImpacts () {
        if (friendlyImpact != null) {
            if (friendlyImpact.animationCounter == 7) {
                friendlyImpact = null;
            } else {
                friendlyImpact.update();
            }
        }
        if (enemyImpact != null) {
            if (enemyImpact.animationCounter == 7) {
                enemyImpact = null;
            } else {
                enemyImpact.update();
            }
        }
    }

    public void updateBullets () {
        if (playerBullet == null) {
            if (KeyHandler.spacePressed) {
                playerBullet = new Bullet(player.x + (Player.WIDHT / 2) -1, Player.Y, true);
                KeyHandler.spacePressed = false;
            }
        } else {
            playerBullet.update();
            checkPlayerBulletCollision();
        }

        if (enemyBullet == null) {
            int monster = new Random().nextInt(monsters.size());
            enemyBullet = new Bullet(monsters.get(monster).x + (Monster.WIDHT / 2) -1, monsters.get(monster).y + Monster.HEIGHT,false);
        } else {
            enemyBullet.update();
            checkEnemyBulletCollision();
        }
    }

    public void update() {
        generateSpeedEffects();
        generateAsteroids();
        player.update();
        updateBullets();
        updateImpacts();
        updateMonsters();
    }
}


