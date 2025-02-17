package io.github.mateuszuran.offernest.v2.logic;

import io.github.mateuszuran.offernest.v2.config.ApplicationConfig;
import io.github.mateuszuran.offernest.v2.entity.ResumeEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileService {
    private final ApplicationConfig config;

    public FileService(ApplicationConfig config) {
        this.config = config;
    }

    public void saveResume(ResumeEntity entity) {
        File file = new File(entity.getPdfPath());

        if (!isPdfFile(file)) {
            System.out.println("File must be a valid PDF.");
            return;
        }

        File resumeDirectory = createResumeDirectory(entity.getNote());
        copyFileToResumeDirectory(file, resumeDirectory);
    }

    private void copyFileToResumeDirectory(File filePath, File resumeDirectory) {
        Path destinationPath = Paths.get(resumeDirectory.getAbsolutePath(), filePath.getName());

        try {
            Files.copy(filePath.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied to: " + destinationPath);
        } catch (IOException e) {
            System.err.println("Error copying file: " + e.getMessage());
        }
    }

    private boolean isPdfFile(File filePath) {
        if (!filePath.exists() || !filePath.isFile()) {
            return false;
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            byte[] header = new byte[4];
            if (fis.read(header) == 4) {
                return header[0] == '%' && header[1] == 'P' && header[2] == 'D' && header[3] == 'F';
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return false;
    }


    private File createResumeDirectory(String note) {
        String directoryName = (note == null || note.isBlank() ? "default" : note.replaceAll("\\s+", "_"));


        var mainDirectory = config.readApplicationConfig();
        if (mainDirectory == null || mainDirectory.isBlank()) {
            throw new IllegalStateException("Main directory is not set in config.");
        }

        File newDirectory = new File(mainDirectory, directoryName);

        if (newDirectory.exists()) {
            System.out.println("Directory already exists: " + newDirectory.getAbsolutePath());
        } else if (newDirectory.mkdirs()) {
            System.out.println("Directory created: " + newDirectory.getAbsolutePath());
        } else {
            System.out.println("Failed to create directory.");
        }

        return newDirectory;
    }
}
