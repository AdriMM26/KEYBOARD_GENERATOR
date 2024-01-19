package Domain;

/**
 * The KeyboardAvaluator class provides a method to evaluate the layout of characters on a Keyboard
 * based on their positions and a TransitionMatrix.
 */
public class KeyboardAvaluator {

    /**
     * Evaluates the keyboard layout based on their character positions and a TransitionMatrix.
     *
     * @param characterPositions The position layout of the characters on the keyboard.
     * @param transitTable The matrix extracted from a TransitionMatrix, representing the number of times between 2 letters that the first letter will be written after the second letter.
     * @return A double value representing the evaluation of the keyboard layout.
     */
    public double avaluateKeyboard(int[][] characterPositions, int[][] transitTable) {
        double sum = 0; // Sum of the total cost between two characters in the transitTable
        int numCharacters = 0; // Sum of the number of characters (with repetitions) appearing in the transitTable

        for (int i = 0; i < characterPositions.length; ++i) {
            for (int j = 0; j < characterPositions[0].length; ++j) {
                for (int i2 = 0; i2 < characterPositions.length; ++i2) {
                    for (int j2 = 0; j2 < characterPositions[0].length; ++j2) {
                        if (characterPositions[i][j] >= 0 && characterPositions[i2][j2] >= 0 &&
                                (i != i2 || j != j2)) {
                            // There is a valid character appearing in the transit table at (i, j) and (i2, j2),
                            // and (i, j) and (i2, j2) are different positions
                            double keyDistances = Math.sqrt(Math.pow(2, Math.abs(i - i2)) + Math.pow(2, Math.abs(j - j2)));
                            int transits = transitTable[characterPositions[i2][j2]][characterPositions[i][j]];
                            numCharacters += transits;
                            sum += keyDistances * transits;
                        }
                    }
                }
            }
        }

        // Return the average cost per character
        return numCharacters > 0 ? sum / numCharacters : 0;
    }
}
