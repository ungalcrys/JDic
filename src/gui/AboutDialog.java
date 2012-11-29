package gui;

import gui.wrap.JLink;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.net.URISyntaxException;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import net.miginfocom.swing.MigLayout;
import resources.ResourceLoader;
import config.Configuration;

public class AboutDialog extends JDialog {

    private static final long serialVersionUID = -4147155878851661385L;

    private static final boolean DEBUG = false;
    private static final int YEAR = 2012;
    private static final String HOMEPAGE = "www.ungalcrys.ro/jdic";

    public AboutDialog(JFrame owner) {
        super(owner, "About", ModalityType.APPLICATION_MODAL);

        Container pane = getContentPane();
        if (DEBUG) {
            pane.setLayout(new MigLayout("debug, fill, novisualpadding"));
        } else {
            pane.setLayout(new MigLayout("fill"));
        }

        pane.add(new JLabel(new ImageIcon(ResourceLoader.getImage("book.png"))), "spany 3");
        JLabel lAppName = new JLabel(Configuration.APP_NAME);
        lAppName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        pane.add(lAppName, "wrap");
        JLabel lVersion = new JLabel("Version 1.0");
        lVersion.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        pane.add(lVersion, "wrap");
        JLink lAdress = null;
        try {
            lAdress = new JLink(HOMEPAGE, HOMEPAGE);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }
        pane.add(lAdress, "wrap");

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel paAbout = new JPanel(new MigLayout());
        paAbout.add(new JLabel("Sample Dictionary Application"), "wrap");
        String period = String.valueOf(YEAR);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if (YEAR != currentYear)
            period += " - " + currentYear;
        paAbout.add(new JLabel("(C) " + period + " Alexandru-Cristian Ungureanu"), "wrap");
        JLink liLicense = new JLink("License: GNU General Public License Version 3");
        liLicense.setAction(new JLink.Action() {
            @Override
            public void execute() {
                new LicenseDialog(AboutDialog.this).setVisible(true);
            }
        });
        paAbout.add(liLicense, "wrap");

        tabbedPane.add("About", new JScrollPane(paAbout));
        String creditsText = ResourceLoader.getText("credits.txt");
        JEditorPane editorPane = new JEditorPane("text/html", creditsText);
        editorPane.setEditable(false);
        editorPane.setCaretPosition(0);
        JScrollPane spCredits = new JScrollPane(editorPane);
        spCredits.setPreferredSize(new Dimension(400, 100));
        tabbedPane.addTab("Credits", spCredits);
        pane.add(tabbedPane, "spanx 2, grow, wrap");
        pack();

        setLocationRelativeTo(owner);
    }

}
