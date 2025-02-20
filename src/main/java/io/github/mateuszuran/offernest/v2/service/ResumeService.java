package io.github.mateuszuran.offernest.v2.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mateuszuran.offernest.v2.entity.ResumeEntity;
import io.github.mateuszuran.offernest.v2.service.logic.FileService;
import io.github.mateuszuran.offernest.v2.service.logic.JsonService;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class ResumeService {
    private static volatile ResumeService instance;
    private final FileService fileService;
    private final JsonService jsonService;

    public ResumeService(FileService fileService, JsonService jsonService) {
        this.fileService = fileService;
        this.jsonService = jsonService;
    }

    public static ResumeService getInstance() {
        if (instance == null) {
            synchronized (ResumeService.class) {
                if (instance == null) {
                    instance = new ResumeService(new FileService(), new JsonService(new ObjectMapper()));
                }
            }
        }
        return instance;
    }

    public List<ResumeEntity> getResumeEntities() {
        return jsonService.getJsonContent();
    }

    /**
     * Save resume PDF in created directory and write data to json file.
     * Update existing ResumeEntity object inside json file.
     */
    public void editJsonData(ResumeEntity entity, boolean removeData, boolean removeEntity) {
        getPdfPath(entity.getPdfPath(), entity.getNote())
                .ifPresent(path -> {
                            jsonService.writeToJsonFile(
                                    path.toUri().getPath(),
                                    entity.getOffers(),
                                    removeData,
                                    removeEntity);

                            if (removeData && removeEntity) {
                                fileService.deleteDirectory(path.getParent());
                            }
                        }
                );
    }


    /**
     * Save resume PDF in created directory.
     */
    private Optional<Path> getPdfPath(String pdfPath, String resumeNote) {
        return fileService.createPdfPath(pdfPath, resumeNote);
    }
}
