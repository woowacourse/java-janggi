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

    public Position move(Position destination, Pieces pieces, Team team) {
        return moveStrategy.move(destination, pieces, team);
    }

    public boolean isSameType(PieceType comparePieceType) {
        return pieceType.equals(comparePieceType);
    }

    public Route getRoute(Position selectPiecePosition, Position movePosition) {
        return moveStrategy.getLegalRoute(selectPiecePosition, movePosition, Team.BLUE);
    }

    public String getType() {
        return pieceType.getType();
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
