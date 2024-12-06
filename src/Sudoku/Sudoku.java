package Sudoku;

import java.awt.*;
import javax.swing.*;

public class Sudoku extends JFrame {
    private static final long serialVersionUID = 1L;
    private float volume = 1.0f;
    private GameBoardPanel board = new GameBoardPanel();
    private JButton btnNewGame = new JButton("New Game");
    private int currentDifficulty;

    public Sudoku(int difficulty) {
        this.currentDifficulty = difficulty;
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        // Create Menu Bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Game Menu
        JMenu gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);

        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem resetGameItem = new JMenuItem("Reset Game");
        JMenuItem exitItem = new JMenuItem("Exit");
        
        gameMenu.add(newGameItem);
        gameMenu.add(resetGameItem); // Menambahkan Reset Game ke menu
        gameMenu.addSeparator();
        gameMenu.add(exitItem);

        // Options Menu
        JMenu optionsMenu = new JMenu("Options");
        menuBar.add(optionsMenu);

        // Volume Control Submenu
        JMenuItem volumeControl = new JMenuItem("Volume Control");
        optionsMenu.add(volumeControl);

        // Help Menu
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);

        JMenuItem aboutItem = new JMenuItem("About");
        helpMenu.add(aboutItem);
        // Add Action Listeners
        newGameItem.addActionListener(e -> showDifficultySelection());
        resetGameItem.addActionListener(e -> resetGame());
        exitItem.addActionListener(e -> System.exit(0));
        volumeControl.addActionListener(e -> showVolumeControl());
        aboutItem.addActionListener(e -> showAboutDialog());

        cp.add(board, BorderLayout.CENTER);
        cp.add(btnNewGame, BorderLayout.SOUTH);
        
        btnNewGame.addActionListener(e -> startNewGame(difficulty));
        startNewGame(difficulty);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sudoku");
        setVisible(true);
    }
    private void resetGame() {
        board.newGame(currentDifficulty); // Reset game dengan difficulty yang sama
    }

    private void showAboutDialog() {
        JDialog aboutDialog = new JDialog(this, "About", true);
        aboutDialog.setLayout(new BorderLayout());
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Sudoku Game");
        JLabel versionLabel = new JLabel("Version 1.0");
        JLabel teamLabel = new JLabel("Created by Team 2");

        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        versionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        teamLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(versionLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(teamLabel);

        JButton closeButton = new JButton("Close");
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeButton.addActionListener(e -> aboutDialog.dispose());

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(closeButton);

        aboutDialog.add(panel);
        aboutDialog.setSize(300, 200);
        aboutDialog.setLocationRelativeTo(this);
        aboutDialog.setVisible(true);
    }
    
    

    private void showDifficultySelection() {
        JDialog difficultyDialog = new JDialog(this, "Select Difficulty", true);
        difficultyDialog.setLayout(new GridLayout(3, 1, 10, 10));
        difficultyDialog.setSize(300, 200);

        JButton btnEasy = new JButton("Easy");
        JButton btnMedium = new JButton("Medium");
        JButton btnHard = new JButton("Hard");

        btnEasy.addActionListener(e -> {
            startNewGame(SudokuConstants.EASY);
            difficultyDialog.dispose();
        });
        btnMedium.addActionListener(e -> {
            startNewGame(SudokuConstants.MEDIUM);
            difficultyDialog.dispose();
        });
        btnHard.addActionListener(e -> {
            startNewGame(SudokuConstants.HARD);
            difficultyDialog.dispose();
        });

        difficultyDialog.add(btnEasy);
        difficultyDialog.add(btnMedium);
        difficultyDialog.add(btnHard);

        difficultyDialog.setLocationRelativeTo(this);
        difficultyDialog.setVisible(true);
    }

    private void showVolumeControl() {
        JDialog volumeDialog = new JDialog(this, "Volume Control", true);
        volumeDialog.setLayout(new BorderLayout(10, 10));
        volumeDialog.setSize(300, 150);

        JPanel volumePanel = new JPanel(new BorderLayout(10, 10));
        volumePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel volumeLabel = new JLabel("Volume: ");
        JSlider volumeSlider = new JSlider(0, 100, (int)(volume * 100));
        
        volumeSlider.addChangeListener(e -> {
            volume = volumeSlider.getValue() / 100f;
            // Implement volume control logic here
        });

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> volumeDialog.dispose());

        volumePanel.add(volumeLabel, BorderLayout.NORTH);
        volumePanel.add(volumeSlider, BorderLayout.CENTER);
        volumePanel.add(closeButton, BorderLayout.SOUTH);

        volumeDialog.add(volumePanel);
        volumeDialog.setLocationRelativeTo(this);
        volumeDialog.setVisible(true);
    }

    private void startNewGame(int difficulty) {
        int cellsToGuess;
        switch (difficulty) {
            case SudokuConstants.EASY: cellsToGuess = SudokuConstants.EASY; break;
            case SudokuConstants.MEDIUM: cellsToGuess = SudokuConstants.MEDIUM; break;
            case SudokuConstants.HARD: cellsToGuess = SudokuConstants.HARD; break;
            default: cellsToGuess = SudokuConstants.EASY;
        }
        board.newGame(cellsToGuess);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WelcomeScreen());
    }
}