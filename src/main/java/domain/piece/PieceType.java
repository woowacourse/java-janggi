package domain.piece;

import domain.piece.strategy.*;

public enum PieceType {
    GENERAL(new GeneralAndGuardStrategy(), 0),
    GUARD(new GeneralAndGuardStrategy(), 3),
    HORSE(new HorseStrategy(), 5),
    ELEPHANT(new ElephantStrategy(), 3),
    CHARIOT(new ChariotStrategy(), 13),
    CANNON(new CannonStrategy(), 7),
    SOLDIER(new SoldierStrategy(), 2);

    private final MoveStrategy moveStrategy;
    private final int score;

    PieceType(MoveStrategy moveStrategy, int score) {
        this.moveStrategy = moveStrategy;
        this.score = score;
    }

    public MoveStrategy getMoveStrategy() {
        return moveStrategy;
    }

    public int score() {
        return score;
    }
}
