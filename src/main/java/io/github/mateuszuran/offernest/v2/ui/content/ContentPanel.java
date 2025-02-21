package io.github.mateuszuran.offernest.v2.ui.content;

import io.github.mateuszuran.offernest.v2.controller.ResumeController;
import io.github.mateuszuran.offernest.v2.entity.ResumeEntity;
import io.github.mateuszuran.offernest.v2.ui.form.ResumeDialog;

import javax.swing.*;
import java.util.List;

public class ContentPanel extends JPanel {
    private final ResumeController resumeController;
    private final JButton dialogButton;
    private final JLabel label;

    public ContentPanel() {
        this.resumeController = new ResumeController();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        label = new JLabel("Content");
        dialogButton = new JButton("Add resume");
        dialogButton.addActionListener(e -> new ResumeDialog(this, resumeController, this::gatherContent));

        add(dialogButton);
        add(label);
        gatherContent();
    }

    private void gatherContent() {
        removeAll();
        add(dialogButton);
        add(label);

        List<ResumeEntity> entities = resumeController.getResumes();

        if (entities.isEmpty()) {
            add(new JLabel("No resumes found."));
        } else {
            for (ResumeEntity entity : entities) {
                add(new ResumePanel(entity, this::deleteElement));
            }
        }

        revalidate();
        repaint();
    }

    private void deleteElement(ResumeEntity entity) {
        resumeController.deleteResume(entity);
        gatherContent();
        revalidate();
        repaint();
    }
}
