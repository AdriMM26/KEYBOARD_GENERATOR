package Domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * QAPAlgorithm implements an algorithm based on the Quadratic Assignment Problem (QAP), used to create a Keyboard.
 * It provides methods for obtaining positions of the characters of a Keyboard that minimize its cost associated with the given TransitionMatrix.
 */
public class QAPAlgorithm {

    private int reps;
    private int limit;

    /**
     * Constructs a QAPAlgorithm with default settings.
     */
    public QAPAlgorithm() {}

    /**
     * Gets the positions of the characters of a Keyboard that minimize its cost associated with the given TransitionMatrix.
     *
     * @param transitTable      The matrix extracted from a TransitionMatrix, representing the number of times between 2 letters that the first letter will be written after the second letter.
     * @param greedyPositions   A possible solution of the Keyboard, obtained by using a GreedyAlgorithm.
     * @param initialbound      The cost of the solution given by the GreedyAlgorithm.
     * @return The possible solutions of the Keyboard that minimize the cost.
     */
    public int[][] getPositions(int[][] transitTable, int[][] greedyPositions, int initialbound) {
        QAPPartialSolution greedySol = new QAPPartialSolution(greedyPositions, initialbound);
        int[][] availableCharacters = getAvailableCharacters(transitTable);
        Arrays.sort(availableCharacters, Comparator.comparingInt(p -> p[2]));

        int[][] positions = new int[greedyPositions.length][greedyPositions[0].length];
        for (int i = 0; i < positions.length; ++i) {
            for (int j = 0; j < positions[0].length; ++j) {
                if ((positions[0].length * i + j) < transitTable.length) positions[i][j] = -1;
                else positions[i][j] = -2;
            }
        }

        double[][][][] keyDistances = new double[greedyPositions.length][greedyPositions[0].length][greedyPositions.length][greedyPositions.length];
        for (int i = 0; i < keyDistances.length; ++i) {
            for (int j = 0; j < keyDistances[0].length; ++j) {

                for (int i2 = 0; i2 < keyDistances.length; ++i2) {
                    for (int j2 = 0; j2 < keyDistances[0].length; ++j2) {
                        keyDistances[i][j][i2][j2] = Math.sqrt(Math.pow(2, Math.abs(i - i2)) + Math.pow(2, Math.abs(j - j2)));
                    }
                }

            }
        }

        reps = 1;
        limit = (int) (1000000 / (Math.pow(2, transitTable.length / 10 - 2)));
        QAPPartialSolution solution = partialPositions(transitTable, greedySol, availableCharacters, positions, 0, keyDistances);
        return solution.getSolution();
    }

    /**
     * Gives the available characters yet to be placed on the Keyboard, based on the given TransitionMatrix.
     *
     * @param transitTable The matrix extracted from a TransitionMatrix, representing the number of times between 2 letters that the first letter will be written after the second letter.
     * @return The available characters yet to be placed on the Keyboard (initially all of them), along with the times that they appear in the TransitionMatrix.
     */
    private int[][] getAvailableCharacters(int[][] transitTable) {
        int[][] availableCharacters = new int[transitTable.length][3];

        for (int i = 0; i < transitTable.length; ++i) {
            int sum = 0;
            for (int j = 0; j < transitTable[i].length; ++j) {
                sum += transitTable[i][j];
            }
            availableCharacters[i][0] = i;
            availableCharacters[i][1] = 1;
            availableCharacters[i][2] = sum;
        }

        return availableCharacters;
    }

    /**
     * Recursively explores partial positions to find the optimal solution for the QAP.
     *
     * @param transitTable     The matrix extracted from a TransitionMatrix, representing the number of times between 2 letters that the first letter will be written after the second letter.
     * @param bestSolution     The best possible solution of the positions of the characters of the Keyboard found so far.
     * @param availableChars   List of the available characters yet to be placed on the Keyboard.
     * @param partialPositions The positions of the characters already on a possible Keyboard.
     * @param charnum          The number of the characters already placed in the partial Keyboard.
     * @param keydistances     Precomputation of the distances between the keys in the Keyboard.
     * @return The best solution on the Keyboards generated in this branch of the tree of solutions, or the bestSolution given by the parameters if there are no better Keyboards in this branch or if the limit number of recursions has been surpassed.
     */
    private QAPPartialSolution partialPositions(int[][] transitTable, QAPPartialSolution bestSolution, int[][] availableChars, int[][] partialPositions, int charnum, double[][][][] keydistances) {
        if (reps >= limit) return bestSolution;
        QAPPartialSolution newBestSolution = bestSolution;

        int posi = charnum / partialPositions[0].length;
        int posj = charnum - posi * partialPositions[0].length;
        if (charnum == transitTable.length - 1) {
            int i = 0;
            while (availableChars[i][1] == 0) ++i;
            partialPositions[posi][posj] = availableChars[i][0];

            double bound = calculateBound(partialPositions, transitTable, availableChars, charnum, keydistances);
            if (bound < newBestSolution.getCost()) {
                newBestSolution = new QAPPartialSolution(partialPositions, bound);
            }

            availableChars[i][1] = 1;

            return newBestSolution;
        } else {
            for (int i = 0; i < availableChars.length; ++i) {
                if (availableChars[i][1] == 1) {
                    partialPositions[posi][posj] = availableChars[i][0];
                    availableChars[i][1] = 0;

                    if (calculateBound(partialPositions, transitTable, availableChars, charnum, keydistances) < newBestSolution.getCost()) {
                        int[][] partialPositions2 = new int[partialPositions.length][partialPositions[0].length];
                        for (int j = 0; j < partialPositions.length; ++j) {
                            System.arraycopy(partialPositions[j], 0, partialPositions2[j], 0, partialPositions[0].length);
                        }
                        ++reps;
                        newBestSolution = partialPositions(transitTable, newBestSolution, availableChars, partialPositions2, charnum + 1, keydistances);
                    }

                    availableChars[i][1] = 1;
                }
            }

            return newBestSolution;
        }
    }

    /**
     * Calculates the lower bound, never lower than the lowest cost of all the Keyboards that can be created in this branch of the tree of solutions.
     *
     * @param partialPositions The positions of the characters already on a possible Keyboard.
     * @param transitTable     The matrix extracted from a TransitionMatrix, representing the number of times between 2 letters that the first letter will be written after the second letter.
     * @param availableChars   List of the available characters yet to be placed on the Keyboard.
     * @param usedChars          The number of the characters already placed in the partial Keyboard.
     * @param keydistances     Precomputation of the distances between the keys in the Keyboard.
     * @return The calculated bound.
     */
    private double calculateBound(int[][] partialPositions, int[][] transitTable, int[][] availableChars, int usedChars, double[][][][] keydistances) {
        if(usedChars == transitTable.length-1) return firstTerm(partialPositions, transitTable, usedChars, keydistances);
        return firstTerm(partialPositions, transitTable, usedChars, keydistances) + term2and3(partialPositions, transitTable, availableChars, usedChars, keydistances);
    }

    /**
     * Calculates the first term of the bound.
     *
     * @param partialPositions The positions of the characters already on a possible Keyboard.
     * @param transitTable     The matrix extracted from a TransitionMatrix, representing the number of times between 2 letters that the first letter will be written after the second letter.
     * @param usedChars          The number of the characters already placed in the partial Keyboard.
     * @param keydistances     Precomputation of the distances between the keys in the Keyboard.
     * @return The calculated bound.
     */
    private double firstTerm(int[][] partialPositions, int[][] transitTable, int usedChars, double[][][][] keydistances) {
        double sum = 0; //primer terme del calcul de la cota de Gilmore-Lawler
        int maxi = (usedChars/partialPositions[0].length)+1;
        for(int i = 0; i < maxi; ++i) {
            for(int j = 0; j < partialPositions[0].length && partialPositions[i][j] >= 0; ++j) {

                for(int i2 = 0; i2 < maxi; ++i2) {
                    for(int j2 = 0; j2 < partialPositions[0].length && partialPositions[i2][j2] >= 0; ++j2) {
                        if(i != i2 || j != j2) {
                            int transits = transitTable[partialPositions[i2][j2]][partialPositions[i][j]];
                            sum += keydistances[i][j][i2][j2]*transits;
                        }
                    }
                }
            }
        }
        return sum;
    }

    /**
     * Calculates the second and third term of the bound.
     *
     * @param partialPositions The positions of the characters already on a possible Keyboard.
     * @param transitTable     The matrix extracted from a TransitionMatrix, representing the number of times between 2 letters that the first letter will be written after the second letter.
     * @param availableChars   List of the available characters yet to be placed on the Keyboard.
     * @param usedChars          The number of the characters already placed in the partial Keyboard.
     * @param keydistances     Precomputation of the distances between the keys in the Keyboard.
     * @return The calculated bound.
     */
    private double term2and3(int[][] partialPositions, int[][] transitTable, int[][] availableChars, int usedChars, double[][][][] keydistances) {
        int notUsedCharsSize = transitTable.length-usedChars-1;
        int[] notUsedChars = new int[notUsedCharsSize];
        int avCharsIt = 0;
        for(int i = 0; i < notUsedCharsSize; ++i) {
            while(availableChars[avCharsIt][1] == 0) ++avCharsIt;
            notUsedChars[i] = availableChars[avCharsIt][0];
            ++avCharsIt;
        }

        double[][] aproxMatrix = new double[notUsedCharsSize][notUsedCharsSize];

        for(int i = 0; i < partialPositions.length && partialPositions[i][0] >= 0; ++i) {
            for(int j = 0; j < partialPositions[0].length && partialPositions[i][j] >= 0; ++j) {
                int mini = (usedChars+1)/partialPositions[0].length;
                int pos = 0;

                for(int i2 = mini; i2 < partialPositions.length; ++i2) {
                    for(int j2 = 0; j2 < partialPositions[0].length; ++j2) {
                        if(partialPositions[i2][j2] == -1) {
                            for(int k = 0; k < aproxMatrix.length; ++k) {
                                int transits = transitTable[notUsedChars[k]][partialPositions[i][j]] + transitTable[partialPositions[i][j]][notUsedChars[k]];
                                aproxMatrix[k][pos]+= keydistances[i][j][i2][j2] * transits;
                            }
                            ++pos;
                        }
                    }
                }
            }
        }

        Double[][] D = new Double[notUsedCharsSize][notUsedCharsSize-1];
        int mini = (usedChars+1)/partialPositions[0].length;
        int pos = 0;
        for(int i = mini; i < partialPositions.length; ++i) {
            for(int j = 0; j < partialPositions[0].length; ++j) {

                if(partialPositions[i][j] == -1) {
                    int elem = 0;
                    for (int i2 = mini; i2 < partialPositions.length; ++i2) {
                        for (int j2 = 0; j2 < partialPositions[0].length; ++j2) {
                            if (partialPositions[i2][j2] == -1 && (i != i2 || j != j2)) {
                                D[pos][elem] = keydistances[i][j][i2][j2];
                                ++elem;
                            }
                        }
                    }
                    ++pos;
                }
            }
        }

        for(int i = 0; i < notUsedCharsSize; ++i) {
            Arrays.sort(D[i], Collections.reverseOrder());
        }
        for(int i = 0; i < notUsedCharsSize; ++i) {
            int[] T = new int[notUsedCharsSize-1];
            int k = 0;
            for(int j = 0; j < notUsedCharsSize; ++j) {
                if(i != j) {
                    T[k] = transitTable[notUsedChars[j]][notUsedChars[i]];
                    ++k;
                }
            }
            Arrays.sort(T);
            for(int l = 0; l < notUsedCharsSize; ++l) {
                double sum = 0;
                for(int m = 0; m < notUsedCharsSize-1; ++m) {
                    sum += T[m]*D[l][m];
                }

                aproxMatrix[i][l] += sum;
            }
        }

        double sum = 0;
        for(int i = 0; i < aproxMatrix.length; ++i) {
            double min = aproxMatrix[i][0];
            for(int j = 1; j < aproxMatrix.length; ++j) {
                if(aproxMatrix[i][j] < min) min = aproxMatrix[i][j];
            }
            sum += min;
        }

        return sum;
    }
}