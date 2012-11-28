package db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Column {
    public static final String[] NAMES = new String[] { "ro", "en" };

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
        return NAMES[index];
    }

    public String getValue() {
        return value;
    }

    public static String createWhereSql(String[] values) {
        String where = new String();
        for (int i = 0; i <= MAX_COL_INDEX; i++) {
            if (values[i].trim().length() > 0) {
                if (where.length() > 0)
                    where += " AND";
                where += " " + NAMES[i] + " like ?";
            }
        }
        return where;
    }

    // TODO add instructions
    public static void addSqlParams(PreparedStatement ps, String[] values) {
        try {
            for (int i = 0; i <= MAX_COL_INDEX; i++) {
                String value = values[i];
                if (value.trim().length() > 0)
                    ps.setString(i + 1, value.replace('*', '%').replace("?", "_"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
