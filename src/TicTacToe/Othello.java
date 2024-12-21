package TicTacToe;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class Othello extends JPanel {
    private static final long serialVersionUID = 1L;

    public static final int ROWS = 8;
    public static final int COLS = 8;
    public static final int CELL_SIZE = 100;
    private static final int BOARD_SIZE = CELL_SIZE * 8; // Othello is typically 8x8

    private Seed[][] board;
    private Seed currentPlayer;
    private JLabel statusBar;
    private Image blackPieceImage;
    private Image whitePieceImage;

    enum Seed {
        EMPTY, BLACK, WHITE
    }

    public Othello() {
        setPreferredSize(new Dimension(600, 600)); // Set preferred size to 600x600
        board = new Seed[ROWS][COLS];
        initGame();

        // Load images using ClassLoader with correct paths
        blackPieceImage = new ImageIcon(getClass().getResource("/Images/Rapip.png")).getImage();
        whitePieceImage = new ImageIcon(getClass().getResource("/Images/Wahyu.png")).getImage();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int colSelected = e.getX() / CELL_SIZE;
                int rowSelected = e.getY() / CELL_SIZE;
                if (isValidMove(currentPlayer, rowSelected, colSelected)) {
                    makeMove(currentPlayer, rowSelected, colSelected);
                    playSoundEffect("Sounds/XO.wav"); // Play sound effect
                    currentPlayer = (currentPlayer == Seed.BLACK) ? Seed.WHITE : Seed.BLACK;
                    repaint();
                    if (isGameOver()) {
                        declareWinner();
                    }
                }
            }
        });

        statusBar = new JLabel("Hamster Bermain");
        statusBar.setFont(new Font("Roboto", Font.PLAIN, 14));
        statusBar.setBackground(new Color(216, 216, 216));
        statusBar.setOpaque(true);
        statusBar.setPreferredSize(new Dimension(600, 30)); // Adjust status bar width
        statusBar.setHorizontalAlignment(JLabel.LEFT);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));

        setLayout(new BorderLayout());
        add(statusBar, BorderLayout.PAGE_END);
    }

    private void initGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                board[row][col] = Seed.EMPTY;
            }
        }
        board[3][3] = Seed.WHITE;
        board[3][4] = Seed.BLACK;
        board[4][3] = Seed.BLACK;
        board[4][4] = Seed.WHITE;
        currentPlayer = Seed.BLACK;
    }

    private boolean isValidMove(Seed seed, int row, int col) {
        if (board[row][col] != Seed.EMPTY) return false;
        return canFlip(seed, row, col);
    }

    private boolean canFlip(Seed seed, int row, int col) {
        Seed opponentSeed = (seed == Seed.BLACK) ? Seed.WHITE : Seed.BLACK;
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        for (int[] dir : directions) {
            int r = row + dir[0], c = col + dir[1];
            boolean hasOpponentSeed = false;
            while (r >= 0 && r < ROWS && c >= 0 && c < COLS && board[r][c] == opponentSeed) {
                r += dir[0];
                c += dir[1];
                hasOpponentSeed = true;
            }
            if (hasOpponentSeed && r >= 0 && r < ROWS && c >= 0 && c < COLS && board[r][c] == seed) {
                return true;
            }
        }
        return false;
    }

    private void makeMove(Seed seed, int row, int col) {
        board[row][col] = seed;
        flipSeeds(seed, row, col);
    }

    private void flipSeeds(Seed seed, int row, int col) {
        Seed opponentSeed = (seed == Seed.BLACK) ? Seed.WHITE : Seed.BLACK;
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        for (int[] dir : directions) {
            int r = row + dir[0], c = col + dir[1];
            boolean hasOpponentSeed = false;
            while (r >= 0 && r < ROWS && c >= 0 && c < COLS && board[r][c] == opponentSeed) {
                r += dir[0];
                c += dir[1];
                hasOpponentSeed = true;
            }
            if (hasOpponentSeed && r >= 0 && r < ROWS && c >= 0 && c < COLS && board[r][c] == seed) {
                r = row + dir[0];
                c = col + dir[1];
                while (board[r][c] == opponentSeed) {
                    board[r][c] = seed;
                    r += dir[0];
                    c += dir[1];
                }
            }
        }
    }

    private boolean isGameOver() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (board[row][col] == Seed.EMPTY && (isValidMove(Seed.BLACK, row, col) || isValidMove(Seed.WHITE, row, col))) {
                    return false;
                }
            }
        }
        return true;
    }

    private void declareWinner() {
        int blackCount = 0, whiteCount = 0;
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (board[row][col] == Seed.BLACK) blackCount++;
                if (board[row][col] == Seed.WHITE) whiteCount++;
            }
        }
        String winner = (blackCount > whiteCount) ? "Hamster Menang!" : (whiteCount > blackCount) ? "Tupai Menang!" : "It's a draw!";
        JOptionPane.showMessageDialog(this, winner);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                int x = col * CELL_SIZE;
                int y = row * CELL_SIZE;
                g.setColor(new Color(119, 221, 119)); // Set color to brown
                g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
                if (board[row][col] == Seed.BLACK) {
                    g.drawImage(blackPieceImage, x + 5, y + 5, CELL_SIZE - 10, CELL_SIZE - 10, this);
                } else if (board[row][col] == Seed.WHITE) {
                    g.drawImage(whitePieceImage, x + 5, y + 5, CELL_SIZE - 10, CELL_SIZE - 10, this);
                }
            }
        }
        statusBar.setText((currentPlayer == Seed.BLACK) ? "Hamster Bermain" : "Tupai Bermain");
    }

    private void playSoundEffect(String resourcePath) {
        try {
            java.net.URL soundURL = getClass().getClassLoader().getResource(resourcePath);
            if (soundURL == null) {
                System.err.println("Sound effect file does not exist.");
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            System.err.println("The specified audio file is not supported.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error occurred while reading the audio file.");
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.err.println("Audio line for playing back is unavailable.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Othello");
        Othello gamePanel = new Othello();
        frame.setContentPane(gamePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600); // Set the frame size to 600x600
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);
    }
}