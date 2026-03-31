package janggi.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public final class ResourceReader {

    private ResourceReader() {
    }

    public static BufferedReader getBufferedReader(String resourcePath) {
        InputStream inputStream = ResourceReader.class
                .getClassLoader()
                .getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new IllegalStateException("리소스 파일을 찾을 수 없습니다: " + resourcePath);
        }
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }
}