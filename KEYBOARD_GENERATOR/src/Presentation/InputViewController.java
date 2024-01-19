package Presentation;

import Exceptions.*;

import javax.swing.*;
import java.io.IOException;

 /**
 * The InputViewController class is responsible for managing the input view in the presentation layer.
 * It interacts with the user interface, delegates user actions to the underlying PresentationController,
 * and handles the initialization of the input view.
 */
public class InputViewController {

    /**
     * The JFrame instance for the input view.
     */
    private JFrame frame;

    /**
     * The PresentationController instance associated with this InputViewController.
     */
    private PresentationController presentationController;

    /**
     * Constructs an InputViewController with the given PresentationController.
     *
     * @param presentationController The PresentationController to be associated with this InputViewController.
     */
    public InputViewController(PresentationController presentationController) {
        frame = new JFrame();
        this.presentationController = presentationController;
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
     * Adds a new alphabet with the specified name and letters to the PresentationController.
     *
     * @param name    The name of the new alphabet.
     * @param letters A string representing the letters in the alphabet.
     * @throws IOException     If an I/O error occurs.
     * @throws NotContainsKey  If the specified key is not found.
     * @throws IncorrectType   If the input types are incorrect.
     * @throws ContainsKey     If the key is already present.
     */
    public void addAlphabet(String name, String letters) throws IOException, NotContainsKey, IncorrectType, ContainsKey {
        char[] listOfCharacters = letters.toCharArray();
        presentationController.createAlphabet(name, listOfCharacters);
    }

    /**
     * Deletes the alphabet with the specified name from the PresentationController.
     *
     * @param name The name of the alphabet to be deleted.
     * @throws IOException    If an I/O error occurs.
     * @throws NotContainsKey If the specified key is not found.
     * @throws AlphabetUsed   If the alphabet is still in use.
     * @throws FileNotDeleted If the associated file cannot be deleted.
     */
    public void deleteAlphabet(String name) throws IOException, NotContainsKey, AlphabetUsed, FileNotDeleted {
        presentationController.deleteAlphabet(name);
    }

    /**
     * Retrieves the characters of the alphabet with the specified name from the PresentationController.
     *
     * @param name The name of the alphabet to be retrieved.
     * @return An array of characters representing the letters in the alphabet.
     * @throws IOException    If an I/O error occurs.
     * @throws NotContainsKey If the specified key is not found.
     */
    public char[] getAlphabet(String name) throws IOException, NotContainsKey {
        return presentationController.getAlphabet(name);
    }

     /**
      * Reads the content of a text file specified by the given path.
      *
      * @param path The path of the text file.
      * @return The content of the text file as a string.
      * @throws IOException If an I/O error occurs while reading the file.
      */
    public String getTextFromFile(String path) throws IOException {
        return presentationController.getTextFromFile(path);
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