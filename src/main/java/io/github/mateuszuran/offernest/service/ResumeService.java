package io.github.mateuszuran.offernest.service;

import io.github.mateuszuran.offernest.config.ConfigManager;
import io.github.mateuszuran.offernest.service.logic.PersistData;
import io.github.mateuszuran.offernest.model.ResumeEntity;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class ResumeService {

    public static void addResume(String note, String filePath, List<String> links) {
        File subdirectory = createOrGetSubdirectory(note);

        if (subdirectory == null || !validateSourceFile(filePath)) {
            return;
        }

        copyFileToDirectory(new File(filePath), subdirectory, links);
    }

    public static List<ResumeEntity> getAllResumes() {
        return PersistData.getResumes();
    }

    public static ResumeEntity getSingleResume(String resumePath) {
        return PersistData.getSingleResume(resumePath);
    }

    public void openFile(String path) {
        File file = new File(path);

        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static File createOrGetSubdirectory(String note) {
        String directoryName = note.isEmpty() ? "default_resume" : note.replaceAll(" ", "_");
        String targetDirectoryPath = ConfigManager.readDirectory() + File.separator + directoryName;

        File subdirectory = new File(targetDirectoryPath);
        if (!subdirectory.exists() && !subdirectory.mkdir()) {
            return null;
        }
        return subdirectory;
    }

    private static boolean validateSourceFile(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.isFile();
    }

    private static void copyFileToDirectory(File sourceFile, File targetDirectory, List<String> links) {
        Path destinationPath = Paths.get(targetDirectory.getAbsolutePath(), sourceFile.getName());

        try {
            Files.copy(sourceFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
            PersistData.saveResume(new ResumeEntity(destinationPath.toString(), links));
            System.out.println("File copied to: " + destinationPath);
        } catch (IOException e) {
            System.err.println("Error copying file: " + e.getMessage());
        }
    }
}
