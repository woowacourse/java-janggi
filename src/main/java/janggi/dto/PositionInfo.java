package janggi.dto;

import janggi.domain.status.Team;
import java.util.List;

public record PositionInfo(
        Team team,
        String pieceName,
        int x,
        int y
) {
    public static PositionInfo from(List<String> data) {
        return new PositionInfo(
                team(data.get(0)),
                data.get(1),
                Integer.parseInt(data.get(2)),
                Integer.parseInt(data.get(3)));
    }

    private static Team team(String team) {
        return Team.valueOf(team);
    }
}
