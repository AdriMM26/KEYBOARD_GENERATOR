package Presentation;

import Domain.DomainController;
import Exceptions.*;

import javax.swing.*;
import java.io.IOException;

/**
 * The PresentationController class is responsible for managing the presentation layer
 * of the application. It interacts with the user interface, delegates user actions
 * to the underlying domain logic through the DomainController, and handles the
 * initialization of the main display.
 */
public class PresentationController {
    /**
     * The DomainController instance associated with this PresentationController.
     */
    private final DomainController domainController;

    /**
     * The MainViewController instance associated with this PresentationController.
     */
    private final MainViewController mainViewController;

    /**
     * The InputViewController instance associated with this PresentationController.
     */
    private final InputViewController inputViewController;

    /**
     * The TransitionViewController instance associated with this PresentationController.
     */
    private TransitionViewController transitionViewController;

    /**
     * The JFrame instance representing the main frame of the user interface.
     */
    private JFrame frame;

    /**
     * Constructs a new PresentationController. Initializes the DomainController and
     * sets up the main frame for the user interface.
     *
     * @throws IOException    if an I/O error occurs during initialization.
     * @throws NotContainsKey if a required key is not found during initialization.
     */
    public PresentationController() throws IOException, NotContainsKey {
        frame = new JFrame();
        domainController = new DomainController();
        mainViewController = new MainViewController(this);
        inputViewController = new InputViewController(this);
        transitionViewController = new TransitionViewController(this);
        initializeDisplay();

    }
    /**
     * Initializes the main display frame, sets its properties, and sets the content
     * pane to the MenuView.
     *
     * @throws IOException    if an I/O error occurs during initialization.
     * @throws NotContainsKey if a required key is not found during initialization.
     */
    public void initializeDisplay() throws IOException, NotContainsKey {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);

        MenuView menu = new MenuView(mainViewController, inputViewController, transitionViewController);
        frame.setContentPane(menu.getMenuView());

        // Set frame properties
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);
    }

    /**
     * Reads the content of a text file specified by the given path.
     *
     * @param path The path of the text file.
     * @return The content of the text file as a string.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public String getTextFromFile(String path) throws IOException{
        return domainController.getTextFromFile(path);
    }

    /**
     * Retrieves an array of words from the specified text file.
     *
     * @param path The path of the text file.
     * @return An array of words extracted from the text file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public String[] getWordsFromFile(String path) throws IOException{
        return domainController.getWordsFromFile(path);
    }

    /**
     * Retrieves an array of frequencies for each word in the specified text file.
     *
     * @param path The path of the text file.
     * @return An array of frequencies corresponding to each word in the text file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public int[] getFrequenciesFromFile(String path) throws IOException{
        return domainController.getFrequenciesFromFile(path);
    }


    /**
     * Creates a new alphabet with the specified name and letters.
     *
     * @param name    the name of the new alphabet.
     * @param letters an array of characters representing the letters in the alphabet.
     * @throws IOException     if an I/O error occurs during the operation.
     * @throws NotContainsKey  if a required key is not found during the operation.
     * @throws IncorrectType   if the input types are incorrect.
     * @throws ContainsKey     if the key is already present.
     */
    public void createAlphabet(String name, char[] letters) throws IOException, NotContainsKey, IncorrectType, ContainsKey {
        domainController.createAlphabet(name, letters);
    }

    /**
     * Deletes the alphabet with the specified name.
     *
     * @param name the name of the alphabet to be deleted.
     * @throws IOException    if an I/O error occurs during the operation.
     * @throws NotContainsKey if the specified key is not found.
     * @throws AlphabetUsed if the specified key for Alphabet is used.
     * @throws FileNotDeleted if error on deleting the file of the Alphabet.
     */
    public void deleteAlphabet(String name) throws IOException, NotContainsKey, AlphabetUsed, FileNotDeleted {
        domainController.deleteAlphabet(name);
    }

    /**
     * Retrieves and returns the alphabet elements of the alphabet with the specified name.
     *
     * @param name the name of the alphabet to be retrieved.
     * @return the elements of the Alphabet object with the specified name.
     * @throws IOException    if an I/O error occurs during the operation.
     * @throws NotContainsKey if the specified key is not found.
     */
    public char[] getAlphabet(String name) throws IOException, NotContainsKey {
        return domainController.getAlphabet(name).getElem();
    }

    /**
     * Lists and returns an array of all available alphabets names.
     *
     * @return an array of String objects representing available alphabets keys.
     * @throws IOException    if an I/O error occurs during the operation.
     * @throws NotContainsKey if a required key is not found during the operation.
     */
    public String[] listAlphabets() throws IOException, NotContainsKey {
        String [] alphabets_name = new String[domainController.listAlphabets().length];
        for(int i = 0; i < domainController.listAlphabets().length; i++){
            alphabets_name[i] = domainController.listAlphabets()[i].getKey();
        }
        return alphabets_name;
    }

    /**
     * Adds an element to the specified alphabet.
     *
     * @param alphaKey the key of the alphabet.
     * @param elem     the element to be added.
     * @throws NotContainsKey if the specified key is not found.
     * @throws IOException     if an I/O error occurs.
     * @throws IncorrectAlphabetType if the specified key has an incorrect type.
     */
    public void addElemAlphabet(String alphaKey, char elem) throws NotContainsKey, IOException, IncorrectAlphabetType{
        domainController.addElemAlphabet(alphaKey, elem);
    }
    /**
     * Deletes an element from the specified alphabet.
     *
     * @param alphaKey the key of the alphabet.
     * @param elem     the element to be deleted.
     * @throws NotContainsKey if the specified key is not found.
     * @throws IOException     if an I/O error occurs.
     * @throws IncorrectAlphabetType if the specified key has an incorrect type.
     */

    public void delElemAlphabet(String alphaKey, char elem) throws IncorrectAlphabetType, NotContainsKey, IOException {
        domainController.delElemAlphabet(alphaKey, elem);
    }
    /**
     * Retrieves an array of transitions from the domain controller.
     *
     * @return an array of strings representing the list of transitions.
     * @throws IOException      if an I/O error occurs.
     * @throws NotContainsKey   if the specified key is not found.
     */
    public String[] listTransitions() throws IOException, NotContainsKey {
        String [] transitions_name = new String[domainController.listTransitionMatrix().length];
        for(int i = 0; i < domainController.listTransitionMatrix().length; i++){
            transitions_name[i] = domainController.listTransitionMatrix()[i].getKey();
        }
        return transitions_name;
    }
    /**
     * Adds a text matrix to the specified alphabet in the domain controller.
     *
     * @param alphaKey the key of the alphabet.
     * @param textKey  the key of the text matrix.
     * @param text     the text matrix to be added.
     * @throws NotContainsKey if the specified key is not found.
     * @throws ContainsKey     if the specified key already exists.
     * @throws IOException     if an I/O error occurs.
     * @throws IncorrectType If the specified key has an incorrect type.
     * @throws StringNotInAlphabet If the specified word is not in the alphabet.
     */
    public void addMatrixText(String alphaKey, String textKey, String text) throws NotContainsKey, ContainsKey, IOException, StringNotInAlphabet, IncorrectType {
        domainController.createMatrixText(alphaKey, textKey, text);
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
    public void modifyKeyboard(String key, int i1, int j1, int i2, int j2) throws NotContainsKey, IOException{
        domainController.modifyKeyboard(key, i1, j1, i2, j2);
    }

    /**
     * Adds a word frequency list matrix to the specified alphabet in the domain controller.
     *
     * @param key     the key of the word frequency list matrix.
     * @param alphaKey    the key of the alphabet.
     * @param frequenciesList an array of frequencies.
     * @param wordsList   an array of words.
     * @throws NotContainsKey if the specified key is not found.
     * @throws ContainsKey     if the specified key already exists.
     * @throws IOException     if an I/O error occurs.
     * @throws IncorrectType If the specified key has an incorrect type.
     * @throws StringNotInAlphabet If the specified word is not in the alphabet.
     */
    public void addMatrixFrequencyList(String alphaKey, String key, int[] frequenciesList, String[] wordsList) throws NotContainsKey, ContainsKey, IOException, IncorrectType, StringNotInAlphabet {
        domainController.createMatrixFrequencyList(alphaKey, key, frequenciesList, wordsList);
    }
    /**
     * Deletes the specified transition matrix from the domain controller.
     *
     * @param keyTrans the key of the transition matrix.
     * @throws NotContainsKey if the specified key is not found.
     * @throws IOException     if an I/O error occurs.
     * @throws FileNotDeleted  if the file cannot be deleted.
     */
    public void deleteTransitionMatrix(String keyTrans) throws NotContainsKey, IOException, FileNotDeleted {
        domainController.deleteTransitionMatrix(keyTrans);
    }

        /**
         * Evaluates the keyboard with the specified name using the given text.
         *
         * @param name the name of the keyboard to be evaluated.
         * @param text the text to be evaluated using the keyboard.
         * @return the evaluation result as a double.
         * @throws NotContainsKey if the specified key is not found.
         */
    public double evaluateKeyboard(String name, String text) throws NotContainsKey {
        return domainController.evaluateKeyboard(name, text);
    }

    /**
     * Retrieves an array of names representing all available keyboards in the system.
     *
     * @return an array of strings containing the names of available keyboards.
     * @throws IOException    if an I/O error occurs during the operation.
     * @throws NotContainsKey if a required key is not found during the operation.
     */
    public String[] listKeyboard() throws IOException, NotContainsKey {
        return domainController.listKeyboard();
    }

    /**
     * Retrieves a keyboard distribution with the specified name from the domain controller.
     *
     * @param name The name of the keyboard to retrieve.
     * @return The char[][] keyboard distribution associated with the specified name.
     * @throws NotContainsKey If the specified name is not found in the domain controller.
     */
    public char[][] getKeyboard(String name) throws NotContainsKey {
        return domainController.getKeyboard(name).getDistribution();
    }

    /**
     * Creates a new keyboard with the provided parameters and adds it to the domain controller.
     *
     * @param key       The key for the new keyboard.
     * @param transKey  The transKey for the new keyboard.
     * @param algorithm The algorithm to be used by the new keyboard.
     * @throws IncorrectType If an incorrect type is encountered during keyboard creation.
     * @throws IOException   If an I/O error occurs during keyboard creation.
     * @throws NotContainsKey If the domain controller does not contain the specified key.
     * @throws ContainsKey    If the domain controller already contains the specified key.
     */
    public void createKeyboard(String key, String transKey, String algorithm)
            throws IncorrectType, IOException, NotContainsKey, ContainsKey {
        domainController.createKeyboard(key, transKey, algorithm);
    }

    /**
     * Deletes the keyboard with the specified name from the domain controller.
     *
     * @param name The name of the keyboard to be deleted.
     * @throws IOException   If an I/O error occurs during keyboard deletion.
     * @throws NotContainsKey If the specified name is not found in the domain controller.
     * @throws FileNotDeleted If error on deleting the file of the Keyboard.
     */
    public void deleteKeyboard(String name) throws IOException, NotContainsKey, FileNotDeleted {
        domainController.deleteKeyboard(name);
    }

}
