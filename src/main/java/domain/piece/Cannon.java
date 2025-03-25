package domain.piece;

import domain.pattern.CannonPath;
import domain.piece.state.MovedCannon;

public class Cannon extends Piece {
    public Cannon(Side side) {
        super(7, side, new CannonPath(), new MovedCannon());
    }

    @Override
    public void captureIfNotMySide(Piece piece) {
        if (piece.getState() instanceof MovedCannon) {
            throw new IllegalArgumentException("포는 포를 잡을 수 없습니다.");
        }
    }
}
