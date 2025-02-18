package io.github.mateuszuran.offernest.v2.service.logic;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mateuszuran.offernest.v2.config.ApplicationConfig;
import io.github.mateuszuran.offernest.v2.entity.ResumeEntity;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JsonService {
    private final ObjectMapper objectMapper;

    public JsonService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Write data to JSON file (path to saved PDF file and array of offers).
     */
    public void writeToJsonFile(String resumePath, List<String> offers) {

        createJsonFile().ifPresent(path -> {
            var jsonContent = readJsonFile(path);

            // if json array is empty -> create ResumeEntity object and add to file
            // if json array contains ResumeEntity objects -> loop list and find object with given resumePath -> if not exists add to list and update json
            // if json array contains ResumeEntity objects -> loop list and find object with given resumePath -> if exists update offers and update json

            if (jsonContent.isEmpty()) {
                addResumeEntity(jsonContent, resumePath, offers, path);
            }
        });
    }

    private void addResumeEntity(List<ResumeEntity> entities, String pdfPath, List<String> offers, String path) {
        entities.add(new ResumeEntity(pdfPath, offers));

        try (FileWriter writer = new FileWriter(path)) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, entities);
        } catch (IOException e) {
            System.err.println("Error when adding new entities: " + e.getMessage());
        }
    }

    private List<ResumeEntity> readJsonFile(String path) {
        try (FileReader reader = new FileReader(path)) {
            return objectMapper.readValue(reader, new TypeReference<>() {
            });
        } catch (IOException e) {
            System.err.println("Error reading json file: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * Read main directory path and create JSON file with empty array to store ResumeEntity objects,
     * if json already exists return path to file.
     */
    private Optional<String> createJsonFile() {
        var mainDirectory = ApplicationConfig.readApplicationConfig();

        File jsonFile = new File(mainDirectory + File.separator + "resumes");

        if (jsonFile.exists()) {
            return Optional.of(jsonFile.getAbsolutePath());
        }

        try {
            if (jsonFile.createNewFile()) {
                try (FileWriter writer = new FileWriter(jsonFile)) {
                    writer.write("[]");
                }
                return Optional.of(jsonFile.getAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("Error creating JSON file: " + e.getMessage());
        }
        return Optional.empty();
    }
}
