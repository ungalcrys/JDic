package config;

import java.io.File;

public class Configuration {
    private static final boolean DEBUG = false;

    public static final String APP_NAME = "JDic";

    private static String sConfigDirPath;

    public static void initConfig() {
        createConfigDir();
    }

    private static void createConfigDir() {
        String string = System.getProperty("user.home") + "/." + APP_NAME;
        if (DEBUG)
            System.out.println("config dir: " + string);
        File file = new File(string);
        if (!file.exists())
            file.mkdirs();
        sConfigDirPath = string;
    }

    public static String getDbPath() {
        String string = sConfigDirPath + "/dictionary.db";
        if (DEBUG) {
            System.out.println("db path: " + string);
        }
        return string;
    }
}
