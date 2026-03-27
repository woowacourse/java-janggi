package janggi.util;

import janggi.presentation.dto.PositionInfo;
import java.util.List;

public class FileParser {

    private FileParser() {
    }

    public static List<PositionInfo> readCsvFile(String filePath) {
        List<String> lines = FileReader.readFile(filePath);
        return Parser.parse(lines);
    }
}
