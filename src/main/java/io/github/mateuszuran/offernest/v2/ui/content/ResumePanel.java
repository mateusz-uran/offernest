package io.github.mateuszuran.offernest.v2.ui.content;

import io.github.mateuszuran.offernest.v2.entity.ResumeEntity;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class ResumePanel extends JPanel {
    private static final int PANEL_HEIGHT = 150;

    public ResumePanel(ResumeEntity entity, Consumer<ResumeEntity> deleteCallback) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.BLACK));

        setMaximumSize(new Dimension(Integer.MAX_VALUE, PANEL_HEIGHT));
        setPreferredSize(new Dimension(getPreferredSize().width, PANEL_HEIGHT));

        OffersPanel offersPanel = new OffersPanel(entity);
        JScrollPane scrollPane = new JScrollPane(offersPanel);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(getPreferredSize().width, PANEL_HEIGHT - 20));

        JPanel navigationPanel = createNavPanel(entity);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteCallback.accept(entity));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK));
        buttonPanel.add(deleteButton);

        add(navigationPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
    }

    private JPanel createNavPanel(ResumeEntity entity) {
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.Y_AXIS));
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel noteLabel = new JLabel("<html><div style='text-align: center; width: 70px;'>" + (entity.getNote() == null ? "" : entity.getNote()) + "</div></html>");
        noteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton openButton = new JButton("Open");
        openButton.addActionListener(e -> openPdf(entity.getPdfPath()));
        openButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        navigationPanel.add(noteLabel);
        navigationPanel.add(Box.createVerticalStrut(10)); // 10px odstępu
        navigationPanel.add(openButton);

        return navigationPanel;
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
