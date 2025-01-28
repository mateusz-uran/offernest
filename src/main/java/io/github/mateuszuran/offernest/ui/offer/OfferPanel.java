package io.github.mateuszuran.offernest.ui.offer;


import io.github.mateuszuran.offernest.logic.OffersService;

import javax.swing.*;
import java.awt.*;

public class OfferPanel extends JPanel {
    private final JButton addButton = new JButton("Add");
    private final JPanel listPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
    private final JTextField linkField = new JTextField();


    public OfferPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(createOfferLabel());
        add(createInputPanel());
        add(createOffersListPanel());
        initializeActions();
    }

    private JLabel createOfferLabel() {
        JLabel offerLabel = new JLabel("Offers");
        offerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return offerLabel;
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        inputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        linkField.setMaximumSize(new Dimension(200, 25));
        inputPanel.add(linkField);
        inputPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        inputPanel.add(addButton);

        return inputPanel;
    }

    private JPanel createOffersListPanel() {
        listPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return listPanel;
    }

    private void initializeActions() {
        addButton.addActionListener(e -> addLink());
    }

    private void addLink() {
        String link = linkField.getText().trim();
        if (link.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Link cannot be empty.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        listPanel.add(createLinkItem(link));
        listPanel.repaint();
        listPanel.revalidate();

        OffersService.gatherLinks(link);
        linkField.setText("");
    }

    private JPanel createLinkItem(String link) {
        JPanel itemPanel = new JPanel(new BorderLayout());
        JLabel linkLabel = new JLabel(link);
        itemPanel.add(linkLabel, BorderLayout.WEST);
        return itemPanel;
    }
}
