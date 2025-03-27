package janggi.domain.movement.pathless;

import janggi.domain.Coordinate;
import janggi.domain.board.PieceSearcher;
import janggi.domain.movement.MoveStep;
import janggi.domain.movement.MoveVector;
import java.util.Set;

public class ByeongMovement extends PathlessMovement {

    public ByeongMovement() {
        super(Set.of(
            new MoveVector(MoveStep.LEFT),
            new MoveVector(MoveStep.RIGHT),
            new MoveVector(MoveStep.DOWN)
        ));
    }

    @Override
    public boolean canMove(
        final Coordinate departure,
        final Coordinate arrival,
        final PieceSearcher pieceSearcher
    ) {
        return moveVectorsAt(departure).stream()
            .map(MoveVector::first)
            .filter(MoveStep::isDownDirection)
            .filter(departure::canMove)
            .map(departure::move)
            .anyMatch(arrival::equals);
    }
}
