package io.github.mateuszuran.offernest.v2.ui.content;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mateuszuran.offernest.v2.entity.ResumeEntity;
import io.github.mateuszuran.offernest.v2.service.ResumeService;
import io.github.mateuszuran.offernest.v2.service.logic.FileService;
import io.github.mateuszuran.offernest.v2.service.logic.JsonService;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ContentPanel extends JPanel {
    private ResumeService resumeService;

    public ContentPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Content");

        add(label);
        gatherContent();
    }

    private ResumeService getResumeService() {
        if (resumeService == null) {
            FileService fileService = new FileService();
            JsonService jsonService = new JsonService(new ObjectMapper());
            resumeService = new ResumeService(fileService, jsonService);
        }
        return resumeService;
    }

    private void gatherContent() {
        List<ResumeEntity> entities = getResumeService().getResumeEntities();

        if (entities.isEmpty()) {
            add(new JLabel("No resumes found."));
        } else {
            for (ResumeEntity entity : entities) {
                add(singlePanel(entity));
            }
        }

        revalidate();
        repaint();
    }

    private JPanel singlePanel(ResumeEntity entity) {
        JPanel singleElement = new JPanel(new BorderLayout());
        singleElement.setPreferredSize(new Dimension(getParentWidth(), 100));
        singleElement.setMaximumSize(new Dimension(getParentWidth(), 100));
        singleElement.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JPanel offersPanel = new JPanel();
        offersPanel.setLayout(new BoxLayout(offersPanel, BoxLayout.Y_AXIS));

        for (String offer : entity.getOffers()) {
            offersPanel.add(new JLabel(offer));
        }

        singleElement.add(createOpenButtonPanel(entity), BorderLayout.WEST);
        singleElement.add(offersPanel, BorderLayout.CENTER);
        singleElement.add(createDeleteButtonPanel(entity), BorderLayout.EAST);

        return singleElement;
    }

    private JPanel createOpenButtonPanel(ResumeEntity entity) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JButton button = new JButton("Open");
        button.addActionListener(e -> openPdf(entity.getPdfPath()));

        panel.add(button);
        return panel;
    }

    private JPanel createDeleteButtonPanel(ResumeEntity entity) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JButton button = new JButton("Delete");
        button.addActionListener(e -> deleteElement(entity));

        panel.add(button);
        return panel;
    }

    private void openPdf(String pdfPath) {
        File file = new File(pdfPath);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "File not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Cannot open the file!", "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Cannot open file: " + ex.getMessage());
        }
    }

    // TODO: need fixing cuz not efficient
    private void deleteElement(ResumeEntity entity) {
        getResumeService().editJsonData(entity, true, true);
        removeAll();
        gatherContent();
        revalidate();
        repaint();
    }

    private int getParentWidth() {
        return getParent() != null ? getParent().getWidth() : 1280;
    }
}
