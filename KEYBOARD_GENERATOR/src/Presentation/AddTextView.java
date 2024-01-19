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
/**
 * The AddTextView class represents the view for adding a text matrix to the
 * specified alphabet in the transition controller. It includes UI components
 * such as text fields and buttons for user interaction.
 */
public class AddTextView {

    /**
     * The JPanel representing the main view for adding a text matrix.
     */
    private JPanel addTextView;

    /**
     * The JTextField for entering the transition matrix key.
     */
    private JTextField IdTransitionField;


    /**
     * The JButton for accepting the input and adding the text matrix.
     */
    private JButton acceptButton;

    /**
     * The JButton for canceling the operation.
     */
    private JButton cancelButton;
    /**
     * JButton for triggering the upload action.
     * Users can click this button to initiate the file upload process.
     */
    private JButton upload;

    /**
     * JComboBox for selecting an alphabet.
     * Users can choose from a list of available alphabets using this combo box.
     */
    private JComboBox alphComboBox;
    /**
     * The JTextArea for entering the text matrix.
     */
    private JTextArea textInput;

    /**
     * Constructs an AddTextView object with the specified TransitionViewController
     * and the parent JPanel.
     *
     * @param transitionViewController The TransitionViewController for handling operations.
     * @param father                   The parent JPanel.
     */
    public AddTextView(TransitionViewController transitionViewController, JPanel father) {
        String[] alphabets = new String[0];
        try {
            alphabets = transitionViewController.listAlphabets();
        } catch (IOException | NotContainsKey e) {
            throw new RuntimeException(e);
        }
        if (alphabets.length > 0)
            for (String alphabet : alphabets) alphComboBox.addItem(alphabet);
        // ActionListener for the cancel button
        cancelButton.addActionListener(new ActionListener() {
            /**
             * Closes the AddTextView when the cancel button is clicked.
             *
             * @param e The event to be processed.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(addTextView);
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
            /**
             * @param e The event to be processed.
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String alphabet = (String) alphComboBox.getSelectedItem();
                    // Attempt to add the text matrix using the TransitionViewController
                    transitionViewController.addMatrixText(alphabet, IdTransitionField.getText(), textInput.getText());

                    // Display success message in a new frame
                    JFrame frame = new JFrame("Transition Added");
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setSize(500, 500);
                    frame.setContentPane(new OperationCorrect().getOperationCorrect());
                    frame.setVisible(true);

                    JFrame listFrame = (JFrame) SwingUtilities.getWindowAncestor(addTextView);
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
                } catch (IOException | NotContainsKey | ContainsKey| StringNotInAlphabet | IncorrectType ex) {
                    // Display error message in case of an exception
                    JFrame frame = new JFrame("Error");
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setSize(500, 500);
                    frame.setContentPane(new Error(ex.getMessage()).getErrorPanel());
                    frame.setVisible(true);
                }
            }
        });
        upload.addActionListener(new ActionListener() {
            /**
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Specify a file to save");
                int userSelection = fileChooser.showSaveDialog(addTextView);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    try {
                        String[] words = transitionViewController.getWordsFromFile(fileChooser.getSelectedFile().getAbsolutePath());
                        for (String word : words) {
                            textInput.setText(textInput.getText() + word + " ");
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
     * Returns the main JPanel representing the AddTextView.
     *
     * @return The main JPanel for adding a text matrix.
     */
    public Container getAddTextView() {
        return addTextView;
    }
}