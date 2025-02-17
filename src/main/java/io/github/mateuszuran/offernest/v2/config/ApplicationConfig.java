package io.github.mateuszuran.offernest.v2.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ApplicationConfig {
    private final String CONFIG_FILE = "config.properties";
    private final String DIRECTORY_URL = "directory_url";
    private final Properties props = new Properties();

    public boolean configFileExists() {
        return new File(CONFIG_FILE).exists();
    }

    public String readApplicationConfig() {
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

    public void saveApplicationConfig(String directory) {
        props.setProperty(DIRECTORY_URL, directory);

        try (FileOutputStream output = new FileOutputStream(CONFIG_FILE)) {
            props.store(output, "Application config");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
