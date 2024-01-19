package Domain;

import Exceptions.ContainsKey;
import Exceptions.IncorrectType;
import Exceptions.NotContainsKey;
import Model.Pair;

import java.util.Map;
import java.util.HashMap;
import java.util.Vector;

/**
 * The KeyboardController class manages the creation, deletion, modification,
 * consultation and evaluation of keyboards.
 * It utilizes an AlgorithmController for generating keyboard layouts and stores
 * the generated Keyboards in a HashMap.
 */
public class KeyboardController {

    /**
     * The AlgorithmController used for generating keyboard layouts.
     */
    private final AlgorithmController algControl;

    /**
     * A HashMap that stores the generated Keyboards with their corresponding keys.
     */
    private final Map<String, Keyboard> keyboards;

    /**
     * Default constructor for the KeyboardController class.
     * Initializes the HashMap of Keyboards and the AlgorithmController.
     * @param boards Vector with all the initial Keyboards.
     */
    public KeyboardController(Vector<Pair<String, char[][]>> boards) {
        keyboards = new HashMap<>();
        algControl = new AlgorithmController();

        for (Pair<String, char[][]> board : boards) {
            Keyboard newKeyboard = new Keyboard(board.first, board.second);
            keyboards.put(board.first, newKeyboard);
        }
    }

    /**
     * Creates a new keyboard with the specified key, TransitionMatrix, Alphabet,
     * and algorithm.
     *
     * @param key         The key associated with the new Keyboard.
     * @param transMatrix The TransitionMatrix used for generating the Keyboard
     *                    layout.
     * @param alpha        The Alphabet used for generating the Keyboard layout.
     * @param algorithm   The algorithm used for generating the Keyboard layout.
     * @throws IncorrectType   If the algorithm returns an incorrect character positions.
     * @throws ContainsKey   If it does exist a keyboard with that key.
     */
    public void createKeyboard(String key, TransitionMatrix transMatrix, Alphabet alpha, String algorithm) throws ContainsKey, IncorrectType {
        if (keyboards.containsKey(key)) throw new ContainsKey("Keyboard Map contains Key: " + key);
        char[] characters = alpha.getElem();
        int[][] characterPositions = algControl.useAlgorithm(transMatrix.getTransitionMatrix(), algorithm);
        if (characterPositions[0][0] == -1) throw new IncorrectType("Keyboard Characters positions NULL");
        Keyboard newKeyboard = new Keyboard(key, characterPositions, characters);
        keyboards.put(key, newKeyboard);
    }

    /**
     * Retrieves the names of all created keyboards.
     *
     * @return An array of strings containing the names of the created keyboards.
     * Returns null if no keyboards have been created.
     */
    public String[] getKeyboardNames() {
        String[] keyboardList = new String[keyboards.size()];
        int i = 0;
        for (Map.Entry<String, Keyboard> entry : keyboards.entrySet()) {
            keyboardList[i] = entry.getKey();
            ++i;
        }
        return keyboardList;
    }

    /**
     * Retrieves the distribution of characters on the specified keyboard.
     *
     * @param key The key associated with the Keyboard to be consulted.
     * @throws NotContainsKey   If a required key is not present.
     * @return A two-dimensional char array representing the distribution of
     * characters on the Keyboard.
     * Returns null if no Keyboard exists with the specified key.
     */
    public Keyboard getKeyboard(String key) throws NotContainsKey {
        if (!keyboards.containsKey(key)) throw new NotContainsKey("Keyboard Map does NOT contain Key: " + key);
        return this.keyboards.get(key);
    }

    /**
     * Modifies the specified Keyboard by swapping characters at two positions.
     *
     * @param key The key associated with the keyboard to be modified.
     * @param i1  The row index of the first character position.
     * @param j1  The column index of the first character position.
     * @param i2  The row index of the second character position.
     * @param j2  The column index of the second character position.
     * @throws NotContainsKey   If a required key is not present.
     */
    public void modifyKeyboard(String key, int i1, int j1, int i2, int j2) throws NotContainsKey {
        if (!keyboards.containsKey(key)) throw new NotContainsKey("Keyboard Map does NOT contain Key: " + key);
        keyboards.get(key).modify(i1, j1, i2, j2);
    }

    /**
     * Evaluates the specified Keyboard based on a TransitionMatrix.
     *
     * @param board    The Keyboard to be evaluated.
     * @param transits The TransitionMatrix used for evaluation.
     * @return A double value representing the evaluation of the keyboard.
     * Returns -1.0 if no keyboard exists with the specified key.
     */
    public double evaluateKeyboard(Keyboard board, TransitionMatrix transits) {
        char[][] boardDistribution = board.getDistribution();
        int[][] charPositions = new int[boardDistribution.length][boardDistribution[0].length];
        char[] alphabet = transits.getAlphabet().getElem();

        for (int i = 0; i < boardDistribution.length; ++i) {
            for (int j = 0; j < boardDistribution[0].length; ++j) {
                charPositions[i][j] = getCharPosIntoAlphabet2(boardDistribution[i][j], alphabet);
            }
        }

        KeyboardAvaluator boardEvaluator = new KeyboardAvaluator();
        return boardEvaluator.avaluateKeyboard(charPositions, transits.getTransitionMatrix());
    }

    /**
     * Gets the Keyboard Distribution from the specified key.
     *
     * @param key The key associated with the keyboard to be deleted.
     * @throws NotContainsKey   If a required key is not present.
     * @return Returns the distribution of the Keyboard made by a Matrix of Char.
     */
    public char[][] getKeyboardDistribution(String key) throws NotContainsKey {
        if (!keyboards.containsKey(key)) throw new NotContainsKey("Keyboard Map does NOT contain Key: " + key);
        return keyboards.get(key).getDistribution();
    }

    /**
     * Deletes the Keyboard with the specified key.
     *
     * @param key The key associated with the keyboard to be deleted.
     * @throws NotContainsKey   If a required key is not present.
     */
    public void deleteKeyboard(String key) throws NotContainsKey {
        if (!keyboards.containsKey(key)) throw new NotContainsKey("Keyboard Map does NOT contain Key: " + key);
        keyboards.remove(key);
    }

    /**
     * Gets the position of a character in the given character list.
     *
     * @param character     The character to find the position of.
     * @param characterList The list of characters to search within.
     * @return The position of the character in the list. Returns -1 if not found.
     */
    private int getCharPosIntoAlphabet2(char character, char[] characterList) {
        for (int i = 0; i < characterList.length; ++i) {
            if (characterList[i] == character) {
                return i;
            }
        }
        return -1;
    }
}
