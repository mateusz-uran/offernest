package io.github.mateuszuran.offernest.v2.service.logic;

import io.github.mateuszuran.offernest.v2.config.ApplicationConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

public class FileService {

    /**
     * Save resume pdf file when file extension is correct. Method returning given path when
     * note is empty otherwise newly created path to PDF file.
     * */
    public Optional<Path> createPdfPath(String pdfPath, String resumeNote) {
        File file = new File(pdfPath.replaceFirst("^/+", ""));

        if (!isPdfFile(file)) {
            System.out.println("File " + file.getPath() + " must be a valid PDF!");
            return Optional.empty();
        }

        if (resumeNote == null) {
            return Optional.of(Paths.get(file.getPath()));
        }

        File resumeDirectory = createResumeDirectory(resumeNote);
        return Optional.ofNullable(copyFileToResumeDirectory(file, resumeDirectory));
    }

    /**
     * Delete PDF directory with pdf file.
     * */
    public void deleteDirectory(Path directoryToDelete) {
        if (directoryToDelete == null || Files.notExists(directoryToDelete)) {
            System.out.println("Directory does not exist: " + directoryToDelete);
            return;
        }

        if (Files.isRegularFile(directoryToDelete)) {
            System.out.println("Given path is a file, not a directory: " + directoryToDelete);
            return;
        }

        try (Stream<Path> pathStream = Files.walk(directoryToDelete)) {
            pathStream.sorted(Comparator.reverseOrder()).forEach(path -> {
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    System.err.println("Failed to delete: " + path + " - " + e.getMessage());
                }
            });
            System.out.println("Deleted folder: " + directoryToDelete);
        } catch (IOException e) {
            System.err.println("Error while deleting directory: " + e.getMessage());
        }
    }

    /**
     * Copy given PDF file to newly created directory.
     * */
    private Path copyFileToResumeDirectory(File filePath, File resumeDirectory) {
        Path destinationPath = Paths.get(resumeDirectory.getAbsolutePath(), filePath.getName());

        try {
            Files.copy(filePath.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied to: " + destinationPath);
            return destinationPath;
        } catch (IOException e) {
            System.err.println("Error copying file: " + e.getMessage());
            return null;
        }
    }

    /**
     * Check if given file is actually an PDF file.
     * */
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

    /**
     * Create resume directory to store PDF file from given note.
     * */
    private File createResumeDirectory(String note) {
        String directoryName = note.replaceAll("\\s+", "_");

        var mainDirectory = ApplicationConfig.readApplicationConfig();
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
