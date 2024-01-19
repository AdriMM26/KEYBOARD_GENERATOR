package Presentation;

import Exceptions.NotContainsKey;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Scanner;

/**
 * The `DisplayKeyboardView` class represents a view for displaying the distribution of characters
 * on a keyboard and allows users to interact with it.
 */
public class DisplayKeyboardView {
    /**
     * The `JPanel` instance representing the display keyboard view.
     */
    private JPanel displayKeyboardView;

    /**
     * The `JLabel` displaying the name of the keyboard.
     */
    private JLabel name;

    /**
     * The `JPanel` containing the keyboard buttons.
     */
    private JPanel keyboardPanel;

    /**
     * The `JTextField` for displaying user input.
     */
    private JTextField keyboardText;

    /**
     * The "Maj" button for toggling between uppercase and lowercase input.
     */
    private JButton majButton;
    private JButton exitButton;
    private JButton changeKeyButton;
    private JLabel informer;

    /**
     * A flag indicating whether the input is in uppercase or lowercase.
     */
    private boolean isMaj;

    private boolean firstTouch = false;

    private int[] index = new int[2];

    /**
     * A flag indicating whether the user is in editor mode.
     */
    private boolean editor = false;
    /**
     * A 2D array of `JButton` representing the keys on the keyboard.
     */
    private JButton[][] key;

    /**
     * Constructs a new `DisplayKeyboardView` for the specified `MainViewController` and keyboard name.
     *
     * @param mv   The `MainViewController` associated with this `DisplayKeyboardView`.
     * @param name The name of the keyboard to display.
     * @throws NotContainsKey If the specified keyboard name is not found.
     */
    public DisplayKeyboardView(MainViewController mv, String name) throws NotContainsKey {
        this.name.setText(name);
        informer.setText("Editor mode is off");
        changeKeyButton.setBackground(Color.RED);
        char[][] keyboard = mv.getDistribution(name);
        keyboardPanel.setLayout(new GridLayout(keyboard.length, keyboard[0].length));
        key = new JButton[keyboard.length][keyboard[0].length];
        for (int i = 0; i < keyboard.length; ++i) {
            for (int j = 0; j < keyboard[0].length; ++j) {
                key[i][j] = new JButton(String.valueOf(keyboard[i][j]));
                int finalI = i;
                int finalJ = j;
                key[i][j].addActionListener(e -> {
                    if(editor){
                        if(!firstTouch){
                            index[0] = finalI;
                            index[1] = finalJ;
                            firstTouch = true;
                        }
                        else{
                            char temp = keyboard[finalI][finalJ];
                            keyboard[finalI][finalJ] = keyboard[index[0]][index[1]];
                            keyboard[index[0]][index[1]] = temp;
                            key[finalI][finalJ].setText(String.valueOf(keyboard[finalI][finalJ]));
                            key[index[0]][index[1]].setText(String.valueOf(keyboard[index[0]][index[1]]));
                            try {
                                mv.modifyKeyboard(name, index[0], index[1], finalI, finalJ);
                                mv.modifyKeyboard(name, finalI, finalJ, index[0], index[1]);
                            } catch (NotContainsKey | IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            firstTouch = false;
                        }
                    }
                    else{
                        if (isMaj) {
                            keyboardText.setText(keyboardText.getText() + key[finalI][finalJ].getText());
                        } else {
                            keyboardText.setText(keyboardText.getText() + key[finalI][finalJ].getText().toLowerCase());
                        }
                    }
                });
                key[i][j].setHorizontalAlignment(JButton.CENTER);
                key[i][j].setVerticalAlignment(JButton.CENTER);
                keyboardPanel.add(key[i][j]);
            }
        }
        keyboardPanel.setVisible(true);
        majButton.addActionListener(new ActionListener() {
            /**
             * Toggles between uppercase and lowercase input when the `majButton` is clicked.
             *
             * @param e The event to be processed.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isMaj){
                    isMaj = false;
                    majButton.setBackground(UIManager.getColor("Button.background"));
                }
                else{
                    isMaj = true;
                    majButton.setBackground(new java.awt.Color(168, 153, 153, 195));
                }
            }
        });
        exitButton.addActionListener(new ActionListener() {
            /**
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(displayKeyboardView);
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
        changeKeyButton.addActionListener(new ActionListener() {
            /**
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if(editor){
                    editor = false;
                    changeKeyButton.setBackground(Color.RED);
                    informer.setText("Editor mode is off");
                }
                else{
                    editor = true;
                    changeKeyButton.setBackground(Color.GREEN);
                    informer.setText("Editor mode is on");
                }
            }
        });
    }



    /**
     * Gets the container associated with this `DisplayKeyboardView`.
     *
     * @return The container representing the `DisplayKeyboardView`.
     */
    public Container getDisplayKeyboardView() {
        return displayKeyboardView;
    }
}
