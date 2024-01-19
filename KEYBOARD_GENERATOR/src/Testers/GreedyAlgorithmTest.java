package Testers;

import Domain.GreedyAlgorithm;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for the GreedyAlgorithm class.
 */
public class GreedyAlgorithmTest {

    /**
     * Test the {@code getPositions} method of the GreedyAlgorithm class.
     */
    @Test
    public void testGetPositions() {
        // Creating a GreedyAlgorithm object
        GreedyAlgorithm greedyAlgorithm = new GreedyAlgorithm();

        // Test case with a 3x3 transit table
        int[][] transitTable = {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8}
        };

        // Calling the getPositions method and getting the result
        int[][] result = greedyAlgorithm.getPositions(transitTable);

        // Check if the result is not null
        assertNotNull("Result should not be null.", result);

        // Check if the result has the expected number of elements
        assertEquals("Result should have the expected number of rows.", 3, result.length);

        // Check if the result contains unique character indices
        assertTrue("Result should have unique character indices.", areUnique(result));

        // Check if the result is a valid distribution (characters are in non-decreasing order)
        assertTrue("Result should be a valid distribution.", isValidDistribution(result));
    }

    /**
     * Check if all elements in the 2D array are unique.
     *
     * @param array The 2D array to check.
     * @return True if all elements are unique, false otherwise.
     */
    private boolean areUnique(int[][] array) {
        int size = array.length * array[0].length;
        boolean[] visited = new boolean[size];
        for (int i = 0; i < array.length; ++i) {
            for (int j = 0; j < array[0].length; ++j) {
                int value = array[i][j];
                if (value < 0 || value >= size || visited[value]) {
                    return false; // Not unique or out of bounds
                }
                visited[value] = true;
            }
        }
        return true;
    }

    /**
     * Check if each row in the 2D array represents a valid distribution (non-decreasing order).
     *
     * @param array The 2D array to check.
     * @return True if each row is a valid distribution, false otherwise.
     */
    private boolean isValidDistribution(int[][] array) {
        for (int i = 0; i < array.length; ++i) {
            for (int j = 0; j < array[0].length - 1; ++j) {
                if (array[i][j] > array[i][j + 1]) {
                    return false; // Invalid distribution
                }
            }
        }
        return true;
    }
}
