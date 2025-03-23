package piece;

import java.util.Objects;
import move.MoveBehavior;

public class MoveRule {

    private final MoveBehavior moveBehavior;
    private final PieceType pieceType;

    public MoveRule(MoveBehavior moveBehavior, PieceType pieceType) {
        this.moveBehavior = moveBehavior;
        this.pieceType = pieceType;
    }

    public Position move(Position destination, Pieces pieces, Team team) {
        return moveBehavior.move(destination, pieces, team);
    }

    public boolean isSameType(PieceType comparePieceType) {
        return pieceType.equals(comparePieceType);
    }

    public Route getRoute(Position selectPiecePosition, Position movePosition, Team team) {
        return moveBehavior.getLegalRoute(selectPiecePosition, movePosition, team);
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
        return pieceType == moveRule.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(moveBehavior, pieceType);
    }
}
