package io.github.mateuszuran.offernest.v2.ui.content;

import io.github.mateuszuran.offernest.v2.controller.ResumeController;
import io.github.mateuszuran.offernest.v2.entity.ResumeEntity;
import io.github.mateuszuran.offernest.v2.ui.form.ResumeDialog;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ContentPanel extends JPanel {
    private final ResumeController resumeController;
    private final JPanel resumesContainer;

    public ContentPanel() {
        this.resumeController = new ResumeController();
        setLayout(new BorderLayout());

        resumesContainer = new JPanel();
        resumesContainer.setLayout(new BoxLayout(resumesContainer, BoxLayout.Y_AXIS));

        add(createAddResumePanel(), BorderLayout.NORTH);
        add(resumesContainer, BorderLayout.CENTER);

        gatherContent();
    }

    private JPanel createAddResumePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton dialogButton = new JButton("Add resume");
        dialogButton.addActionListener(e -> new ResumeDialog(this, resumeController, this::gatherContent));
        panel.add(dialogButton);
        return panel;
    }

    private void gatherContent() {
        resumesContainer.removeAll();

        List<ResumeEntity> entities = resumeController.getResumes();

        if (entities.isEmpty()) {
            resumesContainer.add(new JLabel("No resumes found."));
        } else {
            for (ResumeEntity entity : entities) {
                resumesContainer.add(new ResumePanel(entity, this::deleteElement));
            }
        }

        resumesContainer.revalidate();
        resumesContainer.repaint();
    }

    private void deleteElement(ResumeEntity entity) {
        resumeController.deleteResume(entity);
        gatherContent();
        revalidate();
        repaint();
    }
}
