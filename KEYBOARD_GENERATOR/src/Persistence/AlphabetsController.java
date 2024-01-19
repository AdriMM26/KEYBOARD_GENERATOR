package Persistence;

import Exceptions.FileNotDeleted;
import Model.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

/**
 * The AlphabetsController class manages operations related to alphabets,
 * providing methods for retrieving, creating, deleting, and modifying alphabets
 * using a CSVController for data persistence.
 */
public class AlphabetsController {

    private final CSVController csvCtrl;

    /**
     * Constructs an instance of AlphabetsController with an associated CSVController.
     */
    public AlphabetsController() {
        this.csvCtrl = new CSVController();
    }

    /**
     * Retrieves information about all existing alphabets.
     *
     * @return A Vector containing Pairs, where each Pair represents an alphabet with its name and characters.
     * @throws IOException If an I/O error occurs while reading the CSV files.
     */
    public Vector<Pair<String, char[]>> getAll() throws IOException {
        Vector<String> fileNames = this.csvCtrl.getFileNames("Alphabets");

        Vector<Pair<String, char[]>> listAlphabets = new Vector<>();
        for (String fileName : fileNames) {
            listAlphabets.add(getStats(fileName));
        }
        return listAlphabets;
    }

    /**
     * Retrieves information about a specific alphabet by its name.
     *
     * @param name The name of the alphabet to retrieve.
     * @return A Pair representing the alphabet with its name and characters, or null if not found.
     * @throws IOException If an I/O error occurs while reading the CSV files.
     */
    public Pair<String, char[]> getAlphabet(String name) throws IOException {
        String[] keyRelation = this.csvCtrl.getKeyRelation("Alphabets", name);
        if (keyRelation != null) return getStats(keyRelation[1]);
        else return null;
    }

    /**
     * Retrieves information about an alphabet from a specified CSV file.
     *
     * @param fileName The name of the CSV file containing the alphabet information.
     * @return A Pair representing the alphabet with its name and characters.
     * @throws IOException If an I/O error occurs while reading the CSV files.
     */
    public Pair<String, char[]> getStats(String fileName) throws IOException {
        String[] alphabetStats = this.csvCtrl.getStatsFromFile("Alphabets", fileName);

        int numDimension = Integer.parseInt(alphabetStats[1]), point = 2;
        char[] characters = new char[numDimension];
        for (int i = 0; i < numDimension; ++i) {
            characters[i] = alphabetStats[point].charAt(0);
            ++point;
        }
        Pair<String, char[]> alphabet = new Pair<>();
        alphabet.first = alphabetStats[0];
        alphabet.second = characters;
        return alphabet;
    }

    /**
     * Creates a new alphabet with the specified name and characters.
     *
     * @param name       The name of the new alphabet.
     * @param characters The characters of the new alphabet.
     * @throws IOException If an I/O error occurs while creating the CSV file.
     */
    public void createAlphabet(String name, char[] characters) throws IOException {
        if (getAlphabet(name) != null) {
            return; // Alphabet with the same name already exists
        }

        StringBuilder alphabetToString = new StringBuilder(name);
        alphabetToString.append("\n").append(characters.length);
        for (char c : characters) {
            alphabetToString.append("\n").append(c);
        } // Alphabet in one String

        this.csvCtrl.createFile("Alphabets", name, String.valueOf(alphabetToString));
    }

    /**
     * Deletes an existing alphabet with the specified name.
     *
     * @param name The name of the alphabet to delete.
     * @throws IOException If the CSV file does not exist.
     * @throws FileNotDeleted If an error occurs while deleting the CSV file.
     */
    public void deleteAlphabet(String name) throws IOException, FileNotDeleted {
        String[] keyRelation = this.csvCtrl.getKeyRelation("Alphabets", name);
        if (keyRelation != null) this.csvCtrl.deleteFile("Alphabets", keyRelation);
    }

    /**
     * Modifies an existing alphabet with the specified name and characters.
     *
     * @param name       The name of the alphabet to modify.
     * @param characters The new characters of the alphabet.
     * @throws IOException If an I/O error occurs while modifying the CSV file.
     */
    public void modifyAlphabet(String name, char[] characters) throws IOException {
        StringBuilder alphabetToString = new StringBuilder(name);
        alphabetToString.append("\n").append(characters.length);
        for (char c : characters) {
            alphabetToString.append("\n").append(c);
        }

        String[] keyRelation = this.csvCtrl.getKeyRelation("Alphabets", name);
        this.csvCtrl.modifyFile("Alphabets", keyRelation[1], String.valueOf(alphabetToString));
    }

    /**
     * Reads the content of a file specified by the given path, extracts the first character from each line,
     * and returns an array of characters.
     *
     * @param path The path of the file to read.
     * @return An array of characters representing the first character from each line of the file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public char[] getCharactersFromFile(String path) throws IOException {
        // Retrieves statistics from the CSV controller for the specified file path.
        String[] fileStats = this.csvCtrl.getStatsFromPath(path);

        // Initializes an array to store the extracted characters.
        char[] characters = new char[fileStats.length];

        // Iterates through each line in the file statistics.
        for (int i = 0; i < fileStats.length; ++i) {
            // Outputs the current line to the console (for debugging or informational purposes).
            System.out.println(fileStats[i]);

            // Checks if the current line is empty and assigns a space character if true,
            // otherwise extracts the first character from the line.
            if (Objects.equals(fileStats[i], "")) {
                characters[i] = ' ';
            } else {
                characters[i] = fileStats[i].charAt(0);
            }
        }

        // Returns the array of characters.
        return characters;
    }

}
