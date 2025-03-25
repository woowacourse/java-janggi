package domain.piece.pathMovement;

import domain.Coordinate;
import domain.MoveVector;
import domain.board.PieceSearcher;
import domain.piece.Piece;

public class PoMovement extends UnlimitedPathMovement {

    public PoMovement() {
        super(MoveVector.CROSS_MOVE_VECTORS);
    }

    @Override
    public boolean canMove(
        final Coordinate departure,
        final Coordinate arrival,
        final PieceSearcher pieceSearcher
    ) {
        final var path = findPath(departure, arrival);

        final var piecesInPath = pieceSearcher.findPiecesIn(path.coordinates());
        if (piecesInPath.size() != 1) {
            return false;
        }

        final var podari = piecesInPath.getFirst();
        if (podari.isPo()) {
            return false;
        }

        final var isArrivalPo = pieceSearcher.findAt(arrival)
            .map(Piece::isPo)
            .orElse(false);
        return !isArrivalPo;
    }
}
