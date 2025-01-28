package io.github.mateuszuran.offernest.config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static final String CONFIG_FILE = "config.properties";

    public static String readDirectory() {
        Properties props = new Properties();
        try (FileInputStream input = new FileInputStream(CONFIG_FILE)) {
            props.load(input);
            return props.getProperty("dirUri");
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void saveDirectory(String directory) {
        Properties props = new Properties();
        props.setProperty("dirUri", directory);

        try (FileOutputStream output = new FileOutputStream(CONFIG_FILE)) {
            props.store(output, "Application config");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
