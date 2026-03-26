package janggi.dto;

import janggi.domain.Point;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.status.Team;
import java.util.List;

public record PositionInfo(
        Piece piece,
        Point point
) {
    public static PositionInfo from(List<String> data) {
        Team team = team(data.get(0));
        String pieceName = data.get(1);
        int x = Integer.parseInt(data.get(2));
        int y = Integer.parseInt(data.get(3));
        return new PositionInfo(
                PieceType.createPiece(team, PieceType.valueOf(pieceName)),
                Point.of(x, y)
        );
    }

    private static Team team(String team) {
        return Team.valueOf(team);
    }
}
