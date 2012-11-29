package gui.wrap;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class JLink extends JLabel {
    private static final long serialVersionUID = -232376393355382911L;
    private URI adress;
    private Action action;

    public JLink(String text) {
        this(text, (URI) null);
    }

    public JLink(String text, String adress) throws URISyntaxException {
        this(text, new URI(adress));
    }

    public JLink(String text, final URI adress) {
        super(text);
        if (adress != null) {
            this.adress = adress;
            setToolTipText(adress.toString());
        }
        action = new LinkAction();
        setForeground(new Color(50, 50, 255));
        addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                new Thread() {
                    public void run() {
                        action.execute();
                    };
                }.start();
            }
        });
    }

    @Override
    public void setFont(Font font) {
        HashMap<TextAttribute, Integer> map = new HashMap<TextAttribute, Integer>();
        map.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        super.setFont(font.deriveFont(map));
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public static interface Action {
        void execute();
    }

    class LinkAction implements Action {
        @Override
        public void execute() {
            Desktop desktop = null;
            try {
                desktop = Desktop.getDesktop();
            } catch (UnsupportedOperationException ex) {
                ex.printStackTrace();
                String message = "Desktop API is not supported on the current platform.";
                String title = "Error";
                JOptionPane.showMessageDialog(JLink.this.getRootPane(), message, title, JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                desktop.browse(adress);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
