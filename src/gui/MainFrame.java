package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;

import javax.imageio.ImageIO;
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
import de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 2269971701250845501L;
    private JTextField roField;
    private JTextField enField;

    public MainFrame() {
        super("Dictionar roman-englez");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(getImage("resources/book.png"));

        final ResultsTable resultsTable = new ResultsTable(new MyTableModel());

        Container pane = getContentPane();
        // pane.setLayout(new MigLayout("debug,fill,novisualpadding"));
        pane.setLayout(new MigLayout("fill"));

        roField = SearchField.createRomana();
        pane.add(roField, "grow,w 50%");
        enField = SearchField.createEnglish();
        pane.add(enField, "grow,w 50%");
        JButton bTranslate = createButton("resources/search.png");
        JButton bAdd = createButton("resources/add.png");
        bAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultsTable.addTranlation();
            }
        });
        JButton bRemove = createButton("resources/remove.png");
        bRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultsTable.deleteSelected();
            }
        });
        bAdd.setBackground(Color.GRAY);
        bRemove.setBackground(Color.GRAY);

        pane.add(bTranslate);
        pane.add(bAdd);
        pane.add(bRemove, "wrap");
        // pane.add(createButton("resources/help.png"), "wrap");
        resizeHeight(roField, bTranslate);
        resizeHeight(enField, bTranslate);

        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.getVerticalScrollBar().setUnitIncrement(4);
        pane.add(scrollPane, "span,wrap,grow,h 100%");

        bTranslate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultsTable.search(roField.getText(), enField.getText());
            }
        });

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
        Image img = getImage(imagePath);
        if (img != null)
            button.setIcon(new ImageIcon(img));
        button.setMargin(new Insets(0, 0, 0, 0));
        // button.setMaximumSize(button.getPreferredSize());
        return button;
    }

    public static Image getImage(String path) {
        URL url = ClassLoader.getSystemResource(path);
        Image img = null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    public static void main(String[] args) {
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
}
