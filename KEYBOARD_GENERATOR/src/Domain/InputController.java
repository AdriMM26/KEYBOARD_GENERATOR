package Domain;

import Exceptions.*;
import Model.Pair;

import java.util.Map;
import java.util.HashMap;
import java.util.Vector;

/**
 * The InputController class manages the creation and manipulation of Alphabets
 * and TransitionMatrices.
 * It ensures unique keys for Alphabets and TransitionMatrices and provides
 * methods for creating and deleting them.
 */
public class InputController {

    /**
     * A map that stores Alphabets with their corresponding keys.
     */
    private final Map<String, Alphabet> alphabets;

    /**
     * A map that stores TransitionMatrices with their corresponding keys.
     */
    private final Map<String, TransitionMatrix> transitions;

    /**
     * Default constructor for the InputController class.
     * Initializes the maps for Alphabets and TransitionMatrices.
     *
     * @param alphabetsData    The initial data for Alphabets.
     * @param transitionsData  The initial data for TransitionMatrices.
     * @throws NotContainsKey If a required key is not present in the data structures.
     */
    public InputController(Vector<Pair<String, char[]>> alphabetsData, Vector<Pair<Pair<String, String>, int[][]>> transitionsData) throws NotContainsKey {
        this.alphabets = new HashMap<>();
        this.transitions = new HashMap<>();

        for (Pair<String, char[]> alpha : alphabetsData) {
            Alphabet alphabet = new Alphabet(alpha.first, alpha.second);
            this.alphabets.put(alpha.first, alphabet);
        }

        for (Pair<Pair<String, String>, int[][]> trans : transitionsData) {
            Alphabet alphabet = getAlphabet(trans.first.second);
            TransitionMatrix transition = new TransitionMatrix(trans.first.first, alphabet, trans.second);
            this.transitions.put(trans.first.first, transition);
        }
    }

    // --------------------------------------------------
    // -------------------- ALPHABET---------------------
    // --------------------------------------------------

    /**
     * Creates an Alphabet with the specified key and elements.
     *
     * @param keyAlphabet The key associated with the new Alphabet.
     * @param elem        The elements of the Alphabet.
     * @throws ContainsKey If the specified key is already present in the data structures.
     * @throws IncorrectType If the specified key has an incorrect type.
     */
    public void createAlphabet(String keyAlphabet, char[] elem) throws ContainsKey, IncorrectType {
        if (alphabets.containsKey(keyAlphabet)) throw new ContainsKey("Alphabets Map contains Key: " + keyAlphabet);
        if (keyAlphabet.isEmpty()) throw new IncorrectType("Input for Alphabet Incorrect, empty ID");
        if (elem.length == 0) throw new IncorrectType("Input for Alphabet Incorrect, empty Alphabet");
        if (!correctAlphabet(elem)) throw new IncorrectType("Input for Alphabet Incorrect, characters repeated");
        Alphabet alphabet = new Alphabet(keyAlphabet, elem);
        alphabets.put(keyAlphabet, alphabet);
    }

    /**
     * Deletes an Alphabet with the specified key.
     *
     * @param keyAlpha The key associated with the Alphabet to be deleted.
     * @throws NotContainsKey If the specified key is not present in the data structures.
     */
    public void deleteAlphabet(String keyAlpha) throws NotContainsKey {
        if (!alphabets.containsKey(keyAlpha)) throw new NotContainsKey("Alphabets Map does NOT contain Key: " + keyAlpha);
        alphabets.remove(keyAlpha);
    }

    /**
     * Retrieves an Alphabet with the specified key.
     *
     * @param alphaKey The key associated with the Alphabet.
     * @return The Alphabet corresponding to the key, or null if not found.
     * @throws NotContainsKey If the specified key is not present in the data structures.
     */
    public Alphabet getAlphabet(String alphaKey) throws NotContainsKey {
        if (!alphabets.containsKey(alphaKey)) throw new NotContainsKey("Alphabets Map does NOT contain Key: " + alphaKey);
        return alphabets.get(alphaKey);
    }

    /**
     * Checks if an Alphabet with the specified key is used in any TransitionMatrix.
     *
     * @param alphaKey The key associated with the Alphabet.
     * @return True if the Alphabet is used, false otherwise.
     */
    public boolean checkAlphabetUsed(String alphaKey) {
        Alphabet alphabet = alphabets.get(alphaKey);
        for (TransitionMatrix transition : transitions.values()) {
            if (transition.getAlphabet() == alphabet) return true;
        }
        return false;
    }


    /**
     * Adds an element to an Alphabet.
     *
     * @param alphaKey The key associated with the Alphabet.
     * @param elem    The element to add.
     * @throws NotContainsKey If the specified key is not found.
     * @throws IncorrectAlphabetType If the specified key has an incorrect type.
     */
    public void addElemAlphabet(String alphaKey, char elem) throws NotContainsKey, IncorrectAlphabetType {
        if (!alphabets.containsKey(alphaKey)) throw new NotContainsKey("Alphabets Map does NOT contains Key: " +alphaKey);
        Alphabet alph = alphabets.get(alphaKey);
        char [] elements = alph.getElem();
        for(char e : elements) {
            char upperelem = Character.toUpperCase(elem);
            if (upperelem == e){
                throw new IncorrectAlphabetType("This Alphabet already contains the character: "+ elem);
            }
        }
        alphabets.get(alphaKey).addElem(elem);
    }

    /**
     * Deletes an element from an Alphabet.
     *
     * @param alphaKey The key associated with the Alphabet.
     * @param elem    The element to delete.
     * @throws NotContainsKey If the specified key is not found.
     * @throws IncorrectAlphabetType If the specified key has an incorrect type.
     */
    public void delElemAlphabet(String alphaKey, char elem) throws IncorrectAlphabetType, NotContainsKey {
        if (!alphabets.containsKey(alphaKey)) throw new NotContainsKey("Alphabets Map does NOT contains Key: " +alphaKey);
        Alphabet alph = alphabets.get(alphaKey);
        char [] elements = alph.getElem();
        for(char e : elements) {
            char upperelem = Character.toUpperCase(elem);
            if (!(upperelem == e)) {
                throw new IncorrectAlphabetType("This Alphabet not contains the character: " + elem);
            }
        }

            alphabets.get(alphaKey).delElem(elem);
    }


    // ------------------------------------------------------------
    // -------------------- TRANSITION MATRIX ---------------------
    // ------------------------------------------------------------

    /**
     * Creates a TransitionMatrix from a text with the specified Alphabet key and
     * Text key.
     *
     * @param alphabetKey The key associated with the Alphabet.
     * @param textKey     The key associated with the new TransitionMatrix.
     * @param textIn      The input text for creating the TransitionMatrix.
     * @throws ContainsKey If the specified key already exist.
     * @throws NotContainsKey If the specified key is not found.
     * @throws IncorrectType If the specified key has an incorrect type.
     * @throws StringNotInAlphabet If the specified word is not in the alphabet.
     */
    public void createMatrixText(String alphabetKey, String textKey, String textIn) throws ContainsKey, NotContainsKey, IncorrectType, StringNotInAlphabet {
        if (transitions.containsKey(textKey)) throw new ContainsKey("Transitions Map contains Key: " +textKey);
        Alphabet alphabet = getAlphabet(alphabetKey);
        if (textKey.isEmpty()) throw new IncorrectType("Input for Text Incorrect, empty Transition ID");
        if (textIn.isEmpty()) throw new IncorrectType("Input for Text Incorrect, empty Text");
        if(!alphabet.containString(textIn)) throw new StringNotInAlphabet("Some of the characters of the text are not in the alphabet");
        Text text = new Text(textKey, textIn, alphabet);
        transitions.put(textKey, text);
    }

    /**
     * Creates a TransitionMatrix from a frequency list with the specified Alphabet
     * key and TransitionMatrix key.
     *
     * @param alphabetKey    The key associated with the Alphabet.
     * @param transKey       The key associated with the new TransitionMatrix.
     * @param frequenciesList The list of frequencies for creating the
     *                       TransitionMatrix.
     * @param wordsList      The list of words for creating the TransitionMatrix.
     * @throws ContainsKey If the specified key already exist.
     * @throws NotContainsKey If the specified key is not found.
     * @throws IncorrectType If the specified key has an incorrect type.
     * @throws StringNotInAlphabet If the specified word is not in the alphabet.
     */
    public void createMatrixFrequencyList(String alphabetKey, String transKey, int[] frequenciesList, String[] wordsList) throws ContainsKey, NotContainsKey, IncorrectType, StringNotInAlphabet {
        if (transitions.containsKey(transKey)) throw new ContainsKey("Transitions Map contains Key: " +transKey);
        if (transKey.isEmpty()) throw new IncorrectType("Input for WordFrequencyList Incorrect, empty Transition ID");
        if (frequenciesList.length == 0) throw new IncorrectType("Input for WordFrequencyList Incorrect, empty FrequencyList");
        if (wordsList.length == 0) throw new IncorrectType("Input for WordFrequencyList Incorrect, empty WordsList");
        if (wordsList.length != frequenciesList.length) throw new IncorrectType("Input for WordFrequencyList Incorrect, different number of elements in FrequencyList and WordsList");

        Alphabet alphabet = getAlphabet(alphabetKey);
        for(int i = 0; i < wordsList.length; ++i) {
            if(!alphabet.containString(wordsList[i])) throw new StringNotInAlphabet("Some of the characters of the Word List are not in the alphabet");
        }
        WordFrequencyList wordsFrequencyList = new WordFrequencyList(transKey, frequenciesList, wordsList, alphabet);
        transitions.put(transKey, wordsFrequencyList);
    }

    /**
     * Deletes a TransitionMatrix with the specified key.
     *
     * @param keyTrans The key associated with the TransitionMatrix to be deleted.
     * @throws NotContainsKey If the specified key is not found.
     */
    public void deleteTransitionMatrix(String keyTrans) throws NotContainsKey {
        if (!transitions.containsKey(keyTrans)) throw new NotContainsKey("Transition Map does NOT contains Key: " +keyTrans);
        transitions.remove(keyTrans);
    }

    /**
     * Gets a TransitionMatrix with the specified key.
     *
     * @param transKey The key associated with the TransitionMatrix.
     * @throws NotContainsKey   If it does not exist a Transition Matrix with that key.
     * @return The TransitionMatrix corresponding to the key.
     */
    public TransitionMatrix getTransitionMatrix(String transKey) throws NotContainsKey {
        if (!transitions.containsKey(transKey)) throw new NotContainsKey("Transition Map does NOT contains Key: " +transKey);
        return transitions.get(transKey);
    }


    // ----------------------------------------------------
    // -------------------- LIST DATA ---------------------
    // ----------------------------------------------------

    /**
     * Retrieves an array of Alphabets stored in the InputController.
     *
     * @return An array of Alphabets, where each element represents an Alphabet in the InputController.
     */
    public Alphabet[] listAlphabets() {
        Alphabet[] alphabetList = new Alphabet[alphabets.size()];
        int i = 0;
        for (Map.Entry<String, Alphabet> entry : alphabets.entrySet()) {
            alphabetList[i] = entry.getValue();
            ++i;
        }
        return alphabetList;
    }

    /**
     * Retrieves an array of TransitionMatrices stored in the InputController.
     *
     * @return An array of TransitionMatrices, where each element represents a TransitionMatrix in the InputController.
     */
    public TransitionMatrix[] listTransitionMatrix() {
        TransitionMatrix[] transitionsList = new TransitionMatrix[transitions.size()];
        int i = 0;
        for (Map.Entry<String, TransitionMatrix> entry : transitions.entrySet()) {
            transitionsList[i] = entry.getValue();
            ++i;
        }
        return transitionsList;
    }

    /**
     * Checks if the elements of an Alphabet are unique.
     *
     * @param alphaElem The elements of the Alphabet.
     * @return True if the elements are unique, false otherwise.
     */
    private boolean correctAlphabet(char[] alphaElem) {
        for (int i = 0; i < alphaElem.length; ++i) {
            for (int j = 0; j < alphaElem.length; ++j) {
                if (alphaElem[i] == alphaElem[j] && i != j)
                    return false;
            }
        }
        return true;
    }
}
