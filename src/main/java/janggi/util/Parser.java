package janggi.util;

import janggi.dto.PositionInfo;
import java.util.List;
import java.util.stream.Stream;

public class Parser {

    private Parser() {
    }

    public static List<PositionInfo> parse(List<String> lines) {
        return lines.stream()
                .map(line -> {
                    List<String> data = Stream.of(line.split(","))
                            .toList();
                    return PositionInfo.from(data);
                })
                .toList();
    }
}
