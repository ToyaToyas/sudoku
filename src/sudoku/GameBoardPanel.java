package sudoku;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import sudoku.StopwatchLabel;

public class GameBoardPanel extends JPanel {
    private static final long serialVersionUID = 1L;  // to prevent serial warning


    // Define named constants for UI sizes
    public static final int CELL_SIZE = 60;   // Cell width/height in pixels
    public static final int BOARD_WIDTH = CELL_SIZE * SudokuConstants.GRID_SIZE;
    public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;
    // Board width/height in pixels

    // Define properties
    /**
     * The game board composes of 9x9 Cells (customized JTextFields)
     */
    private Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    /**
     * It also contains a Puzzle with array numbers and isGiven
     */
    private Puzzle puzzle = new Puzzle();
    private int startLevel = 1;
    public int currentLevel = startLevel; // To track the current level
    public Difficulty currentDifficulty;
    StopwatchLabel stopwatchLabel = new StopwatchLabel();

    private PuzzleSolvedListener puzzleSolvedListener;
    private JTextField statusBar;
    private JButton hintsButton;


    /**
     * Constructor
     */
    public GameBoardPanel() {
        super.setLayout(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE));  // JPanel
        super.setLayout(new BorderLayout());

        // Allocate the 2D array of Cell, and added into JPanel.
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col] = new Cell(row, col);
                super.add(cells[row][col]);   // JPanel
            }
        }

        // [TODO 3] Allocate a common listener as the ActionEvent listener for all the
        //  Cells (JTextFields)
        CellInputListener listener = new CellInputListener();

        // [TODO 4] Adds this common listener to all editable cells
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].isEditable()) {
                    cells[row][col].addActionListener(listener);   // For all editable rows and cols
                }
            }
        }
        super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));

        // Create the game board (keeping the GridLayout for cells)
        JPanel boardPanel = new JPanel(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE));

        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col] = new Cell(row, col);
                boardPanel.add(cells[row][col]);
            }
        }

        // Add the game board to the center of the BorderLayout
        this.add(boardPanel, BorderLayout.CENTER);

        // Create and add the status bar to the South
        statusBar = new JTextField("Welcome to Sudoku!");
        statusBar.setEditable(false);
        statusBar.setHorizontalAlignment(JTextField.CENTER);
        this.add(statusBar, BorderLayout.SOUTH);

        // Create and add hint button
        hintsButton = new JButton("Hint");
        hintsButton.addActionListener(e -> showHint());

        // Add the hint button to the control panel
        JPanel controlPanel = new JPanel();
        controlPanel.add(hintsButton);
        this.add(controlPanel, BorderLayout.NORTH);

    }

    public int calculateProgress() {
        int totalCells = SudokuConstants.GRID_SIZE * SudokuConstants.GRID_SIZE;
        int filledCells = 0;

        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].status == CellStatus.CORRECT_GUESS) {
                    filledCells++;
                }
            }
        }

        return (int) ((double) filledCells / totalCells * 100);
    }

    public void setPuzzleSolvedListener(PuzzleSolvedListener listener) {
        this.puzzleSolvedListener = listener;
    }

    /**
     * Generate a new puzzle; and reset the game board of cells based on the puzzle.
     * You can call this method to start a new game.
     */
    public void newGame(Difficulty startDiff) {
        newGame(startLevel, startDiff);
        stopwatchLabel.resetTimer();
    }

    public void newGame(int level, Difficulty difficulty) {
        // Generate a new puzzle

        currentLevel = level;
        currentDifficulty = difficulty;
        puzzle.newPuzzle(level, currentDifficulty);

        // Initialize all the 9x9 cells, based on the puzzle.
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
            }
        }
    }

    /**
     * Return true if the puzzle is solved
     * i.e., none of the cell have status of TO_GUESS or WRONG_GUESS
     */
    public boolean isSolved() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
                    return false;
                }
            }
        }
        return true;
    }

    // [TODO 2] Define a Listener Inner Class for all the editable Cells
    // .........
    private class CellInputListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get a reference of the JTextField that triggers this action event
            Cell sourceCell = (Cell) e.getSource();

            // Retrieve the int entered
            int numberIn = Integer.parseInt(sourceCell.getText());
            // For debugging
            System.out.println("You entered " + numberIn);

            /*
             * [TODO 5] (later - after TODO 3 and 4)
             * Check the numberIn against sourceCell.number.
             * Update the cell status sourceCell.status,
             * and re-paint the cell via sourceCell.paint().
             */
            if (numberIn == sourceCell.number) {
                sourceCell.status = CellStatus.CORRECT_GUESS;
            } else {
                sourceCell.status = CellStatus.WRONG_GUESS;
            }

            highlightConflictingCells(sourceCell, numberIn);

            /*
             * [TODO 6] (later)
             * Check if the player has solved the puzzle after this move,
             *   by calling isSolved(). Put up a congratulation JOptionPane, if so.
             */
            if (isSolved()) {
                stopwatchLabel.stopTimer();
                JOptionPane.showMessageDialog(null, "Puzzle Solved");
                puzzleSolvedListener.onPuzzleSolved();
            }
        }
    }

    public interface PuzzleSolvedListener {
        void onPuzzleSolved();
    }

    private void highlightConflictingCells(Cell sourceCell, int numberIn) {
        // Clear previous conflict highlights
        clearConflictingHighlights();

        // Highlight conflicting cells in the same row, column, and sub-grid
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                //skip its own cell as it cannot conflict with itself
                if (row == sourceCell.row && col == sourceCell.col) {
                    continue;
                }
                // Check the row and column for conflicts
                if (cells[row][col].getText().equals(String.valueOf(numberIn)) && (row == sourceCell.row || col == sourceCell.col)) {
                    // Highlight the conflicting cell
                    cells[row][col].setBackground(Color.YELLOW); // Change this color as needed
                }
                if (isInSubGrid(row, col, sourceCell) && cells[row][col].getText().equals(String.valueOf(numberIn))) {
                    cells[row][col].setBackground(Color.YELLOW);
                }
            }
        }
    }

    private void clearConflictingHighlights() {
        // Reset all cells to default color
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col].paint();  // Reset background color
            }
        }
    }

    private boolean isInSubGrid(int row, int col, Cell sourceCell) {
        int subGridRow = sourceCell.row - (sourceCell.row % 3);
        int subGridCol = sourceCell.col - (sourceCell.col % 3);
        return row >= subGridRow && row < subGridRow + 3 && col >= subGridCol && col < subGridCol + 3;
    }

    private int calculateRemainingCells() {
        return 0;
    }

    // Update the status bar
    public void updateStatusBar() {
        int remainingCells = calculateRemainingCells();
        statusBar.setText("Cells remaining: " + remainingCells);
    }

    private void showHint() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].isEditable() && cells[row][col].getText().isEmpty()) {
                    cells[row][col].setText(String.valueOf(puzzle.numbers[row][col])); // Fill the correct value
                    cells[row][col].status = CellStatus.CORRECT_GUESS;  // Mark it as a correct guess
                    repaint();  // Trigger repaint to show the hint
                    return;
                }
            }
        }
    }
}