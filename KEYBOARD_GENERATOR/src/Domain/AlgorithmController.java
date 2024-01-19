package Domain;

/**
 * AlgorithmController is responsible for the creation of Keyboard
 * distributions, given a TransitionMatrix and a
 * chosen available algorithm.
 */
public class AlgorithmController {

    private GreedyAlgorithm greedy;
    private QAPAlgorithmController qapController;

    /**
     * Constructs an AlgorithmController with instances of GreedyAlgorithm and QAPAlgorithmController.
     */
    public AlgorithmController() {
        greedy = new GreedyAlgorithm();
        qapController = new QAPAlgorithmController();
    }

    /**
     * Uses the specified algorithm and TransitionMatrix to find the positions of
     * the characters of a Keyboard.
     *
     * @param transitTable The matrix extracted from a TransitionMatrix,
     *                     representing the number of times between 2 letters that
     *                     the first letter will be written after the second letter.
     * @param algorithm    The chosen algorithm to use.
     * @return The character positions of a Keyboard obtained by using the specified
     *         algorithm.
     */
    public int[][] useAlgorithm(int[][] transitTable, String algorithm) {
        if (algorithm.equals("Greedy")) return greedy.getPositions(transitTable);
        else if (algorithm.equals("QAP")) return qapController.getPositions(transitTable);
        else return new int[][] {{ -1 }};
    }
}