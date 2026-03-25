package janggi.dto;

import janggi.domain.Point;
import janggi.domain.status.Team;
import java.util.List;

public record PositionInfo(
        Team team,
        String pieceName,
        Point point
) {
    public static PositionInfo from(List<String> data) {
        int x = Integer.parseInt(data.get(2));
        int y = Integer.parseInt(data.get(3));
        Team team = team(data.get(0));
        String pieceName = data.get(1);
        return new PositionInfo(
                ,
                Point.of(x, y)
        );
    }

    private static Team team(String team) {
        return Team.valueOf(team);
    }
}
