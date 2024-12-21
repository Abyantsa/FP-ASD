/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #2
 * 1 - 5026231024 - Rafindra Nabiel Fawwaz
 * 2 - 5026231163 - Muhammad Abyan Tsabit Amani
 * 3 - 5026231188 - Sultan Alamsyah Lintang Mubarok
 */

package TicTacToe;

public class AIPlayerSimple extends AIPlayer {

    public AIPlayerSimple(Board board) {
        super(board);
    }

    @Override
    public int[] move() {
        // Iterate over the board to find the first empty cell
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (cells[row][col].content == Seed.NO_SEED) {
                    return new int[] {row, col};
                }
            }
        }
        // If no empty cell is found, return null (should not happen in a valid game state)
        return null;
    }
}