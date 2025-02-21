package io.github.mateuszuran.offernest.v2.ui.content;

import io.github.mateuszuran.offernest.v2.controller.ResumeController;
import io.github.mateuszuran.offernest.v2.entity.Observer;
import io.github.mateuszuran.offernest.v2.entity.ResumeEntity;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OffersPanel extends JPanel implements Observer {
    private final ResumeEntity entity;
    private final ResumeController controller;
    private final JTextField offerInputField;
    private final JPanel offersListPanel;
    private final Map<String, JCheckBox> checkBoxMap;

    public OffersPanel(ResumeEntity entity) {
        this.entity = entity;
        this.controller = new ResumeController();
        this.entity.addObserver(this);
        setLayout(new BorderLayout());
        checkBoxMap = new HashMap<>();

        offersListPanel = new JPanel();
        offersListPanel.setLayout(new BoxLayout(offersListPanel, BoxLayout.Y_AXIS));
        update();

        offerInputField = new JTextField(25);
        JButton addOfferButton = new JButton("Add offer");
        addOfferButton.addActionListener(e -> addOffer());

        JButton deleteSelectedButton = new JButton("Usuń zaznaczone");
        deleteSelectedButton.addActionListener(e -> removeSelectedOffers());

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.add(offerInputField);
        inputPanel.add(addOfferButton);
        inputPanel.add(deleteSelectedButton);

        add(offersListPanel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
    }

    @Override
    public void update() {
        offersListPanel.removeAll();
        checkBoxMap.clear();

        for (String offer : entity.getOffers()) {
            JPanel offerRow = new JPanel(new FlowLayout(FlowLayout.LEFT));

            JCheckBox checkBox = new JCheckBox(offer);
            checkBoxMap.put(offer, checkBox);
            offerRow.add(checkBox);

            offersListPanel.add(offerRow);
        }

        offersListPanel.revalidate();
        offersListPanel.repaint();
    }

    private void addOffer() {
        String newOffer = offerInputField.getText().trim();
        if (!newOffer.isEmpty()) {
            entity.addOffer(newOffer);
            controller.addResume(entity);
            offerInputField.setText("");
        }
    }

    private void removeSelectedOffers() {
        List<String> selectedOffers = getSelectedOffers();

        ResumeEntity updatedEntity = new ResumeEntity(entity.getPdfPath(), selectedOffers);

        controller.deleteOffers(updatedEntity);

        entity.getOffers().removeAll(selectedOffers);
        update();
    }

    private List<String> getSelectedOffers() {
        List<String> selectedOffers = new ArrayList<>();
        for (Map.Entry<String, JCheckBox> entry : checkBoxMap.entrySet()) {
            if (entry.getValue().isSelected()) {
                selectedOffers.add(entry.getKey());
            }
        }
        return selectedOffers;
    }


}
