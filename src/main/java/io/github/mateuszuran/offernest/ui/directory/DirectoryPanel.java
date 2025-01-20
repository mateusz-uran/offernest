package main.java.io.github.mateuszuran.offernest.ui.directory;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class DirectoryPanel extends JFrame {
    private final JButton searchButton;
    private final JTextField directory;
    private final JButton dirBtn;

    public DirectoryPanel(JButton searchButton, JTextField directory, JButton dirBtn) {
        this.directory = directory;
        this.searchButton = searchButton;
        this.dirBtn = dirBtn;

        directory.setForeground(Color.gray);
        searchBtnAction();
        readPropertiesFile();
        openBtnAction();
    }

    private void searchBtnAction() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        this.searchButton.addActionListener(e -> {
            int result = fileChooser.showOpenDialog(fileChooser);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFolder = fileChooser.getSelectedFile();
                saveToProperties(selectedFolder.getAbsolutePath());

            } else {
                System.out.println("No folder selected.");
            }
        });
    }

    // TODO: disable button before action
    private void openBtnAction() {
        this.dirBtn.addActionListener(e -> {
            File dir = new File(directory.getText());
            if (dir.exists() && dir.isDirectory()) {
                try {
                    Desktop desktop = Desktop.getDesktop();
                    desktop.open(dir);
                } catch (IOException i) {
                    i.printStackTrace();
                }
            } else this.dirBtn.setEnabled(false);
        });
    }

    private void saveToProperties(String directory) {
        Properties props = new Properties();
        props.setProperty("dirUri", directory);

        try (FileOutputStream output = new FileOutputStream("config.properties")) {
            props.store(output, "Application config");
            this.directory.setText(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readPropertiesFile() {
        Properties props = new Properties();

        try (FileInputStream input = new FileInputStream("config.properties")) {
            props.load(input);
            this.directory.setText(props.getProperty("dirUri"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
