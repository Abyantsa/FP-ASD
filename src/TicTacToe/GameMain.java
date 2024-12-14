package TicTacToe;

import javax.swing.*;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

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
                PageAwal pageAwal = new PageAwal(frame);
                frame.setContentPane(pageAwal);

                // Mengatur close operation untuk keluar ketika jendela ditutup
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // Menentukan ukuran jendela setelah panel permainan dipasang
                frame.setSize(Board.CANVAS_WIDTH, Board.CANVAS_HEIGHT + 30); // Menambahkan ruang untuk status bar

                // Menempatkan jendela di tengah layar
                frame.setLocationRelativeTo(null);

                // Menampilkan jendela
                frame.setVisible(true);

                // Play background music
                playBackgroundMusic("C:\\Users\\Alamsyah Mubarok\\IdeaProjects\\FP-ASD1\\src\\SoundtrackTictactoe.wav");
            }
        });
    }

    /** Play background music in a loop */
    private static void playBackgroundMusic(String filePath) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the music continuously
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}