package Presentation;

import Domain.Alphabet;
import Exceptions.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;


/**
 * The AddAlphabetView class is responsible for managing the view to add a new alphabet in the presentation layer.
 * It provides a user interface with input fields and buttons to perform the addition operation.
 */
public class AddAlphabetView {

    /**
     * The JPanel instance representing the add alphabet view.
     */
    private JPanel addAlphabetView;

    /**
     * The JButton for accepting and confirming the addition operation.
     */
    private JButton acceptButton;

    /**
     * The JButton for canceling and closing the add alphabet view.
     */
    private JButton cancelButton;

    /**
     * The JTextField for entering the ID of the alphabet to be added.
     */
    private JTextField IdAlphabetField;

    /**
     * The JTextField for entering the list of characters in the new alphabet.
     */
    private JTextField ListOfCharactersField;
    private JButton upload;

    /**
     * Constructs an AddAlphabetView with the given InputViewController and the parent JPanel.
     *
     * @param inputViewController The InputViewController associated with this AddAlphabetView.
     * @param father              The parent JPanel.
     */
    public AddAlphabetView(InputViewController inputViewController, JPanel father) {
        cancelButton.addActionListener(new ActionListener() {
            /**
             * Closes the AddAlphabetView when the cancel button is clicked.
             *
             * @param e The event to be processed.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(addAlphabetView);
                frame.setTitle("AlphabetList");
                try {
                    AlphabetListView alphabetListView = new AlphabetListView(inputViewController);
                    frame.setContentPane(alphabetListView.getAlphabetList());
                    frame.setVisible(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        upload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Specify a file to save");
                int userSelection = fileChooser.showSaveDialog(addAlphabetView);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    try {
                        ListOfCharactersField.setText(inputViewController.getTextFromFile(fileChooser.getSelectedFile().getAbsolutePath()));
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
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    inputViewController.addAlphabet(IdAlphabetField.getText(), ListOfCharactersField.getText());
                    JFrame frame = new JFrame("Alphabet Added");
                    frame.setContentPane(new OperationCorrect().getOperationCorrect());
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setSize(500, 500);
                    frame.setVisible(true);
                    JFrame listframe = (JFrame) SwingUtilities.getWindowAncestor(addAlphabetView);
                    listframe.setTitle("AlphabetList");
                    try {
                        AlphabetListView alphabetListView = new AlphabetListView(inputViewController);
                        listframe.setContentPane(alphabetListView.getAlphabetList());
                        listframe.setVisible(true);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } catch (IncorrectType | IOException | NotContainsKey | ContainsKey ex) {
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
     * Retrieves the add alphabet view container.
     *
     * @return The Container representing the add alphabet view.
     */
    public Container getAddAlphabetView() {
        return addAlphabetView;
    }
}

