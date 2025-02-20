package io.github.mateuszuran.offernest.v2.service.logic;

import javax.swing.*;
import java.io.File;

public class GlobalFileChooser {

    /**
     * int choice = JFileChooser static value
     * FILES_ONLY = 0
     * DIRECTORIES_ONLY = 1
     */
    public static String chooseFile(int choice) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(choice);

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = fileChooser.getSelectedFile();
            return selectedFolder.getAbsolutePath();

        }
        System.out.println("No folder selected.");
        return "";
    }
}
