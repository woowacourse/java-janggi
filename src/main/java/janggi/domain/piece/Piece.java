package janggi.domain.piece;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Position;
import java.util.List;
import java.util.Map;

public record Piece(
        Dynasty dynasty,
        PieceType pieceType
) {

    public List<Position> canMovePosition(Map<Position, Piece> board, Position from) {
        return pieceType.moveStrategy().canMovePositions(board, from, dynasty);
    }

    public boolean isSameDynasty(Dynasty dynasty) {
        return this.dynasty.equals(dynasty);
    }

}
