package domain.piece;

import domain.Coordinate;
import domain.MoveVector;
import domain.board.PieceSearcher;
import java.util.Set;

public abstract class Movement {

    private final Set<MoveVector> moveVectors;

    public Movement(Set<MoveVector> moveVectors) {
        this.moveVectors = moveVectors;
    }

    public abstract boolean canMove(Coordinate departure, Coordinate arrival, PieceSearcher pieceSearcher);

    protected final Set<MoveVector> moveVectors() {
        return moveVectors;
    }
}
