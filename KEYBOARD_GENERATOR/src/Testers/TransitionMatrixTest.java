package Testers;

import Domain.Alphabet;
import Domain.TransitionMatrix;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The TransitionMatrixTest class contains JUnit test cases for the TransitionMatrix class.
 */
public class TransitionMatrixTest {

    /**
     * The Alphabet instance to be used for testing.
     */
    private Alphabet alphabet;

    /**
     * Set up the test environment by initializing an Alphabet instance before each test.
     */
    @Before
    public void setUp() {
        alphabet = new Alphabet("TestAlphabet", new char[]{'A', 'B', 'C', '*', '2'});
    }

    /**
     * Test the constructor and getter methods of the TransitionMatrix class.
     */
    @Test
    public void testConstructorAndGetter() {
        TransitionMatrix transitionMatrix = new TransitionMatrix("TestId", null, null);
        assertEquals("TestId", transitionMatrix.getKey());
        assertNull(transitionMatrix.getTransitionMatrix());
        assertNull(transitionMatrix.getAlphabet());
    }

    /**
     * Test the set and get methods for the TransitionMatrix array.
     */
    @Test
    public void testSetAndGetTransitionMatrix() {
        TransitionMatrix transitionMatrix = new TransitionMatrix("TestId", null, null);
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        transitionMatrix.setTransitionMatrix(matrix);
        assertArrayEquals(matrix, transitionMatrix.getTransitionMatrix());
    }

    /**
     * Test the set and get methods for the Alphabet instance.
     */
    @Test
    public void testSetAndGetAlphabet() {
        TransitionMatrix transitionMatrix = new TransitionMatrix("TestId", null, null);
        transitionMatrix.setAlphabet(alphabet);
        assertEquals(alphabet, transitionMatrix.getAlphabet());
    }
}
