package janggi.domain.piece;

import janggi.domain.board.BoardSnapshot;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Position;
import java.util.List;

public record Piece(
        Dynasty dynasty,
        PieceType pieceType
) {

    public List<Position> placeablePositions(BoardSnapshot board, Position from) {
        return pieceType.moveStrategy().findPlaceablePositions(board, from, dynasty);
    }

    public boolean isSame(Dynasty dynasty) {
        return this.dynasty.equals(dynasty);
    }

    public boolean isSame(PieceType pieceType) {
        return this.pieceType.equals(pieceType);
    }

}
