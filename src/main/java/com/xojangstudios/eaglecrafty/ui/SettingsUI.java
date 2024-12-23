package com.xojangstudios.eaglecrafty.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class SettingsUI extends JFrame {
    private JCheckBox adultContentToggle;

    public SettingsUI() {
        setTitle("Settings");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        adultContentToggle = new JCheckBox("Enable Adult Content");
        adultContentToggle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (adultContentToggle.isSelected()) {
                    System.out.println("Adult content enabled");
                } else {
                    System.out.println("Adult content disabled");
                }
            }
        });

        panel.add(adultContentToggle);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SettingsUI().setVisible(true);
            }
        });
    }
}