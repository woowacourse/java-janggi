package janggi.domain.piece;

import janggi.domain.board.BoardSnapshot;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Position;
import java.util.List;

public record Piece(
        Dynasty dynasty,
        PieceType pieceType
) {

    public List<Position> canMovePosition(BoardSnapshot board, Position from) {
        return pieceType.moveStrategy().canMovePositions(board, from, dynasty);
    }

    public boolean isSameDynasty(Dynasty dynasty) {
        return this.dynasty.equals(dynasty);
    }

}
