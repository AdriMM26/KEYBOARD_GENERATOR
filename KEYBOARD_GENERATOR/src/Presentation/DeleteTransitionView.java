package Presentation;

import Exceptions.FileNotDeleted;
import Exceptions.NotContainsKey;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * The DeleteTransitionView class represents the view for deleting a transition matrix from the
 * transition controller. It includes UI components such as text fields and buttons for user interaction.
 */
public class DeleteTransitionView {

    /**
     * The JTextField for entering the transition matrix key to be deleted.
     */
    private JTextField IdTransitionField;

    /**
     * The JButton for accepting the input and deleting the transition matrix.
     */
    private JButton acceptButton;

    /**
     * The JButton for canceling the operation.
     */
    private JButton cancelButton;

    /**
     * The JPanel representing the main view for deleting a transition matrix.
     */
    private JPanel deleteTransitionView;
    private JComboBox transComboBox;

    /**
     * Constructs a DeleteTransitionView object with the specified TransitionViewController
     * and the parent JPanel.
     *
     * @param transitionViewController The TransitionViewController for handling operations.
     * @param father                   The parent JPanel.
     */
    public DeleteTransitionView(TransitionViewController transitionViewController, JPanel father) {
        String[] transitions = new String[0];
        try {
            transitions = transitionViewController.listTransitions();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NotContainsKey e) {
            throw new RuntimeException(e);
        }
        if (transitions.length > 0)
            for (int i = 0; i < transitions.length; ++i)
                transComboBox.addItem(transitions[i]);

        // ActionListener for the cancel button
        cancelButton.addActionListener(new ActionListener() {
            /**
             * Closes the DeleteTransitionView when the cancel button is clicked.
             *
             * @param e The event to be processed.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(deleteTransitionView);
                frame.setTitle("TransList");
                try {
                    TransView transView = new TransView(transitionViewController);
                    frame.setContentPane(transView.getTransView());
                    frame.setVisible(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // ActionListener for the accept button
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Attempt to delete the transition matrix using the TransitionViewController
                    String transition = (String) transComboBox.getSelectedItem();
                    transitionViewController.deleteTransitionMatrix(transition);

                    // Display success message in a new frame
                    JFrame frame = new JFrame("Transition Deleted");
                    frame.setContentPane(new OperationCorrect().getOperationCorrect());
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setSize(500, 500);
                    frame.setVisible(true);
                    JFrame listFrame = (JFrame) SwingUtilities.getWindowAncestor(deleteTransitionView);
                    listFrame.setTitle("TransList");
                    try {
                        TransView transView = new TransView(transitionViewController);
                        listFrame.setContentPane(transView.getTransView());
                        listFrame.setVisible(true);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } catch (NotContainsKey | IOException | FileNotDeleted ex) {
                    // Display error message in case of an exception
                    JFrame frame = new JFrame("Error");
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setSize(500, 500);
                    frame.setContentPane(new Error(ex.getMessage()).getErrorPanel());
                    frame.setVisible(true);
                }
            }
        });
    }

    /**
     * Returns the main JPanel representing the DeleteTransitionView.
     *
     * @return The main JPanel for deleting a transition matrix.
     */
    public Container getDeleteTransitionView() {
        return deleteTransitionView;
    }
}