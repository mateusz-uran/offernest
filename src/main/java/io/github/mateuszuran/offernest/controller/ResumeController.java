package io.github.mateuszuran.offernest.controller;

import io.github.mateuszuran.offernest.entity.ResumeEntity;
import io.github.mateuszuran.offernest.service.ResumeService;

import java.util.List;

public class ResumeController {
    private final ResumeService service;

    public ResumeController() {
        this.service = ResumeService.getInstance();
    }

    public List<ResumeEntity> getResumes() {
        return service.getResumeEntities();
    }

    public void addResume(ResumeEntity entity) {
        service.editJsonData(entity, false, false);
    }

    public void deleteResume(ResumeEntity entity) {
        service.editJsonData(entity, true, true);
    }

    public void deleteOffers(ResumeEntity entity) {
        service.editJsonData(entity, true, false);
    }
}
