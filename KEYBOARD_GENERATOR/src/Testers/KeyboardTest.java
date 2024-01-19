package Testers;

import Domain.Keyboard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * The KeyboardTest class contains JUnit test cases for the Keyboard class.
 */
public class KeyboardTest {

    /**
     * The Keyboard instance to be used for testing.
     */
    private Keyboard keyboard;

    /**
     * Set up the test environment by initializing a Keyboard instance before each test.
     */
    @Before
    public void setUp() {
        int[][] characterPositions = {{0, 1, 2}, {3, 4, 5}};
        char[] characters = {'A', 'B', 'C', 'D', 'E', 'F'};
        keyboard = new Keyboard("TestKey", characterPositions, characters);
    }

    /**
     * Test the constructor and getter methods of the Keyboard class.
     */
    @Test
    public void testConstructorAndGetter() {
        assertEquals("TestKey", keyboard.getKey());
        assertArrayEquals(new char[][]{{'A', 'B', 'C'}, {'D', 'E', 'F'}}, keyboard.getDistribution());
    }

    /**
     * Test the modification of character positions with valid positions.
     */
    @Test
    public void testModifyValidPositions() {
        assertEquals(0, keyboard.modify(0, 0, 1, 1));
        assertArrayEquals(new char[][]{{'E', 'B', 'C'}, {'D', 'A', 'F'}}, keyboard.getDistribution());
    }

    /**
     * Test the modification of character positions with an invalid first position.
     */
    @Test
    public void testModifyInvalidFirstPosition() {
        assertEquals(-1, keyboard.modify(-1, 0, 1, 1));
        assertArrayEquals(new char[][]{{'A', 'B', 'C'}, {'D', 'E', 'F'}}, keyboard.getDistribution());
    }

    /**
     * Test the modification of character positions with an invalid second position.
     */
    @Test
    public void testModifyInvalidSecondPosition() {
        assertEquals(-2, keyboard.modify(0, 0, 1, 3));
        assertArrayEquals(new char[][]{{'A', 'B', 'C'}, {'D', 'E', 'F'}}, keyboard.getDistribution());
    }

    /**
     * Test the set and get methods for the key attribute of the Keyboard class.
     */
    @Test
    public void testSetAndGetKey() {
        keyboard.setKey("NewTestKey");
        assertEquals("NewTestKey", keyboard.getKey());
    }
}
