/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #2
 * 1 - 5026221156 - Muhammad Ali Husain Ridwan
 * 2 - 5026221157 - Muhammad Afaf
 * 3 - 5026221162 - Raphael Andhika Pratama
 */
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

class LoopSound {

    private Clip clip;
    private AudioInputStream sound;

    public LoopSound(String musicFilePath) {
        try {
            // Load the music file
            File file = new File(musicFilePath);
            sound = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(sound);
        } catch (UnsupportedAudioFileException e) {
            System.err.println("The audio file format is unsupported: " + musicFilePath);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error reading the audio file: " + musicFilePath);
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.err.println("The audio line is unavailable.");
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            System.err.println("The audio clip could not be initialized.");
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}
