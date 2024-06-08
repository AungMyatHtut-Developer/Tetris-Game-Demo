package com.amh.game;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow {

    private JFrame jFrame;

    public GameWindow(GamePanel gamePanel) {

        jFrame = new JFrame();
        jFrame.add(gamePanel);

        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setTitle("TETRIS");
        jFrame.setVisible(true);
        jFrame.addWindowFocusListener(new WindowFocusListener() {
                                          @Override
                                          public void windowGainedFocus(WindowEvent e) {
                                              gamePanel.requestFocus(true);
                                          }

                                          @Override
                                          public void windowLostFocus(WindowEvent e) {

                                          }
                                      }
        );


    }
}
