package Presentation;

import Exceptions.IncorrectAlphabetType;
import Exceptions.IncorrectType;
import Exceptions.NotContainsKey;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * The `KeyboardList` class represents a view for displaying a list of keyboards,
 * allowing users to interact with keyboard entries, add new keyboards, and delete existing ones.
 */
public class KeyboardList {
    /**
     * The `JPanel` representing the keyboard list view.
     */
    private JPanel keyboardList;

    /**
     * The "Delete Keyboard" button for deleting selected keyboards.
     */
    private JButton deleteKeyboardButton;

    /**
     * The "Add Keyboard" button for adding new keyboards.
     */
    private JButton addKeyboardButton;

    /**
     * The "Evaluate" button for initiating the keyboard evaluation view.
     */
    private JButton evaluateButton;

    /**
     * The `JPanel` for displaying information.
     */
    private JPanel mainPanel;
    private JComboBox comboBox1;
    private JButton displayButton;
    private JPanel listPanel;
    /**
     * The `JLabel` for displaying exception messages.
     */
    private JLabel error;
    private JButton exitButton;
    /**
     * An array of `JLabel` representing keyboard names.
     */
    private JLabel[] names;

    private boolean nullKeyboards;

    /**
     * Constructs a new `KeyboardList` with the specified `MainViewController`.
     *
     * @param mv The `MainViewController` associated with this `KeyboardList`.
     * @throws IOException If an I/O error occurs during initialization.
     */
    public KeyboardList(MainViewController mv) throws IOException {
        listPanel.setBackground(new java.awt.Color(73, 170, 107));
        try {
            String[] keyboards = mv.listKeyboard();
            if (keyboards.length == 0) {
                nullKeyboards = true;
                throw new IOException();
            }
            for (String keyboard : keyboards) {
                comboBox1.addItem(keyboard);
            }
            nullKeyboards = false;
            listPanel = new JPanel(new GridLayout(10, keyboards.length / 3));
            listPanel.setBackground(new java.awt.Color(73, 170, 107));
            names = new JLabel[keyboards.length];
            for (int i = 0; i < keyboards.length; ++i) {
                names[i] = new JLabel(keyboards[i]);
                listPanel.add(names[i]);
            }
            listPanel.setVisible(true);
        } catch (Exception e) {
            error.setText("There are no keyboards yet.");
        }

        addKeyboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("AddKeyboard");
                JFrame origin = (JFrame) SwingUtilities.getWindowAncestor(keyboardList);
                origin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                origin.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                AddKeyboardView addKeyboardView = null;
                addKeyboardView = new AddKeyboardView(mv, keyboardList);
                frame.setContentPane(addKeyboardView.getAddKeyboardView());
                frame.setSize(1000, 800);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        deleteKeyboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("DeleteKeyboard");
                JFrame origin = (JFrame) SwingUtilities.getWindowAncestor(keyboardList);
                origin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                origin.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                DeleteKeyboardView deleteKeyboardView = new DeleteKeyboardView(mv, keyboardList);
                frame.setContentPane(deleteKeyboardView.getDeleteKeyboardView());
                frame.setSize(1000, 800);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        evaluateButton.addActionListener(new ActionListener() {
            /**
             * Processes the event when the "Evaluate" button is clicked.
             *
             * @param e The event to be processed.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Evaluation");
                JFrame origin = (JFrame) SwingUtilities.getWindowAncestor(keyboardList);
                origin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                origin.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                EvaluationView evaluationView = null;

                evaluationView = new EvaluationView(mv);
                frame.setContentPane(evaluationView.getEvaluationView());
                frame.setSize(1000, 800);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        if (!nullKeyboards) {
            displayButton.addActionListener(new ActionListener() {
                /**
                 * @param e the event to be processed
                 */
                @Override
                public void actionPerformed(ActionEvent e) {
                    String keyboardName = (String) comboBox1.getSelectedItem();
                    JFrame frame = new JFrame("DisplayKeyboard");
                    JFrame origin = (JFrame) SwingUtilities.getWindowAncestor(keyboardList);
                    origin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    origin.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                    frame.setLocationRelativeTo(null);
                    DisplayKeyboardView displayKeyboardView = null;
                    try {
                        displayKeyboardView = new DisplayKeyboardView(mv, keyboardName);
                    } catch (NotContainsKey ex) {
                        throw new RuntimeException(ex);
                    }
                    frame.setContentPane(displayKeyboardView.getDisplayKeyboardView());
                    frame.setSize(1000, 800);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);

                }
            });
        }
        exitButton.addActionListener(new ActionListener() {
            /**
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(keyboardList);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                try {
                    mv.restart();
                } catch (IOException | NotContainsKey ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    /**
     * Gets the container associated with this `KeyboardList`.
     *
     * @return The container representing the `KeyboardList`.
     */
    public Container getKeyboardList() {
        return keyboardList;
    }
}
