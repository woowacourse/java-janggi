package janggi.util;

import java.util.List;

public class FileParser {

    private FileParser() {
    }

    public static void readCsvFile(String filePath) {
        List<String> lines = FileReader.readFile(filePath);
        return Parser.parse(lines);
    }
}
