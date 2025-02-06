package io.github.mateuszuran.offernest.ui.mainframe;

import io.github.mateuszuran.offernest.service.logic.PersistData;
import io.github.mateuszuran.offernest.model.ResumeEntity;
import io.github.mateuszuran.offernest.ui.directory.DirectoryPanel;
import io.github.mateuszuran.offernest.ui.resumes.ResumeDialog;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        createWindow();
        createUpperPanel();
    }

    private void createWindow() {
        setTitle("Offer nest");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1280, 960);
        setLayout(new BorderLayout());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createUpperPanel() {
        JPanel upperPanel = new JPanel(new BorderLayout());
        upperPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JPanel buttonPanel = createButtonPanel();
        upperPanel.add(buttonPanel);

        DirectoryPanel directoryPanel = new DirectoryPanel();
        upperPanel.add(directoryPanel, BorderLayout.EAST);

        add(upperPanel, BorderLayout.NORTH);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        JButton addResume = new JButton("Add resume");
        addResume.addActionListener(e -> {
            try {
                new ResumeDialog(this);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error opening dialog: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE
                );
            }
        });

        buttonPanel.add(addResume);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0))); // odstęp między elementami
        return buttonPanel;
    }
}
