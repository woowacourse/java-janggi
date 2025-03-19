package piece;

import java.util.Objects;
import strategy.MoveStrategy;

public class MoveRule {

    private final MoveStrategy moveStrategy;
    private final PieceType pieceType;

    public MoveRule(MoveStrategy moveStrategy, PieceType pieceType) {
        this.moveStrategy = moveStrategy;
        this.pieceType = pieceType;
    }

    public Position move(Position position, MoveRule moveRule, Team team) {
//        return moveStrategy.move();
        return null;
    }

    public boolean isSameType(PieceType comparePieceType) {
        return pieceType.equals(comparePieceType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MoveRule moveRule = (MoveRule) o;
        return Objects.equals(moveStrategy, moveRule.moveStrategy) && pieceType == moveRule.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(moveStrategy, pieceType);
    }
}
