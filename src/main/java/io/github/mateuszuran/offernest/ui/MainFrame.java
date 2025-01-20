package main.java.io.github.mateuszuran.offernest.ui;

import main.java.io.github.mateuszuran.offernest.ui.directory.DirectoryPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private JPanel mainPanel;
    private JPanel upperPanel;
    private JButton addResume;
    private JPanel directoryPanel;
    private JButton searchButton;
    private JTextField directory;
    private JButton dirBtn;

    public MainFrame() {
        setContentPane(mainPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setVisible(true);
        setSize(1280, 960);

        setLocationRelativeTo(null);

        new DirectoryPanel(searchButton, directory, dirBtn);

    }
}
