package janggi.domain.movement;

import janggi.domain.Coordinate;
import janggi.domain.board.PieceSearcher;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Movement {

    private final Set<MoveVector> moveVectors;

    public Movement(Set<MoveVector> moveVectors) {
        this.moveVectors = moveVectors;
    }

    public abstract boolean canMove(Coordinate departure, Coordinate arrival, PieceSearcher pieceSearcher);

    protected final Set<MoveVector> moveVectorsAt(Coordinate coordinate) {
        if (coordinate.isInCastle()) {
            return addDiagonalMoveVectorsAt(coordinate);
        }
        return moveVectors;
    }

    private Set<MoveVector> addDiagonalMoveVectorsAt(final Coordinate coordinate) {
        final var connections = coordinate.findCastleConnections();
        final var moveVectors = connections.stream()
            .map(coordinate::computeMoveUnitToArrival)
            .map(moveUnit -> new MoveVector(moveUnit, moveUnit))
            .collect(Collectors.toSet());

        moveVectors.addAll(this.moveVectors);
        return moveVectors;
    }
}
