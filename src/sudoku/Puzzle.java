/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #1
 * 1 - 5026221156 - Muhammad Ali Husain Ridwan
 * 2 - 5026221157 - Muhammad Afaf
 * 3 - 5026221162 - Raphael Andhika Pratama
 */
package sudoku;
/**
 * The Sudoku number puzzle to be solved
 */
public class Puzzle {
    // All variables have package access
    // The numbers on the puzzle
    int[][] numbers = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    // The clues - isGiven (no need to guess) or need to guess
    boolean[][] isGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];

    // Constructor
    public Puzzle() {
        super();
    }

    // Generate a new puzzle given the number of cells to be guessed, which can be used
    //  to control the difficulty level.
    // This method shall set (or update) the arrays numbers and isGiven
    public void newPuzzle(int level, Difficulty difficulty) {
        // I hardcode a puzzle here for illustration and testing.
        int[][][] hardcodedNumbers = {
            {//level 1
                {5, 3, 4, 6, 7, 8, 9, 1, 2},
                {6, 7, 2, 1, 9, 5, 3, 4, 8},
                {1, 9, 8, 3, 4, 2, 5, 6, 7},
                {8, 5, 9, 7, 6, 1, 4, 2, 3},
                {4, 2, 6, 8, 5, 3, 7, 9, 1},
                {7, 1, 3, 9, 2, 4, 8, 5, 6},
                {9, 6, 1, 5, 3, 7, 2, 8, 4},
                {2, 8, 7, 4, 1, 9, 6, 3, 5},
                {3, 4, 5, 2, 8, 6, 1, 7, 9}
            }, { //level 2
                {8, 2, 7, 1, 5, 4, 3, 9, 6},
                {9, 6, 5, 3, 2, 7, 1, 4, 8},
                {3, 4, 1, 6, 8, 9, 7, 5, 2},
                {5, 9, 3, 4, 6, 8, 2, 7, 1},
                {4, 7, 2, 5, 1, 3, 6, 8, 9},
                {6, 1, 8, 9, 7, 2, 4, 3, 5},
                {7, 8, 6, 2, 3, 5, 9, 1, 4},
                {1, 5, 4, 7, 9, 6, 8, 2, 3},
                {2, 3, 9, 8, 4, 1, 5, 6, 7}
            }, {// level 3
                {2, 7, 6, 9, 5, 1, 4, 3, 8},
                {3, 9, 8, 4, 6, 2, 1, 7, 5},
                {5, 1, 4, 8, 3, 7, 9, 2, 6},
                {9, 6, 1, 7, 4, 5, 3, 8, 2},
                {8, 4, 3, 1, 2, 9, 5, 6, 7},
                {7, 5, 2, 3, 8, 6, 6, 9, 4},
                {4, 3, 9, 6, 7, 8, 2, 5, 1},
                {6, 8, 5, 2, 1, 4, 7, 3, 9},
                {1, 2, 7, 5, 9, 3, 8, 4, 6}
            },  {// level 4
                {1, 5, 9, 6, 7, 3, 2, 8, 4},
                {7, 8, 3, 5, 2, 4, 6, 9, 1},
                {4, 2, 6, 1, 9, 8, 7, 5, 3},
                {3, 1, 8, 9, 6, 5, 4, 2, 7},
                {9, 7, 4, 8, 3, 2, 5, 1, 6},
                {6, 5, 2, 7, 4, 1, 8, 3, 9},
                {5, 3, 7, 2, 8, 9, 1, 4, 6},
                {2, 6, 1, 4, 5, 7, 9, 3, 8},
                {8, 9, 5, 3, 1, 6, 4, 7, 2}
            }, {// level 5
                {5, 3, 8, 6, 7, 9, 4, 2, 1},
                {6, 2, 1, 3, 9, 5, 7, 8, 4},
                {4, 9, 7, 8, 2, 1, 5, 6, 3},
                {8, 5, 2, 7, 6, 3, 9, 4, 1},
                {7, 1, 3, 9, 4, 2, 8, 5, 6},
                {9, 6, 4, 5, 1, 8, 3, 7, 2},
                {3, 4, 9, 2, 5, 7, 1, 3, 8},
                {2, 8, 6, 4, 1, 9, 7, 3, 5},
                {1, 7, 5, 6, 8, 3, 2, 4, 9}
            }
        };


        // Copy from hardcodedNumbers into the array "numbers"
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if(level == 1) {
                    numbers[row][col] = hardcodedNumbers[0][row][col];
                } else if(level == 2) {
                    numbers[row][col] = hardcodedNumbers[1][row][col];
                } else if(level == 3) {
                    numbers[row][col] = hardcodedNumbers[2][row][col];
                } else if(level == 4) {
                    numbers[row][col] = hardcodedNumbers[3][row][col];
                } else if(level == 5) {
                    numbers[row][col] = hardcodedNumbers[4][row][col];
                }
            }
        }

        // Need to use input parameter cellsToGuess!
        // Hardcoded for testing, only 2 cells of "8" is NOT GIVEN
        boolean[][][] hardcodedIsGiven = {
            {//easy
                {true, true, true, true, true, false, true, true, true},
                {true, true, true, true, true, true, true, true, false},
                {true, true, true, true, true, true, true, true, true},
                {true, true, true, true, true, true, true, true, true},
                {true, true, true, true, true, true, true, true, true},
                {true, true, true, true, true, true, true, true, true},
                {true, true, true, true, true, true, true, true, true},
                {true, true, true, true, true, true, true, true, true},
                {true, true, true, true, true, true, true, true, true}
            },{// intermediate
                {true, true, true, true, false, false, true, true, false},
                {true, true, true, true, true, true, true, false, true},
                {true, false, true, true, true, true, false, true, true},
                {true, true, true, true, false, false, true, true, false},
                {true, true, true, true, true, true, true, true, true},
                {true, false, true, true, true, true, true, true, true},
                {true, true, false, false, true, true, true, true, true},
                {true, true, true, true, true, true, true, true, true},
                {true, true, true, false, true, true, true, true, true}
            },{//hard
                {true, false, false, false, false, false, false, false, true},
                {false, true, false, true, false, false, false, true, false},
                {false, false, true, false, true, false, true, false, false},
                {false, true, false, false, false, true, false, false, false},
                {false, false, false, true, true, true, false, false, false},
                {false, false, false, false, false, false, true, false, true},
                {false, false, true, false, true, false, false, false, false},
                {false, true, false, false, false, false, false, true, false},
                {true, false, false, false, false, false, false, false, true}
            }

        };
        // Copy from hardcodedIsGiven into array "isGiven"
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (difficulty == Difficulty.Easy) {
                    isGiven[row][col] = hardcodedIsGiven[0][row][col];
                } else if (difficulty == Difficulty.Intermediate) {
                    isGiven[row][col] = hardcodedIsGiven[1][row][col];
                } else {
                    isGiven[row][col] = hardcodedIsGiven[2][row][col];
                }
            }
        }
    }

    //(For advanced students) use singleton design pattern for this class
}