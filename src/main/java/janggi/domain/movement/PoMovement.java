package janggi.domain.movement;

import janggi.domain.Coordinate;
import janggi.domain.Piece;
import janggi.domain.board.PieceSearcher;

public class PoMovement implements Movement {

    private final SeveralMovement severalMovement;

    public PoMovement(SeveralMovement severalMovement) {
        this.severalMovement = severalMovement;
    }

    @Override
    public boolean canMove(
        final Coordinate departure,
        final Coordinate arrival,
        final PieceSearcher pieceSearcher
    ) {
        final var path = severalMovement.findPath(departure, arrival);

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
