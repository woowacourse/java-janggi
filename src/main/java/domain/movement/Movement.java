package domain.movement;

import domain.Coordinate;
import domain.board.PieceSearcher;
import java.util.Set;

public abstract class Movement {

    private final Set<MoveVector> moveUnits;

    public Movement(Set<MoveVector> moveUnits) {
        this.moveUnits = moveUnits;
    }

    public abstract boolean canMove(Coordinate departure, Coordinate arrival, PieceSearcher pieceSearcher);

    protected final Set<MoveVector> moveVectors() {
        return moveUnits;
    }
}
