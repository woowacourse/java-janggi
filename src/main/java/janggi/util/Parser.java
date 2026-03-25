package janggi.util;

import janggi.domain.Point;
import janggi.dto.PositionInfo;
import java.util.List;
import java.util.stream.Stream;

public class Parser {

    private static final String COMMA = ",";

    private Parser() {
    }

    public static List<PositionInfo> parse(List<String> lines) {
        return lines.stream()
                .map(line -> {
                    List<String> data = Stream.of(line.split(COMMA))
                            .toList();
                    return PositionInfo.from(data);
                })
                .toList();
    }

    public static Point parsePoint(String point) {
        return Point.of(Integer.parseInt(point.split(COMMA)[0]),
                Integer.parseInt(point.split(COMMA)[1])
        );
    }
}
