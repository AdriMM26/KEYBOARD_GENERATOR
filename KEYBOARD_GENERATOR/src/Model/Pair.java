package Model;

/**
 * The Pair class represents a simple data structure that holds two values of different types.
 *
 * @param <Type1> The type of the first value.
 * @param <Type2> The type of the second value.
 */
public class Pair<Type1, Type2> {

    /**
     * The first value of the pair.
     */
    public Type1 first;

    /**
     * The second value of the pair.
     */
    public Type2 second;

    /**
     * Constructs an empty Pair with both values set to null.
     */
    public Pair() {}

    /**
     * Constructs a Pair with the specified values.
     *
     * @param first  The value of the first element.
     * @param second The value of the second element.
     */
    public Pair(Type1 first, Type2 second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Sets the value of the first element in the Pair.
     *
     * @param first The new value for the first element.
     */
    public void setFirst(Type1 first) {
        this.first = first;
    }

    /**
     * Sets the value of the second element in the Pair.
     *
     * @param second The new value for the second element.
     */
    public void setSecond(Type2 second) {
        this.second = second;
    }

    /**
     * Retrieves the value of the first element in the Pair.
     *
     * @return The value of the first element.
     */
    public Type1 getFirst() {
        return first;
    }

    /**
     * Retrieves the value of the second element in the Pair.
     *
     * @return The value of the second element.
     */
    public Type2 getSecond() {
        return second;
    }
}