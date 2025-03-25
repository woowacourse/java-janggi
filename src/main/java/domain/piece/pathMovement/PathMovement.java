package domain.piece.pathMovement;

import domain.Coordinate;
import domain.MoveVector;
import domain.board.PieceSearcher;
import domain.piece.Movement;
import java.util.Set;

public abstract class PathMovement extends Movement {

    public PathMovement(final Set<MoveVector> moveVectors) {
        super(moveVectors);
    }

    @Override
    public boolean canMove(
        final Coordinate departure,
        final Coordinate arrival,
        final PieceSearcher pieceSearcher
    ) {
        final var path = findPath(departure, arrival);
        final var coordinates = path.coordinates();

        return path.isReachable() && pieceSearcher.nonePiecesIn(coordinates);
    }

    protected abstract Path findPath(Coordinate departure, Coordinate arrival);
}
