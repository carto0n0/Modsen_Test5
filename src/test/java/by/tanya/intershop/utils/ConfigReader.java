package by.tanya.intershop.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ConfigReader {
    private static final Properties props = new Properties();
    private static boolean isLoaded = false;

    private ConfigReader() {
    }

    public static synchronized void load() {
        if (!isLoaded) {
            try (InputStream input = ConfigReader
                    .class
                    .getClassLoader()
                    .getResourceAsStream(
                            "config.properties"
                    )) {
                if (input == null) {
                    throw new RuntimeException("Файл config.properties не найден в resources!");
                }
                props.load(new InputStreamReader(input, StandardCharsets.UTF_8));
                isLoaded = true;
            } catch (IOException e) {
                throw new RuntimeException("Ошибка при чтении config.properties", e);
            }
        }
    }

    public static String get(String key) {
        if (!isLoaded) load();
        return props.getProperty(key);
    }
}
