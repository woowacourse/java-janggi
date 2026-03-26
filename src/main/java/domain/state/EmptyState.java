package domain.state;

import domain.piece.Piece;

public class EmptyState implements State {
    @Override
    public Piece getPiece() {
        throw new IllegalStateException("[ERROR] 해당 좌표에 기물이 존재하지 않습니다.");
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}
