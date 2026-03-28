package pieces;

import movepolicy.MoveContext;
import participant.Turn;
import position.Position;

public record EmptyPiece() implements Piece {

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public MoveContext askMoveContext(Position departure, Position destination, Turn turn) {
        throw new IllegalArgumentException("해당 위치에 기물이 없습니다.");
    }
}
