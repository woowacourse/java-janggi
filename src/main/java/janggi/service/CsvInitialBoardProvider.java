package janggi.service;

import janggi.dto.PositionInfo;
import janggi.util.FileParser;
import java.util.List;

public class CsvInitialBoardProvider implements InitialBoardProvider {

    @Override
    public List<PositionInfo> load() {
        return FileParser.readCsvFile("/janggi.csv");
    }
}
