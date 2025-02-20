package io.github.mateuszuran.offernest.v2.ui.directory;

import io.github.mateuszuran.offernest.v2.config.ApplicationConfig;
import io.github.mateuszuran.offernest.v2.service.logic.GlobalFileChooser;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class DirectoryPanel extends JPanel {
    private final JTextField directoryPath;
    private final JButton openButton;
    private final JButton searchButton;

    public DirectoryPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel("Directory");
        directoryPath = new JTextField(20);
        directoryPath.setEditable(false);

        openButton = new JButton("Open");
        searchButton = new JButton("Search");

        checkIfDirectoryExists();
        initializeAction();

        add(label);
        add(directoryPath);
        add(searchButton);
        add(openButton);
    }

    private void checkIfDirectoryExists() {
        String path = ApplicationConfig.readApplicationConfig();
        searchButton.setEnabled(false);

        if (path != null && !path.isBlank()) {
            System.out.println("Path found: " + path);
            directoryPath.setText(path);
            searchButton.setEnabled(true);
        }
    }

    private void initializeAction() {
        openButton.addActionListener(e -> {
            File dir = new File(directoryPath.getText());
            if (dir.exists() && dir.isDirectory()) {
                try {
                    Desktop.getDesktop().open(dir);
                } catch (IOException m) {
                    System.err.println("Error while opening directory: " + m.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Directory does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        searchButton.addActionListener(e -> {
            String path = GlobalFileChooser.chooseFile(JFileChooser.DIRECTORIES_ONLY);
            if (!path.isBlank()) {
                ApplicationConfig.saveApplicationConfig(path);
                directoryPath.setText(path);
                searchButton.setEnabled(true);
            }


        });
    }
}
