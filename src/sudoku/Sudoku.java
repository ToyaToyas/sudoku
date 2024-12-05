package sudoku;
import java.awt.*;
import javax.swing.*;
/**
 * The main Sudoku program
 */
public class Sudoku extends JFrame {
    private static final long serialVersionUID = 1L;  // to prevent serial warning
    private void updateScore(int delta) {
        score += delta;
        scoreLabel.setText("Score: " + score);
    }

    private void resetScore() {
        score = 0;
        scoreLabel.setText("Score: 0");
    }

    private void updateProgress() {
        int progress = board.calculateProgress();
        progressBar.setValue(progress);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Sudoku::new);
    }
    

    // private variables
    GameBoardPanel board = new GameBoardPanel();
    JButton btnNewGame = new JButton("New Game");
    JButton btnResetGame = new JButton("Reset Game");
    JButton btnNextLevel = new JButton("Next Level");
    JButton btnPause = new JButton("Pause Timer");
    JButton btnResume = new JButton("Resume Timer");
    JLabel scoreLabel = new JLabel("Score: 0");
    JProgressBar progressBar = new JProgressBar(0, 100);
    private int score = 0;

    private JComboBox<Difficulty> difficultyComboBox;

    // Constructor
    public Sudoku() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        JPanel stopwatchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        stopwatchPanel.add(board.stopwatchLabel);
        stopwatchPanel.add(btnPause);
        stopwatchPanel.add(btnResume);

        cp.add(stopwatchPanel, BorderLayout.NORTH);

        cp.add(board, BorderLayout.CENTER);

        difficultyComboBox = new JComboBox<>(Difficulty.values()); // Easy, Intermediate, Hard
        difficultyComboBox.setSelectedItem(Difficulty.Hard); // Default selection is Hard
        // Add a button to the south to re-start the game via board.newGame()
        // ......
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));
        JPanel panelSouth = new JPanel();
        panelSouth.add(difficultyComboBox);
        panelSouth.add(btnNewGame);
        panelSouth.add(btnResetGame);
        panelSouth.add(btnNextLevel);
        panelSouth.add(scoreLabel);
        bottomPanel.add(panelSouth);
        cp.add(panelSouth, BorderLayout.SOUTH);
        btnNewGame.addActionListener(e ->{
            Difficulty selectedDifficulty = (Difficulty) difficultyComboBox.getSelectedItem();
            board.newGame(selectedDifficulty);
            resetScore(); // Reset skor
            board.stopwatchLabel.startTimer(); // Memastikan timer berjalan
        });
        btnResetGame.addActionListener(e -> {
            board.stopwatchLabel.resetTimer();
            board.newGame(board.currentLevel, board.currentDifficulty);
            board.stopwatchLabel.startTimer();
        });
        btnPause.addActionListener(e -> board.stopwatchLabel.stopTimer());
        btnResume.addActionListener(e -> board.stopwatchLabel.startTimer());
        btnNextLevel.addActionListener(e -> {
            board.newGame(board.currentLevel+1, board.currentDifficulty);
            btnNextLevel.setEnabled(false);
        });
        btnNextLevel.setEnabled(false); // Initially disabled

        // Add puzzle solved listener to enable the "Next Level" button
        board.setPuzzleSolvedListener(() -> btnNextLevel.setEnabled(true));

        // Initialize the game board to start the game
        Difficulty selectedDifficulty = (Difficulty) difficultyComboBox.getSelectedItem();
        board.newGame(selectedDifficulty);

        pack();     // Pack the UI components, instead of using setSize()
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // to handle window-closing
        setTitle("Sudoku");
        setVisible(true);
    }
}
