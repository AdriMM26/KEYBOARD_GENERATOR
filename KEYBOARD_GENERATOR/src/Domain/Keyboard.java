package Domain;

/**
 * The Keyboard class represents a keyboard, with its unique key and a distribution of characters.
 * It provides methods to initialize the keyboard, modify character positions, and retrieve key and distribution information.
 */
public class Keyboard {

    /**
     * The key associated with the keyboard.
     */
    private String key;

    /**
     * The two-dimensional array representing the distribution of characters on the keyboard.
     */
    private char[][] distribution;

    /**
     * Constructor for the Keyboard class without parameters
     */
    public Keyboard() {}

    /**
     * Constructor for the Keyboard class.
     *
     * @param key                 The unique key associated with the keyboard.
     * @param characterPositions  The positions of characters on the keyboard.
     * @param characters          The characters corresponding to the positions on the keyboard.
     */
    public Keyboard(String key, int[][] characterPositions, char[] characters) {
        this.key = key;
        distribution = new char[characterPositions.length][characterPositions[0].length];

        for (int i = 0; i < characterPositions.length; ++i) {
            for (int j = 0; j < characterPositions[0].length; ++j) {
                if (characterPositions[i][j] < 0) {
                    distribution[i][j] = ' ';
                } else {
                    distribution[i][j] = characters[characterPositions[i][j]];
                }
            }
        }
    }

    /**
     * Constructor for the Keyboard class.
     *
     * @param key                 The unique key associated with the keyboard.
     * @param distribution        The character distribution on the keys of the keyboard.
     */
    public Keyboard(String key, char[][] distribution) {
        this.key = key;
        this.distribution = distribution;
    }

    /**
     * Exchanges the positions of 2 characters on the keyboard.
     *
     * @param i1 The row index of the first position.
     * @param j1 The column index of the first position.
     * @param i2 The row index of the second position.
     * @param j2 The column index of the second position.
     * @return 0 if the modification is successful, -1 if the first position is incorrect, -2 if the second position is incorrect.
     */
    public int modify(int i1, int j1, int i2, int j2) {
        int imax = distribution.length;
        int jmax = distribution[0].length;

        if (i1 < 0 || j1 < 0 || i1 >= imax || j1 >= jmax) return -1; // primera posicio no correcta
        else if (i2 < 0 || j2 < 0 || i2 >= imax || j2 >= jmax) return -2; // segona posicio no correcta
        else {
            char aux = distribution[i1][j1];
            distribution[i1][j1] = distribution[i2][j2];
            distribution[i2][j2] = aux;
            return 0;
        }
    }

    /**
     * Gets the key associated with the keyboard.
     *
     * @return The key of the keyboard.
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key associated with the keyboard.
     *
     * @param key The new key for the keyboard.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Gets the two-dimensional array representing the distribution of characters on the keyboard.
     *
     * @return The distribution of characters on the keyboard.
     */
    public char[][] getDistribution() {
        return distribution;
    }
}
