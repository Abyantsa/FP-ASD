package Sudoku;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Sudoku extends JFrame {
    private static final long serialVersionUID = 1L;

    // Variabel privat
    GameBoardPanel board = new GameBoardPanel();
    JButton btnNewGame = new JButton("New Game");

    // Konstruktor dengan parameter difficulty
    public Sudoku(int difficulty) {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        cp.add(board, BorderLayout.CENTER);

        // Tambahkan tombol untuk memulai permainan baru
        cp.add(btnNewGame, BorderLayout.SOUTH);
        btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectDifficultyAndStartGame();
            }
        });

        // Tampilkan pemilihan tingkat kesulitan saat startup
        selectDifficultyAndStartGame();

        pack(); // Mengemas komponen UI
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sudoku");
        setVisible(true);
    }

    // Konstruktor tanpa argumen
    public Sudoku() {
        this(0);  // Defaultkan difficulty ke "Easy" (0)
    }

    // Tampilkan pemilihan kesulitan dan mulai permainan baru
    private void selectDifficultyAndStartGame() {
        String[] options = {"Easy", "Medium", "Hard"};
        int difficulty = JOptionPane.showOptionDialog(
                this,
                "Select Difficulty Level",
                "Welcome to Sudoku",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

        int cellsToGuess;
        switch (difficulty) {
            case 0: cellsToGuess = 20; break; // Easy
            case 1: cellsToGuess = 40; break; // Medium
            case 2: cellsToGuess = 60; break; // Hard
            default: cellsToGuess = 20; // Default ke Easy
        }

        board.newGame(cellsToGuess);
    }

    // Method main untuk menjalankan permainan
    public static void main(String[] args) {
        // Membuat permainan Sudoku dengan kesulitan default (Easy)
        new Sudoku();
    }
}
