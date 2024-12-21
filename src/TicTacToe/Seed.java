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

 import java.awt.Image;
 import java.net.URL;
 import javax.swing.ImageIcon;
 import java.io.File;


/**
  * This enum is used by:
  * 1. Player: takes value of CROSS or NOUGHT
  * 2. Cell content: takes value of CROSS, NOUGHT, BLACK, WHITE, or NO_SEED.
  *
  * We also attach a display image icon (text or image) for the items.
  * To draw the image:
  *   g.drawImage(content.getImage(), x, y, width, height, null);
  */
public enum Seed {
    CROSS("X", "/Images/X.png"),
    NOUGHT("O", "/Images/O.png"),
    NO_SEED(" ", null),
    BLACK("B", "/Images/Cat.png"),
    WHITE("W", "/Images/Dog.png");

    private final String displayName;
    private final Image img;

    private Seed(String name, String imageFilename) {
        this.displayName = name;

        // Load image if filename is provided
        if (imageFilename != null) {
            URL imgURL = getClass().getResource(imageFilename);
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                this.img = icon.getImage();
            } else {
                System.err.println("Couldn't find file " + imageFilename);
                this.img = null;
            }
        } else {
            this.img = null;
        }
    }

    public String getDisplayName() {
        return displayName;
    }

    public Image getImage() {
        if (img == null) {
            return createFallbackImage();
        }
        return img;
    }

    private Image createFallbackImage() {
        return new ImageIcon("default_image.png").getImage();
    }
}