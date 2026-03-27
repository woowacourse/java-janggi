package janggi.dto;

import janggi.domain.Point;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.status.Team;

public record PositionInfo(
        Piece piece,
        Point point
) {
    public static PositionInfo from(Team team, String pieceName, int x, int y) {
        return new PositionInfo(
                PieceType.createPiece(team, PieceType.valueOf(pieceName)),
                Point.of(x, y)
        );
    }
}
