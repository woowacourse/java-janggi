package domain.board;

import domain.piece.PieceType;
import domain.point.Point;
import domain.team.Team;

public record IntersectionState(
        Point point,
        PieceType pieceType,
        Team team
) {
}
