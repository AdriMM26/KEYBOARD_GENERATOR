package Domain;

/**
 * Represents a Transition Matrix associated with an Alphabet for a specific key.
 */
public class TransitionMatrix {
    /**
     * The key associated with the Transition Matrix.
     */
    private String key;

    /**
     * The 2D array representing the Transition Matrix.
     */
    private int[][] transitionMatrix;

    /**
     * The Alphabet associated with the Transition Matrix.
     */
    private Alphabet alphabet;

    /**
     * Constructs a Transition Matrix without parameters.
     */
    public TransitionMatrix() {}

    /**
     * Constructs a Transition Matrix with the specified key.
     *
     * @param name The key associated with the Transition Matrix.
     * @param alpha Alphabet that will be associated with the Transition Matrix.
     * @param transitions Matrix with all the transitions.
     */
    public TransitionMatrix(String name, Alphabet alpha, int[][] transitions) {
        this.key = name;
        this.alphabet = alpha;
        this.transitionMatrix = transitions;
    }

    /**
     * Gets the 2D array representing the Transition Matrix.
     *
     * @return The Transition Matrix.
     */
    public int[][] getTransitionMatrix() {
        return this.transitionMatrix;
    }

    /**
     * Sets the Transition Matrix with the provided 2D array.
     *
     * @param transMatrix The 2D array representing the new Transition Matrix.
     */
    public void setTransitionMatrix(int[][] transMatrix) {
        this.transitionMatrix = transMatrix;
    }

    /**
     * Gets the Alphabet associated with the Transition Matrix.
     *
     * @return The Alphabet.
     */
    public Alphabet getAlphabet() {
        return this.alphabet;
    }

    /**
     * Sets the Alphabet associated with the Transition Matrix.
     *
     * @param alpha The Alphabet to be associated with the Transition Matrix.
     */
    public void setAlphabet(Alphabet alpha) {
        this.alphabet = alpha;
    }

    /**
     * Gets the key associated with the Transition Matrix.
     *
     * @return The key.
     */
    public String getKey() {
        return this.key;
    }
}
