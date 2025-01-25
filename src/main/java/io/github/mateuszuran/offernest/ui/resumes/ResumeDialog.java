package main.java.io.github.mateuszuran.offernest.ui.resumes;

import main.java.io.github.mateuszuran.offernest.logic.ResumeEntity;
import main.java.io.github.mateuszuran.offernest.logic.ResumeService;
import main.java.io.github.mateuszuran.offernest.ui.GlobalFileChooser;

import javax.swing.*;
import java.awt.*;

public class ResumeDialog {
    private final JTextField noteText = new JTextField();
    private final JButton submitButton = new JButton("Save");
    private final JButton fileButton = new JButton("Search");
    private final JLabel fileLabel = new JLabel("No file selected");

    public ResumeDialog(JFrame frame) {
        JDialog dialog = new JDialog(frame);
        dialog.setLayout(new BorderLayout());

        initializeActions(dialog);

        dialog.add(createNotePanel(), BorderLayout.NORTH);
        dialog.add(createFilePanel(), BorderLayout.CENTER);
        dialog.add(submitButton, BorderLayout.SOUTH);

        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    private void initializeActions(JDialog dialog) {
        fileButton.addActionListener(e -> addResumePathToLabel());
        submitButton.addActionListener(e -> {
            if (noteText.getText().isEmpty() || fileLabel.getText().equals("No file selected")) {
                JOptionPane.showMessageDialog(dialog, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            saveResume(dialog);
        });
    }

    private void saveResume(JDialog dialog) {
        ResumeEntity resume = new ResumeEntity(noteText.getText(), fileLabel.getText());
        // TODO: replace with actual logic later
        ResumeService.addResume(noteText.getText(), fileLabel.getText());

        dialog.dispose();
    }

    private JPanel createNotePanel() {
        JPanel notePanel = new JPanel(new BorderLayout());
        notePanel.add(new JLabel("Notes"), BorderLayout.NORTH);
        notePanel.add(noteText, BorderLayout.SOUTH);
        return notePanel;
    }

    private JPanel createFilePanel() {
        JPanel filePanel = new JPanel(new BorderLayout());
        filePanel.add(fileButton, BorderLayout.EAST);
        filePanel.add(fileLabel, BorderLayout.WEST);
        return filePanel;
    }

    private void addResumePathToLabel() {
        String path = GlobalFileChooser.chooseFile(0);
        if (path != null && !path.isEmpty()) {
            fileLabel.setText(path);
        }
    }
}
