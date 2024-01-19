package Persistence;

import Exceptions.FileNotDeleted;
import Model.Pair;

import java.io.*;
import java.util.*;

/**
 * The KeyboardsController class manages operations related to keyboards,
 * including creating, retrieving, modifying, and deleting keyboards through CSV files.
 */
public class KeyboardsController {

    private final CSVController csvCtrl;

    /**
     * Constructs an instance of KeyboardsController with an associated CSVController.
     */
    public KeyboardsController() {
        this.csvCtrl = new CSVController();
    }

    /**
     * Retrieves information about all keyboards from the "Keyboards" type files.
     *
     * @return A Vector containing Pair objects representing the names and character distributions of all keyboards.
     * @throws IOException If an I/O error occurs while reading the CSV files.
     */
    public Vector<Pair<String, char[][]>> getAll() throws IOException {
        Vector<String> fileNames = this.csvCtrl.getFileNames("Keyboards");

        Vector<Pair<String, char[][]>> listKeyboards = new Vector<>();
        for (String fileName : fileNames) {
            listKeyboards.add(getStats(fileName));
        }
        return listKeyboards;
    }

    /**
     * Retrieves information about a specific keyboard by name from the "Keyboards" type files.
     *
     * @param name The name of the keyboard to retrieve.
     * @return A Pair object representing the name and character distribution of the specified keyboard, or null if not found.
     * @throws IOException If an I/O error occurs while reading the CSV files.
     */
    public Pair<String, char[][]> getKeyboard(String name) throws IOException {
        String[] keyRelation = this.csvCtrl.getKeyRelation("Keyboards", name);
        if (keyRelation != null) return getStats(keyRelation[1]);
        else return null;
    }

    /**
     * Retrieves information about a keyboard from a specified CSV file.
     *
     * @param fileName The name of the CSV file containing keyboard information.
     * @return A Pair object representing the name and character distribution of the keyboard.
     * @throws IOException If an I/O error occurs while reading the CSV file.
     */
    public Pair<String, char[][]> getStats(String fileName) throws IOException {
        String[] keyboardStats = this.csvCtrl.getStatsFromFile("Keyboards", fileName);

        int numRows = Integer.parseInt(keyboardStats[1]), numCols = Integer.parseInt(keyboardStats[2]), pointer = 3;
        char[][] characters = new char[numRows][numCols];
        for (int i = 0; i < numRows; ++i) {
            for (int j = 0; j < numCols; ++j) {
                characters[i][j] = keyboardStats[pointer].charAt(0);
                ++pointer;
            }
        }
        Pair<String, char[][]> keyboard = new Pair<>();
        keyboard.first = keyboardStats[0];
        keyboard.second = characters;
        return keyboard;
    }

    /**
     * Creates a new keyboard with the specified name and character distribution.
     *
     * @param name       The name of the new keyboard.
     * @param characters The character distribution of the new keyboard.
     * @throws IOException If an I/O error occurs while creating the CSV file.
     */
    public void createKeyboard(String name, char[][] characters) throws IOException {
        if (getKeyboard(name) != null) { // checks if exists
            return;
        }

        StringBuilder keyboardToString = new StringBuilder(name);
        keyboardToString.append("\n").append(characters.length).append("\n").append(characters[0].length);
        for (char[] character : characters) {
            for (char c : character) {
                keyboardToString.append("\n").append(c);
            }
        }

        this.csvCtrl.createFile("Keyboards", name, String.valueOf(keyboardToString));
    }

    /**
     * Deletes a specified keyboard by name.
     *
     * @param name The name of the keyboard to delete.
     * @throws IOException If an I/O error occurs while deleting the CSV file.
     * @throws FileNotDeleted If the file cannot be deleted.
     */
    public void deleteKeyboard(String name) throws IOException, FileNotDeleted {
        String[] keyRelation = this.csvCtrl.getKeyRelation("Keyboards", name);

        if (keyRelation != null) {
            this.csvCtrl.deleteFile("Keyboards", keyRelation);
        }
    }

    /**
     * Modifies a specified keyboard's character distribution.
     *
     * @param name       The name of the keyboard to modify.
     * @param characters The new character distribution of the keyboard.
     * @throws IOException If an I/O error occurs while modifying the CSV file.
     */
    public void modifyKeyboard(String name, char[][] characters) throws IOException {
        StringBuilder keyboardToString = new StringBuilder(name);
        keyboardToString.append("\n").append(characters.length).append("\n").append(characters[0].length);
        for (char[] character : characters) {
            for (char c : character) {
                keyboardToString.append("\n").append(c);
            }
        }

        String[] keyRelation = this.csvCtrl.getKeyRelation("Keyboards", name);
        this.csvCtrl.modifyFile("Keyboards", keyRelation[1], String.valueOf(keyboardToString));
    }
}
