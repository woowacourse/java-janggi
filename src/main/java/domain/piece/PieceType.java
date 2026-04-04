package domain.piece;

import domain.piece.strategy.CanonMovingCondition;
import domain.piece.strategy.ChariotMovingCondition;
import domain.piece.strategy.PalaceMovingCondition;
import domain.piece.strategy.ElephantMovingCondition;
import domain.piece.strategy.HorseMovingCondition;
import domain.piece.strategy.MovingCondition;
import domain.piece.strategy.PawnMovingCondition;

public enum PieceType {
    GENERAL("궁", new PalaceMovingCondition(), 0),
    CHARIOT("차", new ChariotMovingCondition(), 13),
    CANON("포", new CanonMovingCondition(), 7),
    HORSE("마", new HorseMovingCondition(), 5),
    ELEPHANT("상", new ElephantMovingCondition(), 3),
    COUNSELOR("사", new PalaceMovingCondition(), 3),
    PAWN("병", new PawnMovingCondition(), 2);

    private final String name;
    private final MovingCondition movingCondition;
    private final int score;

    PieceType(String name, MovingCondition movingCondition, int score) {
        this.name = name;
        this.movingCondition = movingCondition;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public MovingCondition getMovingCondition() {
        return movingCondition;
    }
}
