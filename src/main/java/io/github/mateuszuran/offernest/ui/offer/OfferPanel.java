package io.github.mateuszuran.offernest.ui.offer;


import io.github.mateuszuran.offernest.logic.OffersService;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class OfferPanel extends JPanel {
    private final JButton addButton = new JButton("Add");
    private final JButton removeButton = new JButton("Remove");
    private final JPanel listPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
    private final JTextField linkField = new JTextField();
    private final Map<JPanel, JCheckBox> offerItems = new HashMap<>();
    private final OffersService offers = new OffersService();

    public OfferPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(createOfferLabel());
        add(createInputPanel());
        add(createOffersListPanel());
        initializeActions();

        removeButton.setEnabled(false);
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
        inputPanel.add(removeButton);

        return inputPanel;
    }

    private JPanel createOffersListPanel() {
        listPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return listPanel;
    }

    private void initializeActions() {
        addButton.addActionListener(e -> addLink());
        removeButton.addActionListener(e -> removeSelectedLinks());
    }

    private void addLink() {
        String link = linkField.getText().trim();
        if (link.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Link cannot be empty.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JPanel itemPanel = new JPanel(new BorderLayout());
        JLabel linkLabel = new JLabel(link);
        JCheckBox checkBox = new JCheckBox();

        itemPanel.add(linkLabel, BorderLayout.WEST);
        itemPanel.add(checkBox, BorderLayout.EAST);

        listPanel.add(itemPanel);
        offerItems.put(itemPanel, checkBox);

        listPanel.repaint();
        listPanel.revalidate();

        offers.gatherLinks(link);
        linkField.setText("");

        updateRemoveButtonState();
    }

    private void removeSelectedLinks() {
        List<JPanel> itemsToRemove = new ArrayList<>();

        for (Map.Entry<JPanel, JCheckBox> entry : offerItems.entrySet()) {
            if (entry.getValue().isSelected()) {
                listPanel.remove(entry.getKey());
                itemsToRemove.add(entry.getKey());

                JLabel linkLabel = (JLabel) entry.getKey().getComponent(0);
                offers.removeLink(linkLabel.getText());
            }
        }

        for (JPanel panel : itemsToRemove) {
            offerItems.remove(panel);
        }

        listPanel.repaint();
        listPanel.revalidate();

        updateRemoveButtonState();
    }

    private void updateRemoveButtonState() {
        removeButton.setEnabled(!offers.checkListSize());
    }

    public List<String> getAllOffersLinks() {
        return offers.getLinks();
    }
}
