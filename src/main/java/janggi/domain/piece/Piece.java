package janggi.domain.piece;

import janggi.domain.board.BoardSnapshot;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Position;
import java.util.List;

public class Piece {

    private final Dynasty dynasty;
    private final PieceType pieceType;

    public Piece(Dynasty dynasty, PieceType pieceType) {
        this.dynasty = dynasty;
        this.pieceType = pieceType;
    }

    public List<Position> canMovePosition(BoardSnapshot board, Position from) {
        return pieceType.moveStrategy().findPlaceablePositions(board, from, dynasty);
    }

    public boolean isSameDynasty(Dynasty dynasty) {
        return this.dynasty.equals(dynasty);
    }

    public boolean isSamePieceType(PieceType pieceType) {
        return this.pieceType.equals(pieceType);
    }

    public Dynasty dynasty() {
        return dynasty;
    }

    public PieceType pieceType() {
        return pieceType;
    }
}
