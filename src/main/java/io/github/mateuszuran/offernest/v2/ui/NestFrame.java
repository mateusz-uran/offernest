package io.github.mateuszuran.offernest.v2.ui;

import io.github.mateuszuran.offernest.v2.ui.content.ContentPanel;
import io.github.mateuszuran.offernest.v2.ui.directory.DirectoryPanel;

import javax.swing.*;
import java.awt.*;

public class NestFrame extends JFrame {

    public NestFrame() {
        setTitle("Offer nest");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setMinimumSize(new Dimension(800, 700));
        setLayout(new BorderLayout());

        setLocationRelativeTo(null);
        setVisible(true);

        add(new DirectoryPanel(), BorderLayout.NORTH);
        add(content(), BorderLayout.CENTER);
    }

    private JScrollPane content() {
        ContentPanel contentPanel = new ContentPanel();
        JScrollPane scrollPane = new JScrollPane(contentPanel);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scrollPane;
    }
}
