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
import java.awt.*;
/**
 * The Cell class models each individual cell of the game board.
 */
public class Cell {
    // Define named constants for drawing
    public static final int SIZE = 250;// cell width/height (square)
    // Symbols (cross/nought) are displayed inside a cell, with padding from border
    public static final int PADDING = SIZE / 5;
    public static final int SEED_SIZE = SIZE - PADDING * 2;

    // Define properties (package-visible)
    /** Content of this cell (Seed.EMPTY, Seed.CROSS, or Seed.NOUGHT) */
    Seed content;
    /** Row and column of this cell */
    int row, col;

    /** Constructor to initialize this cell with the specified row and col */
    public Cell(int row, int col, int cellSize) {
        this.row = row;
        this.col = col;
        content = Seed.NO_SEED;
    }

    /** Reset this cell's content to EMPTY, ready for new game */
    public void newGame() {
        content = Seed.NO_SEED;
    }

    /** Paint itself on the graphics canvas, given the Graphics context */
    public void paint(Graphics g) {
        int x1 = col * Board.CELL_SIZE;
        int y1 = row * Board.CELL_SIZE;

        if (content == Seed.CROSS || content == Seed.NOUGHT) {
            Image img = content.getImage();
            if (img != null) {
                // Draw the image scaled to fit the cell
                g.drawImage(img, x1 + 10, y1 + 10, Board.CELL_SIZE - 20, Board.CELL_SIZE - 20, null);
            } else {
                // Fallback to drawing text if image is not available
                g.setFont(new Font("Arial", Font.BOLD, Board.CELL_SIZE / 2));
                g.drawString(content.getDisplayName(), x1 + Board.CELL_SIZE / 4, y1 + 3 * Board.CELL_SIZE / 4);
            }
        }
    }
}