package Presentation;

import Exceptions.ContainsKey;
import Exceptions.FileNotDeleted;
import Exceptions.IncorrectType;
import Exceptions.NotContainsKey;

import javax.swing.*;
import java.io.IOException;

/**
 * The MainViewController class is responsible for managing the main display frame
 * and interacting with the PresentationController to handle user input and actions.
 */
public class MainViewController {

    private JFrame frame;
    private PresentationController pc;
    private InputViewController iv;

    /**
     * Constructs a new MainViewController with the specified PresentationController.
     *
     * @param pc The PresentationController to be associated with this MainViewController.
     * @throws IOException    if an I/O error occurs during initialization.
     * @throws NotContainsKey if a required key is not found during initialization.
     */
    public MainViewController(PresentationController pc) throws IOException, NotContainsKey {
        frame = new JFrame();
        this.pc = pc;
    }



    /**
     * Retrieves the list of available keyboards from the PresentationController.
     *
     * @return An array of Strings representing the list of available keyboards.
     * @throws IOException    if an I/O error occurs during the operation.
     * @throws NotContainsKey if a required key is not found during the operation.
     */
    public String[] listKeyboard() throws IOException, NotContainsKey {
        return pc.listKeyboard();
    }

    /**
     * Retrieves the distribution of characters for the specified keyboard name
     * from the PresentationController.
     *
     * @param name The name of the keyboard to retrieve the distribution for.
     * @return A 2D char array representing the distribution of characters.
     * @throws NotContainsKey if the specified keyboard name is not found.
     */
    public char[][] getDistribution(String name) throws NotContainsKey {
        return pc.getKeyboard(name);
    }

    /**
     * Adds a new keyboard with the specified parameters using the PresentationController.
     *
     * @param text  The key for the new keyboard.
     * @param text1 The transKey for the new keyboard.
     * @param text2 The algorithm to be used by the new keyboard.
     * @throws IncorrectType If an incorrect type is encountered during keyboard creation.
     * @throws IOException   If an I/O error occurs during keyboard creation.
     * @throws NotContainsKey If the PresentationController does not contain the specified key.
     * @throws ContainsKey    If the PresentationController already contains the specified key.
     */
    public void addKeyboard(String text, String text1, String text2)
            throws IncorrectType, IOException, NotContainsKey, ContainsKey {
        pc.createKeyboard(text, text1, text2);
    }

    /**
     * Deletes the keyboard with the specified name using the PresentationController.
     *
     * @param name The name of the keyboard to be deleted.
     * @throws NotContainsKey If the specified name is not found in the PresentationController.
     * @throws IOException   If an I/O error occurs during keyboard deletion.
     * @throws NotContainsKey   If the keyboard does not exist.
     * @throws FileNotDeleted   If an error occurs during the file keyboard deletion.
     */
    public void deleteKeyboard(String name) throws NotContainsKey, IOException, FileNotDeleted {
        pc.deleteKeyboard(name);
    }

    /**
     * Evaluates the keyboard based on two input texts and returns a double value representing the evaluation result.
     *
     * @param text The first input text for keyboard evaluation.
     * @param text1 The second input text for keyboard evaluation.
     * @return A double value representing the evaluation result.
     * @throws NotContainsKey If the provided input texts do not contain the necessary keys for evaluation.
     */
    public double evaluateKeyboard(String text, String text1) throws NotContainsKey {
        // Delegates the keyboard evaluation to the underlying persistence controller.
        return pc.evaluateKeyboard(text, text1);
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
        pc.modifyKeyboard(key, i1, j1, i2, j2);
    }

    /**
     * Retrieves an array of transitions from the presentation controller.
     *
     * @return An array of strings representing the list of transitions.
     * @throws IOException      If an I/O error occurs.
     * @throws NotContainsKey   If the specified key is not found.
     */
    public String[] listTransitions() throws IOException, NotContainsKey {
        return pc.listTransitions();
    }

    /**
     * Restarts the application by initializing the display.
     *
     * @throws IOException      If an I/O error occurs during the display initialization.
     * @throws NotContainsKey   If a required key is not found during the display initialization.
     *                          This exception is specific to the application's logic.
     */
    public void restart() throws IOException, NotContainsKey {
        pc.initializeDisplay();
    }

}
