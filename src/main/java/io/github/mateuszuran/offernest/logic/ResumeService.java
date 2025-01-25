package main.java.io.github.mateuszuran.offernest.logic;

import main.java.io.github.mateuszuran.offernest.config.ConfigManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ResumeService {
    private static final String DEFAULT_DIRECTORY = "default_resume";

    public static void addResume(String note, String filePath) {
        String targetDirectoryPath = ConfigManager.readDirectory()
                + File.separator
                + (note.isEmpty() ? DEFAULT_DIRECTORY : removeWhiteSpaces(note));

        File subdirectory = new File(targetDirectoryPath);

        if (!subdirectory.exists()) {
            if (!subdirectory.mkdir()) {
                return;
            }
        } else {
            // TODO: window popup for user
            System.out.println("Katalog już istnieje: " + targetDirectoryPath);
        }

        File sourceFile = new File(filePath);
        if (!sourceFile.exists() || !sourceFile.isFile()) {
            // TODO: handle error
            System.err.println("Plik źródłowy nie istnieje lub nie jest plikiem: " + filePath);
            return;
        }

        String fileName = sourceFile.getName();
        Path sourcePath = sourceFile.toPath();
        Path destinationPath = Paths.get(subdirectory.getAbsolutePath(), fileName);

        try {
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Plik został skopiowany do: " + destinationPath);
        } catch (IOException e) {
            System.err.println("Błąd podczas kopiowania pliku: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String removeWhiteSpaces(String note) {
        return note.replaceAll(" ", "_");
    }
}
