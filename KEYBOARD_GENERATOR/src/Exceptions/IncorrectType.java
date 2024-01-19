package Exceptions;

/**
 * The `IncorrectType` class represents an exception indicating that an incorrect type is encountered.
 */
public class IncorrectType extends Exception {
    /**
     * Constructs a new `IncorrectType` instance with the specified error message.
     *
     * @param errorMessage The error message to be associated with this exception.
     */
    public IncorrectType(String errorMessage) {
        super(errorMessage);
    }
}
