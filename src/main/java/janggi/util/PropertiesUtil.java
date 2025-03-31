package janggi.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = PropertiesUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException("db.properties 파일을 찾을 수 없습니다.");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("db.properties 로드 중 오류 발생", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}

