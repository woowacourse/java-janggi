package janggi.util;

import janggi.domain.Point;
import janggi.domain.status.Team;
import janggi.presentation.dto.PositionInfo;
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
                    Team team = Team.valueOf(data.get(0));
                    String pieceName = data.get(1);
                    int x = Integer.parseInt(data.get(2));
                    int y = Integer.parseInt(data.get(3));
                    return PositionInfo.from(team, pieceName, x, y);
                })
                .toList();
    }

    public static Point parsePoint(String point) {
        return Point.of(Integer.parseInt(point.split(COMMA)[0].trim()),
                Integer.parseInt(point.split(COMMA)[1].trim())
        );
    }
}
