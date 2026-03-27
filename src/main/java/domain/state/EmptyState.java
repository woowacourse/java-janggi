package domain.state;

import domain.piece.Piece;

public class EmptyState implements State {
    private static final String NOT_FOUNT_PIECE_FROM_POSITION = "[ERROR] 해당 좌표에 기물이 존재하지 않습니다.";

    @Override
    public Piece getPiece() {
        throw new IllegalArgumentException(NOT_FOUNT_PIECE_FROM_POSITION);
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}
