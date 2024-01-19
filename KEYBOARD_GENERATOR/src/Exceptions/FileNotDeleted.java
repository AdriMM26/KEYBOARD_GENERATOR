package Exceptions;

/**
 * The `FileNotDeleted` class represents an exception indicating that a file could not be deleted.
 */
public class FileNotDeleted extends Exception {
    /**
     * Constructs a new `FileNotDeleted` instance with the specified error message.
     *
     * @param errorMessage The error message to be associated with this exception.
     */
    public FileNotDeleted(String errorMessage) {
        super(errorMessage);
    }
}
