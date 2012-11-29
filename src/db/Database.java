package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import config.Configuration;

public class Database {

    private static final boolean DEBUG = false;

    private static Connection connect() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + Configuration.getDbPath());
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static int getMaxId() {
        String sql = "SELECT MAX(id) from translations";
        int maxId = -1;
        Connection connection = connect();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next())
                maxId = rs.getInt(1);
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxId;
    }

    public static void insert(String[] colValues) {
        String sql = "INSERT INTO translations (id, ro, en) VALUES (?, ?, ?)";
        Connection connection = connect();
        try {
            if (DEBUG)
                System.out.println("sql: " + sql);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, getMaxId() + 1);
            ps.setString(2, colValues[Column.RO_INDEX]);
            ps.setString(3, colValues[Column.EN_INDEX]);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
            connection = null;
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
        Connection connection = connect();
        try {
            if (DEBUG)
                System.out.println("sql: " + sql);
            PreparedStatement ps = connection.prepareStatement(sql);
            Column.addSqlParams(ps, values);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                map.put(rs.getString(1), rs.getString(2));
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static void update(String[] whereColumns, Column col) {
        String sql = "UPDATE translations SET " + col.getName() + "=? WHERE ro=? and en=?";
        Connection connection = connect();
        try {
            if (DEBUG)
                System.out.println("sql: " + sql);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, col.getValue());
            ps.setString(2, whereColumns[Column.RO_INDEX]);
            ps.setString(3, whereColumns[Column.EN_INDEX]);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(String[] whereColumns) {
        String sql = "DELETE FROM translations WHERE ro=? and en=?";
        Connection connection = connect();
        try {
            if (DEBUG)
                System.out.println("sql: " + sql);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, whereColumns[Column.RO_INDEX]);
            ps.setString(2, whereColumns[Column.EN_INDEX]);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
