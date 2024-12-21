package TicTacToe;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe extends JPanel {
    private static final long serialVersionUID = 1L;

    public static final String TITLE = "Tic Tac Toe";
    public static final Color COLOR_BG = new Color(162, 241, 140);
    public static final Color COLOR_BG_STATUS = new Color(216, 216, 216);
    public static final Color COLOR_CROSS = new Color(239, 105, 80);
    public static final Color COLOR_NOUGHT = new Color(64, 154, 225);
    public static final Font FONT_STATUS = new Font("OCR A Extended", Font.PLAIN, 14);

    private Board board;
    private State currentState;
    private Seed currentPlayer;
    private JLabel statusBar;
    private JLabel scoreLabel;
    private AIPlayer aiPlayer;
    private boolean vsCpu;
    private int player1Score = 0;
    private int player2Score = 0;

    public TicTacToe(boolean vsCpu) {
        this.vsCpu = vsCpu;
        initGame();
        newGame();

        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                int row = mouseY / Cell.SIZE;
                int col = mouseX / Cell.SIZE;

                if (currentState == State.PLAYING) {
                    if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS
                            && board.cells[row][col].content == Seed.NO_SEED) {
                        currentState = board.stepGame(currentPlayer, row, col);

                        SoundEffect.MOVE.play();

                        currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;

                        if (vsCpu && currentPlayer == aiPlayer.mySeed && currentState == State.PLAYING) {
                            int[] move = aiPlayer.move();
                            currentState = board.stepGame(aiPlayer.mySeed, move[0], move[1]);

                            SoundEffect.MOVE.play();

                            currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                        }
                    }
                } else {
                    newGame();
                }
                repaint();
            }
        });

        statusBar = new JLabel();
        statusBar.setFont(FONT_STATUS);
        statusBar.setBackground(COLOR_BG_STATUS);
        statusBar.setOpaque(true);
        statusBar.setPreferredSize(new Dimension(600, 30));
        statusBar.setHorizontalAlignment(JLabel.LEFT);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));

        scoreLabel = new JLabel();
        scoreLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        scoreLabel.setBackground(new Color(255, 223, 186));
        scoreLabel.setOpaque(true);
        scoreLabel.setPreferredSize(new Dimension(600, 40));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
        updateScoreLabel();

        super.setLayout(new BorderLayout());
        super.add(scoreLabel, BorderLayout.PAGE_START);
        super.add(statusBar, BorderLayout.PAGE_END);
        super.setPreferredSize(new Dimension(600, 570));
        super.setBorder(BorderFactory.createLineBorder(COLOR_BG_STATUS, 2, false));
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Player 1 (X): " + player1Score + " | Player 2 (O): " + player2Score);
    }

    public void setupMenuBar(JFrame frame) {
        frame.setJMenuBar(createMenuBar(frame));
    }

    private JMenuBar createMenuBar(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();

        JMenu gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);

        JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.addActionListener(e -> {
            newGame();
            frame.revalidate();
        });
        gameMenu.add(newGameItem);

        JMenuItem optionsItem = new JMenuItem("Options");
        optionsItem.addActionListener(e -> showSettingsDialog(frame));
        gameMenu.add(optionsItem);

        JMenuItem mainMenuItem = new JMenuItem("Main Menu");
        mainMenuItem.addActionListener(e -> {
            frame.setContentPane(new PageAwal(frame));
            frame.revalidate();
        });
        gameMenu.add(mainMenuItem);

        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.addActionListener(e -> System.exit(0));
        gameMenu.add(quitItem);

        return menuBar;
    }

    private void showSettingsDialog(JFrame frame) {
        JOptionPane.showMessageDialog(frame, "Settings dialog not implemented yet.");
    }

    public void initGame() {
        board = new Board();
        if (vsCpu) {
            aiPlayer = new AIPlayerSimple(board);
            aiPlayer.setSeed(Seed.NOUGHT);
        }
    }

    public void newGame() {
        for (int row = 0; row < Board.ROWS; ++row) {
            for (int col = 0; col < Board.COLS; ++col) {
                board.cells[row][col].content = Seed.NO_SEED;
            }
        }
        currentPlayer = Seed.CROSS;
        currentState = State.PLAYING;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(COLOR_BG);
        board.paint(g);

        if (currentState == State.PLAYING) {
            statusBar.setForeground(Color.BLACK);
            statusBar.setText((currentPlayer == Seed.CROSS) ? "X's Turn" : "O's Turn");
        } else if (currentState == State.DRAW) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("It's a Draw! Click to play again.");
            showWinDialog("It's a Draw!");
        } else if (currentState == State.CROSS_WON || currentState == State.NOUGHT_WON) {
            String winner = (currentState == State.CROSS_WON) ? "'X' Won!" : "'O' Won!";
            statusBar.setForeground(Color.RED);
            statusBar.setText(winner + " Click to play again.");
            SoundEffect.WIN.play();
            showWinDialog(winner);

            if (currentState == State.CROSS_WON) {
                player1Score++;
            } else if (currentState == State.NOUGHT_WON) {
                player2Score++;
            }
            updateScoreLabel();
        }
    }

    private void showWinDialog(String message) {
        SwingUtilities.invokeLater(() -> {
            JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Game Over", true);
            dialog.setLayout(new BorderLayout());
            dialog.setSize(400, 200);
            dialog.setLocationRelativeTo(null);

            JPanel panel = new JPanel();
            panel.setBackground(new Color(70, 130, 180));
            panel.setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" + message + "<br>Would you like to start a new game or quit?</div></html>");
            messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
            messageLabel.setForeground(Color.WHITE);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            panel.add(messageLabel, gbc);

            JButton newGameButton = createStyledButton("New Game");
            newGameButton.addActionListener(e -> {
                newGame();
                dialog.dispose();
            });

            JButton quitButton = createStyledButton("Quit");
            quitButton.addActionListener(e -> {
                dialog.dispose();
                System.exit(0);
            });

            gbc.gridwidth = 1;
            gbc.gridy = 1;
            gbc.gridx = 0;
            panel.add(newGameButton, gbc);

            gbc.gridx = 1;
            panel.add(quitButton, gbc);

            dialog.add(panel, BorderLayout.CENTER);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        });
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 50));
        button.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        button.setBackground(new Color(255, 165, 0));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}