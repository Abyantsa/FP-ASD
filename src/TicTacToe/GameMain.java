package TicTacToe;
import javax.swing.*;

import static TicTacToe.TicTacToe.TITLE;

public class GameMain {
    /** The entry "main" method */
    public static void main(String[] args) {
        // Run GUI construction codes in Event-Dispatching thread for thread safety
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Membuat jendela utama
                JFrame frame = new JFrame(TITLE);

                // Set konten jendela dengan TicTacToe (panel permainan)
                TicTacToe gamePanel = new TicTacToe();
                frame.setContentPane(gamePanel);

                // Mengatur close operation untuk keluar ketika jendela ditutup
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // Menentukan ukuran jendela setelah panel permainan dipasang
                frame.setSize(Board.CANVAS_WIDTH, Board.CANVAS_HEIGHT + 30); // Menambahkan ruang untuk status bar

                // Menempatkan jendela di tengah layar
                frame.setLocationRelativeTo(null);

                // Menampilkan jendela
                frame.setVisible(true);
            }
        });
    }
}

