package janggi;

import janggi.dto.PositionInfo;
import janggi.util.FileParser;
import java.util.List;

public class JanggiApplication {
    public static void main(String[] args) {
        List<PositionInfo> positionInfos = FileParser.readCsvFile("src/main/resources/janggi.csv");


    }
}
