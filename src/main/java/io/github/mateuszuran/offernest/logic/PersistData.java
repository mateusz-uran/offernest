package main.java.io.github.mateuszuran.offernest.logic;

import main.java.io.github.mateuszuran.offernest.config.ConfigManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PersistData {
    private final static String OFFER_LINKS = "OFFERS";

    public static void createJsonFile(ResumeEntity data) {
        var path = ConfigManager.readDirectory();

        if (new File(path).exists()) {
            String filePath = path + File.separator + OFFER_LINKS;
            System.out.println(filePath);

            try (FileWriter writer = new FileWriter(filePath)) {
                // TODO: get data and convert to JSON
//                writer.write(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Main directory doesn't exist.");
        }
    }
}
