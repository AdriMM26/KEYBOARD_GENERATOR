package Presentation;

import javax.swing.*;

/**
 * The `Error` class represents a simple error message panel.
 */
public class Error {
    /**
     * The `JLabel` displaying the error message.
     */
    private JLabel message;

    /**
     * The `JPanel` representing the error message panel.
     */
    private JPanel errorPanel;

    /**
     * Constructs a new `Error` instance with the specified error message.
     *
     * @param message The error message to be displayed.
     */
    public Error(String message) {
        this.message.setText(message);
    }

    /**
     * Gets the `JPanel` associated with this `Error` instance.
     *
     * @return The `JPanel` representing the error message panel.
     */
    public JPanel getErrorPanel() {
        return errorPanel;
    }
}
