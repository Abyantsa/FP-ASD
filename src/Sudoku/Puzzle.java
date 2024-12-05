package Sudoku;

/**
 * The Sudoku number puzzle to be solved
 */
public class Puzzle {
    // All variables have package access
    // The numbers on the puzzle
    int[][] numbers = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    // The clues - isGiven (no need to guess) or need to guess
    boolean[][] isGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    int[][] solution = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];


    // Constructor
    public Puzzle() {
        super();
    }

    // Generate a new puzzle given the number of cells to be guessed, which can be used
    // to control the difficulty level.
    public void newPuzzle(int cellsToGuess) {
        int[][] hardcodedNumbers = {
                {5, 3, 4, 6, 7, 8, 9, 1, 2},
                {6, 7, 2, 1, 9, 5, 3, 4, 8},
                {1, 9, 8, 3, 4, 2, 5, 6, 7},
                {8, 5, 9, 7, 6, 1, 4, 2, 3},
                {4, 2, 6, 8, 5, 3, 7, 9, 1},
                {7, 1, 3, 9, 2, 4, 8, 5, 6},
                {9, 6, 1, 5, 3, 7, 2, 8, 4},
                {2, 8, 7, 4, 1, 9, 6, 3, 5},
                {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };

        // Copy solusi ke dalam array solution
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                numbers[row][col] = hardcodedNumbers[row][col];
                solution[row][col] = hardcodedNumbers[row][col]; // Salin solusi ke solution
                isGiven[row][col] = true;
            }
        }

        // Hapus angka secara acak sesuai cellsToGuess
        int cellsToRemove = cellsToGuess;
        while (cellsToRemove > 0) {
            int row = (int) (Math.random() * SudokuConstants.GRID_SIZE);
            int col = (int) (Math.random() * SudokuConstants.GRID_SIZE);

            if (isGiven[row][col]) {
                numbers[row][col] = 0; // Kosongkan angka
                isGiven[row][col] = false;
                cellsToRemove--;
            }
        }
    }





}
