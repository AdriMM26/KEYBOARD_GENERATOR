package Domain;

/**
 * Represents a possible solution by the QAP Algorithm for the creation of a Keyboard.
 * The QAPPartialSolution class encapsulates a possible solution matrix along with its associated cost.
 */
public class QAPPartialSolution {
    /**
     * The matrix representing a possible solution of the QAP.
     */
    private int[][] solution;
    /**
     * The cost associated with the solution.
     */
    private double cost;

    /**
     * Constructs a QAPPartialSolution with the given solution matrix and cost.
     *
     * @param solution The matrix representing a possible solution of the QAP.
     * @param cost     The cost associated with the solution.
     */
    public QAPPartialSolution(int[][] solution, double cost) {
        this.solution = solution;
        this.cost = cost;
    }
    /**
     * Gets the solution matrix.
     *
     * @return The matrix representing the solution to the QAP.
     */
    public int[][] getSolution() {return solution;}

    /**
     * Gets the cost associated with the solution matrix.
     *
     * @return The cost of the solution.
     */
    public double getCost() {return cost;}
}