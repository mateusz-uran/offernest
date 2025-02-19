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
    public void writeToJsonFile(String pdfPath, List<String> offers, boolean removeData, boolean removeEntity) {

        createJsonFile().ifPresent(path -> {
            var jsonContent = readJsonFile(path);

            if (removeData) {
                removeResumesOrOffers(jsonContent, new ResumeEntity(pdfPath, offers), path, removeEntity);
            } else {
                addResumesOrOffers(jsonContent, new ResumeEntity(pdfPath, offers), path);
            }
        });
    }

    /**
     * Loop existing entities:
     * - when entity exists and removeEntity flag is true - delete entity
     * - when entity exists and removeEntity flag is false - delete selected offers from entity
     */
    private void removeResumesOrOffers(List<ResumeEntity> entities, ResumeEntity data, String jsonPath, boolean removeEntity) {
        entities.removeIf(entity -> entity.getPdfPath().equals(data.getPdfPath()) && removeEntity);

        for (ResumeEntity entity : entities) {
            if (entity.getPdfPath().equals(data.getPdfPath())) {
                entity.getOffers().removeAll(data.getOffers());
            }
        }

        writeToFile(jsonPath, entities);
    }

    /**
     * Loop existing entities:
     * - when entity exists add new offers to list
     * - when entity not exists add to new ResumeEntity to list
     */
    private void addResumesOrOffers(List<ResumeEntity> entities, ResumeEntity data, String jsonPath) {
        if (entities.isEmpty()) {
            entities.add(data);
            writeToFile(jsonPath, entities);
            return;
        }

        for (ResumeEntity entity : entities) {
            if (entity.getPdfPath().equals(data.getPdfPath())) {
                for (String offer : data.getOffers()) {
                    if (!entity.getOffers().contains(offer)) {
                        entity.getOffers().add(offer);
                    }
                }
                writeToFile(jsonPath, entities);
                return;
            }
        }

        entities.add(data);
        writeToFile(jsonPath, entities);
    }

    /**
     * Write to json file list of ResumeEntities
     */
    private void writeToFile(String filePath, List<ResumeEntity> entities) {
        try (FileWriter writer = new FileWriter(filePath)) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, entities);
        } catch (IOException e) {
            System.err.println("Error when adding new entities: " + e.getMessage());
        }
    }

    /**
     * Read json file with ResumeEntities and return list of objects.
     */
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
