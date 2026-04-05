package janggi.presentation.dto;

import janggi.domain.point.Point;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceFactory;
import janggi.domain.piece.PieceType;
import janggi.domain.status.Team;

public record PositionInfo(
        Piece piece,
        Point point
) {
    public static PositionInfo from(Team team, String pieceName, int column, int row) {
        return new PositionInfo(
                PieceFactory.createPiece(team, PieceType.valueOf(pieceName)),
                Point.of(column, row)
        );
    }
}
