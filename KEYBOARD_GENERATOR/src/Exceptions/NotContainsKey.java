package Exceptions;

/**
 * The `NotContainsKey` class represents an exception indicating that a specified key is not found.
 */
public class NotContainsKey extends Exception {
    /**
     * Constructs a new `NotContainsKey` instance with the specified error message.
     *
     * @param errorMessage The error message to be associated with this exception.
     */
    public NotContainsKey(String errorMessage) {
        super(errorMessage);
    }
}
