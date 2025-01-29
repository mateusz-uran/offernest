package io.github.mateuszuran.offernest.ui;


import javax.swing.*;
import java.io.File;

public class GlobalFileChooser {

    /**
     * int choice = JFileChooser static value
     * FILES_ONLY = 0
     * DIRECTORIES_ONLY = 1
     * */
    public static String chooseFile(int choice) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(choice);

        int result = fileChooser.showOpenDialog(fileChooser);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = fileChooser.getSelectedFile();
            return selectedFolder.getAbsolutePath();

        } else {
            System.out.println("No folder selected.");
            return "";
        }
    }
}
