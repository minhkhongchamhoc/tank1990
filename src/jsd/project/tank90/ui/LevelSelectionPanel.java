package jsd.project.tank90.ui;

import jsd.project.tank90.model.tanks.PlayerTank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LevelSelectionPanel extends JPanel {
    private JLabel[] levelLabels;
    private JLabel arrowLabel;
    private int currentIndex = 0;
    private final String[] mapFiles = {
            "src/jsd/project/tank90/resources/map_stage/map_1.txt",
            "src/jsd/project/tank90/resources/map_stage/map_2.txt",
            "src/jsd/project/tank90/resources/map_stage/map_ice.txt"
    };

    public LevelSelectionPanel() {
        setBackground(Color.BLACK);
        setLayout(null);
        setFocusable(true);
        requestFocusInWindow();

        arrowLabel = new JLabel("→");
        arrowLabel.setForeground(Color.WHITE);
        arrowLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        arrowLabel.setBounds(50, 80, 20, 20);
        add(arrowLabel);

        levelLabels = new JLabel[3];
        String[] levelNames = {"Level 1", "Level 2", "Level 3"};
        for (int i = 0; i < 3; i++) {
            levelLabels[i] = new JLabel(levelNames[i]);
            levelLabels[i].setForeground(Color.LIGHT_GRAY);
            levelLabels[i].setFont(new Font("Monospaced", Font.BOLD, 16));
            levelLabels[i].setBounds(80, 80 + i * 30, 100, 20);
            add(levelLabels[i]);
        }

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    moveSelectionUp();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    moveSelectionDown();
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    openGamePlay(mapFiles[currentIndex]);
                }
            }
        });
    }

    private void moveSelectionUp() {
        if (currentIndex > 0) {
            currentIndex--;
            updateArrowPosition();
        }
    }

    private void moveSelectionDown() {
        if (currentIndex < levelLabels.length - 1) {
            currentIndex++;
            updateArrowPosition();
        }
    }

    private void updateArrowPosition() {
        arrowLabel.setLocation(50, 80 + currentIndex * 30);
    }

    private void openGamePlay(String mapFile) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.getContentPane().removeAll();
        GamePanel gamePanel = new GamePanel(mapFile);  // Pass the map file path to GamePanel
        PlayerTank playerTank = gamePanel.getPlayerTank();
        StatusPanel statusPanel = new StatusPanel(playerTank);// Pass the map file path to GamePanel
        frame.add(gamePanel, BorderLayout.CENTER);
        frame.add(statusPanel, BorderLayout.EAST);
        Timer timer = new Timer(100, e -> statusPanel.repaint());
        timer.start();
        gamePanel.requestFocusInWindow();
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
    }
}