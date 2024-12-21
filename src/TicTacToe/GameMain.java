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

 import javax.swing.*;
 import java.io.File;

 import static TicTacToe.TicTacToe.TITLE;

 public class GameMain {
     /** The entry "main" method */
     public static void main(String[] args) {
         // Run GUI construction codes in Event-Dispatching thread for thread safety
         javax.swing.SwingUtilities.invokeLater(new Runnable() {
             public void run() {
                 // Membuat jendela utama
                 JFrame frame = new JFrame(TITLE);

                 // Set konten jendela dengan PageAwal (panel permainan)
                 PageAwal pageAwal = new PageAwal(frame);
                 frame.setContentPane(pageAwal);

                 // Mengatur close operation untuk keluar ketika jendela ditutup
                 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                 // Menentukan ukuran jendela setelah panel permainan dipasang
                 frame.setSize(817, 866);

                 // Menempatkan jendela di tengah layar
                 frame.setLocationRelativeTo(null);

                 // Menampilkan jendela
                 frame.setVisible(true);

                 // Play background music
                 pageAwal.playBackgroundMusic("src\\Sounds\\SoundtrackTictactoe.wav");
             }
         });

     }


 }