package io.github.mateuszuran.offernest.v2.logic;

import io.github.mateuszuran.offernest.v2.config.ApplicationConfig;
import io.github.mateuszuran.offernest.v2.entity.ResumeEntity;

import java.io.File;

public class FileService {
    private final ApplicationConfig config;

    public FileService(ApplicationConfig config) {
        this.config = config;
    }

    public void saveResume(ResumeEntity entity) {
        var filePath = entity.getPdfPath();
        var offers = entity.getOffers();

        var resumeDirectory = createResumeDirectory(filePath);
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
