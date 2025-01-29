package io.github.mateuszuran.offernest.ui.resumes;

import io.github.mateuszuran.offernest.logic.OffersService;
import io.github.mateuszuran.offernest.logic.ResumeService;
import io.github.mateuszuran.offernest.ui.GlobalFileChooser;
import io.github.mateuszuran.offernest.ui.offer.OfferPanel;

import javax.swing.*;
import java.awt.*;

public class ResumeDialog {
    private final JTextField noteText = new JTextField();
    private final JButton submitButton = new JButton("Save");
    private final JButton fileButton = new JButton("Search");
    private final JLabel fileLabel = new JLabel("No file selected");
    private final OfferPanel offerPanel = new OfferPanel();

    public ResumeDialog(JFrame frame) {
        JDialog dialog = createDialogFrame(frame);
        initializeActions(dialog);
        dialog.setVisible(true);
    }

    private JDialog createDialogFrame(JFrame frame) {
        JDialog dialog = new JDialog(frame);
        dialog.setLayout(new BorderLayout());

        dialog.add(createNotePanel(), BorderLayout.NORTH);
        dialog.add(offerPanel, BorderLayout.CENTER);
        dialog.add(createBottomPanel(), BorderLayout.SOUTH);

        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(frame);
        return dialog;
    }

    private void initializeActions(JDialog dialog) {
        fileButton.addActionListener(e -> updateFileLabel());
        submitButton.addActionListener(e -> {
            if (isFormValid()) {
                saveResume(dialog);
            } else {
                JOptionPane.showMessageDialog(dialog, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private boolean isFormValid() {
        return !noteText.getText().isEmpty() && !fileLabel.getText().equals("No file selected");
    }

    private void saveResume(JDialog dialog) {
        ResumeService.addResume(noteText.getText(), fileLabel.getText(), offerPanel.getAllOffersLinks());
        dialog.dispose();
    }

    private JPanel createNotePanel() {
        JPanel notePanel = new JPanel();
        notePanel.setLayout(new BoxLayout(notePanel, BoxLayout.Y_AXIS));
        notePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel noteLabel = new JLabel("Notes");
        noteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        noteText.setMaximumSize(new Dimension(250, 25));

        notePanel.add(noteLabel);
        notePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        notePanel.add(noteText);

        return notePanel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        bottomPanel.add(createFilePanel(), BorderLayout.WEST);
        bottomPanel.add(submitButton, BorderLayout.EAST);
        return bottomPanel;
    }

    private JPanel createFilePanel() {
        JPanel filePanel = new JPanel(new BorderLayout());
        filePanel.add(fileButton, BorderLayout.NORTH);
        filePanel.add(fileLabel, BorderLayout.SOUTH);
        return filePanel;
    }

    private void updateFileLabel() {
        String path = GlobalFileChooser.chooseFile(0);
        if (path != null && !path.isEmpty()) {
            fileLabel.setText(path);
        }
    }
}
