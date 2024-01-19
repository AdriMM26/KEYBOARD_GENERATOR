package Persistence;

import Exceptions.FileNotDeleted;

import java.io.*;
import java.util.*;

/**
 * The CSVController class manages operations related to reading from and writing to CSV files.
 */
public class CSVController {

    private final String path;

    /**
     * Constructs an instance of CSVController with a specified data path.
     * The data path is determined based on the classpath and project structure.
     */
    public CSVController() {
        String[] classPathEntries = System.getProperty("java.class.path").split(File.pathSeparator);
        char c = classPathEntries[0].charAt(classPathEntries[0].length() - 8); // char know if it's working from windows or linux
        if (c == '/') { // LINUX
            this.path = classPathEntries[0].replace("EXE/CLASSES", "") + "DATA/";
        } else { // WINDOWS
            this.path = classPathEntries[0].replace("EXE" + "\\" + "CLASSES", "") + "DATA" + "\\";
        }
    }

    /**
     * Retrieves the names of files of a specified type from the index CSV file.
     *
     * @param type The type of files to retrieve.
     * @return A Vector containing the names of files of the specified type.
     * @throws IOException If an I/O error occurs while reading the index CSV file.
     */
    public Vector<String> getFileNames(String type) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(this.path + type + "/index.csv"));
        String line;
        Vector<String> fileNames = new Vector<>();
        while ((line = br.readLine()) != null) {
            String[] keyRelation = line.split(",");
            fileNames.add(keyRelation[1]);
        }
        br.close();
        return fileNames;
    }

    /**
     * Retrieves statistics from a specified CSV file of a given type.
     *
     * @param type     The type of the CSV file.
     * @param nameFile The name of the CSV file.
     * @return An array containing the statistics from the specified CSV file.
     * @throws IOException If an I/O error occurs while reading the CSV file.
     */
    public String[] getStatsFromFile(String type, String nameFile) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(this.path + type + "/" + nameFile + ".csv"));
        String line;
        ArrayList<String> stats = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            stats.add(line);
        }
        br.close();
        return stats.toArray(new String[0]);
    }

    /**
     * Reads the content of a file specified by the given path and returns an array of strings, each representing a line in the file.
     *
     * @param pathFile The path of the file to read.
     * @return An array of strings containing the lines of the file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public String[] getStatsFromPath(String pathFile) throws IOException {
        // Creates a BufferedReader to read from the specified file path.
        BufferedReader br = new BufferedReader(new FileReader(pathFile));

        // Reads each line from the file and adds it to an ArrayList.
        String line;
        ArrayList<String> stats = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            stats.add(line);
        }

        // Closes the BufferedReader to release resources.
        br.close();

        // Converts the ArrayList to an array of strings and returns it.
        return stats.toArray(new String[0]);
    }


    /**
     * Retrieves the key relation from the index CSV file for a specified name and type.
     *
     * @param type The type of files to search in the index CSV.
     * @param name The name to search for in the index CSV.
     * @return An array containing the key relation if found, or null if not found.
     * @throws IOException If an I/O error occurs while reading the index CSV file.
     */
    public String[] getKeyRelation(String type, String name) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(this.path + type + "/index.csv"));
        String line;
        while ((line = br.readLine()) != null) {
            String[] keyRelation = line.split(",");
            if (Objects.equals(keyRelation[0], name)) {
                br.close();
                return keyRelation;
            }
        }
        br.close();
        return null;
    }

    /**
     * Deletes a file of a specified type based on key relations and updates the index CSV file.
     *
     * @param type        The type of files to delete.
     * @param keyRelation The key relation associated with the file to delete.
     * @throws IOException      If an I/O error occurs while deleting or modifying the CSV files.
     * @throws FileNotDeleted   If the file cannot be deleted.
     */
    public void deleteFile(String type, String[] keyRelation) throws IOException, FileNotDeleted {
        File keyFile = new File(this.path + type + "/" + keyRelation[1] + ".csv");
        if (keyFile.delete()) {
            BufferedReader br = new BufferedReader(new FileReader(this.path + type + "/index.csv"));

            String line;
            StringBuilder finalElements = new StringBuilder();
            while ((line = br.readLine()) != null) {
                String[] lineSplit = line.split(",");
                if (!Objects.equals(keyRelation[0], lineSplit[0])) {
                    finalElements.append(line).append("\n");
                }
            }
            try (FileWriter fw = new FileWriter(this.path + type + "/index.csv", false)) {
                fw.write(String.valueOf(finalElements));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new FileNotDeleted("File " + keyFile + " NOT DELETED");
        }
    }

    /**
     * Creates a new file of a specified type, generates a unique filename, and updates the index CSV file.
     *
     * @param type        The type of file to create.
     * @param name        The name of the new file.
     * @param statsString The statistics string to be written to the new file.
     */
    public void createFile(String type, String name, String statsString) {
        byte[] arrayBy = new byte[7];
        new Random().nextBytes(arrayBy);
        int leftLimit = 97, rightLimit = 122, targetStringLength = 8;
        Random random = new Random();

        String fileName = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        try (FileWriter fw = new FileWriter(this.path + type + "/index.csv", true)) {
            fw.append(name).append(",").append(fileName).append("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File statsFile = new File(this.path + type + "/" + fileName + ".csv");
        try (PrintWriter pw = new PrintWriter(statsFile)) {
            pw.println(statsString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Modifies an existing file of a specified type with new statistics and updates the file.
     *
     * @param type       The type of file to modify.
     * @param nameFile   The name of the file to modify.
     * @param statsString The new statistics string to be written to the file.
     */
    public void modifyFile(String type, String nameFile, String statsString) {
        try (FileWriter fw = new FileWriter(this.path + type + "/" + nameFile + ".csv", false)) {
            fw.write(statsString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
