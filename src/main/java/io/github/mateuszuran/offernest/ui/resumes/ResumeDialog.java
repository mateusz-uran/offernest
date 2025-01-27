package main.java.io.github.mateuszuran.offernest.ui.resumes;

import main.java.io.github.mateuszuran.offernest.logic.ResumeService;
import main.java.io.github.mateuszuran.offernest.ui.GlobalFileChooser;
import main.java.io.github.mateuszuran.offernest.ui.offer.OfferPanel;

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
        dialog.add(new OfferPanel(), BorderLayout.CENTER);
        dialog.add(createBottomPanel(), BorderLayout.SOUTH);

        dialog.setSize(400, 350);
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
        ResumeService.addResume(noteText.getText(), fileLabel.getText());
        dialog.dispose();
    }

    private JPanel createNotePanel() {
        JPanel notePanel = new JPanel();
        notePanel.setLayout(new BoxLayout(notePanel, BoxLayout.Y_AXIS));

        JLabel noteLabel = new JLabel("Notes");
        noteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        noteLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        noteText.setAlignmentX(Component.CENTER_ALIGNMENT);
        noteText.setMaximumSize(new Dimension(250, 25));

        notePanel.add(noteLabel);
        notePanel.add(noteText);
        return notePanel;
    }

    private JPanel createFilePanel() {
        JPanel filePanel = new JPanel(new BorderLayout());
        filePanel.add(fileButton, BorderLayout.NORTH);
        filePanel.add(fileLabel, BorderLayout.SOUTH);
        return filePanel;
    }

    private void addResumePathToLabel() {
        String path = GlobalFileChooser.chooseFile(0);
        if (path != null && !path.isEmpty()) {
            fileLabel.setText(path);
        }
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        JPanel filePanel = createFilePanel();

        bottomPanel.add(filePanel, BorderLayout.WEST);
        bottomPanel.add(submitButton, BorderLayout.EAST);
        return bottomPanel;
    }
}
