package Domain;

/**
 * The QAPAlgorithmController class manages the interaction between the GreedyAlgorithm and QAPAlgorithm
 * for obtaining the positions of characters on a keyboard using the Quadratic Assignment Problem (QAP) algorithm.
 */
public class QAPAlgorithmController {

    private GreedyAlgorithm greedy;
    private QAPAlgorithm qap;

    /**
     * Constructs a QAPAlgorithmController with instances of GreedyAlgorithm and QAPAlgorithm.
     */
    public QAPAlgorithmController() {
        greedy = new GreedyAlgorithm();
        qap = new QAPAlgorithm();
    }

    /**
     * Gives the positions of characters on a Keyboard using the QAP algorithm.
     *
     * @param transitTable The matrix extracted from a TransitionMatrix, representing the number of times between 2 letters
     *                     that the first letter will be written after the second letter.
     * @return A two-dimensional array representing the positions of characters on the Keyboard.
     */
    public int[][] getPositions(int[][] transitTable) {
        int[][] positions = greedy.getPositions(transitTable);

        int sum = 0; // Number of characters (with repetitions) that appear in the transitTable
        for (int i = 0; i < transitTable.length; ++i) {
            for (int j = 0; j < transitTable[0].length; ++j) {
                if (i != j)
                    sum += transitTable[i][j];
            }
        }
        KeyboardAvaluator boardAvaluator = new KeyboardAvaluator();
        int initialbound = (int) (sum * boardAvaluator.avaluateKeyboard(positions, transitTable));
        return qap.getPositions(transitTable, positions, initialbound);
    }
}
