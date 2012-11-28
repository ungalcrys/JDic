package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class SearchField extends JTextField {
    private static final String TEXT_ROMANA = "romana";
    private static final String TEXT_ENGLISH = "english";

    private static final long serialVersionUID = 6538760168724796092L;
    private String inactiveText;

    private SearchField(final String inactiveText) {
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

    public static SearchField createRomana() {
        return new SearchField(TEXT_ROMANA);
    }

    public static SearchField createEnglish() {
        return new SearchField(TEXT_ENGLISH);
    }

    public boolean isEmpty() {
        String text = super.getText();
        return text.trim().length() == 0 || getForeground().equals(Color.LIGHT_GRAY) && text.equals(inactiveText);
    }

    @Override
    public String getText() {
        if (isEmpty())
            return new String();
        else
            return super.getText();
    }

}
