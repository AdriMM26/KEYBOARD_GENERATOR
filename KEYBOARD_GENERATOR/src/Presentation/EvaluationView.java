package Presentation;

import Exceptions.NotContainsKey;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * The `EvaluationView` class is responsible for managing the keyboard evaluation view in the presentation layer.
 * It provides a user interface with input fields and buttons to perform the evaluation operation.
 */
public class EvaluationView {
    /**
     * The `JTextField` for entering the name of the keyboard.
     */
    private JTextField nameKeyboardField;

    /**
     * The `JTextField` for entering the name of the transcription.
     */
    private JTextField nameTransField;

    /**
     * The `JLabel` displaying the evaluation score.
     */
    private JLabel score;

    /**
     * The "Accept" button for initiating the evaluation.
     */
    private JButton acceptButton;

    /**
     * The "Cancel" button for canceling the evaluation operation.
     */
    private JButton exitButton;

    /**
     * The `JPanel` representing the evaluation view.
     */
    private JPanel evaluationView;
    private JComboBox keyComboBox;
    private JComboBox transComboBox;

    /**
     * Constructs an `EvaluationView` with the specified `MainViewController`.
     *
     * @param mv The `MainViewController` associated with this `EvaluationView`.
     */
    public EvaluationView(MainViewController mv) {
        String[] keyboards = new String[0];
        try {
            keyboards = mv.listKeyboard();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NotContainsKey e) {
            throw new RuntimeException(e);
        }
        if (keyboards.length > 0)
            for (int i = 0; i < keyboards.length; ++i)
                keyComboBox.addItem(keyboards[i]);
        String[] transitions = new String[0];
        try {
            transitions = mv.listTransitions();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NotContainsKey e) {
            throw new RuntimeException(e);
        }
        if (transitions.length > 0)
            for (int i = 0; i < transitions.length; ++i)
                transComboBox.addItem(transitions[i]);
        acceptButton.addActionListener(new ActionListener() {
            /**
             * Processes the event when the "Accept" button is clicked.
             *
             * @param e The event to be processed.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String keyboard = (String) keyComboBox.getSelectedItem();
                    String transition = (String) transComboBox.getSelectedItem();
                    double value = mv.evaluateKeyboard(keyboard, transition);
                    if (value == 0 || value>=4) {
                        score.setForeground(new java.awt.Color(255, 0, 0));
                        score.setText("D");
                    }
                    else if (value >=1 && value<2){
                        score.setForeground(new java.awt.Color(53, 255, 0));
                        score.setText("A");
                    }
                    else if (value >=2 && value<3){
                        score.setForeground(new java.awt.Color(255, 255, 0));
                        score.setText("B");
                    }
                    else if (value >=3 && value<4){
                        score.setForeground(new java.awt.Color(248, 78, 16));
                        score.setText("C");
                    }
                } catch (NotContainsKey ex) {
                    JFrame frame = new JFrame("Error");
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setSize(500, 500);
                    frame.setContentPane(new Error(ex.getMessage()).getErrorPanel());
                    frame.setVisible(true);
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            /**
             * Processes the event when the "Exit" button is clicked.
             *
             * @param e The event to be processed.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(evaluationView);
                frame.setTitle("KeyboardList");
                try {
                    KeyboardList keyboardList = new KeyboardList(mv);
                    frame.setContentPane(keyboardList.getKeyboardList());
                    frame.setVisible(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    /**
     * Retrieves the evaluation view container.
     *
     * @return The `JPanel` representing the evaluation view.
     */
    public JPanel getEvaluationView() {
        return evaluationView;
    }
}
