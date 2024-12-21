package Sudoku;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class GameBoardPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public static final int CELL_SIZE = 60;
    public static final int BOARD_WIDTH = CELL_SIZE * SudokuConstants.GRID_SIZE;
    public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;

    private Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    private Puzzle puzzle = new Puzzle();
    private Sudoku sudoku;

    public GameBoardPanel(Sudoku sudoku) {
        this.sudoku = sudoku;
        super.setLayout(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE));
        CellInputListener listener = new CellInputListener();

        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col] = new Cell(row, col);
                super.add(cells[row][col]);
            }
        }

        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].isEditable()) {
                    cells[row][col].addActionListener(listener);
                }
            }
        }

        super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
    }

    public void newGame(int difficulty) {
        puzzle.newPuzzle(difficulty);
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (!puzzle.isGiven[row][col]) {
                    cells[row][col].newGame(0, false);
                } else {
                    cells[row][col].newGame(puzzle.numbers[row][col], true);
                }
            }
        }
    }

    public boolean isSolved() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
                    return false;
                }
            }
        }
        return true;
    }

    public void provideHint() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                Cell cell = cells[row][col];
                if (cell.status == CellStatus.TO_GUESS) {
                    int correctNumber = puzzle.solution[row][col];
                    cell.setText(String.valueOf(correctNumber));
                    cell.status = CellStatus.CORRECT_GUESS;
                    cell.setEditable(false);
                    cell.paint();
                    return;
                }
            }
        }
        JOptionPane.showMessageDialog(null, "No more hints available!");
    }

    private class CellInputListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Cell sourceCell = (Cell) e.getSource();
            int numberIn;
            try {
                numberIn = Integer.parseInt(sourceCell.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a number between 1 and 9!");
                sourceCell.setText("");
                return;
            }

            if (numberIn < 1 || numberIn > 9) {
                JOptionPane.showMessageDialog(null, "Please enter a number between 1 and 9!");
                sourceCell.setText("");
                return;
            }

            if (numberIn == puzzle.solution[sourceCell.row][sourceCell.col]) {
                sourceCell.status = CellStatus.CORRECT_GUESS;
                sudoku.updateScore(10);
                playSound("src\\Sounds\\benarsudoku.wav");
            } else {
                sourceCell.status = CellStatus.WRONG_GUESS;
                playSound("src\\Sounds\\salahsudoku.wav");
            }

            sourceCell.paint();

            if (isSolved()) {
                JOptionPane.showMessageDialog(null, "Congratulations! You solved the puzzle!");
            }
        }
    }

    private void playSound(String filePath) {
        File soundFile = new File(filePath);
        System.out.println("Attempting to load sound from: " + soundFile.getAbsolutePath());
        if (!soundFile.exists()) {
            System.err.println("Sound file not found: " + filePath);
            return;
        }
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }
}