package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class Database {
    private static final boolean DEBUG = false;

    protected static int getMaxId() {
        String sql = "SELECT MAX(id) from translations";
        int maxId = -1;
        try {
            Statement st = DbSqlite.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next())
                maxId = rs.getInt(1);
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxId;
    }

    public static void insert(String[] colValues) {
        String sql = "INSERT INTO translations (id, ro, en) VALUES (?, ?, ?)";
        try {
            if (DEBUG)
                System.out.println("sql: " + sql);
            PreparedStatement ps = DbSqlite.getConnection().prepareStatement(sql);
            ps.setInt(1, getMaxId() + 1);
            ps.setString(2, colValues[Column.RO_INDEX]);
            ps.setString(3, colValues[Column.EN_INDEX]);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // TODO add case in/sensitive
    public static HashMap<String, String> getTranslations(String[] values) {
        String sql = "SELECT ro,en FROM translations";
        String where = Column.createWhereSql(values);
        if (where.length() > 0)
            sql += " WHERE" + where;
        HashMap<String, String> map = new HashMap<String, String>();
        try {
            if (DEBUG)
                System.out.println("sql: " + sql);
            // System.out.println("sql: " + sql + "[" + col.getValue() + ", "
            // + whereColumns[Column.RO_INDEX] + ", " + whereColumns[Column.EN_INDEX]
            // + "]");
            PreparedStatement ps = DbSqlite.getConnection().prepareStatement(sql);
            Column.addSqlParams(ps, values);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                map.put(rs.getString(1), rs.getString(2));
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static void update(String[] whereColumns, Column col) {
        String sql = "UPDATE translations SET " + col.getName() + "=? WHERE ro=? and en=?";
        try {
            if (DEBUG)
                System.out.println("sql: " + sql + "[" + col.getValue() + ", "
                        + whereColumns[Column.RO_INDEX] + ", " + whereColumns[Column.EN_INDEX]
                        + "]");
            PreparedStatement ps = DbSqlite.getConnection().prepareStatement(sql);
            ps.setString(1, col.getValue());
            ps.setString(2, whereColumns[Column.RO_INDEX]);
            ps.setString(3, whereColumns[Column.EN_INDEX]);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(String[] whereColumns) {
        String sql = "DELETE FROM translations WHERE ro=? and en=?";
        try {
            if (DEBUG)
                System.out.println("sql: " + sql);
            PreparedStatement ps = DbSqlite.getConnection().prepareStatement(sql);
            ps.setString(1, whereColumns[Column.RO_INDEX]);
            ps.setString(2, whereColumns[Column.EN_INDEX]);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
