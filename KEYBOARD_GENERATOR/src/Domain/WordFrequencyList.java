package Domain;

/**
 * The WordFrequencyList class represents a specialized form of TransitionMatrix
 * that associates word frequencies with transitions between characters in an
 * Alphabet.
 */
public class WordFrequencyList extends TransitionMatrix {

    private final int[] frequencyList;
    private final String[] wordList;

    /**
     * Constructor for WordFrequencyList.
     *
     * @param id         The unique identifier for the WordFrequencyList.
     * @param frequencies An array of integers representing word frequencies.
     * @param words      An array of strings representing words.
     * @param alphabet   The Alphabet associated with the WordFrequencyList.
     *                   Pre-conditions:
     *                   - 'id' is a unique identifier for the WordFrequencyList.
     *                   - 'frequencies' and 'words' arrays are not null.
     *                   - 'frequencies' and 'words' arrays have the same length.
     *                   - 'alphabet' is not null.
     *                   Post-conditions:
     *                   - If the 'words' list does not match the elements of the
     *                   'alphabet', an error message is printed.
     *                   - Otherwise, a WordFrequencyList is created with the
     *                   specified parameters, and the associated TransitionMatrix
     *                   is initialized.
     */
    public WordFrequencyList(String id, int[] frequencies, String[] words, Alphabet alphabet) {
        super(id, alphabet, null); // creation of TransitionMatrix with key = id
        this.frequencyList = frequencies;
        this.wordList = words;
        String alphabetElem = new String(alphabet.getElem());
        int[][] transitionMatrix = new int[alphabetElem.length()][alphabetElem.length()];
        for (int i = 0; i < words.length; ++i) {
            String word = words[i];
            for (int j = 0; j < word.length() - 1; ++j) {
                char A = word.toCharArray()[j];
                int posiA = alphabetElem.indexOf(Character.toUpperCase(A));
                char B = word.toCharArray()[j + 1];
                int posiB = alphabetElem.indexOf(Character.toUpperCase(B));
                transitionMatrix[posiA][posiB] += frequencies[i];
            }
        }
        setTransitionMatrix(transitionMatrix);
    }

    /**
     * Getter the List of Frequencies
     *
     * @return An array of integers representing word frequencies.
     */
    public int[] getFrequencyList() {
        return this.frequencyList;
    }

    /**
     * Getter the List of Words
     *
     * @return An array of strings representing words.
     */
    public String[] getWordList() {
        return this.wordList;
    }
}