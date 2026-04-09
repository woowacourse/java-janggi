package db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigLoader {
    private static final Properties properties = new Properties();
    private static String configFile = "application.properties";

    static {
        loadProperties();
    }

    private ConfigLoader() {
    }

    public static void setConfigFile(String filename) {
        configFile = filename;
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream(configFile)) {
            if (input == null) {
                throw new IllegalStateException("설정 파일을 찾을 수 없습니다: " + configFile);
            }
            properties.clear();
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("설정 파일 로드에 실패했습니다.", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}

