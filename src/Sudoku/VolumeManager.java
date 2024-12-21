/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #2
 * 1 - 5026231024 - Rafindra Nabiel Fawwaz
 * 2 - 5026231163 - Muhammad Abyan Tsabit Amani
 * 3 - 5026231188 - Sultan Alamsyah Lintang Mubarok
 */

package Sudoku;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class VolumeManager {
    private static VolumeManager instance;
    private float volume = 1.0f;
    private Clip currentClip;

    private VolumeManager() {}

    public static VolumeManager getInstance() {
        if (instance == null) {
            instance = new VolumeManager();
        }
        return instance;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
        updateVolume();
    }

    public void setCurrentClip(Clip clip) {
        this.currentClip = clip;
        updateVolume();
    }

    private void updateVolume() {
        if (currentClip != null) {
            FloatControl gainControl = (FloatControl) currentClip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }
    }

    public void stopMusic() {
        if (currentClip != null && currentClip.isRunning()) {
            currentClip.stop();
            currentClip.close();
        }
    }
}