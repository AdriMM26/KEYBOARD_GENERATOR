package Exceptions;

/**
 * The `ContainsKey` class represents an exception indicating that a specific key is already present.
 */
public class ContainsKey extends Exception {
    /**
     * Constructs a new `ContainsKey` instance with the specified error message.
     *
     * @param errorMessage The error message to be associated with this exception.
     */
    public ContainsKey(String errorMessage) {
        super(errorMessage);
    }
}
