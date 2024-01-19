package Presentation;

import Exceptions.FileNotDeleted;
import Exceptions.NotContainsKey;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * The DeleteKeyboardView class represents a view for deleting an existing keyboard.
 * It includes an input field for the keyboard name, along with accept and cancel buttons.
 */
public class DeleteKeyboardView {
    /**
     * The JPanel instance representing the delete keyboard view.
     */
    private JPanel deleteKeyboardView;
    /**
     * The JTextField for entering the ID of the keyboard to be deleted.
     */
    private JTextField nameKeyboardField;
    /**
     * The JButton for accepting the deletion operation.
     */
    private JButton acceptButton;
    /**
     * The JButton for canceling the deletion operation.
     */
    private JButton cancelButton;
    private JComboBox keyComboBox;

    /**
     * Constructs a new DeleteKeyboardView with the specified MainViewController and
     * the parent JPanel.
     *
     * @param mv     The MainViewController associated with this DeleteKeyboardView.
     * @param father The parent JPanel to update after a keyboard is deleted.
     */
    public DeleteKeyboardView(MainViewController mv, JPanel father) {
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

        cancelButton.addActionListener(new ActionListener() {
            /**
             * Closes the DeleteKeyboardView when the cancel button is clicked.
             *
             * @param e The event to be processed.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(deleteKeyboardView);
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
        acceptButton.addActionListener(new ActionListener() {
            /**
             * Attempts to delete the specified keyboard when the accept button is clicked.
             *
             * @param e The event to be processed.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String keyboard = (String) keyComboBox.getSelectedItem();
                    mv.deleteKeyboard(keyboard);
                    JFrame deleted = new JFrame("Keyboard Deleted");
                    deleted.setContentPane(new OperationCorrect().getOperationCorrect());
                    deleted.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    deleted.setSize(500, 500);
                    deleted.setVisible(true);
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(deleteKeyboardView);
                    frame.setTitle("KeyboardList");
                    try {
                        KeyboardList keyboardList = new KeyboardList(mv);
                        frame.setContentPane(keyboardList.getKeyboardList());
                        frame.setVisible(true);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } catch (NotContainsKey | IOException | FileNotDeleted ex) {
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
     * Gets the JPanel associated with this DeleteKeyboardView.
     *
     * @return The JPanel representing the DeleteKeyboardView.
     */
    public JPanel getDeleteKeyboardView() {
        return deleteKeyboardView;
    }
}
