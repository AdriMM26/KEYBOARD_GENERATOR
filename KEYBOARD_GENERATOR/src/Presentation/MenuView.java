package Presentation;

import Exceptions.NotContainsKey;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * The MenuView class represents a view for the main menu of the application.
 * It allows users to navigate to different sections, such as viewing keyboards and inputs.
 */
public class MenuView {
    /**
     * The JPanel instance representing the main menu view.
     */
    private JPanel menuView;

    /**
     * The JButton for navigating to the "My Keyboards" section.
     */
    private JButton myKeyboardsButton;

    /**
     * The JButton for navigating to the "My Inputs" section.
     */
    private JButton myInputsButton;

    /**
     * The JTextPane for displaying a welcome message or other informative text.
     */
    private JLabel welcomeBackTextPanel;

    /**
     * Constructs a new MenuView with the specified MainViewController.
     *
     * @param mv The MainViewController associated with this MenuView.
     * @param iv The InputViewController associated with this MenuView.
     * @param tv The TransitionViewController associated with this MenuView.
     * @throws IOException        If an I/O error occurs during initialization.
     * @throws NotContainsKey     If a required key is not found during initialization.
     * @throws RuntimeException   If an unexpected error occurs during KeyboardList construction.
     */
    public MenuView(MainViewController mv, InputViewController iv, TransitionViewController tv) throws IOException, NotContainsKey {
        myKeyboardsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame origin = (JFrame) SwingUtilities.getWindowAncestor(menuView);
                origin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                JFrame frame = new JFrame("MyKeyboards");

                origin.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                KeyboardList keyboardList;
                try {
                    keyboardList = new KeyboardList(mv);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                frame.setContentPane(keyboardList.getKeyboardList());
                frame.setSize(1000, 800);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        myInputsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame origin = (JFrame) SwingUtilities.getWindowAncestor(menuView);
                origin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                JFrame frame = new JFrame("MyInputs");
                origin.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                InputView inputView = null;
                inputView = new InputView(iv, tv);
                frame.setContentPane(inputView.getInputList());
                frame.setSize(1000, 800);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    /**
     * Gets the JPanel associated with this MenuView.
     *
     * @return The JPanel representing the MenuView.
     */
    public JPanel getMenuView() {
        return menuView;
    }
}
