package domain.piece;

import domain.piece.strategy.CanonMovingCondition;
import domain.piece.strategy.ChariotMovingCondition;
import domain.piece.strategy.ElephantMovingCondition;
import domain.piece.strategy.HorseMovingCondition;
import domain.piece.strategy.MovingCondition;
import domain.piece.strategy.PalacePieceMovingCondition;
import domain.piece.strategy.PawnMovingCondition;

public enum PieceType {
    GENERAL("궁", new PalacePieceMovingCondition(), 0.0),
    CHARIOT("차", new ChariotMovingCondition(), 13.0),
    CANON("포", new CanonMovingCondition(), 7.0),
    HORSE("마", new HorseMovingCondition(), 5.0),
    ELEPHANT("상", new ElephantMovingCondition(), 3.0),
    COUNSELOR("사", new PalacePieceMovingCondition(), 3.0),
    PAWN("병", new PawnMovingCondition(), 2.0);

    private final String name;
    private final MovingCondition movingCondition;
    private final double pieceValue;

    PieceType(String name, MovingCondition movingCondition, double pieceValue) {
        this.name = name;
        this.movingCondition = movingCondition;
        this.pieceValue = pieceValue;
    }

    public String getName() {
        return name;
    }

    public MovingCondition getMovingCondition() {
        return movingCondition;
    }

    public double getPieceValue() {
        return pieceValue;
    }
}
