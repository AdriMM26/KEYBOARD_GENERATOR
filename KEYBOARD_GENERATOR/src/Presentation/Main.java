package Presentation;

import Exceptions.NotContainsKey;
import Presentation.PresentationController;

import java.io.IOException;


/**
 * The Main class serves as the entry point for the application.
 * It initializes a PresentationController to manage the presentation layer.
 */
public class Main {
    /**
     * The main method, starting point of the application.
     *
     * @param args Command-line arguments (not used in this application).
     * @throws IOException      If an I/O error occurs.
     * @throws NotContainsKey   If a required key is not present.
     */
    public static void main(String[] args) throws IOException, NotContainsKey {
        PresentationController pc = new PresentationController();
    }
}