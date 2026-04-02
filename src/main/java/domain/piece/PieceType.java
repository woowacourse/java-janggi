package domain.piece;

import domain.board.BoardState;
import domain.piece.strategy.CanonMovingCondition;
import domain.piece.strategy.ChariotMovingCondition;
import domain.piece.strategy.ElephantMovingCondition;
import domain.piece.strategy.HorseMovingCondition;
import domain.piece.strategy.MovingCondition;
import domain.piece.strategy.PalacePieceMovingCondition;
import domain.piece.strategy.PawnMovingCondition;
import domain.position.Position;

public enum PieceType {
    GENERAL("궁", new PalacePieceMovingCondition()),
    CHARIOT("차", new ChariotMovingCondition()),
    CANON("포", new CanonMovingCondition()),
    HORSE("마", new HorseMovingCondition()),
    ELEPHANT("상", new ElephantMovingCondition()),
    COUNSELOR("사", new PalacePieceMovingCondition()),
    PAWN("병", new PawnMovingCondition());

    private final String name;
    private final MovingCondition movingCondition;

    PieceType(String name, MovingCondition movingCondition) {
        this.name = name;
        this.movingCondition = movingCondition;
    }

    public String getName() {
        return name;
    }

    public boolean canMove(BoardState boardState, Position startPosition, Position endPosition) {
        return movingCondition.canMove(boardState, startPosition, endPosition);
    }
}
