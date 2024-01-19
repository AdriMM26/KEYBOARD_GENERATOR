package Testers;

import Domain.Alphabet;
import Domain.WordFrequencyList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * The WordFrequencyListTest class is a JUnit test class for testing the functionality of the WordFrequencyList class.
 * It includes tests for the constructor, getFrequencyList method, and getWordList method.
 */
public class WordFrequencyListTest {

    private Alphabet alphabet;

    /**
     * Set up an Alphabet instance before each test.
     */
    @Before
    public void setUp() {
        alphabet = new Alphabet("TestAlphabet", new char[]{'A', 'B', 'C'});
    }

    /**
     * Test the constructor with valid input.
     */
    @Test
    public void testConstructorValidInput() {
        int[] frequencies = {1, 2, 3};
        String[] words = {"ABC", "AB", "BC"};
        WordFrequencyList wordFrequencyList = new WordFrequencyList("TestId", frequencies, words, alphabet);

        // Check if the TransitionMatrix is initialized
        assertNotNull(wordFrequencyList.getTransitionMatrix());
        // Check if the frequency list is set correctly
        assertArrayEquals(frequencies, wordFrequencyList.getFrequencyList());
        // Check if the word list is set correctly
        assertArrayEquals(words, wordFrequencyList.getWordList());
    }

    /**
     * Test the getFrequencyList method.
     */
    @Test
    public void testGetFrequencyList() {
        int[] frequencies = {1, 2, 3};
        String[] words = {"ABC", "AB", "BC"};
        WordFrequencyList wordFrequencyList = new WordFrequencyList("TestId", frequencies, words, alphabet);
        assertArrayEquals(frequencies, wordFrequencyList.getFrequencyList());
    }

    /**
     * Test the getWordList method.
     */
    @Test
    public void testGetWordList() {
        int[] frequencies = {1, 2, 3};
        String[] words = {"ABC", "AB", "BC"};
        WordFrequencyList wordFrequencyList = new WordFrequencyList("TestId", frequencies, words, alphabet);
        assertArrayEquals(words, wordFrequencyList.getWordList());
    }
}