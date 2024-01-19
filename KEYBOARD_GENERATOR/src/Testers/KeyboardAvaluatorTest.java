package Testers;

import static org.junit.Assert.*;

import Domain.KeyboardAvaluator;
import org.junit.Test;

/**
 * Test class for the KeyboardAvaluator class.
 */
public class KeyboardAvaluatorTest {

    /**
     * Test the avaluateKeyboard method of the KeyboardAvaluator class.
     * Example test data:
     * - characterPositions: 2D array representing the positions of characters on the keyboard.
     * - transitTable: 2D array representing the transit costs between characters.
     */
    @Test
    public void testAvaluateKeyboard() {
        // Example test data, modify as needed
        int[][] characterPositions = {
                {0, 1, 2},
                {3, 4, 5},
                {-1, -1, -1}
        };

        int[][] transitTable = {
                {0, 1, 2, 3, 4, 5},
                {1, 0, 3, 2, 5, 4},
                {2, 3, 0, 5, 4, 1},
                {3, 2, 5, 0, 1, 4},
                {4, 5, 4, 1, 0, 3},
                {5, 4, 1, 4, 3, 0}
        };

        // Creating a KeyboardEvaluator object
        KeyboardAvaluator keyboardAvaluator = new KeyboardAvaluator();

        // Calling the evaluateKeyboard method and getting the result
        double evaluation = keyboardAvaluator.avaluateKeyboard(characterPositions, transitTable);

        // Add more specific assertions based on your requirements
        assertTrue("Evaluation should be greater than or equal to 0.", evaluation >= 0);
    }
}