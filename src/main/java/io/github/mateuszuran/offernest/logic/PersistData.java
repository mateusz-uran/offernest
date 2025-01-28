package io.github.mateuszuran.offernest.logic;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mateuszuran.offernest.config.ConfigManager;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersistData {
    private final static String OFFER_LINKS = "OFFERS";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // TODO: rebuild persist data logic
    // read if json file exists
    // if exists check if added resume exists (check by path)
    // if exists add links to it
    // if not exists create file and add resume path with added links
    // add logic only for adding links to existing object

    private static boolean checkIfJsonFileExists(String configPath, String jsonPath) {
        return new File(configPath).exists() && new File(jsonPath).exists();
    }

    public static List<ResumeEntity> readJsonFile() {
        String configPath = ConfigManager.readDirectory();
        String jsonPath = File.separator + OFFER_LINKS;

        if (checkIfJsonFileExists(configPath, configPath + jsonPath)) {
            File file = new File(configPath + jsonPath);
            try (FileReader reader = new FileReader(file)) {
                return objectMapper.readValue(reader, new TypeReference<>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();

    }

    public void updateJsonFile(List<String> links) {
    }

    public static void createJsonFile(ResumeEntity data) {
        var path = ConfigManager.readDirectory();

        if (new File(path).exists()) {
            String filePath = path + File.separator + OFFER_LINKS;

            try (FileWriter writer = new FileWriter(filePath)) {
                var json = convertToJson(data);
                writer.write(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Main directory doesn't exist.");
        }
    }

    private static String convertToJson(ResumeEntity data) {
        StringBuilder jsonBuilder = new StringBuilder();

        // Tworzymy JSON ręcznie
        jsonBuilder.append("{\n");
        jsonBuilder.append("  \"resumePath\": \"").append(data.getResumePath()).append("\",\n");
        jsonBuilder.append("  \"offers\": [");

        List<String> offers = data.getOffers();
        for (int i = 0; i < offers.size(); i++) {
            jsonBuilder.append("\"").append(offers.get(i)).append("\"");
            if (i < offers.size() - 1) {
                jsonBuilder.append(", ");
            }
        }

        jsonBuilder.append("]\n");
        jsonBuilder.append("}");

        return jsonBuilder.toString();
    }
}
