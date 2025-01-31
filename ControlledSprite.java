// This example introduces a graphical sprite under keyboard control

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ControlledSprite extends JPanel implements KeyListener {
    private BufferedImage sprite; // Stores the sprite image
    private int x = 250-16, y = 150-32; // Initial sprite position
    private final int SPEED = 200; // Movement speed (pixels per second)
    private boolean up, down, left, right; // Movement flags
    private long lastTime; // Stores the last frame's timestamp

    public ControlledSprite() {
        // Attempt to load the sprite image
        try {
            sprite = ImageIO.read(new File("sprite.png"));
        } catch (Exception e) {
            System.err.println("Error: Image file not found!");
        }

        setFocusable(true); // Ensures the panel can receive keyboard input
        addKeyListener(this); // Adds key listener for movement
        lastTime = System.nanoTime(); // Initialize time tracking

        // Timer to update movement every ~16ms (~60 FPS)
        Timer timer = new Timer(16, e -> updatePosition());
        timer.start();
    }

    private void updatePosition() {
        long now = System.nanoTime();
        double deltaTime = (now - lastTime) / 1_000_000_000.0; // Convert nanoseconds to seconds
        lastTime = now;

        // Ensure sprite is loaded before checking its size
        if (sprite != null) {
            int spriteWidth = sprite.getWidth();
            int spriteHeight = sprite.getHeight();
            int panelWidth = getWidth();
            int panelHeight = getHeight();

            // Move sprite, ensuring it stays within bounds
            if (left) x = Math.max(0, x - (int) (SPEED * deltaTime));
            if (right) x = Math.min(panelWidth - spriteWidth, x + (int) (SPEED * deltaTime));
            if (up) y = Math.max(0, y - (int) (SPEED * deltaTime));
            if (down) y = Math.min(panelHeight - spriteHeight, y + (int) (SPEED * deltaTime));
        }

        repaint(); // Request repaint to update sprite position
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Clears the previous frame
        if (sprite != null) {
            g.drawImage(sprite, x, y, null); // Draw the sprite at its new position
        }
    }

    // Handles key press events
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> left = true;
            case KeyEvent.VK_RIGHT -> right = true;
            case KeyEvent.VK_UP -> up = true;
            case KeyEvent.VK_DOWN -> down = true;
        }
    }

    // Handles key release events
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> left = false;
            case KeyEvent.VK_RIGHT -> right = false;
            case KeyEvent.VK_UP -> up = false;
            case KeyEvent.VK_DOWN -> down = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {} // Not used, but required by KeyListener

    public static void main(String[] args) {
        // Create a frame to contain the panel
        JFrame frame = new JFrame("Controlled Sprite");
        ControlledSprite panel = new ControlledSprite();
        frame.add(panel);
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
