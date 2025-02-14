package io.github.mateuszuran.offernest.ui.resumes;

import io.github.mateuszuran.offernest.model.ResumeEntity;
import io.github.mateuszuran.offernest.service.ResumeService;
import io.github.mateuszuran.offernest.ui.offer.OfferPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ResumePanel extends JPanel {
    private final JButton fileButton = new JButton("Open");
    private final ResumeService service = new ResumeService();

    public ResumePanel(ResumeEntity resume) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        initializeButtons(resume.getResumePath());
        add(createSingleResume(resume.getOffers()));
    }

    private void initializeButtons(String path) {
        fileButton.addActionListener(e -> service.openFile(path));
    }

    private JPanel createSingleResume(List<String> offers) {
        JPanel resumePanel = new JPanel(new BorderLayout());

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(fileButton, BorderLayout.CENTER);
        resumePanel.add(leftPanel, BorderLayout.WEST);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new OfferPanel(offers), BorderLayout.CENTER);
        resumePanel.add(centerPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(new JButton("Delete"), BorderLayout.CENTER);
        resumePanel.add(rightPanel, BorderLayout.EAST);

        return resumePanel;
    }
}
