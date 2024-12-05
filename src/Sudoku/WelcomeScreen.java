package Sudoku;

import javax.swing .*;
import java.awt .*;

    public class WelcomeScreen extends JFrame {
        private static final long serialVersionUID = 1L;

        public WelcomeScreen() {
            setTitle("Welcome to Sudoku");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());

            // Welcome message
            JLabel lblWelcome = new JLabel("Welcome to Sudoku", JLabel.CENTER);
            lblWelcome.setFont(new Font("Arial", Font.BOLD, 20));
            add(lblWelcome, BorderLayout.NORTH);

            // Difficulty buttons
            JPanel panelButtons = new JPanel(new GridLayout(3, 1, 10, 10));
            JButton btnEasy = new JButton("Easy");
            JButton btnMedium = new JButton("Medium");
            JButton btnHard = new JButton("Hard");

            panelButtons.add(btnEasy);
            panelButtons.add(btnMedium);
            panelButtons.add(btnHard);

            add(panelButtons, BorderLayout.CENTER);

            // Action listeners for difficulty buttons
            btnEasy.addActionListener(e -> startGame(SudokuConstants.EASY));
            btnMedium.addActionListener(e -> startGame(SudokuConstants.MEDIUM));
            btnHard.addActionListener(e -> startGame(SudokuConstants.HARD));

            setLocationRelativeTo(null); // Center the window
            setVisible(true);
        }

        private void startGame(int difficulty) {
            new Sudoku(difficulty); // Start the game with the selected difficulty
            dispose(); // Close the welcome screen
        }
    }


