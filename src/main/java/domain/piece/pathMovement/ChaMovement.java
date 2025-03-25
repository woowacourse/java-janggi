package domain.piece.pathMovement;

import domain.Coordinate;
import domain.MoveVector;
import domain.board.PieceSearcher;

public class ChaMovement extends UnlimitedPathMovement {

    public ChaMovement() {
        super(MoveVector.CROSS_MOVE_VECTORS);
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
}
