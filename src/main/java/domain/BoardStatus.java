package domain;

import domain.piece.Piece;
import domain.position.Position;
import java.util.Map;

public record BoardStatus(
        Map<Position, Piece> status
) {
    public static BoardStatus from(Map<Position, Piece> boardStatus) {
        return new BoardStatus(boardStatus);
    }
}
