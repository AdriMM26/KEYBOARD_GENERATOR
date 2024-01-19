package Exceptions;

/**
 * The `AlphabetUsed` class represents an exception indicating that a specific alphabet has already been used.
 */
public class AlphabetUsed extends Exception {
    /**
     * Constructs a new `AlphabetUsed` instance with the specified error message.
     *
     * @param errorMessage The error message to be associated with this exception.
     */
    public AlphabetUsed(String errorMessage) {
        super(errorMessage);
    }
}
