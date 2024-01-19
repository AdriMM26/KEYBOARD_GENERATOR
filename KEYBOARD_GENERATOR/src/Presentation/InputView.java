package Presentation;

import Exceptions.NotContainsKey;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * The InputView class is responsible for managing the main input view in the presentation layer.
 * It provides buttons to navigate to different views such as Alphabets and Transitions Matrix.
 */
public class InputView {

    /**
     * The JPanel instance representing the main input view.
     */
    private JPanel inputView;

    /**
     * The JButton for navigating to the Alphabets view.
     */
    private JButton alphabetsButton;

    /**
     * The JButton for navigating to the Transitions Matrix view.
     */
    private JButton transitionsButton;
    private JButton exitButton;

    /**
     * Constructs an InputView with the given InputViewController.
     *
     * @param inputViewController  The InputViewController associated with this InputView.
     * @param transitionViewController The TransitionViewController associated with this InputView.
     */
    public InputView(InputViewController inputViewController, TransitionViewController transitionViewController) {
        alphabetsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame origin = (JFrame) SwingUtilities.getWindowAncestor(inputView);
                origin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                JFrame frame = new JFrame("MyInputs");
                origin.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                AlphabetListView alphabetList = null;
                try {
                    alphabetList = new AlphabetListView(inputViewController);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                frame.setContentPane(alphabetList.getAlphabetList());
                frame.setSize(1000, 800);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

        transitionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame origin = (JFrame) SwingUtilities.getWindowAncestor(inputView);
                origin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                JFrame frame = new JFrame("Transitions Matrix(Texts/WordFrequencyLists)");
                origin.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                TransView transView = null;
                try {
                    transView = new TransView(transitionViewController);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                frame.setContentPane(transView.getTransView());
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
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(inputView);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                try {
                    inputViewController.restart();
                } catch (IOException | NotContainsKey ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    /**
     * Retrieves the main input view container.
     *
     * @return The Container representing the main input view.
     */
    public Container getInputList() {
        return inputView;
    }
}