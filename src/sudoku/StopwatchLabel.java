/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #2
 * 1 - 5026221156 - Muhammad Ali Husain Ridwan
 * 2 - 5026221157 - Muhammad Afaf
 * 3 - 5026221162 - Raphael Andhika Pratama
 */
package sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StopwatchLabel extends JLabel {
    private Timer timer;          // Timer untuk mengupdate label
    private long startTime;       // Waktu ketika timer dimulai
    private long pausedTime;      // Menyimpan waktu ketika timer di-*pause*
    private boolean isPaused;     // Status apakah timer sedang di-*pause*

    public StopwatchLabel() {
        setText("00:00:00");  // Default tampilan waktu
        timer = new Timer(1000, new TimerListener()); // Update setiap detik
    }

    public void startTimer() {
        if (isPaused) {
            // Lanjutkan dari waktu terakhir di-*pause*
            startTime = System.currentTimeMillis() - pausedTime;
            isPaused = false;
        } else {
            // Mulai timer baru
            startTime = System.currentTimeMillis();
        }
        timer.start();
    }

    public void stopTimer() {
        if (!isPaused) {
            // Simpan waktu sebelum di-*pause*
            pausedTime = System.currentTimeMillis() - startTime;
            isPaused = true;
        }
        timer.stop();
    }

    public void resetTimer() {
        timer.stop();
        startTime = 0;
        pausedTime = 0;
        isPaused = false;
        setText("00:00:00");
    }

    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            long elapsed = System.currentTimeMillis() - startTime;
            int hours = (int) (elapsed / 3600000);
            int minutes = (int) ((elapsed % 3600000) / 60000);
            int seconds = (int) ((elapsed % 60000) / 1000);
            setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
        }
    }
}
