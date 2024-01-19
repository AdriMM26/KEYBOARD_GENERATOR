package Presentation;

import Exceptions.NotContainsKey;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * The AlphabetListView class is responsible for managing the alphabet list view in the presentation layer.
 * It provides functionality to display and interact with a list of alphabets.
 */
public class AlphabetListView {

    /**
     * The JPanel instance representing the alphabet list view.
     */
    private JPanel alphabetList;

    /**
     * The JButton for adding a new alphabet.
     */
    private JButton addAlphabetButton;

    /**
     * The JButton for deleting an existing alphabet.
     */
    private JButton deleteAlphabetButton;

    /**
     * The JLabel displaying exception text when there are no alphabets.
     */
    private JLabel exceptionText;

    /**
     * An array of JLabels representing the names of alphabets.
     */
    private JLabel[] names;

    private JLabel error;

    private JTextArea textField1;
    private JButton exitButton;

    /**
     * Constructs an AlphabetListView with the given InputViewController.
     *
     * @param inputViewController The InputViewController used to retrieve the list of alphabets.
     * @throws IOException If an I/O error occurs.
     */
    public AlphabetListView(InputViewController inputViewController) throws IOException {
        try {
            String[] alphabets = inputViewController.listAlphabets();
            if (alphabets.length == 0) {
                throw new IOException();
            }
            for (String alpha : alphabets) {
                textField1.setText(textField1.getText() + alpha + "\n");

                char[] alphaChars = inputViewController.getAlphabet(alpha);
                for (char c : alphaChars) {
                    textField1.setText(textField1.getText() + " " + c);
                }
                textField1.setText(textField1.getText() + "\n");
            }
            error.setVisible(false);
        } catch (Exception e) {
            error.setVisible(true);
            error.setText("There are no alphabets yet.");
        }
        alphabetList.setVisible(true);

        addAlphabetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("AddAlphabet");
                JFrame origin = (JFrame) SwingUtilities.getWindowAncestor(alphabetList);
                origin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                origin.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                AddAlphabetView addAlphabetView = null;
                addAlphabetView = new AddAlphabetView(inputViewController, alphabetList);
                frame.setContentPane(addAlphabetView.getAddAlphabetView());
                frame.setSize(1000, 800);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        deleteAlphabetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("DeleteAlphabet");
                JFrame origin = (JFrame) SwingUtilities.getWindowAncestor(alphabetList);
                origin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                origin.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                DeleteAlphabetView deleteAlphabetView = null;
                deleteAlphabetView = new DeleteAlphabetView(inputViewController, alphabetList);
                frame.setContentPane(deleteAlphabetView.getDeleteAlphabetView());
                frame.setSize(1000, 800);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(alphabetList);
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
     * Retrieves the alphabet list view container.
     *
     * @return The Container representing the alphabet list view.
     */
    public Container getAlphabetList() {
        return alphabetList;
    }
}