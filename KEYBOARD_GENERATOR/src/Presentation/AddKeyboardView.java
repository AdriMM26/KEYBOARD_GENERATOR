package Presentation;

import Exceptions.ContainsKey;
import Exceptions.IncorrectType;
import Exceptions.NotContainsKey;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * The AddKeyboardView class represents a view for adding a new keyboard. It includes
 * input fields for key, transKey, and algorithm, along with accept and cancel buttons.
 */
public class AddKeyboardView {
    /**
     * The JPanel instance representing the add keyboard view.
     */
    private JPanel addKeyboardView;
    /**
     * The JButton for canceling the addition operation.
     */
    private JButton cancelButton;
    /**
     * The JButton for accepting and confirming the addition operation.
     */
    private JButton acceptButton;
    /**
     * The JTextField for entering the name of the algorithm to be used.
     */
    private JTextField algortihmField;
    /**
     * The JTextField for entering the ID of the transition matrix to be used.
     */
    private JTextField transIdField;
    /**
     * The JTextField for entering the ID of the keyboard to be added.
     */
    private JTextField nameKeyboardField;
    private JComboBox algorithmComboBox;
    private JComboBox transComboBox;
    private JLabel info;

    private volatile boolean thinking = false;

    /**
     * Constructs a new AddKeyboardView with the specified MainViewController and
     * the parent JPanel.
     *
     * @param mv     The MainViewController associated with this AddKeyboardView.
     * @param father The parent JPanel to update after a keyboard is added.
     */
    public AddKeyboardView(MainViewController mv, JPanel father) {
        algorithmComboBox.addItem("Greedy");
        algorithmComboBox.addItem("QAP");
        String[] transitions = new String[0];
        try {
            transitions = mv.listTransitions();
        } catch (IOException | NotContainsKey e) {
            throw new RuntimeException(e);
        }
        if (transitions.length > 0)
            for (int i = 0; i < transitions.length; ++i)
                transComboBox.addItem(transitions[i]);

        cancelButton.addActionListener(new ActionListener() {
            /**
             * Closes the AddKeyboardView when the cancel button is clicked.
             *
             * @param e The event to be processed.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(addKeyboardView);
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
             * Attempts to add a new keyboard when the accept button is clicked.
             *
             * @param e The event to be processed.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String transition = (String) transComboBox.getSelectedItem();
                String algorithm = (String) algorithmComboBox.getSelectedItem();
                if(thinking){
                    info.setText("WARNING! There is a process running. Please wait.");
                }
                else {
                    new Thread(() -> {
                        thinking = true;
                        info.setText("Please wait...");
                        try {
                            mv.addKeyboard(nameKeyboardField.getText(), transition, algorithm);
                            JFrame added = new JFrame("Keyboard Added");
                            added.setContentPane(new OperationCorrect().getOperationCorrect());
                            added.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            added.setSize(500, 500);
                            added.setVisible(true);
                            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(addKeyboardView);
                            frame.setTitle("KeyboardList");
                            try {
                                KeyboardList keyboardList = new KeyboardList(mv);
                                frame.setContentPane(keyboardList.getKeyboardList());
                                frame.setVisible(true);
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
                        info.setText("");
                        thinking = false;
                    }).start();
                }
            }
        });
    }

    /**
     * Gets the JPanel associated with this AddKeyboardView.
     *
     * @return The JPanel representing the AddKeyboardView.
     */
    public JPanel getAddKeyboardView() {
        return addKeyboardView;
    }
}
