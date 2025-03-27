package domain.piece.state;

import domain.piece.Piece;
import java.util.List;

public abstract class Moved implements PieceState {
    @Override
    public PieceState captured() {
        return new Captured();
    }

    @Override
    public void validateMove(List<Piece> hurdlePieces) {

        if (!hurdlePieces.isEmpty()) {
            throw new IllegalArgumentException("경로에 장애물이 있어서 기물을 움직일 수 없습니다.");
        }
    }
}
