package io.github.mateuszuran.offernest.v2.ui.content;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mateuszuran.offernest.v2.service.ResumeService;
import io.github.mateuszuran.offernest.v2.service.logic.FileService;
import io.github.mateuszuran.offernest.v2.service.logic.JsonService;

import javax.swing.*;
import java.awt.*;

public class ContentPanel extends JPanel {

    public ContentPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel label = new JLabel("Directory");

        add(label);
    }

    private void gatherContent() {
        FileService fileService = new FileService();
        JsonService jsonService = new JsonService(new ObjectMapper());
        ResumeService resumeService = new ResumeService(fileService, jsonService);

    }
}
