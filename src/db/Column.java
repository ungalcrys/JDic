package db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import config.Configuration;

public class Column {
    public static final String[] SHORT_NAMES = new String[] { "ro", "en" };
    public static final String[] LONG_NAMES = new String[] { "romana", "english" };

    public static final int RO_INDEX = 0;
    public static final int EN_INDEX = 1;

    public static final int MAX_COL_INDEX = EN_INDEX;

    private int index;

    private String value;

    public Column(int index, String value) {
        this.index = index;
        this.value = value;
    }

    public String getName() {
        return SHORT_NAMES[index];
    }

    public String getValue() {
        return value;
    }

    public static String createWhereSql(String[] values) {
        String where = Configuration.EMPTY_STRING;
        for (int i = 0; i <= MAX_COL_INDEX; i++) {
            if (values[i].trim().length() > 0) {
                if (where.length() > 0)
                    where += " AND";
                where += " " + SHORT_NAMES[i] + " like ?";
            }
        }
        return where;
    }

    // TODO add help instructions in a frame
    public static void addSqlParams(PreparedStatement ps, String[] values) {
        int variableIndex = 1;
        try {
            for (int i = 0; i <= MAX_COL_INDEX; i++) {
                String value = values[i];
                if (value.trim().length() > 0)
                    ps.setString(variableIndex++, value.replace('*', '%').replace("?", "_"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
