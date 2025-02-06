package io.github.mateuszuran.offernest.ui.resumes;

import io.github.mateuszuran.offernest.ui.offer.OfferPanel;

import javax.swing.*;
import java.awt.*;

public class ResumePanel extends JPanel {

    public ResumePanel(int number) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(createResumeRow(number));
    }

    private JPanel createResumeRow(int number) {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("CV #" + number);
        JButton open = new JButton("Open");

        panel.add(label);
        panel.add(open);

        return panel;
    }
}
