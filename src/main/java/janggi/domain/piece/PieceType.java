package janggi.domain.piece;

import janggi.domain.board.Dynasty;
import java.util.function.Function;

public enum PieceType {
    GENERAL(0, General::new),
    CHARIOT(13, Chariot::new),
    CANNON(7, Cannon::new),
    HORSE(5, Horse::new),
    GUARD(3, Guard::new),
    ELEPHANT(3, Elephant::new),
    HAN_SOLIDER(2, HanSoldier::new),
    CHU_SOLIDER(2, ChuSoldier::new)
    ;

    private final int score;
    private final Function<Dynasty, Piece> function;

    PieceType(int score, Function<Dynasty, Piece> function) {
        this.score = score;
        this.function = function;
    }

    public Piece from(Dynasty dynasty) {
        return this.function.apply(dynasty);
    }

    public int getScore() {
        return score;
    }
}
