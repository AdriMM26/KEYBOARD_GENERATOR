package Testers;

import Domain.Alphabet;
import Domain.Text;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * The TextTest class contains JUnit test cases for the Text class.
 */
public class TextTest {

    /**
     * The Alphabet instance to be used for testing.
     */
    private Alphabet alphabet;

    /**
     * Set up the test environment by initializing an Alphabet instance before each test.
     */
    @Before
    public void setUp() {
        alphabet = new Alphabet("TestAlphabet", new char[]{'A', 'B', 'C', '*', ' ', '2'});
    }

    /**
     * Test the constructor of the Text class with valid input.
     */
    @Test
    public void testConstructorValidInput() {
        Text text = new Text("TestId", "ABC* 2", alphabet);

        // Check if the TransitionMatrix is initialized
        assertNotNull(text.getTransitionMatrix());
        // Check if the text is set correctly
        assertEquals("ABC* 2", text.getWordList());
    }

    /**
     * Test the getWordList method of the Text class.
     */
    @Test
    public void testGetWordList() {
        Text text = new Text("TestId", "ABC* 2", alphabet);
        assertEquals("ABC* 2", text.getWordList());
    }
}
