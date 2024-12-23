package com.xojangstudios.eaglecrafty.ui;

import com.xojangstudios.eaglecrafty.core.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("EaglecraftY Java Edition");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("EaglecraftY Java Edition");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        JButton startButton = new JButton("Start Game");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(startButton, gbc);

        JButton optionsButton = new JButton("Options");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(optionsButton, gbc);

        JButton exitButton = new JButton("Exit");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(exitButton, gbc);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    Game game = new Game();
                    game.init();
                    game.run();
                }).start();
                System.out.println("Start Game button clicked");
            }
        });

        optionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SettingsUI settingsUI = new SettingsUI();
                settingsUI.setVisible(true);
                System.out.println("Options button clicked");
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainMenu().setVisible(true);
            }
        });
    }
}