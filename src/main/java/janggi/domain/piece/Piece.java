package janggi.domain.piece;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Position;
import java.util.List;
import java.util.Map;

public record Piece(
        Dynasty dynasty,
        MoveStrategy moveStrategy
) {

    public List<Position> canMovePosition(Map<Position, Piece> board, Position from) {
        return moveStrategy.findMovablePositions(board, from, dynasty);
    }

    public PieceType pieceType() {
        return moveStrategy.pieceType();
    }

    public boolean isSameDynasty(Dynasty dynasty) {
        return this.dynasty.equals(dynasty);
    }

}
