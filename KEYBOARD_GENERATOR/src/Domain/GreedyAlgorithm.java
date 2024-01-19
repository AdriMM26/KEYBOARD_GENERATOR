package Domain;

import java.util.Arrays;
import java.util.Comparator;

/**
 * The GreedyAlgorithm class implements a greedy algorithm to determine the positions of the characters on a keyboard
 * based on the given transit table.
 */
public class GreedyAlgorithm {

    /**
     * Gives the positions of characters on a Keyboard using a greedy algorithm.
     *
     * @param transitTable The matrix extracted from a TransitionMatrix, representing the number of times between 2 letters
     *                     that the first letter will be written after the second letter.
     * @return A two-dimensional array representing the positions of characters on the Keyboard.
     */
    public int[][] getPositions(int[][] transitTable) {
        int[][] charTransits = getCharTransits(transitTable); // Obtain character transitions
        Arrays.sort(charTransits, Comparator.comparingInt(p -> -p[1])); // Sort characters by descending transit

        int c = (int) Math.sqrt(transitTable.length); // Columns in the keyboard distribution
        int f = (int) Math.ceil(transitTable.length / (double) c); // Rows in the keyboard distribution

        return getCharacterPositions(f, c, charTransits);
    }

    /**
     * Gives the characters to be placed on the Keyboard, along with the times that they appear in the TransitionMatrix.
     *
     * @param transitTable The matrix extracted from a TransitionMatrix, representing the number of times between 2 letters
     *                     that the first letter will be written after the second letter.
     * @return A two-dimensional array containing the character indices, along with the summation of their row in the
     * TransitionMatrix.
     */
    private int[][] getCharTransits(int[][] transitTable) {
        int[][] charTransits = new int[transitTable.length][2];

        for (int i = 0; i < transitTable.length; ++i) {
            int sum = 0;
            for (int j = 0; j < transitTable[i].length; ++j) {
                sum += transitTable[i][j];
            }
            charTransits[i][0] = i;
            charTransits[i][1] = sum;
        }

        return charTransits;
    }

    /**
     * Assigns the positions of the characters to be placed on the keyboard, based on the numbers of rows and columns on
     * the Keyboard distribution, and their appearance on the TransitionMatrix.
     *
     * @param f            The number of rows in the keyboard distribution.
     * @param c            The number of columns in the keyboard distribution.
     * @param charTransits The ordered array by transits of the characters to be placed on the Keyboard.
     * @return A two-dimensional array representing the positions of characters on the Keyboard.
     */
    private int[][] getCharacterPositions(int f, int c, int[][] charTransits) {
        int[][] characterPositions = new int[f][c]; // Distribution matrix with necessary rows and columns
        int i, j, dirx, diry, iter = 0; // The iterator for the CharTransits initially set to 0

        // Choose initial position/direction of character assignment
        if (c % 2 == 0) {
            i = c / 2 - 1;
            j = i + 1;
            dirx = -1;
            diry = 0;
        } else {
            i = j = (int) Math.ceil(c / 2.0d) - 1;
            dirx = 1;
            diry = 0;
        }

        boolean[][] visited = new boolean[f][c]; // Initialization of the matrix of visited positions in the keyboard distribution

        if (c == 0) return characterPositions; // Special case when the matrix is empty
        else if (c == 1) {
            characterPositions[0][0] = charTransits[iter][0];
            ++iter;
        } else {
            boolean cont = true; // Boolean representing whether characters can continue to be assigned in a spiral pattern
            boolean posinicial = true;

            while (cont) {
                characterPositions[i][j] = charTransits[iter][0];
                ++iter;
                visited[i][j] = true;

                if (posinicial == false) {
                    if (i == c - 1 && i == j) cont = false;
                    else {
                        if (dirx == 1) {
                            if (visited[i - 1][j] == false) {
                                dirx = 0;
                                diry = -1;
                            }
                        } else if (dirx == -1) {
                            if (visited[i + 1][j] == false) {
                                dirx = 0;
                                diry = 1;
                            }
                        } else if (diry == 1) {
                            if (visited[i][j + 1] == false) {
                                dirx = 1;
                                diry = 0;
                            }
                        } else {
                            if (visited[i][j - 1] == false) {
                                dirx = -1;
                                diry = 0;
                            }
                        }
                    }
                }
                i += diry;
                j += dirx;
                posinicial = false;
            }
        }

        if (f > c) {
            i = c;
            j = c - 1;
            while (j >= 0 && iter < charTransits.length) {
                characterPositions[i][j] = charTransits[iter][0];
                --j;
                ++iter;
            }
            while (j >= 0) {
                characterPositions[i][j] = -1;
                --j;
            }
        }
        if (f == c + 2) {
            i = c + 1;
            j = 0;
            while (j < c && iter < charTransits.length) {
                characterPositions[i][j] = charTransits[iter][0];
                ++j;
                ++iter;
            }
            while (j < c) {
                characterPositions[i][j] = -1;
                ++j;
            }
        }

        return characterPositions;
    }

    /**
     * Checks if the given matrix is a valid square matrix.
     *
     * @param matrix The matrix to check.
     * @return True if the matrix is a valid square matrix, false otherwise.
     */
    private boolean isValidSquareMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix.length != matrix[0].length) {
            return false;
        }
        return true;
    }
}
