package io.github.mateuszuran.offernest.ui.offer;


import io.github.mateuszuran.offernest.logic.OffersService;

import javax.swing.*;
import java.util.List;
import java.awt.*;
import java.util.ArrayList;

public class OfferPanel extends JPanel {
    private final JButton addButton = new JButton("Add");
    private final JButton removeButton = new JButton("Remove");
    private final JPanel listPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
    private final JTextField linkField = new JTextField();
    private final List<JPanel> offerItems = new ArrayList<>();



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

        JPanel itemPanel = createLinkItem(link);
        listPanel.add(itemPanel);
        offerItems.add(itemPanel);

        listPanel.repaint();
        listPanel.revalidate();

        OffersService.gatherLinks(link);
        linkField.setText("");

        updateRemoveButtonState();
    }

    private JPanel createLinkItem(String link) {
        JPanel itemPanel = new JPanel(new BorderLayout());
        JLabel linkLabel = new JLabel(link);
        JCheckBox checkBox = new JCheckBox();

        itemPanel.add(linkLabel, BorderLayout.WEST);
        itemPanel.add(checkBox, BorderLayout.EAST);

        return itemPanel;
    }

    private void removeSelectedLinks() {
        List<JPanel> itemsToRemove = new ArrayList<>();

        for (JPanel item : offerItems) {
            JCheckBox checkBox = (JCheckBox) item.getComponent(1);
            if (checkBox.isSelected()) {
                listPanel.remove(item);
                itemsToRemove.add(item);
            }
        }

        offerItems.removeAll(itemsToRemove);

        listPanel.repaint();
        listPanel.revalidate();

        updateRemoveButtonState();
    }

    private void updateRemoveButtonState() {
        removeButton.setEnabled(!OffersService.checkListSize());
    }
}
