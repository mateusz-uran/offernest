package main.java.io.github.mateuszuran.offernest.ui.directory;

import main.java.io.github.mateuszuran.offernest.ui.configmanager.ConfigManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class DirectoryPanel {
    private final JButton searchButton;
    private final JTextField directory;
    private final JButton dirBtn;

    public DirectoryPanel(JButton searchButton, JTextField directory, JButton dirBtn) {
        this.directory = directory;
        this.searchButton = searchButton;
        this.dirBtn = dirBtn;

        directory.setForeground(Color.gray);

        initializeActions();
        loadInitialDirectory();
    }

    private void initializeActions() {
        searchButton.addActionListener(e -> openDirectoryChooser());
        dirBtn.addActionListener(e -> openDirectory());
    }

    private void loadInitialDirectory() {
        String dir = ConfigManager.readDirectory();
        if (dir != null && !dir.isEmpty()) {
            directory.setText(dir);
            dirBtn.setEnabled(true);
        } else dirBtn.setEnabled(false);
    }

    private void openDirectoryChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int result = fileChooser.showOpenDialog(fileChooser);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = fileChooser.getSelectedFile();
            String path = selectedFolder.getAbsolutePath();
            ConfigManager.saveDirectory(path);
            directory.setText(path);
            dirBtn.setEnabled(true);

        } else {
            System.out.println("No folder selected.");
        }
    }

    private void openDirectory() {
        File dir = new File(directory.getText());
        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(dir);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
