package db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ConfigLoader {

    private final Properties properties;
    private final String configFile;

    public ConfigLoader(String configFile) {
        this.configFile = configFile;
        this.properties = new Properties();
        loadProperties();
    }

    public ConfigLoader() {
        this("application.properties");
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(configFile)) {
            if (input == null) {
                throw new IllegalStateException("설정 파일을 찾을 수 없습니다: " + configFile);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException(configFile + " 설정 파일 로드에 실패했습니다.", e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}