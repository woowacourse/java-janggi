package janggi.presentation.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    private FileReader() {
    }

    public static List<String> readFile(String resourcePath) {
        try (BufferedReader reader = createReader(resourcePath)) {
            return readLines(reader);
        } catch (IOException e) {
            throw new IllegalStateException("[ERROR] 파일을 찾을 수 없습니다.");
        }
    }

    private static BufferedReader createReader(String resourcePath) {
        InputStream inputStream = FileReader.class.getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new IllegalStateException("[ERROR] 파일을 찾을 수 없습니다.");
        }
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    private static List<String> readLines(BufferedReader reader) throws IOException {
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }
}
