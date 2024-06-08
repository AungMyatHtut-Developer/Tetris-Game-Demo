package com.amh.game;

import javax.swing.*;
import java.awt.*;

public class TrackWindow {
    private JFrame frame;
    private JPanel panel;
    private JLabel arrayBucket;

    private GamePanel gamePanel;

    public TrackWindow(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        frame = new JFrame("Array Tracker");
        frame.setSize(250, 400);

        // Get the parent JFrame of the GamePanel
        JFrame gameFrame = (JFrame) SwingUtilities.getWindowAncestor(this.gamePanel);
        if (gameFrame != null) {
            // Get the location and size of the GamePanel's parent frame
            Rectangle gameBounds = gameFrame.getBounds();

            // Set the location of the TrackWindow frame to the right of the GamePanel's frame
            int x = gameBounds.x + gameBounds.width;
            int y = gameBounds.y;
            frame.setLocation(x, y);
        } else {
            // Fallback to centering the frame if the GamePanel's parent frame is not available
            frame.setLocationRelativeTo(gamePanel);
        }


        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.DARK_GRAY);

        arrayBucket = new JLabel("<html>Tracked Array</html>");
        arrayBucket.setAlignmentX(Component.CENTER_ALIGNMENT);
        arrayBucket.setForeground(Color.WHITE);

        panel.add(arrayBucket);

        frame.add(panel);
        frame.setVisible(true);
    }

    public void updateTrackedArray(StringBuilder trackedArray) {
        String formattedArray = trackedArray.toString().replace("\n", "<br>");
        arrayBucket.setText("<html>" + formattedArray + "</html>");
    }
}
