package Exceptions;

/**
 * Exception thrown when a string does not conform to the expected alphabet.
 * This exception is typically used to indicate that a string contains
 * characters outside the acceptable alphabet defined by the application.
 */
public class StringNotInAlphabet extends Exception {

    /**
     * Constructs a new StringNotInAlphabet exception with the specified error message.
     *
     * @param errorMessage The error message describing the reason for the exception.
     */
    public StringNotInAlphabet(String errorMessage) {
        super(errorMessage);
    }
}
