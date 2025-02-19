package io.github.mateuszuran.offernest.v2.service;

import io.github.mateuszuran.offernest.v2.entity.ResumeEntity;
import io.github.mateuszuran.offernest.v2.service.logic.FileService;
import io.github.mateuszuran.offernest.v2.service.logic.JsonService;

import java.nio.file.Path;
import java.util.Optional;

public class ResumeService {
    private final FileService fileService;
    private final JsonService jsonService;

    public ResumeService(FileService fileService, JsonService jsonService) {
        this.fileService = fileService;
        this.jsonService = jsonService;
    }

    /**
     * Save resume PDF in created directory and write data to json file.
     * Update existing ResumeEntity object inside json file.
     */
    public void saveResumeEntityDataToJson(ResumeEntity entity, boolean removeData, boolean removeEntity) {
        saveResumePdf(entity.getPdfPath(), entity.getNote())
                .ifPresent(path -> {
                            jsonService.writeToJsonFile(
                                    path.toUri().getPath(),
                                    entity.getOffers(),
                                    removeData,
                                    removeEntity);

                            if (removeData && removeEntity) {
                                System.out.println("Deleting folder");
                                fileService.deleteDirectory(path);
                            }
                        }
                );
    }


    /**
     * Save resume PDF in created directory.
     */
    private Optional<Path> saveResumePdf(String pdfPath, String resumeNote) {
        return fileService.saveResume(pdfPath, resumeNote);
    }
}
