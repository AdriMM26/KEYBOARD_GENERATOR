package Persistence;

import java.io.IOException;
import java.util.Vector;

import Exceptions.FileNotDeleted;
import Model.Pair;

/**
 * The TransitionsController class acts as a controller for managing transitions between states,
 * handling the creation, retrieval, and deletion of transition matrices.
 */
public class TransitionsController {

    private final CSVController csvCtrl;

    /**
     * Constructs an instance of TransitionsController with an associated CSVController.
     */
    public TransitionsController() {
        this.csvCtrl = new CSVController();
    }

    // STRUCT USED: Pair<Pair<transitionName, alphabetName>, parameters>
    /**
     * Retrieves information about all transition matrices.
     *
     * @return A Vector containing Pair objects representing the names, associated alphabets, and transition matrix values of all transition matrices.
     * @throws IOException If an I/O error occurs while retrieving transition matrices.
     */
    public Vector<Pair<Pair<String, String>, int[][]>> getAll() throws IOException {
        Vector<String> fileNames = this.csvCtrl.getFileNames("Transitions");

        Vector<Pair<Pair<String, String>, int[][]>> listTransitions = new Vector<>();
        for (String file : fileNames) {
            listTransitions.add(getStats(file));
        }
        return listTransitions;
    }

    /**
     * Retrieves information about a specific transition matrix by name.
     *
     * @param name The name of the transition matrix to retrieve.
     * @return A Pair object representing the name, associated alphabet, and transition matrix values of the specified transition matrix.
     * @throws IOException If an I/O error occurs while retrieving the transition matrix.
     */
    public Pair<Pair<String, String>, int[][]> getTransition(String name) throws IOException {
        String[] keyRelation = this.csvCtrl.getKeyRelation("Transitions", name);
        if (keyRelation != null) return getStats(keyRelation[1]);
        else return null;
    }

    /**
     * Retrieves information about a specific transition matrix by file name.
     *
     * @param fileName The file name of the transition matrix to retrieve.
     * @return A Pair object representing the name, associated alphabet, and transition matrix values of the specified transition matrix.
     * @throws IOException If an I/O error occurs while retrieving the transition matrix.
     */
    public Pair<Pair<String, String>, int[][]> getStats(String fileName) throws IOException {
        String[] transitionStats = csvCtrl.getStatsFromFile("Transitions", fileName);

        int numDimension = Integer.parseInt(transitionStats[2]), pointer = 3;
        int[][] transitions = new int[numDimension][numDimension];
        for (int i = 0; i < numDimension; ++i) {
            for (int j = 0; j < numDimension; ++j) {
                transitions[i][j] = Character.getNumericValue(transitionStats[pointer].charAt(0));
                ++pointer;
            }
        }
        Pair<String, String> transParam = new Pair<>();
        transParam.first = transitionStats[0];
        transParam.second = transitionStats[1];
        Pair<Pair<String, String>, int[][]> transition = new Pair<>();
        transition.first = transParam;
        transition.second = transitions;
        return transition;
    }

    /**
     * Creates a new transition matrix with the specified name, associated alphabet name, and transition matrix values.
     *
     * @param name          The name of the new transition matrix.
     * @param alphabetName  The name of the associated alphabet.
     * @param transitions   The 2D array representing the transition matrix values.
     * @throws IOException If an I/O error occurs while creating the transition matrix.
     */
    public void createTransition(String name, String alphabetName, int[][] transitions) throws IOException {
        if (getTransition(name) != null) return;

        StringBuilder transitionToString = new StringBuilder(name);
        transitionToString.append("\n").append(alphabetName).append("\n").append(transitions.length);
        for (int[] transition : transitions) {
            for (int t : transition) {
                transitionToString.append("\n").append(t);
            }
        }

        this.csvCtrl.createFile("Transitions", name, String.valueOf(transitionToString));
    }

    /**
     * Deletes a specified transition matrix by name.
     *
     * @param name The name of the transition matrix to delete.
     * @throws IOException If an I/O error occurs while deleting the transition matrix.
     * @throws FileNotDeleted If the file cannot be deleted.
     */
    public void deleteTransition(String name) throws IOException, FileNotDeleted {
        String[] keyRelation = this.csvCtrl.getKeyRelation("Transitions", name);
        if (keyRelation != null) this.csvCtrl.deleteFile("Transitions", keyRelation);
    }

    /**
     * Retrieves the text content of a file specified by the given path.
     *
     * @param path The path of the file to read.
     * @return The text content of the file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public String getTextFromFile(String path) throws IOException {
        // Retrieves statistics from the CSV controller for the specified file path.
        String[] fileStats = this.csvCtrl.getStatsFromPath(path);

        // Returns the first element from the file statistics, assumed to be the text content.
        return fileStats[0];
    }

    /**
     * Retrieves an array of words from a file specified by the given path.
     *
     * @param path The path of the file to read.
     * @return An array of words extracted from the file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public String[] getWordsFromFile(String path) throws IOException {
        // Retrieves statistics from the CSV controller for the specified file path.
        return this.csvCtrl.getStatsFromPath(path);
    }

    /**
     * Retrieves an array of frequencies from a file specified by the given path.
     *
     * @param path The path of the file to read.
     * @return An array of integer frequencies extracted from the file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public int[] getFrequenciesFromFile(String path) throws IOException {
        // Retrieves statistics from the CSV controller for the specified file path.
        String[] fileStats = this.csvCtrl.getStatsFromPath(path);

        // Converts each element in the file statistics array to an integer and stores in a new array.
        int[] frequencies = new int[fileStats.length];
        for (int i = 0; i < fileStats.length; ++i) {
            frequencies[i] = Integer.parseInt(fileStats[i]);
        }

        // Returns the array of integer frequencies.
        return frequencies;
    }
}
