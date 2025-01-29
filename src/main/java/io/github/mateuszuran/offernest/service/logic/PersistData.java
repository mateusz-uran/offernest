package io.github.mateuszuran.offernest.service.logic;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mateuszuran.offernest.config.ConfigManager;
import io.github.mateuszuran.offernest.model.ResumeEntity;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersistData {
    private final static String OFFER_LINKS = "OFFERS";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static boolean checkIfJsonFileExists(String filePath) {
        return new File(filePath).exists();
    }

    public static List<ResumeEntity> readJsonFile() {
        String filePath = ConfigManager.readDirectory() + File.separator + OFFER_LINKS;

        if (checkIfJsonFileExists(filePath)) {
            try (FileReader reader = new FileReader(filePath)) {
                return objectMapper.readValue(reader, new TypeReference<>() {});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    public static void saveResume(ResumeEntity data) {
        String filePath = ConfigManager.readDirectory() + File.separator + OFFER_LINKS;
        List<ResumeEntity> existingData = readJsonFile();

        boolean entityExists = false;

        for (ResumeEntity entity : existingData) {
            if (entity.getResumePath().equals(data.getResumePath())) {
                List<String> actualOffers = entity.getOffers();

                for (String offer : data.getOffers()) {
                    if (!actualOffers.contains(offer)) {
                        actualOffers.add(offer);
                    }
                }
                entity.setOffers(actualOffers);
                entityExists = true;
                break;
            }
        }

        if (!entityExists) {
            ResumeEntity freshEntity = new ResumeEntity(data.getResumePath(), new ArrayList<>(data.getOffers()));
            existingData.add(freshEntity);
        }

        writeJsonFile(filePath, existingData);
    }


    private static void writeJsonFile(String filePath, List<ResumeEntity> data) {
        try (FileWriter writer = new FileWriter(filePath)) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
