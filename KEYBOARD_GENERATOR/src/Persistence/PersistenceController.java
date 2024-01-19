package Persistence;

import Exceptions.FileNotDeleted;
import Model.Pair;

import java.io.IOException;
import java.util.Vector;

/**
 * The PersistenceController class manages the persistence of keyboards, alphabets, and transition matrices,
 * providing methods for creating, retrieving, deleting, and modifying these entities.
 * It uses associated controllers for keyboards, alphabets, and transition matrices,
 * delegating specific operations to these controllers and handling potential I/O errors.
 */
public class PersistenceController {

    private final KeyboardsController keyboardsController;
    private final AlphabetsController alphabetsController;
    private final TransitionsController transitionController;

    /**
     * Constructs an instance of PersistenceController with associated controllers for keyboards, alphabets, and transition matrices.
     */
    public PersistenceController() {
        keyboardsController = new KeyboardsController();
        alphabetsController = new AlphabetsController();
        transitionController = new TransitionsController();
    }

    // CREATION

    /**
     * Creates a new keyboard with the specified name and character distribution.
     *
     * @param name       The name of the new keyboard.
     * @param characters The character distribution of the new keyboard.
     * @throws IOException If an I/O error occurs while creating the keyboard.
     */
    public void createKeyboard(String name, char[][] characters) throws IOException {
        keyboardsController.createKeyboard(name, characters);
    }

    /**
     * Creates a new alphabet with the specified name and characters.
     *
     * @param name       The name of the new alphabet.
     * @param characters The characters of the new alphabet.
     * @throws IOException If an I/O error occurs while creating the alphabet.
     */
    public void createAlphabet(String name, char[] characters) throws IOException {
        alphabetsController.createAlphabet(name, characters);
    }

    /**
     * Creates a new transition matrix with the specified name, associated alphabet, and transition matrix values.
     *
     * @param name        The name of the new transition matrix.
     * @param alphabet    The name of the associated alphabet.
     * @param transitions The 2D array representing the transition matrix values.
     * @throws IOException If an I/O error occurs while creating the transition matrix.
     */
    public void createTM(String name, String alphabet, int[][] transitions) throws IOException {
        transitionController.createTransition(name, alphabet, transitions);
    }

    // GETTER LIST

    /**
     * Retrieves information about all keyboards.
     *
     * @return A Vector containing Pair objects representing the names and character distributions of all keyboards.
     * @throws IOException If an I/O error occurs while retrieving keyboards.
     */
    public Vector<Pair<String, char[][]>> getAllKeyboards() throws IOException {
        return keyboardsController.getAll();
    }

    /**
     * Retrieves information about all alphabets.
     *
     * @return A Vector containing Pair objects representing the names and characters of all alphabets.
     * @throws IOException If an I/O error occurs while retrieving alphabets.
     */
    public Vector<Pair<String, char[]>> getAllAlphabets() throws IOException {
        return alphabetsController.getAll();
    }

    /**
     * Retrieves information about all transition matrices.
     *
     * @return A Vector containing Pair objects representing the names, associated alphabets, and transition matrix values of all transition matrices.
     * @throws IOException If an I/O error occurs while retrieving transition matrices.
     */
    public Vector<Pair<Pair<String, String>, int[][]>> getAllTMs() throws IOException {
        return transitionController.getAll();
    }

    // DELETE

    /**
     * Deletes a specified keyboard by name.
     *
     * @param name The name of the keyboard to delete.
     * @throws IOException If an I/O error occurs while deleting the keyboard.
     * @throws FileNotDeleted If the file deletion operation fails.
     */
    public void deleteKeyboard(String name) throws IOException, FileNotDeleted {
        keyboardsController.deleteKeyboard(name);
    }

    /**
     * Deletes a specified alphabet by name.
     *
     * @param name The name of the alphabet to delete.
     * @throws IOException If an I/O error occurs while deleting the alphabet.
     * @throws FileNotDeleted If the file deletion operation fails.
     */
    public void deleteAlphabet(String name) throws IOException, FileNotDeleted {
        alphabetsController.deleteAlphabet(name);
    }

    /**
     * Deletes a specified transition matrix by name.
     *
     * @param name The name of the transition matrix to delete.
     * @throws IOException If an I/O error occurs while deleting the transition matrix.
     * @throws FileNotDeleted If the file deletion operation fails.
     */
    public void deleteTM(String name) throws IOException, FileNotDeleted {
        transitionController.deleteTransition(name);
    }

    // MODIFY

    /**
     * Modifies a specified alphabet's characters.
     *
     * @param name       The name of the alphabet to modify.
     * @param characters The new characters of the alphabet.
     * @throws IOException If an I/O error occurs while modifying the alphabet.
     */
    public void modifyAlphabet(String name, char[] characters) throws IOException {
        alphabetsController.modifyAlphabet(name, characters);
    }

    /**
     * Modifies a specified keyboard's character distribution.
     *
     * @param name       The name of the keyboard to modify.
     * @param characters The new character distribution of the keyboard.
     * @throws IOException If an I/O error occurs while modifying the keyboard.
     */
    public void modifyKeyboard(String name, char[][] characters) throws IOException {
        keyboardsController.modifyKeyboard(name, characters);
    }

    /**
     * Retrieves the text content of a file specified by the given path using the transition controller.
     *
     * @param path The path of the file to read.
     * @return The text content of the file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public String getTextFile(String path) throws IOException {
        return transitionController.getTextFromFile(path);
    }

    /**
     * Retrieves an array of frequencies from a file specified by the given path using the transition controller.
     *
     * @param path The path of the file to read.
     * @return An array of integer frequencies extracted from the file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public int[] getFrequenciesFile(String path) throws IOException {
        return transitionController.getFrequenciesFromFile(path);
    }

    /**
     * Retrieves an array of words from a file specified by the given path using the transition controller.
     *
     * @param path The path of the file to read.
     * @return An array of words extracted from the file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public String[] getWordsFile(String path) throws IOException {
        return transitionController.getWordsFromFile(path);
    }

    /**
     * Retrieves an array of characters from a file specified by the given path using the alphabets controller.
     *
     * @param path The path of the file to read.
     * @return An array of characters extracted from the file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public char[] getCharactersFile(String path) throws IOException {
        return alphabetsController.getCharactersFromFile(path);
    }

}