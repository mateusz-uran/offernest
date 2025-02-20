package io.github.mateuszuran.offernest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mateuszuran.offernest.v2.entity.ResumeEntity;
import io.github.mateuszuran.offernest.v2.service.ResumeService;
import io.github.mateuszuran.offernest.v2.service.logic.FileService;
import io.github.mateuszuran.offernest.v2.service.logic.JsonService;
import io.github.mateuszuran.offernest.v2.ui.NestFrame;

import java.awt.*;
import java.util.List;

public class OfferNestMain {
    public static void main(String[] args) {
        EventQueue.invokeLater(NestFrame::new);
    }

    /*public static void main(String[] args) {
        FileService fileService = new FileService();
        JsonService jsonService = new JsonService(new ObjectMapper());
        ResumeService resumeService = new ResumeService(fileService, jsonService);

        // call when updating entity
        resumeService.editJsonData(new ResumeEntity( "third", "D:/temp_folder/temp3.pdf", List.of("link1", "link2")), false, false);
    }*/
}