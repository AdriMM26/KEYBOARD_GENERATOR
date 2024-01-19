package Presentation;

import Domain.Alphabet;
import Exceptions.AlphabetUsed;
import Exceptions.FileNotDeleted;
import Exceptions.NotContainsKey;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * The DeleteAlphabetView class is responsible for managing the view to delete an alphabet in the presentation layer.
 * It provides a user interface with input fields and buttons to perform the deletion operation.
 */
public class DeleteAlphabetView {

    /**
     * The JPanel instance representing the delete alphabet view.
     */
    private JPanel deleteAlphabetView;

    /**
     * The JTextField for entering the ID of the alphabet to be deleted.
     */
    private JTextField IdAlphabetField;

    /**
     * The JButton for accepting and confirming the deletion operation.
     */
    private JButton acceptButton;

    /**
     * The JButton for canceling and closing the delete alphabet view.
     */
    private JButton cancelButton;
    private JComboBox alphComboBox;

    /**
     * Constructs a DeleteAlphabetView with the given InputViewController and the parent JPanel.
     *
     * @param inputViewController The InputViewController associated with this DeleteAlphabetView.
     * @param father              The parent JPanel.
     */
    public DeleteAlphabetView(InputViewController inputViewController, JPanel father) {
        String[] alphabets = new String[0];
        try {
            alphabets = inputViewController.listAlphabets();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NotContainsKey e) {
            throw new RuntimeException(e);
        }
        if (alphabets.length > 0)
            for (int i = 0; i < alphabets.length; ++i)
                alphComboBox.addItem(alphabets[i]);

        // ActionListener for the cancel button
        cancelButton.addActionListener(new ActionListener() {
            /**
             * Closes the DeleteAlphabetView when the cancel button is clicked.
             *
             * @param e The event to be processed.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(deleteAlphabetView);
                frame.setTitle("AlphabetList");
                try {
                    AlphabetListView alphabetList = new AlphabetListView(inputViewController);
                    frame.setContentPane(alphabetList.getAlphabetList());
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
                    // Attempt to delete the alphabet using the InputViewController
                    String alphabet = (String) alphComboBox.getSelectedItem();
                    inputViewController.deleteAlphabet(alphabet);

                    // Display success message in a new frame
                    JFrame frame = new JFrame("Alphabet Deleted");
                    frame.setContentPane(new OperationCorrect().getOperationCorrect());
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setSize(500, 500);
                    frame.setVisible(true);
                    JFrame listFrame = (JFrame) SwingUtilities.getWindowAncestor(deleteAlphabetView);
                    listFrame.setTitle("AlphabetList");
                    try {
                        AlphabetListView alphabetList = new AlphabetListView(inputViewController);
                        listFrame.setContentPane(alphabetList.getAlphabetList());
                        listFrame.setVisible(true);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } catch (NotContainsKey | AlphabetUsed | IOException | FileNotDeleted ex) {
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
     * Retrieves the delete alphabet view container.
     *
     * @return The Container representing the delete alphabet view.
     */
    public Container getDeleteAlphabetView() {
        return deleteAlphabetView;
    }
}