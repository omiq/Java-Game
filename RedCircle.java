import javax.swing.*;
import java.awt.*;

public class RedCircle extends JPanel {
    public RedCircle() {
        setFocusable(true); // Allows the panel to receive keyboard input
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillOval(200, 100, 50, 50); // Draws a simple red circle as a placeholder sprite
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Simple Game");
        RedCircle panel = new RedCircle();
        frame.add(panel);
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}