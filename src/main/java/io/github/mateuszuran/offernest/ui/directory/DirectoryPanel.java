package main.java.io.github.mateuszuran.offernest.ui.directory;

import main.java.io.github.mateuszuran.offernest.config.ConfigManager;
import main.java.io.github.mateuszuran.offernest.ui.GlobalFileChooser;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class DirectoryPanel extends JPanel {
    private final JButton searchButton = new JButton("Search");
    private final JTextField directory = new JTextField();
    private final JButton dirButton = new JButton("Open");

    public DirectoryPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setOpaque(false);

        JPanel textPanel = new JPanel();
        Dimension size = new Dimension(200, 25);
        directory.setPreferredSize(size);
        directory.setMaximumSize(size);
        directory.setEditable(false);
        directory.setMargin(new Insets(1, 5, 1, 5));

        textPanel.add(directory);
        textPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        add(searchButton);
        add(textPanel);
        add(dirButton);

        initializeActions();
        loadInitialDirectory();
    }


    private void initializeActions() {
        searchButton.addActionListener(e -> openDirectoryChooser());
        dirButton.addActionListener(e -> openDirectory());
    }

    private void loadInitialDirectory() {
        String dir = ConfigManager.readDirectory();
        if (dir != null && !dir.isEmpty()) {
            directory.setText(dir);
            dirButton.setEnabled(true);
        } else dirButton.setEnabled(false);
    }

    private void openDirectoryChooser() {
        String path = GlobalFileChooser.chooseFile(1);
        ConfigManager.saveDirectory(path);
        directory.setText(path);
        dirButton.setEnabled(true);
    }

    private void openDirectory() {
        File dir = new File(directory.getText());
        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
