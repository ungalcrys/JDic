package gui;

import javax.swing.table.DefaultTableModel;

import db.Column;

public class MyTableModel extends DefaultTableModel {
    private static final long serialVersionUID = -7441506461478338443L;

    @Override
    public String getValueAt(int row, int column) {
        Object value = super.getValueAt(row, column);
        if (value == null)
            return "";
        return value.toString();
    }

    public String getRo(int row) {
        return getValueAt(row, Column.RO_INDEX);
    }

    public String getEn(int row) {
        return getValueAt(row, Column.EN_INDEX);
    }

    public String[] getValues(int row) {
        return new String[] { getRo(row), getEn(row) };
    }
}
