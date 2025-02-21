package io.github.mateuszuran.offernest.v2.ui.content;

import io.github.mateuszuran.offernest.v2.entity.ResumeEntity;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class ResumePanel extends JPanel {
    private static final int PANEL_HEIGHT = 220;

    public ResumePanel(ResumeEntity entity, Consumer<ResumeEntity> deleteCallback) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        setMaximumSize(new Dimension(Integer.MAX_VALUE, PANEL_HEIGHT));
        setPreferredSize(new Dimension(getPreferredSize().width, PANEL_HEIGHT));

        OffersPanel offersPanel = new OffersPanel(entity);

        JButton openButton = new JButton("Open");
        openButton.addActionListener(e -> openPdf(entity.getPdfPath()));

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteCallback.accept(entity));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(deleteButton);

        add(openButton, BorderLayout.WEST);
        add(offersPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
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
        }
    }
}
