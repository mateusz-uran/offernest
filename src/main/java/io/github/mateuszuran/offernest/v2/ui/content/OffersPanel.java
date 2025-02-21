package io.github.mateuszuran.offernest.v2.ui.content;

import io.github.mateuszuran.offernest.v2.entity.Observer;
import io.github.mateuszuran.offernest.v2.entity.ResumeEntity;

import javax.swing.*;
import java.awt.*;

public class OffersPanel extends JPanel implements Observer {
    private final ResumeEntity entity;
    private final JTextField offerInputField;
    private final JPanel offersListPanel;

    public OffersPanel(ResumeEntity entity) {
        this.entity = entity;
        this.entity.addObserver(this);
        setLayout(new BorderLayout());

        offersListPanel = new JPanel();
        offersListPanel.setLayout(new BoxLayout(offersListPanel, BoxLayout.Y_AXIS));
        update();

        offerInputField = new JTextField(25);
        JButton addOfferButton = new JButton("Add offer");
        addOfferButton.addActionListener(e -> addOffer());

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.add(offerInputField);
        inputPanel.add(addOfferButton);

        add(offersListPanel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
    }

    @Override
    public void update() {
        offersListPanel.removeAll();
        for (String offer : entity.getOffers()) {
            JPanel offerRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel offerLabel = new JLabel(offer);
            JButton removeOfferButton = new JButton("X");

            removeOfferButton.addActionListener(e -> removeOffer(offer));

            offerRow.add(offerLabel);
            offerRow.add(removeOfferButton);
            offersListPanel.add(offerRow);
        }
        offersListPanel.revalidate();
        offersListPanel.repaint();
    }

    private void addOffer() {
        String newOffer = offerInputField.getText().trim();
        if (!newOffer.isEmpty()) {
            entity.addOffer(newOffer);
            offerInputField.setText("");
        }
    }

    private void removeOffer(String offer) {
        entity.removeOffer(offer);
    }
}
