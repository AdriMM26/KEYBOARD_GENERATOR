package Domain;

import Exceptions.*;
import Persistence.PersistenceController;

import java.io.IOException;

/**
 * The DomainController class acts as a controller for the domain layer,
 * managing interactions between input handling,
 * keyboard operations, and other domain-related functionalities.
 */
public class DomainController {

    /**
     * The InputController instance responsible for handling input-related
     * operations.
     */
    private final InputController inputController;

    /**
     * The KeyboardController instance responsible for managing keyboard operations.
     */
    private final KeyboardController keyboardController;

    /**
     * The PersistenceController instance responsible for managing the data layer operations.
     */
    private final PersistenceController persistenceController;

    /**
     * Default constructor for the DomainController class.
     * Initializes instances of InputController, KeyboardController, and PersistenceController.
     *
     * @throws IOException If an I/O error occurs while creating controllers.
     * @throws NotContainsKey If a required key is not present in the data structures.
     */
    public DomainController() throws IOException, NotContainsKey {
        persistenceController = new PersistenceController();
        // We initialize inputController by loading Data into the HashMap
        inputController = new InputController(persistenceController.getAllAlphabets(), persistenceController.getAllTMs());
        keyboardController = new KeyboardController(persistenceController.getAllKeyboards());
    }

    // --------------------------------------------------
    // -------------------- ALPHABET---------------------
    // --------------------------------------------------

    /**
     * Creates an alphabet with the specified key and elements.
     *
     * @param key  The key associated with the alphabet.
     * @param elem The elements of the alphabet.
     * @throws ContainsKey If the specified key for the Alphabet is used.
     * @throws IOException If an error creating the Alphabet file.
     * @throws IncorrectType If the input is not correct.
     */
    public void createAlphabet(String key, char[] elem) throws ContainsKey, IOException, IncorrectType {
        this.inputController.createAlphabet(key, elem);
        this.persistenceController.createAlphabet(key, elem);
    }

    /**
     * Deletes an alphabet with the specified key.
     *
     * @param alphaKey The key associated with the alphabet to be deleted.
     *         Pre-conditions:
     *         - 'alphaKey' is not null.
     *         - Exists an Alphabet with 'alphaKey'
     *         Post-conditions:
     *         - If the alphabet with the specified key exists, it is deleted.
     * @throws NotContainsKey If the specified key for the Alphabet is not found.
     * @throws IOException If an error getting the Transition file.
     * @throws FileNotDeleted If an error deleting the Alphabet file.
     * @throws AlphabetUsed If the Alphabet is Used for some Transition Matrix.
     */
    public void deleteAlphabet(String alphaKey) throws NotContainsKey, IOException, AlphabetUsed, FileNotDeleted {
        if (inputController.checkAlphabetUsed(alphaKey)) throw new AlphabetUsed("Alphabet " +alphaKey+ " used in some TransitionMatrix");
        this.inputController.deleteAlphabet(alphaKey);
        this.persistenceController.deleteAlphabet(alphaKey);
    }

    /**
     * Retrieves an Alphabet with the specified key from the associated InputController.
     *
     * @param alphaKey The key associated with the Alphabet.
     * @return The Alphabet corresponding to the key, or {@code null} if not found.
     * @throws NotContainsKey If the specified key for the Alphabet is not found.
     */
    public Alphabet getAlphabet(String alphaKey) throws NotContainsKey {
        return inputController.getAlphabet(alphaKey);
    }

    /**
     * Retrieves an array of Alphabets from the associated InputController.
     *
     * @return An array of Alphabets, where each element represents an Alphabet in the InputController.
     */
    public Alphabet[] listAlphabets() {
        return inputController.listAlphabets();
    }

    /**
     * Adds an element to the Alphabet with the specified key in the associated InputController.
     *
     * @param alphaKey The key associated with the Alphabet.
     * @param elem    The element to add.
     * @throws NotContainsKey If the specified key for the Alphabet is not found.
     * @throws IOException If an error getting the Transition file.
     * @throws IncorrectAlphabetType If the specified key has an incorrect type.
     */
    public void addElemAlphabet(String alphaKey, char elem) throws IncorrectAlphabetType, NotContainsKey, IOException {
        inputController.addElemAlphabet(alphaKey, elem);
        Alphabet alpha = inputController.getAlphabet(alphaKey);
        persistenceController.modifyAlphabet(alpha.getKey(), alpha.getElem());
    }

    /**
     * Deletes an element from the Alphabet with the specified key in the associated InputController.
     *
     * @param alphaKey The key associated with the Alphabet.
     * @param elem    The element to delete.
     * @throws NotContainsKey If the specified key for the Alphabet is not found.
     * @throws IOException If an error getting the Transition file.
     * @throws IncorrectAlphabetType If the specified key has an incorrect type.
     */
    public void delElemAlphabet(String alphaKey, char elem) throws IncorrectAlphabetType, NotContainsKey, IOException {
        inputController.delElemAlphabet(alphaKey, elem);
        Alphabet alpha = inputController.getAlphabet(alphaKey);
        persistenceController.modifyAlphabet(alpha.getKey(), alpha.getElem());
    }

    /**
     * Retrieves an array of characters from a file specified by the given path using the persistence controller.
     *
     * @param path The path of the file to read.
     * @return An array of characters extracted from the file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public char[] getCharactersFromFile(String path) throws IOException {
        return persistenceController.getCharactersFile(path);
    }


    // ------------------------------------------------------------
    // -------------------- TRANSITION MATRIX ---------------------
    // ------------------------------------------------------------

    /**
     * Creates a matrix text with the specified alphabet key, text key, and text.
     *
     * @param alphaKey The associated with alphabet.
     * @param textKey  The key associated with the text.
     * @param text     The text for creating the matrix.
     * @throws NotContainsKey If the specified key for the Alphabet is not found.
     * @throws IOException If an error getting the Transition file.
     * @throws ContainsKey If the specified key for Transition exist.
     * @throws IncorrectType If the specified key has an incorrect type.
     * @throws StringNotInAlphabet If the specified word is not in the alphabet.
     */
    public void createMatrixText(String alphaKey, String textKey, String text) throws NotContainsKey, ContainsKey, IOException, StringNotInAlphabet, IncorrectType {
        inputController.createMatrixText(alphaKey, textKey, text);
        TransitionMatrix transition = getTransitionMatrix(textKey);
        persistenceController.createTM(textKey, alphaKey, transition.getTransitionMatrix());
    }

    /**
     * Creates a matrix frequency list with the specified alphabet key, key,
     * frequency list, and words list.
     *
     * @param alphaKey       The associated with alphabet.
     * @param key            The key associated with the matrix.
     * @param frequenciesList The list of frequencies.
     * @param wordsList      The list of words.
     * @throws NotContainsKey If the specified key for the Alphabet is not found.
     * @throws IOException If an error getting the Transition file.
     * @throws ContainsKey If the specified key for Transition exist.
     * @throws IncorrectType If the specified key has an incorrect type.
     * @throws StringNotInAlphabet If the specified word is not in the alphabet.
     */
    public void createMatrixFrequencyList(String alphaKey, String key, int[] frequenciesList, String[] wordsList) throws NotContainsKey, ContainsKey, IOException, IncorrectType, StringNotInAlphabet {
        inputController.createMatrixFrequencyList(alphaKey, key, frequenciesList, wordsList);
        TransitionMatrix transition = getTransitionMatrix(key);
        persistenceController.createTM(key, alphaKey, transition.getTransitionMatrix());
    }

    /**
     * Deletes a transition matrix with the specified key.
     *
     * @param keyTrans The key associated with the transition matrix to be deleted.
     * @throws NotContainsKey If the specified key is not found.
     * @throws IOException If an error getting the Transition file.
     * @throws FileNotDeleted If an error deleting the Transition Matrix file.
     */
    public void deleteTransitionMatrix(String keyTrans) throws NotContainsKey, IOException, FileNotDeleted {
        inputController.deleteTransitionMatrix(keyTrans);
        persistenceController.deleteTM(keyTrans);
    }

    /**
     * Retrieves a TransitionMatrix with the specified key from the associated InputController.
     *
     * @param keyTrans The key associated with the TransitionMatrix.
     * @throws NotContainsKey If the specified key is not found.
     * @return The TransitionMatrix corresponding to the key, or {@code null} if not found.
     */
    public TransitionMatrix getTransitionMatrix(String keyTrans) throws NotContainsKey {
        return inputController.getTransitionMatrix(keyTrans);
    }

    /**
     * Retrieves an array of TransitionMatrices from the associated InputController.
     *
     * @return An array of TransitionMatrices, where each element represents a TransitionMatrix in the InputController.
     */
    public TransitionMatrix[] listTransitionMatrix() {
        return inputController.listTransitionMatrix();
    }

    /**
     * Reads the content of a text file specified by the given path.
     *
     * @param path The path of the text file.
     * @return The content of the text file as a string.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public String getTextFromFile(String path) throws IOException {
        return persistenceController.getTextFile(path);
    }

    /**
     * Retrieves an array of words from the specified text file.
     *
     * @param path The path of the text file.
     * @return An array of words extracted from the text file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public String[] getWordsFromFile(String path) throws IOException {
        return persistenceController.getWordsFile(path);
    }

    /**
     * Retrieves an array of frequencies for each word in the specified text file.
     *
     * @param path The path of the text file.
     * @return An array of frequencies corresponding to each word in the text file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public int[] getFrequenciesFromFile(String path) throws IOException {
        return persistenceController.getFrequenciesFile(path);
    }


    // ---------------------------------------------------
    // -------------------- KEYBOARD ---------------------
    // ---------------------------------------------------

    /**
     * Creates a keyboard with the specified key, transition matrix, alphabet, and
     * algorithm.
     *
     * @param key       The key associated with the new keyboard.
     * @param transKey  The key associated with the transition matrix.
     * @param algorithm The algorithm to use for keyboard creation.
     * @throws IOException If an I/O error occurs while creating the keyboard.
     * @throws NotContainsKey If the specified key is not present in the data structures.
     * @throws IncorrectType If the specified key has an incorrect type.
     * @throws ContainsKey If the specified key is already present in the data structures.
     */
    public void createKeyboard(String key, String transKey, String algorithm) throws IOException, NotContainsKey, IncorrectType, ContainsKey {
        if (key.isEmpty()) throw new IncorrectType("Input for Keyboard Incorrect, empty Keyboard ID");
        TransitionMatrix transMatrix = inputController.getTransitionMatrix(transKey);
        Alphabet alpha = transMatrix.getAlphabet();
        keyboardController.createKeyboard(key, transMatrix, alpha, algorithm);
        persistenceController.createKeyboard(key, keyboardController.getKeyboardDistribution(key));
    }

    /**
     * Retrieves the names of existing keyboards.
     *
     * @return An array of strings containing the names of existing keyboards.
     */
    public String[] listKeyboard() {
        return keyboardController.getKeyboardNames();
    }

    /**
     * Retrieves the character distribution of a specified keyboard.
     *
     * @param key The key associated with the keyboard.
     * @throws NotContainsKey If the specified key is not found.
     * @return A two-dimensional array representing the character distribution of
     *         the keyboard.
     */
    public Keyboard getKeyboard(String key) throws NotContainsKey {
        return keyboardController.getKeyboard(key);
    }

    /**
     * Modifies the positions of characters on a specified keyboard.
     *
     * @param key The key associated with the keyboard.
     * @param i1  The row index of the first position.
     * @param j1  The column index of the first position.
     * @param i2  The row index of the second position.
     * @param j2  The column index of the second position.
     * @throws NotContainsKey If the specified key is not found.
     * @throws IOException If an error getting the Keyboard file.
     */
    public void modifyKeyboard(String key, int i1, int j1, int i2, int j2) throws NotContainsKey, IOException {
        keyboardController.modifyKeyboard(key, i1, j1, i2, j2);
        Keyboard keyboard = keyboardController.getKeyboard(key);
        persistenceController.modifyKeyboard(keyboard.getKey(), keyboard.getDistribution());
    }

    /**
     * Evaluates the efficiency of a keyboard using a specified transition matrix
     * and alphabet.
     *
     * @param keyboardKey         The key associated with the keyboard.
     * @param transKey The key associated with the transition matrix.
     * @throws NotContainsKey If the specified key is not found.
     * @return A double representing the efficiency of the keyboard.
     */
    public double evaluateKeyboard(String keyboardKey, String transKey) throws NotContainsKey {
        TransitionMatrix trans = inputController.getTransitionMatrix(transKey);
        Keyboard keyboard = keyboardController.getKeyboard(keyboardKey);
        return keyboardController.evaluateKeyboard(keyboard, trans);
    }

    /**
     * Deletes a specified keyboard.
     *
     * @param key The key associated with the keyboard to be deleted.
     * @throws IOException If an I/O error occurs while deleting the keyboard.
     * @throws NotContainsKey If the specified key is not present in the data structures.
     * @throws FileNotDeleted If the corresponding file is not deleted.
     */
    public void deleteKeyboard(String key) throws IOException, NotContainsKey, FileNotDeleted {
        keyboardController.deleteKeyboard(key);
        persistenceController.deleteKeyboard(key);
    }
}
