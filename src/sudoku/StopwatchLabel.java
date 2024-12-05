package sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StopwatchLabel extends JLabel {

    private long startTime;
    private long elapsedPausedTime = 0; // Waktu yang terakumulasi saat timer dijeda
    private boolean running = false; // Status timer
    private Timer timer;

    public StopwatchLabel() {
        setFont(new Font("Arial", Font.PLAIN, 24));
        setForeground(Color.BLACK);
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });
        resetTimer(); // Inisialisasi timer
    }

    private void updateTime() {
        if (running) {
            long currentTime = System.currentTimeMillis();
            long timeGoesOn = currentTime - startTime + elapsedPausedTime;
            String timeString = String.format("%02d:%02d", timeGoesOn / 1000 / 60, (timeGoesOn / 1000) % 60);
            setText(timeString);
        }
    }

    public void startTimer() {
        if (!running) {
            startTime = System.currentTimeMillis();
            timer.start();
            running = true;
        }
    }

    public void pauseTimer() {
        if (running) {
            elapsedPausedTime += System.currentTimeMillis() - startTime;
            timer.stop();
            running = false;
        }
    }

    public void resumeTimer() {
        if (!running) {
            startTime = System.currentTimeMillis();
            timer.start();
            running = true;
        }
    }

    public void stopTimer() {
        timer.stop();
        running = false;
    }

    public void resetTimer() {
        stopTimer();
        elapsedPausedTime = 0;
        setText("00:00");
    }

    public boolean isRunning() {
        return running;
    }
}
