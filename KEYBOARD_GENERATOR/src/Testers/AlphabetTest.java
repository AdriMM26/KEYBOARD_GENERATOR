package Testers;

import Domain.Alphabet;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The AlphabetTest class contains JUnit test cases for the Alphabet class.
 */
public class AlphabetTest {

    /**
     * The Alphabet instance to be used for testing.
     */
    private Alphabet alphabet;

    /**
     * Set up the test environment by initializing an Alphabet instance before each test.
     */
    @Before
    public void setUp() {
        alphabet = new Alphabet("TestKey", new char[]{'A', 'B', 'C', '*', '4', '2'});
    }

    /**
     * Test the getKey method of the Alphabet class.
     */
    @Test
    public void testGetKey() {
        assertEquals("TestKey", alphabet.getKey());
    }

    /**
     * Test the getElem method of the Alphabet class.
     */
    @Test
    public void testGetElem() {
        char[] expectedElem = {'A', 'B', '2','C', '4', '*'};
        assertArrayEquals(expectedElem, alphabet.getElem());
    }

    /**
     * Test the setKey method of the Alphabet class.
     */
    @Test
    public void testSetKey() {
        alphabet.setKey("NewKey");
        assertEquals("NewKey", alphabet.getKey());
    }

    /**
     * Test the setElem method of the Alphabet class.
     */
    @Test
    public void testSetElem() {
        alphabet.setElem(new char[]{'D', 'E', 'F'});
        char[] expectedElem = {'D', 'E', 'F'};
        assertArrayEquals(expectedElem, alphabet.getElem());
    }

    /**
     * Test the containString method of the Alphabet class.
     */
    @Test
    public void testContainString() {
        assertTrue(alphabet.containString("ABC*2"));
        assertFalse(alphabet.containString("DEF"));
    }

    /**
     * Test the addElem method of the Alphabet class.
     */
    @Test
    public void testAddElem() {
        alphabet.addElem('D');
        assertTrue(alphabet.containString("D"));
    }

    /**
     * Test the delElem method of the Alphabet class.
     */
    @Test
    public void testDelElem() {
        alphabet.delElem('B');
        assertFalse(alphabet.containString("B"));
    }
}
