package io.github.mateuszuran.offernest.v2.service;

import io.github.mateuszuran.offernest.v2.entity.ResumeEntity;
import io.github.mateuszuran.offernest.v2.service.logic.FileService;
import io.github.mateuszuran.offernest.v2.service.logic.JsonService;

import java.nio.file.Path;
import java.util.List;
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
     * */
    public void saveResumeEntityDataToJson(ResumeEntity entity) {
        saveResumePdf(entity.getPdfPath(), entity.getNote())
                .ifPresent(path -> jsonService.writeToJsonFile(path.toString(), entity.getOffers()));
    }

    /**
     * Update existing ResumeEntity object inside json file.
     * */
    public void updateResumeEntity(String resumePath, List<String> offers) {
        jsonService.writeToJsonFile(resumePath, offers);
    }

    /**
     * Save resume PDF in created directory.
     * */
    private Optional<Path> saveResumePdf(String pdfPath, String resumeNote) {
        return fileService.saveResume(pdfPath, resumeNote);
    }
}
