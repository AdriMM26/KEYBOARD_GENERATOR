package Presentation;

import Exceptions.ContainsKey;
import Exceptions.IncorrectType;
import Exceptions.NotContainsKey;
import Exceptions.StringNotInAlphabet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

/**
 * The AddWordFrequencyListView class represents the view for adding a word frequency list matrix
 * to the specified alphabet in the transition controller. It includes UI components such as text fields
 * and buttons for user interaction.
 */
public class AddWordFrequencyListView {

    /**
     * The JTextField for entering the transition matrix key.
     */
    private JTextField IdTransitionField;

    /**
     * The JButton for canceling the operation.
     */
    private JButton cancelButton;

    /**
     * The JButton for accepting the input and adding the word frequency list matrix.
     */
    private JButton acceptButton;

    /**
     * The JPanel representing the main view for adding a word frequency list matrix.
     */
    private JPanel addWordFrequencyListView;
    private JButton uploadF;
    private JButton uploadW;
    private JComboBox alphComboBox;
    private JTextArea FrequenciesField;
    private JTextArea WordListField;

    /**
     * Constructs an AddWordFrequencyListView object with the specified TransitionViewController
     * and the parent JPanel.
     *
     * @param transitionViewController The TransitionViewController for handling operations.
     * @param father                   The parent JPanel.
     */
    public AddWordFrequencyListView(TransitionViewController transitionViewController, JPanel father) {
        String[] alphabets = new String[0];
        try {
            alphabets = transitionViewController.listAlphabets();
        } catch (IOException | NotContainsKey e) {
            throw new RuntimeException(e);
        }
        for (String alphabet : alphabets) alphComboBox.addItem(alphabet);
        // ActionListener for the cancel button
        cancelButton.addActionListener(new ActionListener() {

            /**
             * Closes the AddWordFrequencyListView when the cancel button is clicked.
             *
             * @param e The event to be processed.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(addWordFrequencyListView);
                frame.setTitle("TransList");
                try {
                    TransView transView = new TransView(transitionViewController);
                    frame.setContentPane(transView.getTransView());
                    frame.setVisible(true);
                } catch (IOException ex) {
                    JFrame frame2 = new JFrame("Error");
                    frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame2.setSize(500, 500);
                    frame2.setContentPane(new Error(ex.getMessage()).getErrorPanel());
                    frame2.setVisible(true);
                }
            }
        });

        // ActionListener for the accept button
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String[] frequenciesString = new String[0];

                    if (!Objects.equals(FrequenciesField.getText(), "")) frequenciesString = FrequenciesField.getText().split("\n");

                    int[] frequencies = new int[frequenciesString.length];
                    for (int i = 0; i < frequenciesString.length; ++i) {
                        frequencies[i] = Integer.parseInt(frequenciesString[i]);
                    }

                    String[] words = new String[0];
                    if(!Objects.equals(WordListField.getText(), "")) words = WordListField.getText().split("\n");

                    String alphabet = (String) alphComboBox.getSelectedItem();
                    transitionViewController.addMatrixWordFrequencyList(IdTransitionField.getText(), alphabet, frequencies, words);
                    JFrame frame = new JFrame("Word Frequency List Added");
                    frame.setContentPane(new OperationCorrect().getOperationCorrect());
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setSize(500, 500);
                    frame.setVisible(true);
                    JFrame listFrame = (JFrame) SwingUtilities.getWindowAncestor(addWordFrequencyListView);
                    listFrame.setTitle("TransList");
                    try {
                        TransView transView = new TransView(transitionViewController);
                        listFrame.setContentPane(transView.getTransView());
                        listFrame.setVisible(true);
                    } catch (IOException ex) {
                        JFrame frame2 = new JFrame("Error");
                        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame2.setSize(500, 500);
                        frame2.setContentPane(new Error(ex.getMessage()).getErrorPanel());
                        frame2.setVisible(true);
                    }
                } catch (NotContainsKey | ContainsKey | IOException | IncorrectType | NumberFormatException | StringNotInAlphabet ex) {
                    JFrame frame = new JFrame("Error");
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setSize(500, 500);
                    frame.setContentPane(new Error(ex.getMessage()).getErrorPanel());
                    frame.setVisible(true);
                }
            }
        });
        uploadF.addActionListener(new ActionListener() {
            /**
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Specify a file to save");
                int userSelection = fileChooser.showSaveDialog(addWordFrequencyListView);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    try {
                        int[] frequencies = transitionViewController.getFrequenciesFromFile(fileChooser.getSelectedFile().getAbsolutePath());
                        for (int frequency : frequencies) {
                            FrequenciesField.setText(FrequenciesField.getText() + frequency + "\n");
                        }
                    } catch (IOException ex) {
                        JFrame frame = new JFrame("Error");
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.setSize(500, 500);
                        frame.setContentPane(new Error(ex.getMessage()).getErrorPanel());
                        frame.setVisible(true);
                    }
                }
            }
        });
        uploadW.addActionListener(new ActionListener() {
            /**
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Specify a file to save");
                int userSelection = fileChooser.showSaveDialog(addWordFrequencyListView);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    try {
                        String [] words = transitionViewController.getWordsFromFile(fileChooser.getSelectedFile().getAbsolutePath());
                        for (String word : words) {
                            WordListField.setText(WordListField.getText() + word + "\n");
                        }
                    } catch (IOException ex) {
                        JFrame frame = new JFrame("Error");
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.setSize(500, 500);
                        frame.setContentPane(new Error(ex.getMessage()).getErrorPanel());
                        frame.setVisible(true);
                    }
                }
            }
        });
    }

    /**
     * Returns the main JPanel representing the AddWordFrequencyListView.
     *
     * @return The main JPanel for adding a word frequency list matrix.
     */
    public Container getAddWordFrequencyListView() {
        return addWordFrequencyListView;
    }
}