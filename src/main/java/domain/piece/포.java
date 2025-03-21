package domain.piece;

import domain.pattern.포Path;
import domain.piece.state.Moved포;

public class 포 extends Piece {
    public 포(Side side) {
        super(7, side, new 포Path(), new Moved포());
    }

    @Override
    public void captureIfNotMySide(Piece piece) {
        if (piece.getState() instanceof Moved포) {
            throw new IllegalStateException("포는 포를 잡을 수 없습니다.");
        }
    }
}
