package db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import resources.ResourceLoader;
import config.Configuration;

public class DbSqlite {

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            // load driver
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            // check if database exists
            boolean mustCreateTables = false;
            String dbPath = Configuration.getDbPath();
            if (!new File(dbPath).exists())
                mustCreateTables = true;

            try {
                System.out.println(dbPath);
                // create db connection
                connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
                connection.setAutoCommit(true);

                // create tables
                if (mustCreateTables) {
                    String sql = ResourceLoader.getText("create.sql");
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.execute();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void dispose() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
