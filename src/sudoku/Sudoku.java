package sudoku;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
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
    private Difficulty currentDifficulty = Difficulty.Easy; // Mulai dari Easy
    
    
    

    // private variables
    
    private int score = 0;
    private JLabel scoreLabel = new JLabel("Score: 0");
    private JProgressBar progressBar = new JProgressBar(0, 100);
    private JComboBox<Difficulty> difficultyComboBox;
    private GameBoardPanel board = new GameBoardPanel();
    private JButton btnNextLevel = new JButton("Next Level");
    private JButton btnPause = new JButton("Pause Timer");
    private JButton btnResume = new JButton("Resume Timer");
    private JButton btnNewGame = new JButton("New Game");
    private JButton btnResetGame = new JButton("Reset Game");
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
        panelSouth.add(btnNextLevel);
        panelSouth.add(scoreLabel);
        bottomPanel.add(panelSouth);
        // Add Menu Bar
        cp.add(panelSouth, BorderLayout.SOUTH);
        // Add Menu Bar
        setJMenuBar(createMenuBar());
    
        btnPause.addActionListener(e -> {
            board.stopwatchLabel.stopTimer(); // Pause the timer
            btnPause.setEnabled(false);       // Disable Pause button
            btnResume.setEnabled(true);       // Enable Resume button
        });
        
        btnResume.addActionListener(e -> {
            board.stopwatchLabel.startTimer(); // Resume the timer
            btnResume.setEnabled(false);       // Disable Resume button
            btnPause.setEnabled(true);         // Enable Pause button
        });        
        btnNextLevel.addActionListener(e -> {
            currentDifficulty = getNextDifficulty(currentDifficulty); // Tingkatkan level
            board.newGame(currentDifficulty); // Mulai game baru dengan level berikutnya
            board.stopwatchLabel.startTimer(); // Memastikan timer berjalan
            btnNextLevel.setEnabled(false);   // Nonaktifkan tombol hingga teka-teki selesai
            difficultyComboBox.setSelectedItem(currentDifficulty);
            System.out.println("Level changed to: " + currentDifficulty); // Debug log
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
    
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // File Menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem resetGameItem = new JMenuItem("Reset Game");
        JMenuItem exitItem = new JMenuItem("Exit");

        newGameItem.addActionListener(e ->{
            Difficulty selectedDifficulty = (Difficulty) difficultyComboBox.getSelectedItem();
            board.newGame(selectedDifficulty);
            resetScore(); // Reset skor
            board.stopwatchLabel.startTimer(); // Memastikan timer berjalan
        });
    
        resetGameItem.addActionListener(e -> {
            board.stopwatchLabel.resetTimer();
            board.newGame(board.currentLevel, board.currentDifficulty);
            board.stopwatchLabel.startTimer();
    });
    
        
        

        fileMenu.add(newGameItem);
        fileMenu.add(resetGameItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // Options Menu
        JMenu optionsMenu = new JMenu("Options");
        JMenuItem changeDifficultyItem = new JMenuItem("Change Difficulty");

        // Help Menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");

        aboutItem.addActionListener(e -> showAboutDialog());
        helpMenu.add(aboutItem);

        // Add menus to menu bar
        menuBar.add(fileMenu);
        menuBar.add(optionsMenu);
        menuBar.add(helpMenu);

        return menuBar;
    }
    private void startNewGame() {
        Difficulty selectedDifficulty = (Difficulty) difficultyComboBox.getSelectedItem();
        board.newGame(selectedDifficulty); // Memulai permainan baru
        resetScore(); // Reset skor
        board.stopwatchLabel.startTimer(); // Mulai timer
        JOptionPane.showMessageDialog(this, "Starting a new game!", "New Game", JOptionPane.INFORMATION_MESSAGE);
    }

    private void resetGame() {
        board.newGame(board.currentLevel, board.currentDifficulty);
        JOptionPane.showMessageDialog(this, "Game has been reset!", "Reset Game", JOptionPane.INFORMATION_MESSAGE);
    }
    private Difficulty getNextDifficulty(Difficulty currentDifficulty) {
        switch (currentDifficulty) {
            case Easy:
                return Difficulty.Intermediate;
            case Intermediate:
                return Difficulty.Hard;
            case Hard:
            default:
                return Difficulty.Easy; // Tetap di Hard jika sudah di level terakhir
        }
    }
    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this, "Sudoku Game v1.0\nDeveloped by [Your Name]", "About", JOptionPane.INFORMATION_MESSAGE);
    }
}
