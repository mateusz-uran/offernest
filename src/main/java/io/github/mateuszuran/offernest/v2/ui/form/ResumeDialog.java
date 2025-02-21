package io.github.mateuszuran.offernest.v2.ui.form;

import io.github.mateuszuran.offernest.v2.controller.ResumeController;
import io.github.mateuszuran.offernest.v2.entity.ResumeEntity;
import io.github.mateuszuran.offernest.v2.service.logic.GlobalFileChooser;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ResumeDialog extends JDialog {
    private final ResumeController resumeController;
    private final Runnable refreshCallback;
    private JTextField noteText;
    private JLabel fileLabel;
    private DefaultListModel<String> listModel;
    private JList<String> stringList;

    public ResumeDialog(Component parent, ResumeController resumeController, Runnable refreshCallback) {
        super(SwingUtilities.getWindowAncestor(parent), "Resume Dialog", ModalityType.APPLICATION_MODAL);
        this.resumeController = resumeController;
        this.refreshCallback = refreshCallback;
        setLayout(new BorderLayout());

        add(createNotePanel(), BorderLayout.NORTH);
        add(createFilePanel(), BorderLayout.CENTER);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createSaveButton(), BorderLayout.SOUTH);

        setSize(400, 350);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private JPanel createNotePanel() {
        JPanel notePanel = new JPanel(new BorderLayout());
        JLabel noteLabel = new JLabel("Note:");
        noteText = new JTextField();
        noteText.setPreferredSize(new Dimension(250, 25));

        notePanel.add(noteLabel, BorderLayout.WEST);
        notePanel.add(noteText, BorderLayout.CENTER);

        return notePanel;
    }

    private JPanel createFilePanel() {
        JPanel filePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton fileButton = new JButton("Choose a PDF");
        fileLabel = new JLabel("No file selected");

        fileButton.addActionListener(e -> {
            String path = GlobalFileChooser.chooseFile(JFileChooser.FILES_ONLY);
            fileLabel.setText(path != null ? path : "No file selected");
        });

        filePanel.add(fileButton);
        filePanel.add(fileLabel);

        return filePanel;
    }

    private JPanel createStringListPanel() {
        JPanel stringListPanel = new JPanel(new BorderLayout());

        JLabel listLabel = new JLabel("Offers:");
        JTextField inputField = new JTextField();
        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");

        listModel = new DefaultListModel<>();
        stringList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(stringList);

        addButton.addActionListener(e -> {
            String text = inputField.getText().trim();
            if (!text.isEmpty()) {
                listModel.addElement(text);
                inputField.setText("");
            }
        });

        removeButton.addActionListener(e -> {
            int selectedIndex = stringList.getSelectedIndex();
            if (selectedIndex != -1) {
                listModel.remove(selectedIndex);
            }
        });

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new BorderLayout());
        buttonsPanel.add(addButton, BorderLayout.WEST);
        buttonsPanel.add(removeButton, BorderLayout.EAST);

        inputPanel.add(buttonsPanel, BorderLayout.EAST);


        stringListPanel.add(listLabel, BorderLayout.NORTH);
        stringListPanel.add(scrollPane, BorderLayout.CENTER);
        stringListPanel.add(inputPanel, BorderLayout.SOUTH);

        return stringListPanel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(createFilePanel());
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(createStringListPanel());

        return centerPanel;
    }

    private JPanel createSaveButton() {
        JPanel panel = new JPanel();
        JButton saveButton = new JButton("Save");

        saveButton.addActionListener(e -> {
            if (validateInput()) {
                List<String> offers = new ArrayList<>();
                for (int i=0; i<listModel.size(); i++) {
                    offers.add(listModel.get(i));
                }

                resumeController.addResume(new ResumeEntity(noteText.getText(), fileLabel.getText(), offers));

                refreshCallback.run();
                dispose();
            }
        });

        panel.add(saveButton);
        return panel;
    }

    private boolean validateInput() {
        if (noteText.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Note cannot be empty!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (fileLabel.getText().equals("No file selected")) {
            JOptionPane.showMessageDialog(this, "You must select a file!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
}
