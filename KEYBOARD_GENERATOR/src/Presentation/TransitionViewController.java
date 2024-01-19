package Presentation;

import Exceptions.*;

import java.io.IOException;

/**
 * The TransitionViewController class is responsible for managing transitions in the presentation layer.
 * It acts as a bridge between the GUI and the PresentationController, handling operations related to transitions.
 */
public class TransitionViewController {

    /**
     * The PresentationController instance associated with this TransitionViewController.
     */
    private final PresentationController presentationController;

    /**
     * Constructs a TransitionViewController with the given PresentationController.
     *
     * @param presentationController The PresentationController to be associated with this TransitionViewController.
     */
    public TransitionViewController(PresentationController presentationController) {
        this.presentationController = presentationController;
    }

    /**
     * Retrieves an array of transitions from the presentation controller.
     *
     * @return An array of strings representing the list of transitions.
     * @throws IOException      If an I/O error occurs.
     * @throws NotContainsKey   If the specified key is not found.
     */
    public String[] listTransitions() throws IOException, NotContainsKey {
        return presentationController.listTransitions();
    }

    /**
     * Retrieves an array of words from the specified text file.
     *
     * @param path The path of the text file.
     * @return An array of words extracted from the text file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public String[] getWordsFromFile(String path) throws IOException{
        return presentationController.getWordsFromFile(path);
    }

    /**
     * Retrieves an array of frequencies for each word in the specified text file.
     *
     * @param path The path of the text file.
     * @return An array of frequencies corresponding to each word in the text file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public int[] getFrequenciesFromFile(String path) throws IOException{
        return presentationController.getFrequenciesFromFile(path);
    }

    /**
     * Adds a text matrix to the specified alphabet in the presentation controller.
     *
     * @param alphaKey The key of the alphabet.
     * @param textKey  The key of the text matrix.
     * @param text     The text matrix to be added.
     * @throws NotContainsKey If the specified key is not found.
     * @throws ContainsKey     If the specified key already exists.
     * @throws IOException     If an I/O error occurs.
     * @throws IncorrectType If the specified key has an incorrect type.
     * @throws StringNotInAlphabet If the specified word is not in the alphabet.
     */
    public void addMatrixText(String alphaKey, String textKey, String text) throws NotContainsKey, ContainsKey, IOException, StringNotInAlphabet, IncorrectType {
        presentationController.addMatrixText(alphaKey, textKey, text);
    }

    /**
     * Adds a word frequency list matrix to the specified alphabet in the presentation controller.
     *
     * @param wordKey     The key of the word frequency list matrix.
     * @param alphaKey    The key of the alphabet.
     * @param frequencies  The array of frequencies.
     * @param wordList    The array of words.
     * @throws NotContainsKey If the specified key is not found.
     * @throws ContainsKey     If the specified key already exists.
     * @throws IOException     If an I/O error occurs.
     * @throws IncorrectType If the specified key has an incorrect type.
     * @throws StringNotInAlphabet If the specified word is not in the alphabet.
     */
    public void addMatrixWordFrequencyList(String wordKey, String alphaKey, int[] frequencies, String[] wordList) throws NotContainsKey, ContainsKey, IOException, IncorrectType, StringNotInAlphabet {
        presentationController.addMatrixFrequencyList(alphaKey, wordKey, frequencies, wordList);
    }

    /**
     * Deletes a transition matrix from the presentation controller.
     *
     * @param keyTrans The key of the transition matrix.
     * @throws NotContainsKey If the specified key is not found.
     * @throws IOException     If an I/O error occurs.
     * @throws FileNotDeleted If the file cannot be deleted.
     */
    public void deleteTransitionMatrix(String keyTrans) throws NotContainsKey, IOException, FileNotDeleted {
        presentationController.deleteTransitionMatrix(keyTrans);
    }
    /**
     * Retrieves an array of alphabets from the PresentationController.
     *
     * @return An array of strings representing the list of alphabets.
     * @throws IOException    If an I/O error occurs.
     * @throws NotContainsKey If the specified key is not found.
     */
    public String[] listAlphabets() throws IOException, NotContainsKey {
        return presentationController.listAlphabets();
    }

    /**
     * Restarts the application by initializing the display.
     *
     * @throws IOException      If an I/O error occurs during the display initialization.
     * @throws NotContainsKey   If a required key is not found during the display initialization.
     *                          This exception is specific to the application's logic.
     */
    public void restart() throws IOException, NotContainsKey {
        presentationController.initializeDisplay();
    }
}