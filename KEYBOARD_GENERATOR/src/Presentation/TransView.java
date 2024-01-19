package Presentation;

import Exceptions.NotContainsKey;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * The TransView class is responsible for managing the transition matrix view in the presentation layer.
 * It provides functionality to display and interact with a list of transition matrices.
 */
public class TransView {

    /**
     * The JPanel instance representing the transition matrix view.
     */
    private JPanel transView;

    /**
     * The JButton for adding a new text matrix.
     */
    private JButton addTextButton;

    /**
     * The JButton for adding a new word frequency list matrix.
     */
    private JButton addWordFrequencyListButton;

    /**
     * The JButton for deleting an existing transition matrix.
     */
    private JButton deleteTransitionButton;
    private JTextArea transList;
    private JLabel error;
    private JButton exitButton;

    /**
     * The JLabel displaying exception text when there are no transition matrices.
     */
    private JLabel exceptionText;

    /**
     * An array of JLabels representing the names of transition matrices.
     */
    private JLabel[] names;

    /**
     * Constructs a TransView with the given TransitionViewController.
     *
     * @param transitionViewController The TransitionViewController used to retrieve the list of transition matrices.
     * @throws IOException If an I/O error occurs.
     */
    public TransView(TransitionViewController transitionViewController) throws IOException {

        try {
            String[] transitions = transitionViewController.listTransitions();
            if (transitions.length == 0) {
                throw new IOException();
            }
            for (String transition : transitions) {
                transList.setText(transList.getText() + transition + "\n");
            }
            error.setVisible(false);
        } catch (Exception e) {
            error.setVisible(true);
            error.setText("There are not Transitions yet");
        }
        transView.setVisible(true);

        addTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("AddText");
                JFrame origin = (JFrame) SwingUtilities.getWindowAncestor(transView);
                origin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                origin.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                AddTextView addTextView = null;
                addTextView = new AddTextView(transitionViewController, transView);
                frame.setContentPane(addTextView.getAddTextView());
                frame.setSize(1000, 800);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        addWordFrequencyListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("AddWordFrequencyList");
                JFrame origin = (JFrame) SwingUtilities.getWindowAncestor(transView);
                origin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                origin.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                AddWordFrequencyListView addWordFrequencyListView = null;
                addWordFrequencyListView = new AddWordFrequencyListView(transitionViewController, transView);
                frame.setContentPane(addWordFrequencyListView.getAddWordFrequencyListView());
                frame.setSize(1000, 800);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        deleteTransitionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("DeleteTransition");
                JFrame origin = (JFrame) SwingUtilities.getWindowAncestor(transView);
                origin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                origin.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                DeleteTransitionView deleteTransitionView = null;
                deleteTransitionView = new DeleteTransitionView(transitionViewController, transView);
                frame.setContentPane(deleteTransitionView.getDeleteTransitionView());
                frame.setSize(1000, 800);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        exitButton.addActionListener(new ActionListener() {
            /**
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(transView);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                try {
                    transitionViewController.restart();
                } catch (IOException | NotContainsKey ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }

    /**
     * Retrieves the transition matrix view container.
     *
     * @return The Container representing the transition matrix view.
     */
    public Container getTransView() {
        return transView;
    }

}