package io.github.mateuszuran.offernest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mateuszuran.offernest.v2.service.ResumeService;
import io.github.mateuszuran.offernest.v2.service.logic.FileService;
import io.github.mateuszuran.offernest.v2.service.logic.JsonService;

import java.util.List;

public class OfferNestMain {
    // TODO: replace with new MainFrame when created
//    public static void main(String[] args) {
//        EventQueue.invokeLater(MainFrame::new);
//    }

    public static void main(String[] args) {
        FileService fileService = new FileService();
        JsonService jsonService = new JsonService(new ObjectMapper());
        ResumeService resumeService = new ResumeService(fileService, jsonService);

        // call when adding new resume entity
//        resumeService.saveResumeEntityDataToJson(new ResumeEntity("notka tego", "D:/temp_folder/temp3.pdf", List.of("of4", "of7")));

        // call when updating existing resume path with new offers or when updating existing offers
        resumeService.updateResumeEntity("D:/Dokumenty/aplikowanko/notka_tego/temp3.pdf", List.of("of1"));
    }
}