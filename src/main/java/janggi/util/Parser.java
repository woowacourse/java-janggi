package janggi.util;

import janggi.domain.Point;
import janggi.domain.status.Team;
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

                    return PositionInfo.from(
                            Team.valueOf(data.get(0)),
                            data.get(1),
                            Integer.parseInt(data.get(2)),
                            Integer.parseInt(data.get(3))
                    );
                })
                .toList();
    }

    public static Point parsePoint(String point) {
        return Point.of(
                Integer.parseInt(point.split(COMMA)[0]),
                Integer.parseInt(point.split(COMMA)[1])
        );
    }
}
