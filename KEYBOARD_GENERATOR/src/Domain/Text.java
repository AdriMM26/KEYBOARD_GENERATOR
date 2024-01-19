package Domain;

/**
 * The Text class represents a form of TransitionMatrix generated from an input
 * text
 * by associating transitions between characters with their frequencies.
 */
public class Text extends TransitionMatrix {

    /**
     * Ths String representing the text.
     */
    private final String text;

    /**
     * Constructor for Text.
     *
     * @param id       The unique identifier for the Text.
     * @param textIn   The input text to be analyzed.
     * @param alphabet The Alphabet associated with the Text.
     *                 Pre-conditions:
     *                 - 'id' is a unique identifier for the Text.
     *                 - 'textIn' is not null.
     *                 - 'alphabet' is not null.
     *                 Post-conditions:
     *                 - If the 'textIn' does not match the elements of the
     *                 'alphabet', an error message is printed.
     *                 - Otherwise, a Text is created with the specified parameters,
     *                 and the associated TransitionMatrix is initialized.
     */
    public Text(String id, String textIn, Alphabet alphabet) {
        super(id, alphabet, null); // creation of TransitionMatrix with key = id
        String alphabetElem = new String(alphabet.getElem());
        this.text = textIn;
        int[][] transMatrix = new int[alphabetElem.length()][alphabetElem.length()];
        for (int i = 0; i < this.text.length() - 1; ++i) {
            if(this.text.toCharArray()[i] != '\n' && this.text.toCharArray()[i+1] != '\n') {
                char A = this.text.toCharArray()[i];
                int posiA = alphabetElem.indexOf(Character.toUpperCase(A));
                char B = this.text.toCharArray()[i + 1];
                int posiB = alphabetElem.indexOf(Character.toUpperCase(B));
                ++transMatrix[posiA][posiB];
            }
        }
        setTransitionMatrix(transMatrix);
    }

    /**
     * Getter the text
     *
     * @return An array of strings representing words.
     */
    public String getWordList() {
        return this.text;
    }
}
