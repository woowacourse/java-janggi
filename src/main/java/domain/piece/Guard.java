package domain.piece;

import domain.pattern.GuardPath;
import domain.piece.state.MovedGuard;

public class Guard extends Piece {
    public Guard(Side side) {
        super(3, side, new GuardPath(), new MovedGuard(side));
    }
}
