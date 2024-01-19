package Exceptions;

/**
 * The `IncorrectAlphabetType` class represents an exception indicating that an incorrect alphabet type is encountered.
 */
public class IncorrectAlphabetType extends Exception {
    /**
     * Constructs a new `IncorrectAlphabetType` instance with the specified error message.
     *
     * @param errorMessage The error message to be associated with this exception.
     */
    public IncorrectAlphabetType(String errorMessage) {
        super(errorMessage);
    }
}
