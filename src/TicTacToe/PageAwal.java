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
 import javax.sound.sampled.*;
 import java.awt.*;
 import java.io.File;
 import java.io.IOException;
 
 public class PageAwal extends JPanel {
     private static final long serialVersionUID = 1L;
     private float volumeLevel = 1.0f; // Set to maximum volume for testing
     private Clip backgroundClip; // Keep a reference to the Clip
 
     public PageAwal(JFrame frame) {
         setLayout(new GridBagLayout());
         setBackground(new Color(70, 130, 180)); // Set background to blue
 
         GridBagConstraints gbc = new GridBagConstraints();
         gbc.insets = new Insets(10, 10, 10, 10);
 
         JButton playTicTacToeButton = createStyledButton("Play TicTacToe");
         JButton playOthelloButton = createStyledButton("Play Othello");
         JButton settingsButton = createStyledButton("Settings");
         JButton quitButton = createStyledButton("Quit Game");
 
         gbc.gridx = 0;
         gbc.gridy = 0;
         add(playTicTacToeButton, gbc);
 
         gbc.gridy = 1;
         add(playOthelloButton, gbc);
 
         gbc.gridy = 2;
         add(settingsButton, gbc);
 
         gbc.gridy = 3;
         add(quitButton, gbc);
 
         playTicTacToeButton.addActionListener(e -> showTicTacToeOptions(frame));
 
         playOthelloButton.addActionListener(e -> {
             frame.setContentPane(new Othello()); // Switch to Othello
             frame.revalidate();
         });
 
         settingsButton.addActionListener(e -> showSettingsDialog(frame));
 
         quitButton.addActionListener(e -> System.exit(0));

         // Add created by and version information
         JLabel infoLabel = new JLabel("Created by Perkap team | Version 1.0 | 2024");
         infoLabel.setFont(new Font("Arial", Font.BOLD, 14));
         infoLabel.setForeground(Color.WHITE);
         gbc.gridy = 4;
         add(infoLabel, gbc);
     }

     private JLabel createImageLabel(String imagePath) {
         java.net.URL imgURL = getClass().getClassLoader().getResource(imagePath);
         if (imgURL != null) {
             ImageIcon imageIcon = new ImageIcon(imgURL);
             return new JLabel(imageIcon);
         } else {
             System.err.println("Couldn't find file: " + imagePath);
             return new JLabel("Image not found");
         }
     }
 
     private JButton createStyledButton(String text) {
         JButton button = new JButton(text);
         button.setPreferredSize(new Dimension(400, 100)); // Adjust size as needed
         button.setFont(new Font("Comic Sans MS", Font.BOLD, 25)); // Change to Comic Sans
         button.setBackground(new Color(255, 165, 0)); // Orange background
         button.setForeground(Color.WHITE); // White text
         button.setFocusPainted(false);
         button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
         button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand
         return button;
     }
 
     private void showTicTacToeOptions(JFrame frame) {
         JDialog dialog = new JDialog(frame, "TicTacToe Mode", true);
         dialog.setLayout(new BorderLayout());
         dialog.setSize(600, 250);
         dialog.setLocationRelativeTo(frame);
 
         JPanel panel = new JPanel(new GridBagLayout());
         panel.setBackground(new Color(70, 130, 180));
 
         GridBagConstraints gbc = new GridBagConstraints();
         gbc.insets = new Insets(10, 10, 10, 10);
 
         JLabel messageLabel = new JLabel("Choose TicTacToe Mode:");
         messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
         messageLabel.setForeground(Color.WHITE);
         gbc.gridx = 0;
         gbc.gridy = 0;
         gbc.gridwidth = 2;
         panel.add(messageLabel, gbc);
 
         JButton playerVsPlayerButton = createStyledButton("Player vs Player");
         playerVsPlayerButton.addActionListener(e -> {
             frame.setContentPane(new TicTacToe(false));
             frame.setSize(800, 800); // Adjust the size to fit the game
             frame.setLocationRelativeTo(null); // Center the frame
             frame.revalidate();
             dialog.dispose();
         });
 
         JButton playerVsCpuButton = createStyledButton("Player vs CPU");
         playerVsCpuButton.addActionListener(e -> {
             frame.setContentPane(new TicTacToe(true));
             frame.setSize(800, 800); // Adjust the size to fit the game
             frame.setLocationRelativeTo(null); // Center the frame
             frame.revalidate();
             dialog.dispose();
         });
 
         gbc.gridwidth = 1;
         gbc.gridy = 1;
         gbc.gridx = 0;
         panel.add(playerVsPlayerButton, gbc);
 
         gbc.gridx = 1;
         panel.add(playerVsCpuButton, gbc);
 
         dialog.add(panel, BorderLayout.CENTER);
         dialog.setVisible(true);
     }
 
     private void showSettingsDialog(JFrame frame) {
         JDialog settingsDialog = new JDialog(frame, "Settings", true);
         settingsDialog.setLayout(new BorderLayout());
         settingsDialog.setSize(300, 200);
         settingsDialog.setLocationRelativeTo(frame);
 
         JPanel audioPanel = new JPanel();
         audioPanel.add(new JLabel("Volume:"));
         JSlider volumeSlider = new JSlider(0, 100, (int) (volumeLevel * 100));
         volumeSlider.addChangeListener(e -> {
             volumeLevel = volumeSlider.getValue() / 100f;
             if (backgroundClip != null && backgroundClip.isOpen()) {
                 setVolume(backgroundClip, volumeLevel);
             }
         });
         audioPanel.add(volumeSlider);
 
         settingsDialog.add(audioPanel, BorderLayout.CENTER);
 
         JButton closeButton = new JButton("Close");
         closeButton.addActionListener(e -> settingsDialog.dispose());
 
         settingsDialog.add(closeButton, BorderLayout.SOUTH);
         settingsDialog.setVisible(true);
     }
 
     public void playBackgroundMusic(String filePath) {
         try {
             System.out.println("Attempting to play audio from: " + filePath);
             File audioFile = new File(filePath);
             if (!audioFile.exists()) {
                 System.err.println("Audio file does not exist.");
                 return;
             }
             AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
             backgroundClip = AudioSystem.getClip();
             backgroundClip.open(audioStream);
             setVolume(backgroundClip, volumeLevel);
             backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
             backgroundClip.start();
             System.out.println("Audio is playing.");
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
 
     private void setVolume(Clip clip, float volume) {
         if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
             FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
             float range = gainControl.getMaximum() - gainControl.getMinimum();
             float gain = (range * volume) + gainControl.getMinimum();
             gainControl.setValue(gain);
         }
     }
 }