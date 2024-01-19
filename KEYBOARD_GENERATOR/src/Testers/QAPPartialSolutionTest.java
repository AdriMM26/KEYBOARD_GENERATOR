package Testers;

import static org.junit.Assert.*;

import Domain.QAPPartialSolution;
import org.junit.Test;

/**
 * Test class for the QAPPartialSolution class.
 */
public class QAPPartialSolutionTest {

    /**
     * Test the constructor and getters of the QAPPartialSolution class.
     */
    @Test
    public void testConstructorAndGetters() {
        // Example test data, modify as needed
        int[][] solution = {
                {0, 1, 2},
                {1, 2, 0},
                {2, 0, 1}
        };

        int cost = 10;

        // Creating a QAPPartialSolution object
        QAPPartialSolution qapPartialSolution = new QAPPartialSolution(solution, cost);

        // Assert that the QAPPartialSolution is not null
        assertNotNull("QAPPartialSolution object should not be null.", qapPartialSolution);

        // Assert that the solution and cost match the provided values
        assertArrayEquals("Solution should match the provided values.", solution, qapPartialSolution.getSolution());
        assertEquals("Cost should match the provided value.", cost, qapPartialSolution.getCost());
    }
}