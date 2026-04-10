package janggi.domain.piece;

import janggi.domain.piece.diagonal.ElephantMoveStrategy;
import janggi.domain.piece.diagonal.HorseMoveStrategy;
import janggi.domain.piece.line.CannonMoveStrategy;
import janggi.domain.piece.line.ChariotMoveStrategy;
import janggi.domain.piece.single.PalaceMoveStrategy;
import janggi.domain.piece.single.SoldierMoveStrategy;

public enum PieceType {

    GENERAL(PalaceMoveStrategy.instance(), 0),
    CHARIOT(ChariotMoveStrategy.instance(), 13),
    CANNON(CannonMoveStrategy.instance(), 7),
    HORSE(HorseMoveStrategy.instance(), 5),
    ELEPHANT(ElephantMoveStrategy.instance(), 3),
    GUARD(PalaceMoveStrategy.instance(), 3),
    SOLDIER(SoldierMoveStrategy.instance(), 2);

    private final MoveStrategy moveStrategy;
    private final int points;

    PieceType(MoveStrategy moveStrategy, int points) {
        this.moveStrategy = moveStrategy;
        this.points = points;
    }

    public MoveStrategy moveStrategy() {
        return moveStrategy;
    }

    public int points() {
        return points;
    }

}
