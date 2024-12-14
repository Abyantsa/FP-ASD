package TicTacToe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PageAwal extends JPanel {
    private static final long serialVersionUID = 1L;

    public PageAwal(JFrame frame) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton playButton = createStyledButton("Play Game");
        JButton playVsCpuButton = createStyledButton("Play vs CPU");
        JButton settingsButton = createStyledButton("Settings");
        JButton quitButton = createStyledButton("Quit Game");

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(playButton, gbc);

        gbc.gridy = 1;
        add(playVsCpuButton, gbc);

        gbc.gridy = 2;
        add(settingsButton, gbc);

        gbc.gridy = 3;
        add(quitButton, gbc);

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(new TicTacToe(false)); // Human vs Human
                frame.revalidate();
            }
        });

        playVsCpuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(new TicTacToe(true)); // Human vs CPU
                frame.revalidate();
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSettingsDialog(frame);
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 50));
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        return button;
    }

    private void showSettingsDialog(JFrame frame) {
        JDialog settingsDialog = new JDialog(frame, "Settings", true);
        settingsDialog.setLayout(new BorderLayout());
        settingsDialog.setSize(300, 200);
        settingsDialog.setLocationRelativeTo(frame);

        JPanel audioPanel = new JPanel();
        audioPanel.add(new JLabel("Audio Settings:"));
        JCheckBox audioCheckBox = new JCheckBox("Enable Audio");
        audioPanel.add(audioCheckBox);

        settingsDialog.add(audioPanel, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsDialog.dispose();
            }
        });

        settingsDialog.add(closeButton, BorderLayout.SOUTH);
        settingsDialog.setVisible(true);
    }
}