package gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.miginfocom.swing.MigLayout;
import resources.ResourceLoader;
import config.Configuration;
import db.Column;
import de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = 2269971701250845501L;

    private static final boolean DEBUG = false;

    private JTextField roField;
    private JTextField enField;
    private ResultsTable resultsTable;

    public MainFrame() {
        super(Configuration.APP_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(ResourceLoader.getImage("book.png"));

        resultsTable = new ResultsTable(new MyTableModel());

        Container pane = getContentPane();
        if (DEBUG) {
            pane.setLayout(new MigLayout("debug, fill, novisualpadding"));
        } else {
            pane.setLayout(new MigLayout("fill"));
        }

        SearchListener searchListener = new SearchListener();
        roField = new SearchField(Column.LONG_NAMES[Column.RO_INDEX], searchListener);
        enField = new SearchField(Column.LONG_NAMES[Column.EN_INDEX], searchListener);
        pane.add(roField, "grow, w 50%, span 2");
        pane.add(enField, "grow, w 50%");
        JButton bTranslate = createButton("search.png");
        JButton bAdd = createButton("add.png");
        bAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultsTable.addTranlation();
            }
        });
        JButton bRemove = createButton("remove.png");
        bRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultsTable.deleteSelected();
            }
        });
        // bAdd.setBackground(Color.GRAY);
        // bRemove.setBackground(Color.GRAY);

        pane.add(bTranslate, "wrap");
        resizeHeight(roField, bTranslate);
        resizeHeight(enField, bTranslate);

        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.getVerticalScrollBar().setUnitIncrement(4);
        pane.add(scrollPane, "span, wrap, grow, h 100%");

        pane.add(bAdd);
        pane.add(bRemove);
        pane.add(Box.createHorizontalGlue(), "grow");
        // TODO add instructions
        JButton bHelp = createButton("help.png");
        bHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AboutDialog(MainFrame.this).setVisible(true);
            }
        });
        pane.add(bHelp, "wrap");

        bTranslate.addActionListener(searchListener);

        setUndecorated(true);
        setDefaultLookAndFeelDecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.FRAME);

        pack();
        scrollPane.setMinimumSize(scrollPane.getPreferredSize());
        resultsTable.requestFocusInWindow();
        resultsTable.search("", "");
        setPreferredSize(getMinimumSize());
    }

    private void resizeHeight(JTextField textField, JButton translate) {
        textField.setPreferredSize(new Dimension(textField.getPreferredSize().width,
                translate.getPreferredSize().height));
    }

    private JButton createButton(String imagePath) {
        JButton button = new JButton();
        Image img = ResourceLoader.getImage(imagePath);
        if (img != null)
            button.setIcon(new ImageIcon(img));
        button.setMargin(new Insets(0, 0, 0, 0));
        return button;
    }

    public static void main(String[] args) {
        Configuration.initConfig();
        try {
            UIManager.setLookAndFeel(new SyntheticaAluOxideLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        MainFrame frame = new MainFrame();
        SwingUtilities.updateComponentTreeUI(frame);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    class SearchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            resultsTable.search(roField.getText(), enField.getText());
        }
    }
}
