package sudoku;
import java.awt.*;
import javax.swing.*;
/**
 * The main Sudoku program
 */
public class Sudoku extends JFrame {
    private static final long serialVersionUID = 1L;  // to prevent serial warning

    // private variables
    GameBoardPanel board = new GameBoardPanel();
    JButton btnNewGame = new JButton("New Game");
    JButton btnResetGame = new JButton("Reset Game");
    JButton btnNextLevel = new JButton("Next Level");

    private JComboBox<Difficulty> difficultyComboBox;

    // Constructor
    public Sudoku() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        cp.add(board, BorderLayout.CENTER);

        difficultyComboBox = new JComboBox<>(Difficulty.values()); // Easy, Intermediate, Hard
        difficultyComboBox.setSelectedItem(Difficulty.Hard); // Default selection is Hard
        // Add a button to the south to re-start the game via board.newGame()
        // ......
        JPanel panelSouth = new JPanel();
        panelSouth.add(difficultyComboBox);
        panelSouth.add(btnNewGame);
        panelSouth.add(btnResetGame);
        panelSouth.add(btnNextLevel);
        cp.add(panelSouth, BorderLayout.SOUTH);
        btnNewGame.addActionListener(e ->{
            Difficulty selectedDifficulty = (Difficulty) difficultyComboBox.getSelectedItem();
            board.newGame(selectedDifficulty);
        });
        btnResetGame.addActionListener(e -> board.newGame(board.currentLevel, board.currentDifficulty));
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