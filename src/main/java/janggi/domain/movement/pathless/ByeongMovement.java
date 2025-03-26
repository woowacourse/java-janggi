package janggi.domain.movement.pathless;

import janggi.domain.Coordinate;
import janggi.domain.board.PieceSearcher;
import janggi.domain.movement.MoveUnit;
import janggi.domain.movement.MoveVector;
import java.util.Set;

public class ByeongMovement extends PathlessMovement {

    public ByeongMovement() {
        super(Set.of(
            new MoveVector(MoveUnit.LEFT),
            new MoveVector(MoveUnit.RIGHT),
            new MoveVector(MoveUnit.DOWN)
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
            .filter(MoveUnit::isDownDirection)
            .filter(departure::canMove)
            .map(departure::move)
            .anyMatch(arrival::equals);
    }
}
