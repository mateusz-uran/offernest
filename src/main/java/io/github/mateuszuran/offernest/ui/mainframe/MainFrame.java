package io.github.mateuszuran.offernest.ui.mainframe;

import io.github.mateuszuran.offernest.service.OffersService;
import io.github.mateuszuran.offernest.service.ResumeService;
import io.github.mateuszuran.offernest.service.logic.PersistData;
import io.github.mateuszuran.offernest.model.ResumeEntity;
import io.github.mateuszuran.offernest.ui.directory.DirectoryPanel;
import io.github.mateuszuran.offernest.ui.resumes.ResumeDialog;
import io.github.mateuszuran.offernest.ui.resumes.ResumePanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {
    private JPanel mainContentPanel;

    public MainFrame() {
        createWindow();
        createUpperPanel();

        createMainPanel();
    }

    private void createWindow() {
        setTitle("Offer nest");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1280, 960);
        setLayout(new BorderLayout());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createUpperPanel() {
        JPanel upperPanel = new JPanel(new BorderLayout());
        upperPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JPanel buttonPanel = createButtonPanel();
        upperPanel.add(buttonPanel);

        DirectoryPanel directoryPanel = new DirectoryPanel();
        upperPanel.add(directoryPanel, BorderLayout.EAST);

        add(upperPanel, BorderLayout.NORTH);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        JButton addResume = new JButton("Add resume");
        addResume.addActionListener(e -> {
            try {
                new ResumeDialog(this);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error opening dialog: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE
                );
            }
        });

        buttonPanel.add(addResume);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        return buttonPanel;
    }

    private void createMainPanel() {
        mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));

        List<ResumeEntity> resumes = ResumeService.getAllResumes();

        for (ResumeEntity entity : resumes) {
            mainContentPanel.add(new ResumePanel(entity));
            mainContentPanel.add(Box.createVerticalStrut(10));
        }

        JScrollPane scrollPane = new JScrollPane(mainContentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(1200, 800));

        add(scrollPane, BorderLayout.CENTER);

        revalidate();
        repaint();
    }
}
