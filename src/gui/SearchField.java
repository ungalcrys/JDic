package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

public class SearchField extends JTextField {
    private static final long serialVersionUID = 6538760168724796092L;

    private String inactiveText;

    public SearchField(final String inactiveText, ActionListener listener) {
        super(20);
        setMaximumSize(new Dimension(1000, getPreferredSize().height));
        setMinimumSize(new Dimension(200, getPreferredSize().height));
        this.inactiveText = inactiveText;
        setActive(false);
        addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
                if (getText().trim().length() == 0) {
                    setActive(false);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                if (isEmpty())
                    setActive(true);
            }
        });
        addActionListener(listener);
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    setText("");
                }
            }
        });
    }

    private void setActive(boolean active) {
        if (active) {
            setForeground(Color.BLACK);
            setText("");
        } else {
            setForeground(Color.LIGHT_GRAY);
            setText(inactiveText);
        }
    }

    public boolean isEmpty() {
        String text = super.getText();
        return text.trim().length() == 0 || getForeground().equals(Color.LIGHT_GRAY) && text.equals(inactiveText);
    }

    @Override
    public String getText() {
        if (isEmpty())
            return "";
        else
            return super.getText();
    }

}
