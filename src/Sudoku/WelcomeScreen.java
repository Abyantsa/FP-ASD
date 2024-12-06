package Sudoku;

import java.awt.*;
import javax.swing.*;
import javax.sound.sampled.*;
import java.io.File;

public class WelcomeScreen extends JFrame {
    private static final long serialVersionUID = 1L;
    private Color primaryColor = new Color(41, 128, 185);
    private Color secondaryColor = new Color(52, 152, 219);
    private Color backgroundColor = new Color(236, 240, 241);
    private Color textColor = new Color(44, 62, 80);
    private Clip backgroundClip;
    private JSlider volumeSlider;
    private JTextField playerNameField;

    public WelcomeScreen() {
        setTitle("Sudoku Game");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Play background music
        playBackgroundMusic("src\\Sudoku\\backsound.wav");

        // Set background color
        getContentPane().setBackground(backgroundColor);
        setLayout(new BorderLayout(20, 20));

        // Panel for title with gradient background
        JPanel titlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, primaryColor, 0, getHeight(), secondaryColor);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        titlePanel.setPreferredSize(new Dimension(500, 150));


        JLabel lblWelcome = new JLabel("SUDOKU", JLabel.CENTER);
        lblWelcome.setFont(new Font("Montserrat", Font.BOLD, 48));
        lblWelcome.setForeground(Color.WHITE);
        titlePanel.add(lblWelcome);

        // Main menu panel
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(backgroundColor);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Player name input
        JLabel nameLabel = new JLabel("Enter Your Name:");
        nameLabel.setFont(new Font("Montserrat", Font.BOLD, 16));
        nameLabel.setForeground(textColor);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        playerNameField = new JTextField();
        playerNameField.setFont(new Font("Montserrat", Font.PLAIN, 16));
        playerNameField.setMaximumSize(new Dimension(300, 40));
        playerNameField.setAlignmentX(Component.CENTER_ALIGNMENT);


        menuPanel.add(nameLabel);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(playerNameField);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));


        JButton btnPlayGame = createStyledButton("Play Game", new ImageIcon("path/to/play-icon.png"));
        JButton btnOptions = createStyledButton("Options", new ImageIcon("path/to/settings-icon.png"));
        JButton btnExit = createStyledButton("Exit", new ImageIcon("path/to/exit-icon.png"));

        // Add spacing between buttons
        addButtonToPanel(menuPanel, btnPlayGame);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        addButtonToPanel(menuPanel, btnOptions);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        addButtonToPanel(menuPanel, btnExit);

        // Action listeners
        btnPlayGame.addActionListener(e -> showDifficultySelection());
        btnOptions.addActionListener(e -> showOptions());
        btnExit.addActionListener(e -> System.exit(0));

        add(titlePanel, BorderLayout.NORTH);
        add(menuPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void playBackgroundMusic(String filePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(audioInputStream);

            // Use VolumeManager to manage volume
            VolumeManager.getInstance().setCurrentClip(backgroundClip);

            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
            backgroundClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showOptions() {
        JDialog optionsDialog = new JDialog(this, "Options", true);
        optionsDialog.setLayout(new BorderLayout(10, 10));
        optionsDialog.setSize(400, 200);
        optionsDialog.getContentPane().setBackground(backgroundColor);

        JPanel volumePanel = new JPanel(new BorderLayout(10, 10));
        volumePanel.setBackground(backgroundColor);
        volumePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel volumeLabel = new JLabel("Volume Control");
        volumeLabel.setFont(new Font("Montserrat", Font.BOLD, 16));
        volumeLabel.setForeground(textColor);

        volumeSlider = new JSlider(0, 100, (int)(VolumeManager.getInstance().getVolume() * 100));
        volumeSlider.setBackground(backgroundColor);
        volumeSlider.setForeground(primaryColor);

        volumeSlider.addChangeListener(e -> {
            float newVolume = volumeSlider.getValue() / 100.0f;
            VolumeManager.getInstance().setVolume(newVolume);
        });

        JButton btnClose = createStyledButton("Save", null);
        btnClose.addActionListener(e -> optionsDialog.dispose());

        volumePanel.add(volumeLabel, BorderLayout.NORTH);
        volumePanel.add(volumeSlider, BorderLayout.CENTER);
        volumePanel.add(btnClose, BorderLayout.SOUTH);

        optionsDialog.add(volumePanel);
        optionsDialog.setLocationRelativeTo(this);
        optionsDialog.setVisible(true);
    }

    private JButton createStyledButton(String text, ImageIcon icon) {
        JButton button = new JButton(text);
        button.setFont(new Font("Montserrat", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(primaryColor);
        button.setPreferredSize(new Dimension(300, 60));
        button.setMaximumSize(new Dimension(300, 60));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(secondaryColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(primaryColor);
            }
        });

        return button;
    }

    private void addButtonToPanel(JPanel panel, JButton button) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setMaximumSize(new Dimension(400, 70));
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.add(button);
        panel.add(buttonPanel);
    }

    private void showDifficultySelection() {
        JDialog difficultyDialog = new JDialog(this, "Select Difficulty", true);
        difficultyDialog.setLayout(new BorderLayout());
        difficultyDialog.setSize(400, 300);
        difficultyDialog.getContentPane().setBackground(backgroundColor);

        JPanel difficultyPanel = new JPanel();
        difficultyPanel.setLayout(new BoxLayout(difficultyPanel, BoxLayout.Y_AXIS));
        difficultyPanel.setBackground(backgroundColor);
        difficultyPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnEasy = createStyledButton("Easy", null);
        JButton btnMedium = createStyledButton("Medium", null);
        JButton btnHard = createStyledButton("Hard", null);

        addButtonToPanel(difficultyPanel, btnEasy);
        difficultyPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        addButtonToPanel(difficultyPanel, btnMedium);
        difficultyPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        addButtonToPanel(difficultyPanel, btnHard);

        btnEasy.addActionListener(e -> {
            startGame(SudokuConstants.EASY);
            difficultyDialog.dispose();
        });
        btnMedium.addActionListener(e -> {
            startGame(SudokuConstants.MEDIUM);
            difficultyDialog.dispose();
        });
        btnHard.addActionListener(e -> {
            startGame(SudokuConstants.HARD);
            difficultyDialog.dispose();
        });

        difficultyDialog.add(difficultyPanel, BorderLayout.CENTER);
        difficultyDialog.setLocationRelativeTo(this);
        difficultyDialog.setVisible(true);
    }

    private void startGame(int difficulty) {
        String playerName = playerNameField.getText().trim();
        if (playerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your name before starting the game.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        new Sudoku(difficulty, playerName);
        dispose();
    }

    @Override
    public void dispose() {
        if (backgroundClip != null && backgroundClip.isRunning()) {
            backgroundClip.stop();
            backgroundClip.close();
        }
        super.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WelcomeScreen());
    }
}