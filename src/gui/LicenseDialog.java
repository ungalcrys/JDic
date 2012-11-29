package gui;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import resources.ResourceLoader;

public class LicenseDialog extends JDialog {
    private static final long serialVersionUID = -3356096063532862423L;

    public LicenseDialog(JDialog owner) {
        super(owner, "License");
        JTextArea textArea = new JTextArea();
        textArea.setText(ResourceLoader.getText("gpl-3.0.txt"));
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 500));
        add(scrollPane);

        pack();
        setLocationRelativeTo(owner);
    }
}
