package janggi.domain.movement.path;

import janggi.domain.Coordinate;
import janggi.domain.movement.InfiniteMoveProcess;
import janggi.domain.movement.MoveStep;
import janggi.domain.board.PieceSearcher;
import janggi.domain.Piece;
import java.util.Set;

public class PoMovement extends PathMovement {

    public PoMovement() {
        super(Set.of(
            new InfiniteMoveProcess(MoveStep.LEFT),
            new InfiniteMoveProcess(MoveStep.RIGHT),
            new InfiniteMoveProcess(MoveStep.UP),
            new InfiniteMoveProcess(MoveStep.DOWN)
        ));
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
