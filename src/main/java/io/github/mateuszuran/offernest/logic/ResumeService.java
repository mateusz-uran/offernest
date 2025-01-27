package main.java.io.github.mateuszuran.offernest.logic;

import main.java.io.github.mateuszuran.offernest.config.ConfigManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class ResumeService {

    public static void addResume(String note, String filePath) {
        File subdirectory = createSubdirectory(note);

        if (subdirectory == null) {
            return;
        }

        File sourceFile = new File(filePath);
        if (!validateSourceFile(sourceFile)) {
            return;
        }

        copyFileToDirectory(sourceFile, subdirectory);
    }

    private static File createSubdirectory(String note) {
        String targetDirectoryPath = ConfigManager.readDirectory()
                + File.separator
                + (note.isEmpty() ? "default_resume" : note.replaceAll(" ", "_"));

        File subdirectory = new File(targetDirectoryPath);

        if (!subdirectory.exists()) {
            if (!subdirectory.mkdir()) {
                return null;
            }
        } else {
            // TODO: window popup for user
            System.out.println("Katalog już istnieje: " + targetDirectoryPath);
        }

        return subdirectory;
    }

    private static boolean validateSourceFile(File sourceFile) {
        if (!sourceFile.exists()) {
            System.err.println("Plik źródłowy nie istnieje: " + sourceFile.getAbsolutePath());
            return false;
        }
        if (!sourceFile.isFile()) {
            System.err.println("Podana ścieżka nie jest plikiem: " + sourceFile.getAbsolutePath());
            return false;
        }
        return true;
    }

    private static void copyFileToDirectory(File sourceFile, File targetDirectory) {
        String fileName = sourceFile.getName();
        Path sourcePath = sourceFile.toPath();
        Path destinationPath = Paths.get(targetDirectory.getAbsolutePath(), fileName);

        try {
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Plik został skopiowany do: " + destinationPath);

            PersistData.createJsonFile(
                    new ResumeEntity(destinationPath.toString(),
                            List.of("link1", "link2", "link3")
                    )
            );

        } catch (IOException e) {
            System.err.println("Błąd podczas kopiowania pliku: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
