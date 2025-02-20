package io.github.mateuszuran.offernest.v2.ui;

import io.github.mateuszuran.offernest.v2.ui.directory.DirectoryPanel;

import javax.swing.*;
import java.awt.*;

public class NestFrame extends JFrame {

    public NestFrame() {
        setTitle("Offer nest");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1280, 960);
        setLayout(new BorderLayout());

        setLocationRelativeTo(null);
        setVisible(true);

        add(new DirectoryPanel(), BorderLayout.NORTH);
    }
}
