package Testers;

import Exceptions.FileNotDeleted;
import Persistence.CSVController;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import static org.junit.Assert.*;

/**
 * Test class for the CSVController class.
 */
public class CSVTest {

    private CSVController csvCtrl;
    private String type;
    private String path;

    /**
     * Set up the test environment by initializing the CSVController, type, and path.
     */
    @Before
    public void setUp() {
        this.csvCtrl = new CSVController();
        this.type = "CSVTesterData";

        String[] classPathEntries = System.getProperty("java.class.path").split(File.pathSeparator);
        char c = classPathEntries[4].charAt(classPathEntries[4].length() - 8); // char know if it's working from windows or linux
        if (c == '/') { // LINUX
            this.path = classPathEntries[4].replace("EXE/CLASSES", "") + "DATA/";
        } else { // WINDOWS
            this.path = classPathEntries[4].replace("EXE" + "\\" + "CLASSES", "") + "DATA" + "\\";
        }

        try (FileWriter fw = new FileWriter(this.path + this.type + "/index.csv", true)) {
            fw.append("alpha-1").append(",").append("file-1").append("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (FileWriter fw = new FileWriter(this.path + this.type + "/index.csv", true)) {
            fw.append("alpha-2").append(",").append("file-2").append("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Test the {@code getFileNames} method of the CSVController class.
     */
    @Test
    public void testGetFileNames() {
        Vector<String> names = new Vector<>();
        Vector<String> namesFile = new Vector<>();
        names.add("alpha-1");
        names.add("alpha-2");
        try {
            namesFile = this.csvCtrl.getFileNames(this.type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(names, namesFile);
    }

    /**
     * Test the {@code getStatsFromFile} method of the CSVController class.
     *
     * @throws IOException If an I/O error occurs.
     */
    @Test
    public void testGetStatsFromFile() throws IOException {
        String[] stats = {"alpha-1", "5", "a", "b", "c", "d", "e"};
        String[] statsFile;
        statsFile = this.csvCtrl.getStatsFromFile(this.type, "file-1");
        assertArrayEquals(stats, statsFile);
    }

    /**
     * Test the {@code getStatsFromPath} method of the CSVController class.
     *
     * @throws IOException If an I/O error occurs.
     */
    @Test
    public void testGetStatsFromPath() throws IOException {
        String[] stats = {"alpha-1", "5", "a", "b", "c", "d", "e"};
        String[] statsFile;
        statsFile = this.csvCtrl.getStatsFromPath(this.path + this.type + "/" + "file-1" + ".csv");
        assertArrayEquals(stats, statsFile);
    }

    /**
     * Test the {@code getKeyRelation} method of the CSVController class.
     *
     * @throws IOException If an I/O error occurs.
     */
    @Test
    public void testGetKeyRelation() throws IOException {
        String[] relation = {"alpha-1", "file-1"};
        String[] relationFile;
        relationFile = this.csvCtrl.getKeyRelation(this.type, "alpha-1");
        assertArrayEquals(relation, relationFile);
    }

    /**
     * Test the {@code deleteFile} method of the CSVController class.
     *
     * @throws FileNotDeleted If the file could not be deleted.
     * @throws IOException If an I/O error occurs.
     */
    @Test
    public void testDeleteFile() throws FileNotDeleted, IOException {
        String stats = "alpha-3,4,a,b,c,d";

        try (FileWriter fw = new FileWriter(this.path + this.type + "/index.csv", true)) {
            fw.append("alpha-3").append(",").append("file-3").append("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File statsFile = new File(this.path + this.type + "/file-3.csv");
        try (PrintWriter pw = new PrintWriter(statsFile)) {
            pw.println(stats);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(this.path);

        this.csvCtrl.deleteFile(this.type, new String[]{"alpha-3", "file-3"});
        File f = new File(this.path + this.type + "/file-3.csv");
        assertTrue(f.exists());
    }
}