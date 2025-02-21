package io.github.mateuszuran.offernest.ui.content;

import io.github.mateuszuran.offernest.controller.ResumeController;
import io.github.mateuszuran.offernest.entity.Observer;
import io.github.mateuszuran.offernest.entity.ResumeEntity;

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
        setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK));
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

        add(inputPanel, BorderLayout.NORTH);
        add(offersListPanel, BorderLayout.CENTER);
    }

    @Override
    public void update() {
        offersListPanel.removeAll();
        checkBoxMap.clear();

        for (String offer : entity.getOffers()) {
            JPanel offerRow = new JPanel(new FlowLayout(FlowLayout.LEFT));

            JCheckBox checkBox = new JCheckBox();
            checkBoxMap.put(offer, checkBox);
            offerRow.add(checkBox);

            JTextField offerText = new JTextField(offer);
            offerText.setEditable(false);
            offerText.setPreferredSize(new Dimension(520, offerText.getPreferredSize().height));

            offerRow.add(offerText);

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
