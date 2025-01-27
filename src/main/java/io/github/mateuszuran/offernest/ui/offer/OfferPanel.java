package main.java.io.github.mateuszuran.offernest.ui.offer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class OfferPanel extends JPanel {
    private final JButton textButton = new JButton("Add");
    private final JPanel listPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
    private final JTextField text = new JTextField();


    public OfferPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel offerLabel = new JLabel("Offers");
        offerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        offerLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));


        add(offerLabel);
        add(createTextPanel());
        add(createOffersList());

        initializeActions();
    }

    private void initializeActions() {
        textButton.addActionListener(e -> {
            listPanel.add(createItemPanel(text.getText()));
            listPanel.repaint();
            listPanel.revalidate();
        });
    }

    private JPanel createTextPanel() {
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.X_AXIS));
        textPanel.setAlignmentX(Component.LEFT_ALIGNMENT);


        text.setMinimumSize(new Dimension(100, 25));
        text.setMaximumSize(new Dimension(150, 25));
        text.setAlignmentX(Component.LEFT_ALIGNMENT);
        textButton.setAlignmentX(Component.RIGHT_ALIGNMENT);

        textPanel.add(text);
        textPanel.add(textButton);

        return textPanel;
    }

    private JPanel createItemPanel(String link) {
        JPanel itemPanel = new JPanel(new BorderLayout());
        JLabel item = new JLabel(link);
        item.setPreferredSize(new Dimension(80, 30));
        itemPanel.add(item, BorderLayout.WEST);
        return itemPanel;
    }

    public JPanel createOffersList() {
        // data to test
        ArrayList<String> links = new ArrayList<>();
        links.add("link1");
        links.add("link2");
        links.add("link3");

        for (String link : links) {
            listPanel.add(createItemPanel(link));
        }

        listPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return listPanel;
    }
}
