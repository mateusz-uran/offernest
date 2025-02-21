package io.github.mateuszuran.offernest.v2.ui.content;

import io.github.mateuszuran.offernest.v2.controller.ResumeController;
import io.github.mateuszuran.offernest.v2.entity.ResumeEntity;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class ResumePanel extends JPanel {
    private final ResumeEntity entity;
    private final ResumeController resumeController;
    private final JPanel offersPanel;
    private final JTextField offerInputField;

    public ResumePanel(ResumeEntity entity, Consumer<ResumeEntity> deleteCallback) {
        this.entity = entity;
        this.resumeController = new ResumeController();
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        offersPanel = new JPanel();
        offersPanel.setLayout(new BoxLayout(offersPanel, BoxLayout.Y_AXIS));
        updateOffersPanel();

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        offerInputField = new JTextField(15);
        JButton addOfferButton = new JButton("Add Offer");

        addOfferButton.addActionListener(e -> addOffer());

        inputPanel.add(offerInputField);
        inputPanel.add(addOfferButton);

        JButton openButton = new JButton("Open");
        openButton.addActionListener(e -> openPdf(entity.getPdfPath()));

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteCallback.accept(entity));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(deleteButton);

        add(openButton, BorderLayout.WEST);
        add(offersPanel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.EAST);
    }

    private void updateOffersPanel() {
        offersPanel.removeAll();
        for (String offer : entity.getOffers()) {
            JPanel offerRow = new JPanel(new FlowLayout(FlowLayout.LEFT));

            JLabel offerLabel = new JLabel(offer);
            JButton removeOfferButton = new JButton("X");

            removeOfferButton.addActionListener(e -> removeOffer(offer));

            offerRow.add(offerLabel);
            offerRow.add(removeOfferButton);
            offersPanel.add(offerRow);
        }
        offersPanel.revalidate();
        offersPanel.repaint();
    }

    private void addOffer() {
        String newOffer = offerInputField.getText().trim();
        if (!newOffer.isEmpty()) {
            entity.getOffers().add(newOffer);
            resumeController.addResume(entity);
            updateOffersPanel();
            offerInputField.setText("");
        }
    }

    private void removeOffer(String offer) {
        entity.getOffers().remove(offer);
        resumeController.deleteOffers(entity);
        updateOffersPanel();
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
