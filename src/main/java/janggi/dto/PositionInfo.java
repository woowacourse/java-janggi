package janggi.dto;

import janggi.domain.Point;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceFactory;
import janggi.domain.piece.PieceType;
import janggi.domain.status.Team;
import java.util.List;

public record PositionInfo(
        Piece piece,
        Point point
) {
    public static PositionInfo from(List<String> data) {
        return from(
                Team.valueOf(data.get(0)),
                data.get(1),
                Integer.parseInt(data.get(2)),
                Integer.parseInt(data.get(3))
        );
    }

    public static PositionInfo from(Team team, String pieceName, int x, int y) {
        return new PositionInfo(
                PieceFactory.initPiece(team, PieceType.valueOf(pieceName)),
                Point.of(x, y)
        );
    }
}
