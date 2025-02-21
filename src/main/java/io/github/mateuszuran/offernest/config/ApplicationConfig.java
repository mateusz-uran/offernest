package io.github.mateuszuran.offernest.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ApplicationConfig {
    private final static String CONFIG_FILE = "config.properties";
    private final static String DIRECTORY_URL = "directory_url";
    private final static Properties props = new Properties();

    /**
     * Check if config,properties file exists.
     * */
    public static boolean configFileExists() {
        File configFile = new File(CONFIG_FILE);
        return configFile.exists() && configFile.length() > 0;
    }

    /**
     * Read main directory path from config.properties file.
     * */
    public static String readApplicationConfig() {
        if (!configFileExists()) {
            System.out.println("Config file not found.");
            return null;
        }

        try (FileInputStream input = new FileInputStream(CONFIG_FILE)) {
            props.load(input);
            return props.getProperty(DIRECTORY_URL);
        } catch (IOException e) {
            System.out.println("Error during reading config file. Message: " + e.getMessage());
            return null;
        }
    }

    /**
     * Create config.properties file and save path to main directory.
     * */
    public static String saveApplicationConfig(String directory) {
        props.setProperty(DIRECTORY_URL, directory);

        try (FileOutputStream output = new FileOutputStream(CONFIG_FILE)) {
            props.store(output, "Application config");
            return directory;
        } catch (IOException e) {
            System.err.println("Error while adding path to config: " + e.getMessage());
            return null;
        }

    }
}
