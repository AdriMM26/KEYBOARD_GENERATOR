package Domain;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents an alphabet containing a set of unique characters identified by a
 * key.
 */
public class Alphabet {

    /**
     * The key associated with the alphabet.
     */
    private String key;

    /**
     * The set of unique characters in the alphabet.
     */
    private Set<Character> elem;

    /**
     * Constructs an empty alphabet.
     */
    public Alphabet() {
    }

    /**
     * Constructs an alphabet with the specified key.
     *
     * @param key The key associated with the alphabet.
     */
    public Alphabet(String key) {
        this.key = key;
    }

    /**
     * Constructs an alphabet with the specified key and elements in UpperCase.
     *
     * @param key  The key associated with the alphabet.
     * @param elem An array of characters representing the elements of the alphabet.
     */
    public Alphabet(String key, char[] elem) {
        this.key = key;
        this.elem = new HashSet<>();
        for (char c : elem) {
            // this.elem.add(c);
            this.elem.add(Character.toUpperCase(c));
        }
    }

    /**
     * Gets the key associated with the alphabet.
     *
     * @return The key.
     */
    public String getKey() {
        return key;
    }

    /**
     * Gets an array of characters representing the elements of the alphabet.
     *
     * @return The array of characters.
     */
    public char[] getElem() {
        char[] charArray = new char[elem.size()];
        int index = 0;

        // Iterate through the set and add each character to the array
        for (char ch : elem) {
            charArray[index++] = ch;
        }
        return charArray;
    }

    /**
     * Sets the key associated with the alphabet.
     *
     * @param key The key to be set.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Sets the elements of the alphabet using the provided array of characters.
     *
     * @param elem An array of characters representing the new elements.
     */
    public void setElem(char[] elem) {
        this.elem = new HashSet<>();
        for (char c : elem) {
            this.elem.add(Character.toUpperCase(c));
        }
    }

    /**
     * Checks if the alphabet contains a given string.
     *
     * @param s The string to check.
     * @return {@code true} if all characters in the string are present in the
     *         alphabet, {@code false} otherwise.
     */
    public boolean containString(String s) {
        int numCorrect = 0;
        for (char c : s.toUpperCase().toCharArray()) {
            if (elem.contains(c) || c == '\n')
                ++numCorrect;
        }
        return (numCorrect == s.length());
    }

    /**
     * Adds a new element to the alphabet.
     *
     * @param c The character to add.
     */
    public void addElem(char c) {
        elem.add(Character.toUpperCase(c));
    }

    /**
     * Removes an element from the alphabet.
     *
     * @param c The character to remove.
     */
    public void delElem(char c) {
        elem.remove(Character.toUpperCase(c));
    }
}
