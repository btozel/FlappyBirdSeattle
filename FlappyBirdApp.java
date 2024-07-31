import javax.swing.*;

public class FlappyBirdApp {
    public static void main(String[] args) {
        int boardWidth = 360;
        int boardHeight = 640;

        // Create frame with the title Flappy Bird
        JFrame frame = new JFrame("Flappy Bird");

        // Set the size of the window
        frame.setSize(boardWidth, boardHeight);
        // Place the window at the center of screen
        frame.setLocationRelativeTo(null);
        // Make the window not resizable
        frame.setResizable(false);
        // Set closing operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FlappyBird flappyBird = new FlappyBird(boardWidth,boardHeight);
        frame.add(flappyBird);
        // It fixes the dimension problems, dimensions will start after title bar
        frame.pack();
        flappyBird.requestFocus();
        // Set frame visible
        frame.setVisible(true);
    }
}
