package object.piece;

import java.util.Objects;
import object.Coordinate;
import object.Route;
import object.strategy.MoveStrategy;

public class MoveRule {

    private final MoveStrategy moveStrategy;
    private final PieceType pieceType;

    public MoveRule(MoveStrategy moveStrategy, PieceType pieceType) {
        this.moveStrategy = moveStrategy;
        this.pieceType = pieceType;
    }

    public Coordinate move(Coordinate destination, Pieces onRoutePieces, Team team) {
        return moveStrategy.move(destination, onRoutePieces, team);
    }

    public Route getLegalRoute(Coordinate startPosition, Coordinate destination, Team team) {
        return moveStrategy.getLegalRoute(startPosition, destination, team);
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
