
package config;

import java.io.File;

public class Configuration {
    private static final boolean DEBUG = true;

    public static final String APP_NAME = "JDic";

    private static String dbPath;

    public static String getDbPath() {
        if (dbPath == null) {
            String cofigDirPath = System.getProperty("user.home") + "/." + APP_NAME;
            if (DEBUG)
                System.out.println("config dir: " + cofigDirPath);
            File file = new File(cofigDirPath);
            if (!file.exists())
                file.mkdirs();
            dbPath = cofigDirPath + "/dictionary.db";
        }
        if (DEBUG) {
            System.out.println("db path: " + dbPath);
        }
        return dbPath;
    }
}
