package game;
// Assets library https://kenney.nl/assets/space-shooter-redux
import javax.swing.*;

public class Main {

    public static void main (String[] args) {
        JFrame window = new JFrame("Space Invaders");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        GameLoop gl = new GameLoop();
        window.add(gl);
        window.pack(); //Adapt window to game panel

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gl.launchGame();

    }
}
