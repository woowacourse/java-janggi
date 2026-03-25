package janggi.dto;

import janggi.domain.Point;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceFactory;
import janggi.domain.status.Team;
import java.util.List;

public record PositionInfo(
        Piece piece,
        Point point
) {
    public static PositionInfo from(List<String> data) {
        int x = Integer.parseInt(data.get(2));
        int y = Integer.parseInt(data.get(3));
        Team team = team(data.get(0));
        String pieceName = data.get(1);
        return new PositionInfo(
                PieceFactory.of(team, pieceName),
                Point.of(x, y)
        );
    }

    private static Team team(String team) {
        return Team.valueOf(team);
    }
}
