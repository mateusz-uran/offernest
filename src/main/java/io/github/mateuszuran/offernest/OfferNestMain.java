package io.github.mateuszuran.offernest;

import io.github.mateuszuran.offernest.v2.config.ApplicationConfig;
import io.github.mateuszuran.offernest.v2.entity.ResumeEntity;
import io.github.mateuszuran.offernest.v2.logic.FileService;

import java.util.List;

public class OfferNestMain {
    // TODO: replace with new MainFrame when created
//    public static void main(String[] args) {
//        EventQueue.invokeLater(MainFrame::new);
//    }

    public static void main(String[] args) {
        ApplicationConfig config = new ApplicationConfig();
        FileService fileService = new FileService(config);
        fileService.saveResume(new ResumeEntity("test apki", "random path", List.of()));
//        config.saveApplicationConfig("D:/Dokumenty/aplikowanko");
    }
}

/*
1. Open and and check
* */