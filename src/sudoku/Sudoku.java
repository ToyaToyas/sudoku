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

import java.awt.*;
import javax.swing.*;

/**
 * The main Sudoku program
 */
public class Sudoku extends JFrame {
    private static final long serialVersionUID = 1L; // to prevent serial warning

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Sudoku::new);
    }

    private Difficulty currentDifficulty = Difficulty.Easy; // Default difficulty
    private int score = 0;
    private JLabel scoreLabel = new JLabel("Score: 0");
    private JComboBox<Difficulty> difficultyComboBox;
    private GameBoardPanel board = new GameBoardPanel();
    private JButton btnNextLevel = new JButton("Next Level");
    private JButton btnPause = new JButton("Pause Timer");
    private JButton btnResume = new JButton("Resume Timer");
    private JButton btnHint = new JButton("Hint");
    private String playerName = "Player";
    private int bestScore = Integer.MAX_VALUE;
    private JProgressBar progressBar = new JProgressBar(0, 100); // Initialize Progress Bar
    private JMenu helpMenu; // Declare as a class variable for clarity

    // Constructor
    public Sudoku() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        // Stopwatch Panel
        JPanel stopwatchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        stopwatchPanel.add(board.stopwatchLabel);
        stopwatchPanel.add(btnPause);
        stopwatchPanel.add(btnResume);
        stopwatchPanel.add(btnHint);
        cp.add(stopwatchPanel, BorderLayout.NORTH);

        // Game Board
        cp.add(board, BorderLayout.CENTER);

        // Bottom Panel
        difficultyComboBox = new JComboBox<>(Difficulty.values());
        difficultyComboBox.setSelectedItem(Difficulty.Hard);
        JPanel panelSouth = new JPanel();
        panelSouth.add(difficultyComboBox);
        panelSouth.add(btnNextLevel);
        panelSouth.add(scoreLabel);
        panelSouth.add(progressBar); // Add progress bar to the bottom panel
        cp.add(panelSouth, BorderLayout.SOUTH);

        // Menu Bar
        setJMenuBar(createMenuBar());

        // Button Actions
        btnPause.addActionListener(e -> {
            board.stopwatchLabel.stopTimer(); // Pause the timer
            btnPause.setEnabled(false); // Disable Pause button
            btnResume.setEnabled(true); // Enable Resume button
        });

        btnResume.addActionListener(e -> {
            board.stopwatchLabel.startTimer(); // Resume the timer
            btnResume.setEnabled(false); // Disable Resume button
            btnPause.setEnabled(true); // Enable Pause button
        });

        btnNextLevel.addActionListener(e -> {
            int currentLevel = board.currentLevel;
            if (currentLevel < 5) {
                board.newGame(currentLevel + 1, board.currentDifficulty);
                board.stopwatchLabel.startTimer(); // Start timer
                btnNextLevel.setEnabled(false); // Disable until solved
            } else {
                JOptionPane.showMessageDialog(this, "You have reached the maximum level!", "Level 5", JOptionPane.INFORMATION_MESSAGE);
                btnNextLevel.setEnabled(false);
            }
        });

        btnHint.addActionListener(e -> {
            board.showHint(); // Show a hint
        });

        btnNextLevel.setEnabled(false); // Initially disabled

        // Puzzle Solved Listener
        board.setPuzzleSolvedListener(() -> btnNextLevel.setEnabled(true));

        // Start a New Game
        Difficulty selectedDifficulty = (Difficulty) difficultyComboBox.getSelectedItem();
        board.newGame(selectedDifficulty);

        // Final Setup
        pack(); // Pack the UI components
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sudoku");
        setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // File Menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem resetGameItem = new JMenuItem("Reset Game");
        JMenuItem exitItem = new JMenuItem("Exit");
        JMenuItem highScoresItem = new JMenuItem("View High Scores");
        highScoresItem.addActionListener(e -> showHighScores());

        newGameItem.addActionListener(e -> {
            Difficulty selectedDifficulty = (Difficulty) difficultyComboBox.getSelectedItem();
            board.newGame(selectedDifficulty);
            resetScore(); // Reset score
            board.stopwatchLabel.startTimer(); // Ensure timer starts
        });

        resetGameItem.addActionListener(e -> {
            board.stopwatchLabel.resetTimer();
            board.newGame(board.currentLevel, board.currentDifficulty);
            board.stopwatchLabel.startTimer();
        });

        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(newGameItem);
        fileMenu.add(resetGameItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // Help Menu
        helpMenu = new JMenu("Help"); // Class-level variable
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAboutDialog());
        helpMenu.add(aboutItem);
        helpMenu.add(highScoresItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        return menuBar;
    }

    private void resetScore() {
        score = 0;
        scoreLabel.setText("Score: " + score);
    }

    private void updateProgressBar() {
        int filledCells = 0;
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                if (!board.cells[row][col].getText().isEmpty()) {
                    filledCells++;
                }
            }
        }
        progressBar.setValue((int) ((double) filledCells / (SudokuConstants.GRID_SIZE * SudokuConstants.GRID_SIZE) * 100));
    }

    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this, "Sudoku Game v1.0\nDeveloped by [Kelompok 1]", "About", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showHighScores() {
        JOptionPane.showMessageDialog(this, "Best Score: " + bestScore + " seconds by " + playerName, "High Score", JOptionPane.INFORMATION_MESSAGE);
    }
}
